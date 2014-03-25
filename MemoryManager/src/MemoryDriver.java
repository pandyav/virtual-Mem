import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MemoryDriver {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		LinkedList<Partition> memory = new LinkedList<Partition>();
		Queue<Job> jobs = new LinkedList<Job>();

		System.out.println("Total Memory: 200k");

		int numJobs = 1;
		String option;

		FixedPartition fp;

		memory.add(new Partition(100, 1));
		memory.add(new Partition(25, 2));
		memory.add(new Partition(25, 3));
		memory.add(new Partition(50, 4));

		do {
			System.out.println("fixed or dynamic");
			String option3 = sc.next();
			System.out.println("first or best");
			String option2 = sc.next();
			fp = new FixedPartition(memory, jobs,option3,option2);
			do {
				System.out.println("Which option? (help=Help Menu)");
				option = sc.next();

				if (option.equals("a")) {

					addJobs(numJobs, sc, jobs);

					if (option2.equals("first") && option3.equals("fixed")) {
						fp.ApplyFixed();

					} else if (option2.equals("best")
							&& option3.equals("fixed"))
						fp.ApplyBestFit();
					else if (option2.equals("first")
							&& option3.equals("dynamic"))
						fp.ApplyDynamicFirstFit();
					else if (option2.equals("best")
							&& option3.equals("dynamic"))
						fp.ApplyDynamicBestFit();

				} else if (option.equals("m")) {
					fp.drawMemory();
				} else if (option.equals("q"))
					fp.drawWaiting();
				else if (option.equals("help"))
					helpMenu();
				else if(option.equals("dealloc"))
					fp.finish();
				
				if(option.equals("exit"))
					break;
			} while (!option.equals("restart"));

			
		} while (!option.equals("exit"));
		sc.close();
	}

	private static void addJobs(int numJobs, Scanner sc, Queue<Job> jobs) {
		int x = 1;
		System.out.println("How many jobs you want?");
		numJobs = sc.nextInt();

		while (x <= numJobs) {
			System.out.println("Job Name  Job Size");
			jobs.offer(new Job(sc.next(), sc.nextInt()));
			x++;
		}
	}

	private static void helpMenu() {
		System.out.println("Help Menu\n-------------------");
		System.out.println("Type 'a' for adding new jobs");
		System.out.println("Type 'm' for Main Memory");
		System.out.println("Type 'q' for job waiting queue");
		System.out.println("Type 'dealloc' so jobs can leave and new one can arrive");
		System.out.println("Type 'help' for this help menu");
		System.out.println("Type 'restart' to restart");
		System.out.println("Type 'exit' to exit");
	}

}
