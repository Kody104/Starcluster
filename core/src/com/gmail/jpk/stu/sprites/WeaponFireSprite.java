package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.spaceships.ShipWeapon;

public class WeaponFireSprite extends PositionSprite {
	
	private ShipWeapon parent; // Who shot this weapon
	private float speed; // Speed of the weapon shot
	
	
	public WeaponFireSprite(Sprite sprite, float x, float y, ShipWeapon parent, float speed) {
		super(sprite, SpriteType.WEAPON_SPRITE, x, y);
		this.parent = parent;
		this.speed = speed;
	}
	
	public WeaponFireSprite(Sprite sprite, ShipWeapon parent, float speed) {
		super(sprite, SpriteType.WEAPON_SPRITE, 0.0f, 0.0f);
		this.speed = speed;
		this.parent = parent;
	}
	
	public WeaponFireSprite() {
		super();
		this.parent = null;
		speed = 0.0f;
	}
	
	public ShipWeapon getParent() {
		return parent;
	}
	
	public void setParent(ShipWeapon parent) {
		this.parent = parent;
	}
	
	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	@Override
	public void update() {
		Position position = getPosition();
		// Radial movement
		float radian = (float)(getRotation() * (Math.PI / 180.0f));
		float changeX = (float)(speed * Math.sin(radian));
		float changeY = (float)(speed * Math.cos(radian));
		/*if(parent.getWeaponType() == WeaponType.TORPEDO) {
			float render = getRenderTime();
			if(render <= 0.35f) {
				changeX *= render * 0.05f;
				changeY *= render * 0.05f;
			}
		}*/
		position.setX(position.getX() + -changeX);
		position.setY(position.getY() + changeY);
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isVisible()) {
			update();
		}
	}
}
