package com.gmail.jpk.stu.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.sprites.Renderable;

public class ComponentGui extends Renderable {
	
	private Array<Component> children; // All the component children this object has
	
	/**
	 * Constructor for a component gui
	 */
	public ComponentGui() {
		children = new Array<Component>();
		setX(0.0f);
		setY(0.0f);
		setVisible(true);
	}
	
	/**
	 * Clears all children from this object.
	 */
	public void clear() {
		children.clear();
	}
	
	/**
	 * Remove a child from the children array by index.
	 * @param index	The index
	 */
	public void removeChild(int index) {
		if(index < children.size) {
			children.removeIndex(index);
		}
	}
	
	/**
	 * Adds a component to the array of components.
	 * @param component	The component
	 */
	public void addChild(Component component) {
		children.add(component);
		component.setParent(this);
	}
	
	/**
	 * Gets a component from the array by it's index
	 * @param index	The index
	 * @return	The component
	 */
	public Component getChild(int index) {
		return getChildren()[index];
	}

	public Component[] getChildren() {
		Component[] toReturn = new Component[children.size];
		for(int i = 0; i < children.size; i++) {
			toReturn[i] = children.get(i);
		}
		return toReturn;
	}

	public void setChildren(Array<Component> children) {
		this.children = children;
	}

	@Override
	public void render(SpriteBatch batch) {
		if(this.isVisible()) {
			for(Component c : this.getChildren()) {
				c.render(batch);
			}
		}
	}

	@Override
	public void update() {
		
	}
}
