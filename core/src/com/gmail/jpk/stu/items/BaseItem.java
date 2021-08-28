package com.gmail.jpk.stu.items;

import com.badlogic.gdx.graphics.g2d.Sprite;

public abstract class BaseItem {
	
	public enum ItemType {
		NULL, ITEM, EQUIPMENT;
	}
	
	private ItemType itemType; // The type of item this object is
	private Sprite sprite; // The sprite of this object
	private String name; // The name of this item
	private int amount; // The amount of item 
	
	public BaseItem() {
		itemType = ItemType.NULL;
		sprite = null;
		name = "";
		amount = 0;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
}
