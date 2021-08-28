package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Renderable {
	
	private float renderTime; // The amount of time since this object has first been rendered
	private boolean isVisible; // If the object is going to be painted or not
	private boolean isInteractable; // If the object can be interacted with by objects
	private float x, y; // The x and y coords of the object
	
	/**
	 * Ran every loop to update things if necessary
	 */
	public abstract void update();
	
	/**
	 * Used to render specific things to the screen by the things that implement this.
	 * @param batch	The spritebatch to output to
	 */
	public void render(SpriteBatch batch) {
		if(isVisible()) {
			renderTime += Gdx.graphics.getDeltaTime();
		}
	}
	
	public float getRenderTime() {
		return renderTime;
	}

	public void setRenderTime(float renderTime) {
		this.renderTime = renderTime;
	}

	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean isInteractable() {
		return isInteractable;
	}
	
	public void setInteractable(boolean isInteractable) {
		this.isInteractable = isInteractable;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
}
