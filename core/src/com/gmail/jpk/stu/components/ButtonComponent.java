package com.gmail.jpk.stu.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gmail.jpk.stu.utils.MouseUtils;

public class ButtonComponent extends Component {

	private Sprite defaultSprite; // The regular button texture
	private Sprite highlightSprite; // The highlighted button texture
	private Sprite disableSprite; // The disabled button texture
	private boolean isHovered; // If the button is being hovered over
	
	/**
	 * Constructor for a button component for a component gui
	 */
	public ButtonComponent() {
		
	}
	
	/**
	 * Constructor for a button component for a component gui
	 * @param defaultSprite	The texture of the button
	 * @param highlightSprite The texture of the button when highlighted
	 * @param disableSprite	The texture of the button when disabled
	 */
	public ButtonComponent(Sprite defaultSprite, Sprite highlightSprite, Sprite disableSprite) {
		super();
		this.defaultSprite = defaultSprite;
		this.highlightSprite = highlightSprite;
		this.disableSprite = disableSprite;
		this.setDisplay(defaultSprite);
		this.isHovered = false;
	}
	
	/**
	 * Constructor for a button component for a component gui
	 * @param defaultSprite	The texture of the button
	 * @param highlightSprite The texture of the button when highlighted
	 * @param disableSprite	The texture of the button when disabled
	 * @param x	The x of this component in relation to it's parent
	 * @param y	The y of this component in relation to it's parent
	 */
	public ButtonComponent(Sprite defaultSprite, Sprite highlightSprite, Sprite disableSprite, float x, float y) {
		super(x, y, true, true);
		this.defaultSprite = defaultSprite;
		this.highlightSprite = highlightSprite;
		this.disableSprite = disableSprite;
		this.setDisplay(defaultSprite);
		this.isHovered = false;
	}
	
	public Sprite getDefaultSprite() {
		return defaultSprite;
	}

	public void setDefaultSprite(Sprite defaultSprite) {
		this.defaultSprite = defaultSprite;
	}
	
	public Sprite getHighlightSprite() {
		return highlightSprite;
	}
	
	public void setHighlightSprite(Sprite highlightSprite) {
		this.highlightSprite = highlightSprite;
	}
	
	public Sprite getDisableSprite() {
		return disableSprite;
	}
	
	public void setDisableSprite(Sprite disableSprite) {
		this.disableSprite = disableSprite;
	}
	
	public boolean isHovered() {
		return isHovered;
	}
	
	public void setHovered(boolean isHovered) {
		this.isHovered = isHovered;
		if(isInteractable()) {
			if(isHovered && (getDisplay() != highlightSprite)) {
				this.setDisplay(highlightSprite);
			}
			else if(!isHovered && (getDisplay() != defaultSprite)) {
				this.setDisplay(defaultSprite);
			}
		}
	}
	
	@Override
	public void setInteractable(boolean isInteractable) {
		super.setInteractable(isInteractable);
		if(isInteractable) {
			this.setDisplay(defaultSprite);
		}
		else {
			this.setDisplay(disableSprite);
		}
	}

	@Override
	protected boolean checkInput() {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		if(MouseUtils.isTouched(this.getParent().getX() + this.getX(), this.getParent().getY() + this.getY(), this.getDisplay().getWidth(), this.getDisplay().getHeight(), x, y)) {
			setHovered(true);
		}
		else {
			setHovered(false);
		}
		return super.checkInput();
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
