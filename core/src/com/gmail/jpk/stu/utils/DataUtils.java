package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.sprites.WeaponFireSprite;

public class DataUtils {
	
	private static final Array<WeaponFireSprite> weaponSprites = new Array<WeaponFireSprite>();
	private static final int maxWeaponSprites = 100;
	
	public static void addWeaponFireSprite(WeaponFireSprite sprite) {
		if(weaponSprites.size + 1 <= DataUtils.maxWeaponSprites) {
			DataUtils.weaponSprites.add(sprite);
		}
	}
	
	public static void removeWeaponFireSprite(int i) {
		DataUtils.weaponSprites.removeIndex(i);
	}
	
	public static WeaponFireSprite[] getWeaponSprites() {
		WeaponFireSprite[] toReturn = new WeaponFireSprite[DataUtils.weaponSprites.size];
		for(int i = 0; i < DataUtils.weaponSprites.size; i++) {
			toReturn[i] = DataUtils.weaponSprites.get(i);
		}
		return toReturn;
	}
}
