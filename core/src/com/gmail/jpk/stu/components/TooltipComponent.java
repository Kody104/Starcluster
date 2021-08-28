package com.gmail.jpk.stu.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.sprites.DisplayableText;
import com.gmail.jpk.stu.utils.SpriteData;

public class TooltipComponent extends Component {
	
	private DisplayableText text;
	
	public TooltipComponent(final BitmapFont font) {
		text = new DisplayableText(font, "Tooltip Text");
		this.setDisplay(SpriteData.TOOLTIP_BACKGROUND.getSprite());
		text.setWrapping(true);
		text.setTargetWidth((int) (getDisplay().getWidth() - 10.0f));
		this.setInteractable(false);
		this.setVisible(false);
	}
	
	public DisplayableText getText() {
		return text;
	}
	
	public void setText(DisplayableText text) {
		this.text = text;
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		if(text != null) {
			text.setX(x + 5.0f);
		}
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		if(text != null) {
			text.setY(y + (this.getDisplay().getHeight() - 5.0f));
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(checkInput()) {
			if(!isVisible()) {
				setVisible(true);
			}
		}
		else {
			if(isVisible()) { 
				setVisible(false);
			}
		}
		if(isVisible()) {
			batch.draw(getDisplay(), this.getX(), this.getY());
			text.render(batch);
		}
	}
	
	@Override
	protected void onInteract() {
		// Doesn't need to function
	}

	@Override
	public void update() {
		
	}
}
