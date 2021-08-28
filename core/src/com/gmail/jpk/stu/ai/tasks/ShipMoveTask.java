package com.gmail.jpk.stu.ai.tasks;

import com.badlogic.gdx.math.Vector2;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.Position;

public class ShipMoveTask extends ShipTask {
	
	private Position targetPosition; // The target position to reach
	
	public ShipMoveTask(Spaceship ship, Position targetPosition) {
		super(ship);
		this.targetPosition = targetPosition;
	}

	public Position getTargetPosition() {
		return targetPosition;
	}

	public void setTargetPosition(Position targetPosition) {
		this.targetPosition = targetPosition;
	}
	
	@Override
	public void performTask() {
		if(!isTaskDone()) {
			Spaceship ship = getShip();
			Vector2 rotationVector = orientateShip(ship, targetPosition);
			// If the position hasn't been reached yet
			if(rotationVector.x != 0.0f && rotationVector.y != 0.0f) {
				float curThrust = ship.getThrust() > 0.0f ? ship.getThrust() : 0.01f;
				// The amount of frames for the ship to reach the destination on x axis
				int xFrames = (int)Math.abs(Math.ceil(rotationVector.x / curThrust)); 
				// The amount of frames for the ship to reach the destination on y axis.
				int yFrames = (int)Math.abs(Math.ceil(rotationVector.y / curThrust));
				// Whichever has the most frames, we use.
				int mostFrames = xFrames > yFrames ? xFrames : yFrames;
				// The max amount of frames to stop at the given ship thrust
				int maxBrakeFrames = (int)Math.ceil(curThrust / ship.getBrakePower());
				// If the average amount of frames to stop is higher than the brake frames, we accelerate
				if(mostFrames > maxBrakeFrames) {
					// Keep the ai slower than player unless they are aggressive
					if(!(ship.getThrust() > ship.getMaxThrust() / 2.0f)) {
						ship.accelerate();
					}
				}
				// Otherwise we attempt to stop
				else {
					ship.decelerate();
				}
				// If we are close enough to the position
				if(Math.abs(rotationVector.x) <= 1.5f && Math.abs(rotationVector.y) <= 1.5f) {
					setTaskDone(true);
					ship.setThrust(0.0f);
				}
			}
		}
	}
}
