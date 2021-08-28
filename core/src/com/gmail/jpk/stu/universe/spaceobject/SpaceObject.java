package com.gmail.jpk.stu.universe.spaceobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gmail.jpk.stu.sprites.PositionSprite;
import com.gmail.jpk.stu.sprites.SpriteType;

public abstract class SpaceObject extends PositionSprite {

	private String name;

	public SpaceObject(Sprite displaySprite, String name, float x, float y) {
		super(displaySprite, SpriteType.SPACE_OBJECT, x, y);
		this.name = name;
	}

	public SpaceObject(Sprite displaySprite, SpriteType spriteType) {
		super(displaySprite, spriteType, 0.0f, 0.0f);
		this.name = "";
	}

	public SpaceObject(Sprite displaySprite, String name) {
		super(displaySprite, SpriteType.SPACE_OBJECT, 0.0f, 0.0f);
		this.name = name;
	}

	public SpaceObject(Sprite displaySprite, SpriteType spriteType, String name, float x, float y) {
		super(displaySprite, spriteType, x, y);
		this.name = name;
	}

	public SpaceObject(Sprite displaySprite, SpriteType spriteType, String name) {
		super(displaySprite, spriteType, 0.0f, 0.0f);
		this.name = name;
	}

	/**
	 * Creates a clone of a space object
	 * 
	 * @param toClone The space object to clone
	 * @return A clone of the given object
	 */
	public static PlanetaryObject cloneObject(PlanetaryObject toClone) {
		return new PlanetaryObject(toClone.getDisplaySprite(), toClone.getCitySprite(), toClone.getName(),
				toClone.getSpriteType(), toClone.getX(), toClone.getY(), toClone.getPopulation(),
				toClone.getTradeCurrency(), toClone.getEconomyStrength());
	}

	public static AsteroidObject cloneObject(AsteroidObject toClone) {
		return new AsteroidObject(toClone.getDisplaySprite(), toClone.getSpriteType(), toClone.getHealth());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
