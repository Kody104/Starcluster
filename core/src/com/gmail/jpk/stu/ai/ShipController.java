package com.gmail.jpk.stu.ai;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gmail.jpk.stu.ai.tasks.ShipIdleTask;
import com.gmail.jpk.stu.ai.tasks.ShipMoveTask;
import com.gmail.jpk.stu.ai.tasks.ShipTask;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.utils.Mathematics;

public class ShipController {
	
	private long callTimer; // The timer to call the autotask method
	private Array<Spaceship> controlledShips; // The ships that this controller controls
	private ArrayMap<Spaceship, ShipTask> tasks; // The tasks that the ships are performing
	
	public ShipController() {
		controlledShips = new Array<Spaceship>();
		tasks = new ArrayMap<Spaceship, ShipTask>();
		callTimer = 0L;
	}
	
	/**
	 * Creates a random ship task for a given ship
	 * @param s	The ship to be given to
	 * @return	The random ship task
	 */
	protected ShipTask createRandomTask(Spaceship s) {
		int num = Mathematics.rand.nextInt(2);
		switch(num) {
			case 0:
			{
				float x = Mathematics.rand.nextFloat() * 14000.0f;
				float y = Mathematics.rand.nextFloat() * 14000.0f;
				if(x > 7000.0f) {
					x -= 14000.0f;
				}
				if(y > 7000.0f) {
					y -= 14000.0f;
				}
				return new ShipMoveTask(s, new Position(x, y));
			}
			case 1:
			{
				int duration = Mathematics.rand.nextInt(4500) + 500;
				System.out.printf("Idling for %.2f seconds!\n", (duration / 1000.0f));
				return new ShipIdleTask(duration, s);
			}
			default:
			{
				return null;
			}
		}
	}
	
	/**
	 * Automatically creates tasks for any ship that doesn't have a task.
	 */
	protected void autotask() {
		for(Spaceship s : controlledShips) {
			if(!tasks.containsKey(s)) {
				// Give the ship a random task
				createShipTask(s, createRandomTask(s));
			}
		}
		// Create a random time to call next between 0.25 seconds and 0.5 seconds.
		callTimer = (System.currentTimeMillis() + Mathematics.rand.nextInt(250) + 250);
	}
	
	/**
	 * Clears all the ships and ship tasks in this object
	 */
	public void clear() {
		callTimer = 0L;
		controlledShips.clear();
		tasks.clear();
	}
	
	/**
	 * Adds a ship to be controlled by the AI.
	 * @param ship	The ship to be controlled
	 */
	public void addShip(Spaceship ship) {
		if(!controlledShips.contains(ship, true)) {
			controlledShips.add(ship);
		}
	}
	
	/**
	 * Remove a ship from the ai controller and delete any task it is doing.
	 * @param ship	The ship to be removed
	 */
	public void removeShip(Spaceship ship) {
		if(controlledShips.contains(ship, true)) {
			controlledShips.removeValue(ship, true);
			if(tasks.containsKey(ship)) {
				tasks.removeKey(ship);
			}
		}
	}
	
	/**
	 * Creates a task for a specified spaceship. <br><b>Note that the ship needs to be added to the controller first!</b>
	 * @param ship	The ship to add the task for
	 * @param task	The task to do
	 */
	public void createShipTask(Spaceship ship, ShipTask task) {
		if(controlledShips.contains(ship, true)) {
			tasks.put(ship, task);
		}
	}
	
	/**
	 * Ends the current task of the selected ship
	 * @param ship	The ship
	 */
	public void endCurrentTask(Spaceship ship) {
		if(controlledShips.contains(ship, true)) {
			if(tasks.containsKey(ship)) {
				tasks.removeKey(ship);
			}
		}
	}
	
	/**
	 * Returns the current task that the ship is performing.
	 * @param ship	The ship who's task to get
	 * @return	The current task of the ship
	 */
	public ShipTask getCurrentTask(Spaceship ship) {
		if(controlledShips.contains(ship, true)) {
			if(tasks.containsKey(ship)) {
				return tasks.get(ship);
			}
		}
		return null;
	}
	
	public void update() {
		if(callTimer <= System.currentTimeMillis()) {
			autotask();
		}
		for(int i = 0; i < tasks.size; i++) {
			Spaceship s = tasks.getKeyAt(i);
			tasks.get(s).performTask();
			if(tasks.get(s).isTaskDone()) {
				tasks.removeIndex(i);
				i--;
			}
		}
	}
	
}
