package com.gmail.jpk.stu.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.sprites.Renderable;
import com.gmail.jpk.stu.utils.MouseUtils;

public abstract class Component extends Renderable {
	
	private ComponentGui parent; // The parent of this component
	private Sprite displayComponent; // The displayable component
	
	protected Component() {
		this.parent = null;
		this.setX(0.0f);
		this.setY(0.0f);
		this.setVisible(true);
		this.setInteractable(true);
	}
	
	protected Component(float x, float y, boolean isVisible, boolean isInteractable) {
		this.parent = null;
		this.setX(x);
		this.setY(y);
		this.setVisible(isVisible);
		this.setInteractable(isInteractable);
	}
	
	protected abstract void onInteract();

	public ComponentGui getParent() {
		return parent;
	}

	public void setParent(ComponentGui parent) {
		this.parent = parent;
	}
	
	public Sprite getDisplay() {
		return displayComponent;
	}
	
	public void setDisplay(Sprite displayComponent) {
		this.displayComponent = displayComponent;
	}
	
	protected boolean checkInput() {
		if(Gdx.input.justTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			if(MouseUtils.isTouched(this.getParent().getX() + this.getX(), this.getParent().getY() + this.getY(), this.displayComponent.getWidth(), this.displayComponent.getHeight(), x, y)) {
				onInteract();
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(getParent().isVisible()) {
			if(isVisible()) {
				batch.draw(getDisplay(), this.getParent().getX() + this.getX(), this.getParent().getY() + this.getY());
				if(isInteractable()) {
					checkInput();
				}
			}
		}
	}
}
