package com.gmail.jpk.stu.inventory;

import java.util.Random;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.items.Item;
import com.gmail.jpk.stu.items.ItemMaterial;

public abstract class BasicInventory {
	
	protected Array<BaseItem> slots;
	protected int maxSlots;
	
	public BasicInventory() {
		slots = new Array<BaseItem>();
		maxSlots = 16;
	}
	
	public BasicInventory(int maxSlots) {
		slots = new Array<BaseItem>();
		this.maxSlots = maxSlots;
	}
	
	/**
	 * Generates an amount of random items based on the amount given
	 * @param amount	The amount of items to generate
	 * @param maxStack	The max amount of items in a stack
	 * @return	The generated array of items
	 */
	public static Item[] generateItems(int amount, int maxStack) {
		Item[] genItems = new Item[amount];
		Random rand = new Random();
		if(amount > 0) {
			genItems[0] = new Item(ItemMaterial.DIAMOND);
		}
		for(int i = 1; i < amount; i++) {
			genItems[i] = new Item(ItemMaterial.values()[rand.nextInt(ItemMaterial.values().length)], rand.nextInt(maxStack) + 1);
		}
		return genItems;
	}
	
	public BaseItem[] getSlots() {
		BaseItem[] items = new BaseItem[slots.size];
		for(int i = 0; i < items.length; i++) {
			items[i] = slots.get(i);
		}
		return items;
	}
	
	public void addItem(BaseItem item, int index) {
		if(slots.size + 1 <= maxSlots) {
			if(index < maxSlots) {
				slots.insert(index, item);
				return;
			}
		}
	}
	
	public void addItem(BaseItem item) {
		if(slots.size + 1 <= maxSlots) {
			slots.add(item);
		}
	}
	
	public void removeItem(int index) { 
		if(slots.size > index) {
			slots.removeIndex(index);
		}
	}
	
	public void removeItem(BaseItem item) {
		for(int i = 0; i < slots.size; i++) {
			BaseItem itm = slots.get(i);
			if(itm == item) {
				slots.removeIndex(i);
				return;
			}
		}
	}

	public void setSlots(Array<BaseItem> slots) {
		this.slots = slots;
	}

	public int getMaxSlots() {
		return maxSlots;
	}

	public void setMaxSlots(int maxSlots) {
		this.maxSlots = maxSlots;
	}
}
