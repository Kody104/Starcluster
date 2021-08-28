package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public enum AtlasData {
	WEAPON_ATLAS("./spaceships/weapons/weapons.txt"), BUTTON_ATLAS("./planets/interaction/buttons.txt"),
	SPACESHIP_ATLAS("./spaceships/FactionOne/sprites.txt"), MINIMAP_ATLAS("./map/icons/minimap-sprites.txt"),
	S_PLANET_ATLAS("./planets/small/sm-planets.txt"), X_PLANET_ATLAS("./planets/xsmall/x-planets.txt"), TALENT_ATLAS("./talents/sprites.txt");
	
	private final TextureAtlas atlas;
	
	AtlasData(String filepath) {
		this.atlas = AssetUtils.getAtlas(filepath);
	}
	
	/**
	 * Create a sprite from the specified atlas and return it.
	 * @param data	The atlas to create the sprite from
	 * @param spriteName	The sprite's name in the atlas
	 * @return	The created sprite
	 */
	public static Sprite createSprite(AtlasData data, String spriteName) {
		return data.getAtlas().createSprite(spriteName);
	}
	
	/**
	 * Creates a sprite from the index given.
	 * @param data	The atlas to create the sprite from.
	 * @param index	The index to get
	 * @return	The sprite
	 */
	public static Sprite createSprite(AtlasData data, int index) {
		return data.getAtlas().createSprites().get(index);
	}
	
	/**
	 * Creates a random sprite from the atlas data given to it.
	 * @param data	The atlas data to return the random sprite from
	 * @return	The created random sprite
	 */
	public static Sprite createRandomSprite(AtlasData data) {
		int random = Mathematics.rand.nextInt(data.getAtlas().createSprites().size);
		return data.getAtlas().createSprites().get(random);
	}
	
	public TextureAtlas getAtlas() {
		return atlas;
	}
}
