package com.gmail.jpk.stu.inventory;

import com.gmail.jpk.stu.items.Item;
import com.gmail.jpk.stu.universe.spaceobject.PlanetaryObject;

public class ShopInventory extends BasicInventory {
	
	protected PlanetaryObject owner;
	
	public ShopInventory(PlanetaryObject owner) {
		super();
		this.owner = owner;
		int amountToGen = 0;
		int maxStack = 0;
		switch(owner.getEconomyStrength()) {
			case BOOMING:
			{
				amountToGen = 16;
				maxStack = 16;
				break;
			}
			case THRIVING:
			{
				amountToGen = 16;
				maxStack = 16;
				break;
			}
			case STEADY:
			{
				amountToGen = 12;
				maxStack = 12;
				break;
			}
			case FAIR:
			{
				amountToGen = 8;
				maxStack = 8;
				break;
			}
			case WEAK:
			{
				amountToGen = 4;
				maxStack = 4;
				break;
			}
			case NONE:
			{
				break;
			}
		}
		//Create a random list of trade items to trade with
		for(Item i : BasicInventory.generateItems(amountToGen, maxStack)) {
			this.slots.add(i);
		}
	}
	
	public ShopInventory(int maxSlots, PlanetaryObject owner) {
		super(maxSlots);
		this.owner = owner;
		//Create a random list of trade items to trade with
		for(Item i : BasicInventory.generateItems(maxSlots, maxSlots)) {
			this.slots.add(i);
		}
	}

	public PlanetaryObject getOwner() {
		return owner;
	}

	public void setOwner(PlanetaryObject owner) {
		this.owner = owner;
	}
	
	/**
	 * Returns the value that this planet will buy an item at.
	 * @param item	The item to get the value of 
	 * @return	The value of the item to this planet
	 */	
	public float getBuyValue(Item item) {
		float mod = 1.0f;
		switch(owner.getEconomyStrength()) {
			case BOOMING:
			{
				mod = 1.09f;
				break;
			}
			case THRIVING:
			{
				mod = 1.03f;
				break;
			}
			case STEADY:
			{
				mod = 0.98f;
				break;
			}
			case FAIR:
			{
				mod = 0.92f;
				break;
			}
			case WEAK:
			{
				mod = 0.85f;
				break;
			}
			case NONE:
			{
				mod = 0.0f;
				break;
			}
		}
		return ((item.getMaterial().getBaseValue() * item.getAmount()) * mod);
	}
	
	public float getSellValue(Item item) {
		float mod = 1.0f;
		switch(owner.getEconomyStrength()) {
			case BOOMING:
			{
				mod += 0.15f;
				break;
			}
			case THRIVING:
			{
				mod += 0.08f;
				break;
			}
			case STEADY:
			{
				mod += 0.02f;
				break;
			}
			case FAIR:
			{
				mod -= 0.03f;
				break;
			}
			case WEAK:
			{
				mod -= 0.09f;
				break;
			}
			case NONE:
			{
				mod = 0.0f;
				break;
			}
		}
		mod /= 2.0f; // Halve the value because the planet is buying it.
		return ((item.getMaterial().getBaseValue() * item.getAmount()) * mod);
	}
}
