package com.gmail.jpk.stu.ai.tasks;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.Position;

public abstract class ShipTask extends BaseTask {

	private Spaceship ship; // The ship that is doing this task
	
	public ShipTask(long duration, Spaceship ship) {
		super(duration);
		this.ship = ship;
	}
	
	public ShipTask(Spaceship ship) {
		super(0L);
		this.ship = ship;
	}
	
	public Spaceship getShip() {
		return ship;
	}

	public void setShip(Spaceship ship) {
		this.ship = ship;
	}
	
	/**
	 * Turns the given ship to the angle of the target position.
	 * @param ship
	 * @param target
	 * @return The rotation vector used
	 */
	protected Vector2 orientateShip(Spaceship ship, Position target) {
		// Transform vector
		Vector2 rotationVector = new Vector2(target.getX() - ship.getPosition().getX(), target.getY() - ship.getPosition().getY());
		// Get the rotation in radians
		float rotationRad = rotationVector.angleRad();
		// Get the rotation in degrees
		float rotationDegree = rotationRad * MathUtils.radiansToDegrees;
		// Minus 90.0f to set the rotation correctly (Image is saved top down)
		rotationDegree -= 90.0f;
		// The difference of the ship and the target degree
		// float difference = Math.abs(ship.getRotation() - rotationDegree);
		// Turning one way
		if(ship.getRotation() != rotationDegree) {
			ship.setRotation(rotationDegree);
		}
		return rotationVector;
	}
}
