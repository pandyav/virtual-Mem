import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

public class FixedPartition {

	LinkedList<Partition> l;
	Queue<Job> j;

	Queue<Job> waiting = new LinkedList<Job>();

	Queue<Job> finish = new LinkedList<Job>();

	String o1,o2;

	public FixedPartition(LinkedList<Partition> l, Queue<Job> j,String o1, String o2) {
		this.l = l;
		this.j = j;
		this.o1=o1;
		this.o2=o2;

	}

	private boolean areAllBusy()
	{
		ListIterator<Partition> lt2 = l.listIterator();
		while(lt2.hasNext())
		{
			if(lt2.next().getJob().size()>0)
				return true;
		}
		return false;
	}

	public void finish()
	{

		while(areAllBusy())
		{
			ListIterator<Partition> lt2 = l.listIterator();
			while(lt2.hasNext())
			{
				j.addAll(waiting);
				waiting.clear();
				Partition p = lt2.next();

				ListIterator<Job> lp = p.ListIter();

				while(lp.hasNext())
				{
					Job j1 = lp.next();
					j1.setStatus("Leaving");
					finish.offer(j1);
					lp.remove();



				}
				p.setStatus("Free");
				p.setOccupiedSize(0);
				if(!j.isEmpty())
				{
					if(o1.equals("fixed")&&o2.equals("first"))
						ApplyFixed();
					else if(o1.equals("fixed")&&o2.equals("best"))
						ApplyBestFit();
					else if(o1.equals("dynamic")&&o2.equals("first"))
						ApplyDynamicFirstFit();
					else if(o1.equals("dynamic")&&o2.equals("best"))
						ApplyDynamicBestFit();
				}


			}
		}

		while(!finish.isEmpty())
			System.out.println(finish.peek().getJobName() +" - "+finish.remove().getStatus());
	}

	public void ApplyFixed() {
		int counter;
		System.out.println("Applying First-Fit to Fixed Partition.....");
		do {
			out: while (!j.isEmpty()) {
				if (j.peek().getJobSize() > l.get(getLargestPartIndex())
						.getPartitionSize()) {
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Rejected"));
					System.out
					.println(j.remove().getJobName()
							+ " was rejected. Job size greater than largest partition");

				} else {
					counter = 1;
					while (counter <= l.size()) {
						if (j.peek().getJobSize() > l.get(counter - 1)
								.getPartitionSize())
							counter++;
						else {
							if (l.get(counter - 1).getStatus().equals("Free")) {
								l.get(counter - 1).setJobFixed(j.peek());
								l.get(counter - 1).setStatus("Busy");

								finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Arrives"));
								System.out.println(j.remove().getJobName()
										+ " loaded in memory");
								break out;
							} else
								counter++;
						}
					}
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Waiting"));
					System.out.println("No Partitions available at this time '"
							+ j.peek().getJobName()
							+ "' will be put in a waiting queue");
					waiting.offer(j.remove());
				}
			}
		} while (!j.isEmpty());

	}

	public void ApplyBestFit() {
		int memory_block = 300;
		int initial_Mem_waste;
		int memoryWaste;
		int subscript;
		int counter;
		System.out.println("Applying Best-Fit to Fixed Partition.......");
		while (!j.isEmpty()) {

			if (j.peek().getJobSize() > l.get(getLargestPartIndex())
					.getPartitionSize()) {
				finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Rejected"));
				System.out
				.println(j.remove().getJobName()
						+ " was rejected. Job size greater than largest partition");

			} else {
				initial_Mem_waste = memory_block - j.peek().getJobSize();
				subscript = 0;
				counter = 1;

				while (counter <= l.size()) {
					if (j.peek().getJobSize() > l.get(counter - 1)
							.getPartitionSize()
							|| l.get(counter - 1).getStatus().equals("Busy"))
						counter++;
					else {
						memoryWaste = l.get(counter - 1).getPartitionSize()
								- j.peek().getJobSize();

						if (initial_Mem_waste > memoryWaste)
							subscript = counter;

						initial_Mem_waste = memoryWaste;
						counter++;

					}

				}
				if (subscript == 0) {
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Waiting"));
					System.out.println("No Partitions available at this time '"
							+ j.peek().getJobName()
							+ "' will be put in a waiting queue");
					waiting.offer(j.remove());
				} else {
					l.get(subscript - 1).setJobFixed(j.peek());
					l.get(subscript - 1).setStatus("Busy");
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Arrives"));
					System.out.println(j.remove().getJobName()
							+ " loaded in memory");
				}
			}
		}

	}

	public void ApplyDynamicFirstFit() {

		int counter;
		System.out.println("Applying First-Fit to Dynamic Partition.....");
		do {
			out: while (!j.isEmpty()) {
				if (j.peek().getJobSize() > l.get(getLargestPartIndex())
						.getPartitionSize()) {
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Rejected"));
					System.out
					.println(j.remove().getJobName()
							+ " was rejected. Job size greater than largest partition");

				} else {
					counter = 1;
					while (counter <= l.size()) {
						if (j.peek().getJobSize() > l.get(counter - 1)
								.getPartitionSize())
							counter++;
						else {
							if (l.get(counter - 1).getStatus().equals("Free")&&l.get(counter - 1).getFreeSpace()>=j.peek().getJobSize()) {

								l.get(counter - 1).setJobDynamic(j.peek());
								finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Arrives"));

								System.out.println(j.remove().getJobName()
										+ " loaded in memory");
								break out;
							} else
								counter++;
						}
					}
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Waiting"));
					System.out.println("No Partitions available at this time '"
							+ j.peek().getJobName()
							+ "' will be put in a waiting queue");
					waiting.offer(j.remove());
				}
			}
		} while (!j.isEmpty());

	}

	public void ApplyDynamicBestFit() {
		int memory_block = 300;
		int initial_Mem_waste;
		int memoryWaste;
		int subscript;
		int counter;
		System.out.println("Applying Best-Fit to Dynamic Partition.......");
		while (!j.isEmpty()) {

			if (j.peek().getJobSize() > l.get(getLargestPartIndex())
					.getPartitionSize()) {
				finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Rejected"));
				System.out
				.println(j.remove().getJobName()
						+ " was rejected. Job size greater than largest partition");

			} else {
				initial_Mem_waste = memory_block - j.peek().getJobSize();
				subscript = 0;
				counter = 1;

				while (counter <= l.size()) {
					if (l.get(counter - 1).getStatus().equals("Busy")||l.get(counter - 1).getFreeSpace()<j.peek().getJobSize())
						counter++;
					else {
						memoryWaste = l.get(counter - 1).getFreeSpace()
								- j.peek().getJobSize();

						if (initial_Mem_waste > memoryWaste)
							subscript = counter;

						initial_Mem_waste = memoryWaste;
						counter++;

					}

				}
				if (subscript == 0) {
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Waiting"));
					System.out.println("No Partitions available at this time '"
							+ j.peek().getJobName()
							+ "' will be put in a waiting queue");
					waiting.offer(j.remove());
				} else {

					l.get(subscript - 1).setJobDynamic(j.peek());
					finish.offer(new Job(j.peek().getJobName(),j.peek().getJobSize(),"Arrives"));
					System.out.println(j.remove().getJobName()
							+ " loaded in memory");
				}
			}
		}

	}

	private int getLargestPartIndex() {
		ListIterator<Partition> lt2 = l.listIterator();
		int index = 1;
		int index2 = 0;
		int size = lt2.next().getPartitionSize();
		while (lt2.hasNext()) {
			if (lt2.next().getPartitionSize() > size) {
				size = lt2.previous().getPartitionSize();
				index2 = index;
				index--;
			}

			index++;
		}
		return index2;
	}

	public void drawMemory() {

		ListIterator<Partition> lt2 = l.listIterator();
		while (lt2.hasNext()) {
			System.out.println("-------------------------");
			System.out.println(lt2.next().toString());
			System.out.println("-------------------------");
		}

	}

	public void drawWaiting() {
		System.out.println("Waiting Queue\n------------------------");
		for (Job j : waiting)
			System.out.println(j);
	}
}
