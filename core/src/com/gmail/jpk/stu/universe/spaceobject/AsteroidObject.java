package com.gmail.jpk.stu.universe.spaceobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.items.LootTable;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.utils.Mathematics;

public class AsteroidObject extends LootableObject {
	
	private float health;
	private float spinSpeed;
	
	public AsteroidObject(Sprite displaySprite, SpriteType spriteType, float health) {
		super(displaySprite, spriteType, LootTable.DEFAULT_ASTEROID_TABLE);
		this.health = health;
		spinSpeed = Mathematics.rand.nextFloat() * 0.4f;
	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(this.getRotation() + spinSpeed > 360.0f) {
			this.setRotation((getRotation() + spinSpeed) - 360.0f);
		}
		else {
			this.setRotation(getRotation() + spinSpeed);
		}
	}
}
