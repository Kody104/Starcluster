package com.gmail.jpk.stu.ai.tasks;

public abstract class BaseTask {
	
	private boolean isTaskDone; // If the task is done and ready for cleanup
	private long endTime; // The end time of this task
	
	public abstract void performTask();
	
	public BaseTask(long duration) {
		this.endTime = System.currentTimeMillis() + duration;
		this.isTaskDone = false;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public boolean isTaskDone() {
		return isTaskDone;
	}

	public void setTaskDone(boolean isTaskDone) {
		this.isTaskDone = isTaskDone;
	}
}
