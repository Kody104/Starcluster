package com.gmail.jpk.stu.universe;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.game.Faction;

public class Universe {
	
	private static Array<SolarSystem> allKnownSystems;
	private static Array<Faction> allFactions;
	
	/**
	 * Returns the faction owner of a specific system
	 * @param system	The system who's owner we are looking for
	 * @return	The system faction owner
	 */
	public static Faction getSystemFactionOwner(SolarSystem system) {
		for(Faction f : allFactions) {
			if(f.doesOwnSystem(system)) {
				return f;
			}
		}
		return null;
	}
	
	public static Array<SolarSystem> getAllSystems() {
		return allKnownSystems;
	}
	
	public static SolarSystem[] getAllSystemsAsBasic() {
		SolarSystem[] systems = new SolarSystem[allKnownSystems.size];
		for(int i = 0; i < systems.length; i++) {
			systems[i] = allKnownSystems.get(i);
		}
		return systems;
	}
	
	public static void setAllSystems(Array<SolarSystem> allKnownSystems) {
		Universe.allKnownSystems = allKnownSystems;
	}
	
	public static Array<Faction> getAllFactions() {
		return allFactions;
	}
	
	public static void setAllFactions(Array<Faction> allFactions) {
		Universe.allFactions = allFactions;
	}
}
