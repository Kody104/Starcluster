package com.gmail.jpk.stu.items;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gmail.jpk.stu.utils.SpriteData;

public class Item extends BaseItem {
	
	private ItemMaterial material; // The material that this item is

	
	public Item(ItemMaterial material) {
		setName(material.getName());
		setAmount(1);
		setMaterial(material);
		setItemType(ItemType.ITEM);
	}
	
	public Item(ItemMaterial material, int amount) {
		setName(material.getName());
		setAmount(amount);
		setMaterial(material);
		setItemType(ItemType.ITEM);
	}
	
	public Item(String name, ItemMaterial material) {
		setName(name);
		setAmount(1);
		setMaterial(material);
		setItemType(ItemType.ITEM);
	}
	
	public Item(String name, ItemMaterial material, int amount) {
		setName(name);
		setAmount(amount);
		setMaterial(material);
		setItemType(ItemType.ITEM);
	}

	public ItemMaterial getMaterial() {
		return material;
	}

	public void setMaterial(ItemMaterial material) {
		this.material = material;
		switch(material) {
			case DIAMOND_ORE:
			{
				setSprite(new Sprite(SpriteData.DIAMOND_ORE_SPRITE.getSprite()));
				break;
			}
			case DIAMOND:
			{
				setSprite(new Sprite(SpriteData.DIAMOND_SPRITE.getSprite()));
				break;
			}
			default:
			{
				setSprite(null);
				break;
			}
		}
	}
}
