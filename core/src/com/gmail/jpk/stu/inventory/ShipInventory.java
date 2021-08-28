package com.gmail.jpk.stu.inventory;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.items.Item;
import com.gmail.jpk.stu.items.BaseItem.ItemType;
import com.gmail.jpk.stu.items.Equipment;

public class ShipInventory extends BasicInventory {
	
	public ShipInventory() {
		super();
	}
	
	public ShipInventory(int maxStacks) {
		super(maxStacks);
	}
	
	public Equipment[] getEquipmentItems() {
		Array<Equipment> aEquipment = new Array<Equipment>();
		for(int i = 0; i < slots.size; i++) {
			if(slots.get(i).getItemType() == ItemType.EQUIPMENT) {
				Equipment e = (Equipment) slots.get(i);
				aEquipment.add(e);
			}
		}
		Equipment[] toReturn = new Equipment[aEquipment.size];
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = aEquipment.get(i);
		}
		return toReturn;
	}
	
	@Override
	public void addItem(BaseItem item) {
		for(int i = 0; i < slots.size; i++) {
			/* Make sure it's an item that we are adding*/
			if(slots.get(i).getItemType() == ItemType.ITEM && item.getItemType() == ItemType.ITEM) {
				Item it = (Item) slots.get(i);
				Item itm = (Item) item;
				/* If the item we are adding and the item in the slot are the same material */
				if(it.getMaterial() == itm.getMaterial()) {
					/* If the items added together is less than the material's max stacks size */
					if(it.getAmount() + itm.getAmount() <= it.getMaterial().getMaxStack()) {
						int amount = it.getAmount() + itm.getAmount();
						slots.set(i, new Item(it.getName(), it.getMaterial(), amount)); // Combine the items together
						return;
					}
				}
			}
			else {
				/* If the item is an equipment item */
				if(item.getItemType() == ItemType.EQUIPMENT) {
					if(slots.size + 1 <= maxSlots) {
						Equipment eq = (Equipment) item;
						slots.add(eq); // Add to the equipment list
						return;
					}
				}
			}
		}
		
		if(slots.size + 1 <= maxSlots) {
			slots.add(item);
		}
	}
}
