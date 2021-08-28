package com.gmail.jpk.stu.spaceships;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.items.Equipment;
import com.gmail.jpk.stu.items.EquipmentType;

public class SpaceshipBlueprints {
	
	private Array<Equipment> shipEquipment; // All the equipment that this blueprint has
	
	/**
	 * Constructor for a spaceship blueprint.
	 * @param equipment	The equipment parts that this equipment has
	 */
	public SpaceshipBlueprints(Equipment...equipment) {
		shipEquipment = new Array<Equipment>();
		for(Equipment e : equipment) {
			addEquipment(e);
		}
	}
	
	/**
	 * Constructor for cloning a blueprint.
	 * @param clone	The blueprint to clone
	 */
	public SpaceshipBlueprints(SpaceshipBlueprints clone) {
		shipEquipment = new Array<Equipment>();
		for(Equipment e : clone.getShipEquipment()) {
			addEquipment(e);
		}
	}
	
	/**
	 * Returns all ship equipment in this blueprint.
	 * @return	All ship equipment
	 */
	public Equipment[] getShipEquipment() {
		Equipment[] equipment = new Equipment[shipEquipment.size];
		for(int i = 0; i < equipment.length; i++) {
			equipment[i] = shipEquipment.get(i);
		}
		return equipment;
	}
	
	/**
	 * Returns the equipment of a given equipment type.
	 * @param type	The type of equipment to return
	 * @return	The equipment
	 */
	public Equipment getEquipmentByType(EquipmentType type) {
		for(Equipment e : shipEquipment) {
			if(e.getEquipmentType() == type) {
				return e;
			}
		}
		return null;
	}
	
	/**
	 * Returns the equipment at a given index.
	 * @param index	The index to return
	 * @return	The equipment
	 */
	public Equipment getEquipmentAt(int index) {
		return shipEquipment.get(index);
	}
	
	/**
	 * Checks the array for equipment of the same equipment type as the given equipment.
	 * <br> If it exists, it is replaced with the new equipment.
	 * @param equipment	The equipment to add
	 */
	private void addEquipment(Equipment equipment) {
		for(int i = 0; i < shipEquipment.size; i++) {
			Equipment e = shipEquipment.get(i);
			if(e.getEquipmentType() == equipment.getEquipmentType()) {
				shipEquipment.set(i, equipment);
				return;
			}
		}
		shipEquipment.add(equipment);
	}
}
