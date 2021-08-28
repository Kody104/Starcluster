package com.gmail.jpk.stu.universe.spaceobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.items.Item;
import com.gmail.jpk.stu.items.ItemMaterial;
import com.gmail.jpk.stu.items.LootTable;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.utils.Mathematics;

public abstract class LootableObject extends SpaceObject {
	
	private LootTable lootTable;
	
	public LootableObject(Sprite displaySprite, SpriteType spriteType, LootTable lootTable) {
		super(displaySprite, spriteType);
		this.lootTable = lootTable;
	}
	
	/**
	 * Returns the loot to drop from this asteroid
	 * @return	The array of items
	 */
	public Item[] getDroppedLoot() {
		Array<Item> toDrop = new Array<Item>();
		for(ItemMaterial material : lootTable.getItemMaterials()) {
			if(Mathematics.rand.nextInt(100) + 1 <= lootTable.getDropChance(material)) {
				int min = lootTable.getMinDropAmount(material);
				int max = lootTable.getMaxDropAmount(material);
				int amount = Mathematics.rand.nextInt(max - min) + min;
				toDrop.add(new Item(material, amount));
			}
		}
		Item[] toReturn = new Item[toDrop.size];
		for(int i = 0; i < toReturn.length; i++) {
			toReturn[i] = toDrop.get(i);
		}
		return toReturn;
	}
}
