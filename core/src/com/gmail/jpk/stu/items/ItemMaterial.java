package com.gmail.jpk.stu.items;

public enum ItemMaterial {
	DIAMOND_ORE("Diamond Ore", 45.0f, 99), DIAMOND("Diamond", 80.0f, 99),
	ULKTO_ORE("Ulkto Ore", 90.0f, 99), UNREFINED_ULKTO("Unrefined Ulkto", 112.0f, 99), REFINED_ULKTO("Refined Ulkto", 305.0f, 99), 
	ULKTO_METAL("Ulkto Metal", 325.0f, 99), ULKTO_SYNTHETICS("Ulkto Synthetics", 515.0f, 99),
	GASEOUS_OXYGEN("Gaseous Oxygen", 189.0f, 99), LIQUID_OXYGEN("Liquid Oxygen", 227.0f, 99), SOLID_OXYGEN("Solid Oxygen", 286.0f, 99),
	LARIUM("Larium", 0.89f, 999);
	
	private final String name;
	private final float baseValue;
	private final int maxStack;
	
	ItemMaterial(String name, float baseValue, int maxStack) {
		this.name = name;
		this.baseValue = baseValue;
		this.maxStack = maxStack;
	}
	
	public String getName() {
		return name;
	}
	
	public float getBaseValue() {
		return baseValue;
	}
	
	public int getMaxStack() {
		return maxStack;
	}
}
