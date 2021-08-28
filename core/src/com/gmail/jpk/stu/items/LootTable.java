package com.gmail.jpk.stu.items;

import com.badlogic.gdx.utils.ArrayMap;

public enum LootTable {
	DEFAULT_ASTEROID_TABLE;
	
	private ArrayMap<ItemMaterial, Integer> minDropAmount;
	private ArrayMap<ItemMaterial, Integer> maxDropAmount;
	private ArrayMap<ItemMaterial, Float> dropChance;
	
	public static void createTables() {
		/* Commonly used item materials */
		ItemMaterial diaOre = ItemMaterial.DIAMOND_ORE;
		ItemMaterial diamond = ItemMaterial.DIAMOND;
		ItemMaterial larium = ItemMaterial.LARIUM;
		
		/* Default asteroid loot table */
		DEFAULT_ASTEROID_TABLE.minDropAmount.put(diaOre, 1);
		DEFAULT_ASTEROID_TABLE.minDropAmount.put(diamond, 1);
		DEFAULT_ASTEROID_TABLE.minDropAmount.put(larium, 1);
		DEFAULT_ASTEROID_TABLE.maxDropAmount.put(diaOre, 3);
		DEFAULT_ASTEROID_TABLE.maxDropAmount.put(diamond, 2);
		DEFAULT_ASTEROID_TABLE.maxDropAmount.put(larium, 5);
		DEFAULT_ASTEROID_TABLE.dropChance.put(diaOre, 2.5f);
		DEFAULT_ASTEROID_TABLE.dropChance.put(diamond, 0.8f);
		DEFAULT_ASTEROID_TABLE.dropChance.put(larium, 35.0f);
	}
	
	LootTable() {
		minDropAmount = new ArrayMap<ItemMaterial, Integer>();
		maxDropAmount = new ArrayMap<ItemMaterial, Integer>();
		dropChance = new ArrayMap<ItemMaterial, Float>();
	}
	
	public ItemMaterial[] getItemMaterials() {
		ItemMaterial[] materials = new ItemMaterial[dropChance.size];
		for(int i = 0; i < materials.length; i++) {
			materials[i] = dropChance.getKeyAt(i);
		}
		return materials;
	}
	
	public int getMinDropAmount(ItemMaterial material) {
		if(minDropAmount.containsKey(material)) {
			return minDropAmount.get(material);
		}
		else {
			return 0;
		}
	}
	
	public int getMaxDropAmount(ItemMaterial material) {
		if(maxDropAmount.containsKey(material)) {
			return maxDropAmount.get(material);
		}
		else {
			return 0;
		}
	}
	
	public float getDropChance(ItemMaterial material) {
		if(dropChance.containsKey(material)) {
			return dropChance.get(material);
		}
		else {
			return 0;
		}
	}
}
