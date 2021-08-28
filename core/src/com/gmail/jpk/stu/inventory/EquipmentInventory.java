package com.gmail.jpk.stu.inventory;

import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.items.BaseItem.ItemType;

public class EquipmentInventory extends BasicInventory {

	public EquipmentInventory() {
		super(4);
	}

	@Override
	public void addItem(BaseItem item, int index) {
		if(item.getItemType() != ItemType.EQUIPMENT) {
			return;
		} else {
			if(slots.size + 1 <= maxSlots) {
				if(index < maxSlots) {
					slots.insert(index, item);
					return;
				}
			}
		}
	}

	@Override
	public void addItem(BaseItem item) {
		if(item.getItemType() != ItemType.EQUIPMENT) {
			return;
		} else {
			if(slots.size + 1 <= maxSlots) {
				slots.add(item);
			}
		}
	}
}