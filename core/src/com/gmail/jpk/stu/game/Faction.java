package com.gmail.jpk.stu.game;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.gmail.jpk.stu.universe.SolarSystem;

public class Faction {
	
	private String name;
	private Array<String> ownedSystems;
	private ArrayMap<Faction, Float> relations;
	
	public Faction(String name) {
		this.name = name;
		ownedSystems = new Array<String>();
		relations = new ArrayMap<Faction, Float>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Checks to see if the system is owned by this faction.
	 * @param system	The system to check for
	 * @return	True/False
	 */
	public boolean doesOwnSystem(SolarSystem system) {
		for(String s : ownedSystems) {
			if(system.getName().equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public void addSystemToOwn(SolarSystem system) {
		this.ownedSystems.add(system.getName());
	}
	
	public void removeOwnedSystem(SolarSystem system) {
		this.ownedSystems.removeValue(system.getName(), false);
	}
	
	public Array<String> getOwnedSystems(){
		return ownedSystems;
	}
	
	public void setOwnedSystems(Array<String> ownedSystems) {
		this.ownedSystems = ownedSystems;
	}
	
	public void setRelation(Faction faction, float relation) {
		relations.put(faction, relation);
	}
	
	public float getRelationWith(Faction faction) {
		return relations.get(faction);
	}
	
	public boolean hasRelationWith(Faction faction) {
		return relations.containsKey(faction);
	}

	public ArrayMap<Faction, Float> getRelations() {
		return relations;
	}

	public void setRelations(ArrayMap<Faction, Float> relations) {
		this.relations = relations;
	}
}
