package com.gmail.jpk.stu.components;

import com.gmail.jpk.stu.inventory.BasicInventory;
import com.gmail.jpk.stu.items.BaseItem;

public class ComponentUtils {
	
	/**
	 * Creates a number of inventory slots and returns them.
	 * @param bg	The sprite to create the slots inside of
	 * @param inventory	The inventory to display
	 * @param slots	The numbers of slots to create
	 */
	public static SlotComponent[] createInventorySlots(SpriteComponent bg, BasicInventory inventory, int slots) {
		SlotComponent[] toReturn = new SlotComponent[slots];
		for (int i = 0; i < slots; i++) {
			int divisions = (int)(Math.floor(i / 4)); // Used to calculate rows
			BaseItem item = null;
			if (i < inventory.getSlots().length) {
				if (inventory.getSlots()[i] != null) {
					item = inventory.getSlots()[i];
				}
			}
			toReturn[i] = new SlotComponent(item);
			if(i >= inventory.getMaxSlots()) {
				toReturn[i].setInteractable(false);
			}
			toReturn[i].setX((bg.getX() + 5.0f) + ((i - (4 * divisions)) * 61.75f)); // Resets after every 4
			toReturn[i].setY((bg.getY() + bg.getDisplay().getHeight())
					- ((toReturn[i].getDisplay().getHeight() * ((i / 4) + 1)) + (5.0f * ((i / 4) + 1)))); // Goes down after every 4
			//parent.addSlot(slot);
		}
		return toReturn;
	}
	
	public static SlotComponent[] createEquipmentSlots(SpriteComponent bg, BasicInventory inventory, int slots) {
		SlotComponent[] toReturn = new SlotComponent[slots];
		for (int i = 0; i < slots; i++) {
			BaseItem item = null;
			if (i < inventory.getSlots().length) {
				if (inventory.getSlots()[i] != null) {
					item = inventory.getSlots()[i];
				}
			}
			toReturn[i] = new SlotComponent(item);
			if(i >= inventory.getMaxSlots()) {
				toReturn[i].setInteractable(false);
			}
			toReturn[i].setX(bg.getX() + 5.0f);
			toReturn[i].setY((bg.getY() + bg.getDisplay().getHeight())
					- ((toReturn[i].getDisplay().getHeight() * ((i / 1) + 1)) + (5.0f * ((i / 1) + 1)))); // Goes down after every 4
		}
		return toReturn;
	}
	
	/**
	 * Centers a component according to another component
	 * @param toCenter	The component to center
	 * @param centerAround	The component to center around
	 * @param centerX	True if you want to center the x value
	 * @param centerY	True if you want to center the y value
	 * @param xOffset	The amount to offset the x value
	 * @param yOffset	The amount to offset the y value
	 */
	public static void centerComponent(Component toCenter, Component centerAround, float xOffset, float yOffset) {
		ComponentUtils.centerComponentX(toCenter, centerAround, xOffset);
		ComponentUtils.centerComponentY(toCenter, centerAround, yOffset);
	}
	
	/**
	 * Centers a component x according to another component's x
	 * @param toCenter	The component to center
	 * @param centerAround	The component to center around
	 * @param xOffset	The amount to offset the x value
	 */
	public static void centerComponentX(Component toCenter, Component centerAround, float xOffset) {
		if(!(toCenter instanceof TextComponent)) {
			toCenter.setX((centerAround.getX() + ((centerAround.getDisplay().getWidth() / 2.0f) - (toCenter.getDisplay().getWidth() / 2.0f))) + xOffset);
		}
	}
	
	/**
	 * Centers a component y according to another component's y
	 * @param toCenter	The component to center
	 * @param centerAround	The component to center around
	 * @param yOffset	The amount to offset the y value
	 */
	public static void centerComponentY(Component toCenter, Component centerAround, float yOffset) {
		if(!(toCenter instanceof TextComponent)) {
			toCenter.setY((centerAround.getY() + ((centerAround.getDisplay().getHeight() / 2.0f) - (toCenter.getDisplay().getHeight() / 2.0f))) + yOffset);
		}
	}
}
