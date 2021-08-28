package com.gmail.jpk.stu.ai.tasks;

import com.badlogic.gdx.math.Vector2;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.sprites.WeaponFireSprite;
import com.gmail.jpk.stu.utils.DataUtils;

public class ShipHuntTask extends ShipTask {

	private Spaceship target; // The target of this task
	private boolean isTimed; // If this task should end after an amount of time has passed
	
	public ShipHuntTask(long duration, Spaceship ship, Spaceship target) {
		super(duration, ship);
		this.target = target;
		if(duration > 0.0f) {
			isTimed = true;
		}
	}
	
	public ShipHuntTask(Spaceship ship, Spaceship target) {
		super(ship);
		this.target = target;
		isTimed = false;
	}

	public Spaceship getTarget() {
		return target;
	}

	public void setTarget(Spaceship target) {
		this.target = target;
	}

	@Override
	public void performTask() {
		if(!isTaskDone()) {
			if(isTimed) {
				if(getEndTime() <= System.currentTimeMillis()) {
					setTaskDone(true);
					return;
				}
			}
			Spaceship ship = getShip();
			// The position of the target
			Position targetPosition;
			if(target.getSpriteType() == SpriteType.PLAYERSHIP) {
				targetPosition = target.getPosition().add(new Position(400.0f, 240.0f).subtract(new Position(50.0f, 50.0f)));
			}
			else {
				targetPosition = target.getPosition().subtract(new Position(50.0f, 50.0f));
			}
			// Rotate ship and get the transform vector
			Vector2 rotationVector = orientateShip(ship, targetPosition);
			
			if(rotationVector.x != 0.0f || rotationVector.y != 0.0f) {
				float curThrust = ship.getThrust() > 0.0f ? ship.getThrust() : 0.01f;
				// The amount of frames for the ship to reach the destination on x axis
				int xFrames = (int)Math.abs(Math.ceil((rotationVector.x) / curThrust)); 
				// The amount of frames for the ship to reach the destination on y axis.
				int yFrames = (int)Math.abs(Math.ceil(rotationVector.y / curThrust));
				// Whichever has the most frames, we use.
				int maxFrames = xFrames > yFrames ? xFrames: yFrames;
				// The max amount of frames to stop at the given ship thrust
				int maxBrakeFrames = (int)Math.ceil(curThrust / ship.getBrakePower());
				// If the average amount of frames to stop is higher than the brake frames, we accelerate
				if(maxFrames > maxBrakeFrames) {
					ship.accelerate();
				}
				// Otherwise we attempt to stop
				else {
					ship.decelerate();
				}
			}
			// If we are close enough to the position
			if(Math.abs(rotationVector.x) <= 300.0f || Math.abs(rotationVector.y) <= 180.0f) {
				WeaponFireSprite shot = ship.fireWeapon();
				if(shot != null) {
					DataUtils.addWeaponFireSprite(shot);
				}
			}
			if(!target.isAlive()) {
				setTaskDone(true);
			}
		}
	}

}
