package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PositionSprite extends BasicSprite {
 
	private Position position; // The position in universe for this sprite
	
	public PositionSprite() {
		super();
		this.position = new Position(0.0f, 0.0f);
	}
	
	public PositionSprite(Sprite sprite, SpriteType spriteType) {
		super(sprite, spriteType);
		this.position = new Position(0.0f, 0.0f);
	}
	
	public PositionSprite(Sprite sprite, SpriteType spriteType, float x, float y) {
		super(sprite, spriteType, x, y);
		this.position = new Position(x, y);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
	}
}
