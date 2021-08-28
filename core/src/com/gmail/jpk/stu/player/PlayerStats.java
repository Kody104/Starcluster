package com.gmail.jpk.stu.player;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gmail.jpk.stu.game.Faction;
import com.gmail.jpk.stu.items.EquipmentType;
import com.gmail.jpk.stu.spaceships.SpaceshipBlueprints;
import com.gmail.jpk.stu.universe.SolarSystem;
import com.gmail.jpk.stu.utils.StarDate;

public class PlayerStats {
	
	private static StarDate starDate;
	private static Array<String> visitedSystem;
	private static ArrayMap<Faction, Float> factionRelations;
	
	private static float maxShipFuel;
	private static float curShipFuel;
	
	public static void init() {
		starDate = new StarDate(2100, 1, 1, 1);
		visitedSystem = new Array<String>();
		factionRelations = new ArrayMap<Faction, Float>();
		maxShipFuel = 0.0f;
		curShipFuel = maxShipFuel;
	}
	
	/**
	 * Returns the current stardate
	 * @return	Stardate
	 */
	public static StarDate getStardate() {
		return PlayerStats.starDate;
	}
	
	/**
	 * Adds a solar system to the list of visited solar systems the player has been to.
	 * @param system	The solar system visited
	 */
	public static void addVisitedSystem(SolarSystem system) {
		if(!hasVisitedSystem(system)) {
			visitedSystem.add(system.getName());
		}
	}
	
	/**
	 * Removes a solar system from the list of visited solar systems that the player has been to.
	 * @param system	The solar system to remove
	 */
	public static void removeVisitedSystem(SolarSystem system) {
		if(hasVisitedSystem(system)) {
			visitedSystem.removeValue(system.getName(), false);
		}
	}
	
	/**
	 * Returns true if the player has visited a specific solar system.
	 * @param system	The system to check for
	 * @return	True/False
	 */
	public static boolean hasVisitedSystem(SolarSystem system) {
		for(String s : visitedSystem) {
			if(s.equals(system.getName())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns whether a faction is friendly, neutral, or hostile to the player.
	 * @param faction	The faction's opinion to look for
	 * @return	The faction opinion of the player
	 */
	public static String getFactionOpinion(Faction faction) {
		float relation = getFactionRelations(faction);
		if(relation > 25.0f) {
			return "friendly";
		}
		else if(relation > -25.0f) {
			return "neutral";
		}
		else {
			return "hostile";
		}
	}
	
	/**
	 * Returns the relation number of the faction to the player. 
	 * @param faction	The faction to search up
	 * @return	The relation number
	 */
	public static float getFactionRelations(Faction faction) {
		return PlayerStats.factionRelations.get(faction);
	}
	
	/**
	 * Sets a specified faction to a numbered relation to the player.
	 * @param faction	The specific faction
	 * @param relation	The relation to the player
	 * @return True if the faction's overall opinion of the player has changed, false if otherwise
	 */
	public static boolean setFactionRelation(Faction faction, float relation) {
		String pre = getFactionOpinion(faction);
		String post = "";
		if(relation < 100.0f && relation > -100.0f) {
			PlayerStats.factionRelations.put(faction, relation);
			post = getFactionOpinion(faction);
		}
		else {
			if(relation > 100.0f) {
				relation = 100.0f;
			}
			else {
				relation = -100.0f;
			}
			PlayerStats.factionRelations.put(faction, relation);
			post = getFactionOpinion(faction);
		}
		if(pre.equalsIgnoreCase(post)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Sets the faction relation array map.
	 * @param factionRelations	The new array map
	 */
	public static void setFactionRelations(ArrayMap<Faction, Float> factionRelations) {
		PlayerStats.factionRelations = factionRelations;
	}
	
	/**
	 * Returns the max ship fuel that can be used in the player ship
	 * @return	max fuel
	 */
	public static float getMaxShipFuel() {
		return maxShipFuel;
	}
	
	/**
	 * Sets the max fuel that can be used in the player ship
	 * @param blueprint	The spaceship stats to use
	 */
	public static void setMaxShipFuel(SpaceshipBlueprints blueprint) {
		PlayerStats.maxShipFuel = blueprint.getEquipmentByType(EquipmentType.FUEL_TANK).getMaxFuelAdd();
	}
	
	/**
	 * Returns the current ship fuel that the player ship has
	 * @return	The current fuel
	 */
	public static float getCurShipFuel() {
		return curShipFuel;
	}
	
	/**
	 * Sets the current ship fuel that the player ship has
	 * @param fuel	The current fuel
	 */
	public static void setCurShipFuel(float fuel) {
		PlayerStats.curShipFuel = fuel;
	}
}
