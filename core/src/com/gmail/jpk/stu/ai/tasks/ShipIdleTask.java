package com.gmail.jpk.stu.ai.tasks;

import com.gmail.jpk.stu.spaceships.Spaceship;

public class ShipIdleTask extends ShipTask {

	public ShipIdleTask(long duration, Spaceship ship) {
		super(duration, ship);
	}

	@Override
	public void performTask() {
		if(!isTaskDone()) {
			if(getEndTime() <= System.currentTimeMillis()) {
				setTaskDone(true);
			}
		}
	}

}
