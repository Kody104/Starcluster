package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class BasicSprite extends Renderable {
	
	private Sprite displaySprite; // The sprite this displays
	private SpriteType spriteType; // The type of sprite this is
	private float originWidth, originHeight; // The original width and height of this sprite
	private float size; // The current size of this sprite
	
	public BasicSprite() {
		displaySprite = null;
		spriteType = null;
		originWidth = 0.0f;
		originHeight = 0.0f;
		size = 1.0f;
		setVisible(true);
		setInteractable(true);
		setX(0.0f);
		setY(0.0f);
	}
	
	public BasicSprite(Sprite displaySprite, SpriteType spriteType) {
		this.displaySprite = displaySprite;
		this.spriteType = spriteType;
		this.originWidth = displaySprite.getWidth();
		this.originHeight = displaySprite.getHeight();
		this.size = 1.0f;
		setVisible(true);
		setInteractable(true);
		setX(0.0f);
		setY(0.0f);
	}
	
	public BasicSprite(Sprite displaySprite, SpriteType spriteType, float x, float y) {
		this.displaySprite = displaySprite;
		this.spriteType = spriteType;
		this.originWidth = displaySprite.getWidth();
		this.originHeight = displaySprite.getHeight();
		this.size = 1.0f;
		setVisible(true);
		setInteractable(true);
		setX(x);
		setY(y);
	}

	public Sprite getDisplaySprite() {
		return displaySprite;
	}

	public void setDisplaySprite(Sprite displaySprite) {
		this.displaySprite = displaySprite;
	}
	
	public SpriteType getSpriteType() {
		return spriteType;
	}

	public void setSpriteType(SpriteType spriteType) {
		this.spriteType = spriteType;
	}

	public void dispose() {
		displaySprite.getTexture().dispose();
	}
	
	public float getHeight() {
		if(displaySprite != null) {
			return displaySprite.getHeight() * size;
		}
		return 0.0f;
	}
	
	public float getWidth() {
		if(displaySprite != null) {
			return displaySprite.getWidth() * size;
		}
		return 0.0f;
	}
	
	public float getOriginalWidth() {
		return originWidth;
	}
	
	public float getOriginalHeight() {
		return originHeight;
	}
	
	public float getSize() {
		return size;
	}
	
	public void setSize(float size) {
		this.size = size;
		displaySprite.setSize(originWidth * size, originHeight * size);
	}
	
	/**
	 * Sets the rotation of the display sprite within 0 to 360 degrees
	 */
	public void setRotation(float degrees) {
		if(degrees < 0) {
			int x = (int)Math.ceil(degrees / -360);
			degrees += 360 * x;
		}
		else if(degrees > 360) {
			int x = (int)Math.ceil(degrees / 360);
			degrees -= 360 * x;
		}
		getDisplaySprite().setRotation(degrees);
	}
	
	/**
	 * Returns the rotation of the display sprite
	 * @return	The rotation
	 */
	public float getRotation() {
		return getDisplaySprite().getRotation();
	}
	
	/**
	 * Returns the rotation in a way that math can be done to it
	 * @return	Math-usable rotation
	 */
	public float getCalculatableRotation() {
		if(this.getRotation() == 0.0f) {
			return this.getRotation() + 0.01f;
		}
		else {
			return this.getRotation();
		}
	}
	
	/**
	 * Returns a string showing what direction the sprite is facing
	 * @return	The name of the quadrant
	 */
	public String getQuadrant() {
		float rotation = getRotation();
		if(rotation >= 0 && rotation <= 45) {
			return "north";
		}
		else if(rotation > 45 && rotation <= 90) {
			return "northwest";
		}
		else if(rotation > 90 && rotation <= 135) {
			return "west";
		}
		else if(rotation > 135 && rotation <= 180) {
			return "southwest";
		}
		else if(rotation > 180 && rotation <= 225) {
			return "south";
		}
		else if(rotation > 225 && rotation <= 270) {
			return "southeast";
		}
		else if(rotation > 270 && rotation <= 315) {
			return "east";
		}
		else if(rotation > 315 && rotation <= 361) {
			return "northeast";
		}
		else {
			System.out.println(rotation);
			return "ERROR";
		}
	}
	
	/**
	 * Check if this sprite overlaps another sprite
	 * @param s	The other sprite
	 * @return	True/False
	 */
	public boolean overlaps(BasicSprite s) {
		if(displaySprite != null) {
			return displaySprite.getBoundingRectangle().overlaps(s.getBoundingRectangle());
		}
		return false;
	}
	
	/**
	 * Get the bounding rectangle of this sprite.
	 * @return	The bounding rectangle
	 */
	public Rectangle getBoundingRectangle() {
		return displaySprite.getBoundingRectangle();
	}
	
	@Override
	public void setX(float x) {
		if(displaySprite != null) {
			displaySprite.setX(x);
		}
	}
	
	@Override
	public float getX() {
		if(displaySprite != null) {
			return displaySprite.getX();
		}
		return 0.0f;
	}
	
	@Override
	public void setY(float y) {
		if(displaySprite != null) {
			displaySprite.setY(y);
		}
	}
	
	@Override
	public float getY() {
		if(displaySprite != null) {
			return displaySprite.getY();
		}
		return 0.0f;
	}
	
	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isVisible()) {
			displaySprite.draw(batch);
		}
	}
}
