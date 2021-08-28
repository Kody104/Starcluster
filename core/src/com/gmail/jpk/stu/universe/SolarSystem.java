package com.gmail.jpk.stu.universe;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.universe.spaceobject.AsteroidObject;
import com.gmail.jpk.stu.universe.spaceobject.PlanetaryObject;
import com.gmail.jpk.stu.universe.spaceobject.SpaceObject;

public class SolarSystem {
	
	private String name; // Name of the solar system
	private Position position; // The position of this system in the universe
	private PlanetaryObject homeworld; // The main planet of this system
	private Array<SpaceObject> extras; // The other planets or moons
	private Array<AsteroidObject> asteroids; // Asteroid field
	private int avgTraffic; // The median amount of random ships to spawn in the system
	private Array<Spaceship> shipsInSystem; // The ships that are in the system
	
	/**
	 * Constructor for a solar system.
	 * @param name	The name of the solar system
	 * @param homeworld	The main planet for this system
	 * @param position	The position of this solar system in the universe
	 * @param	avgTraffic	The median amount of random ship traffic to spawn in this system
	 */
	public SolarSystem(String name, PlanetaryObject homeworld, Position position, int avgTraffic) {
		this.name = name;
		this.homeworld = homeworld;
		extras = new Array<SpaceObject>();
		asteroids = new Array<AsteroidObject>();
		this.position = position;
		this.avgTraffic = avgTraffic;
		shipsInSystem = new Array<Spaceship>(true, 16);
	}
	
	public void reset() {
		shipsInSystem.clear();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Position getPosition() {
		return position;
	}
	
	public void setPosition(Position position) {
		this.position = position;
	}

	public PlanetaryObject getHomeworld() {
		return homeworld;
	}

	public void setHomeworld(PlanetaryObject homeworld) {
		this.homeworld = homeworld;
	}
	
	public SpaceObject[] getExtras() {
		SpaceObject[] toReturn = new SpaceObject[extras.size];
		for(int i = 0; i < extras.size; i++) {
			toReturn[i] = extras.get(i);
		}
		return toReturn;
	}
	
	public void setExtras(Array<SpaceObject> extras) {
		this.extras = extras;
	}
	
	public AsteroidObject[] getAsteroids() {
		AsteroidObject[] toReturn = new AsteroidObject[asteroids.size];
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = asteroids.get(i);
		}
		return toReturn;
	}
	
	public void setAsteroids(Array<AsteroidObject> asteroids) {
		this.asteroids = asteroids;
	}
	
	public int getAvgTraffic() {
		return avgTraffic;
	}

	public void setAvgTraffic(int avgTraffic) {
		this.avgTraffic = avgTraffic;
	}
	
	public void addExtraToSystem(SpaceObject extra) {
		this.extras.add(extra);
	}
	
	public void removeExtraFromSystem(SpaceObject extra) {
		this.extras.removeValue(extra, true);
	}
	
	public void removeExtraFromSystem(int index) {
		this.extras.removeIndex(index);
	}
	
	public void clearExtras() {
		extras.clear();
	}
	
	public void addAsteroidToSystem(AsteroidObject asteroid) {
		this.asteroids.add(asteroid);
	}
	
	public void removeAsteroidFromSystem(AsteroidObject asteroid) {
		this.asteroids.removeValue(asteroid, true);
	}
	
	public void removeAsteroidFromSystem(int index) {
		this.asteroids.removeIndex(index);
	}
	
	public void clearAsteroids() {
		asteroids.clear();
	}
	
	public void addShipToSystem(Spaceship ship) {
		this.shipsInSystem.add(ship);
	}
	
	public void removeShipFromSystem(Spaceship ship) {
		this.shipsInSystem.removeValue(ship, true);
	}
	
	public void removeShipFromSystem(int index) {
		this.shipsInSystem.removeIndex(index);
	}
	
	public void clearShips() {
		shipsInSystem.clear();
	}
	
	/**
	 *	Returns all spaceships in the system 
	 * @return	All spaceships
	 */
	public Spaceship[] getShipsInSystem() {
		Spaceship[] toReturn = new Spaceship[shipsInSystem.size];
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = shipsInSystem.get(i);
		}
		return toReturn;
	}
}
