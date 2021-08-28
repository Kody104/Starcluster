package com.gmail.jpk.stu.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent extends Component {
	
	/**
	 * Constructor for a sprite component for a component gui
	 * @param sprite	The sprite to display
	 */
	public SpriteComponent(Sprite sprite) {
		super(0.0f, 0.0f, true, true);
		this.setDisplay(sprite);
	}
	
	/**
	 * Constructor for a sprite component for a component gui
	 * @param sprite	The sprite to display
	 * @param x	The x of this component in relation to it's parent
	 * @param y	The y of this component in relation to it's parent
	 */
	public SpriteComponent(Sprite sprite, float x, float y) {
		super(x, y, true, true);
		this.setDisplay(sprite);
	}
	
	/**
	 * Constructor for a sprite component for a component gui
	 * @param texture	A texture to create a sprite with
	 */
	public SpriteComponent(Texture texture) {
		super(0.0f, 0.0f, true, true);
		this.setDisplay(new Sprite(texture));
	}
	
	/**
	 * Constructor for a sprite component for a component gui
	 * @param texture	A texture to create a sprite with
	 * @param x	The x of this component in relation to it's parent
	 * @param y	The y of this component in relation to it's parent
	 */
	public SpriteComponent(Texture texture, float x, float y) {
		super(x, y, true, true);
		this.setDisplay(new Sprite(texture));
	}

	public void setSprite(Sprite sprite) {
		this.setDisplay(sprite);
	}
	
	/**
	 * <b>This needs to be overridden!</b>
	 */
	@Override
	protected void onInteract() {
		
	}

	@Override
	public void update() {
		
	}
}
