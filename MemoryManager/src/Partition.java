import java.util.LinkedList;
import java.util.ListIterator;

public class Partition {

	private int partitionSize;
	private String Status = "Free";
	private LinkedList<Job> job;
	private int partitionNumber;
	private int occupiedSize = 0;
	private String IntFrag = "none";

	public Partition(int partitionSize, int num) {

		this.partitionSize = partitionSize;
		partitionNumber = num;
		job = new LinkedList<>();

	}

	public int getPartitionSize() {
		return partitionSize;
	}

	public void setPartitionSize(int partitionSize) {
		this.partitionSize = partitionSize;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public LinkedList<Job> getJob() {

		return job;
	}

	public ListIterator<Job> ListIter() {
		return job.listIterator();
	}

	public void setJobFixed(Job Newjob) {

		job.add(Newjob);
		occupiedSize = Newjob.getJobSize();
		IntFrag = " " + (partitionSize - occupiedSize) + "k";

	}

	public void setJobDynamic(Job Newjob) {

		job.add(Newjob);
		occupiedSize += Newjob.getJobSize();
		if (occupiedSize == partitionSize)
			Status = "Busy";

	}

	public void setOccupiedSize(int occupiedSize) {
		this.occupiedSize = occupiedSize;
	}

	public int getFreeSpace() {
		return partitionSize - occupiedSize;
	}

	public String toString() {
		String s;

		s = "|  Partition " + partitionNumber + ": " + partitionSize + "k\n|"
				+ "  Status: " + Status + "\n" + "|  Occupied: " + occupiedSize
				+ "k; Available: " + getFreeSpace()
				+ "k; Internal Fragmentation: " + IntFrag + "\n";

		s += job.toString();

		return s;

	}

}
