package com.gmail.jpk.stu.items;

public enum EquipmentType {
	HULL(9), THRUSTER(9), SHIELD(9), FUEL_TANK(9);
	
	private final int maxStack;
	
	EquipmentType(int maxStack) {
		this.maxStack = maxStack;
	}
	
	public int getMaxStack() {
		return maxStack;
	}
}
