public class Job {

	private String JobName;
	private int JobSize;
	private String Status;
	

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public Job(String jobName, int jobSize) {
		JobName = jobName;
		JobSize = jobSize;
	}
	
	public Job(String jobName, int jobSize,String status) {
		JobName = jobName;
		JobSize = jobSize;
		Status=status;
	}

	public String getJobName() {
		return JobName;
	}

	public int getJobSize() {
		return JobSize;
	}

	public String toString() {
		return "Job: " + JobName + " (" + JobSize + "k)";
	}

}
