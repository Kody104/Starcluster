package com.gmail.jpk.stu.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.sprites.DisplayableText;
import com.gmail.jpk.stu.utils.MouseUtils;

public class TextComponent extends Component {
	
	private final BitmapFont font;
	private DisplayableText textfield;
	
	/**
	 * Constructor for a text component for a component gui
	 * @param font	The game font to pass
	 * @param text	The text to display
	 */
	public TextComponent(final BitmapFont gameFont, String text) {
		super(0.0f, 0.0f, true, false);
		this.font = gameFont;
		this.textfield = new DisplayableText(font, text) {
			@Override
			public void render(SpriteBatch batch) {
				if(isVisible()) {
					font.getData().setScale(getFontScale());
					font.setColor(getColor());
					if(isWrapping()) {
						font.draw(batch, getText(), getParent().getX() + getX(), getParent().getY() + getY(), getTargetWidth(), 1, isWrapping());
					}
					else {
						font.draw(batch, getText(), getParent().getX() + getX(), getParent().getY() + getY());
					}
					font.getData().setScale(1.0f);
					font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
					update();
				}
			}
		};
	}
	
	/**
	 * Constructor for a text component for a component gui
	 * @param font	The game font to pass
	 * @param text	The text to display
	 * @param x	The x of this text component in relation to it's parent
	 * @param y	The y of this text component in relation to it's parent
	 */
	public TextComponent(final BitmapFont gameFont, String text, float x, float y) {
		super(x, y, true, false);
		this.font = gameFont;
		this.textfield = new DisplayableText(font, text) {
			@Override
			public void render(SpriteBatch batch) {
				if(isVisible()) {
					font.getData().setScale(getFontScale());
					font.setColor(getColor());
					if(isWrapping()) {
						font.draw(batch, getText(), getParent().getX() + getX(), getParent().getY() + getY(), getTargetWidth(), 1, isWrapping());
					}
					else {
						font.draw(batch, getText(), getX(), getY());
					}
					font.getData().setScale(1.0f);
					font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
					update();
				}
			}
		};
	}
	
	public DisplayableText getTextField() {
		return textfield;
	}
	
	public void setTextField(DisplayableText textfield) {
		this.textfield = textfield;
	}
	
	@Override
	public boolean isVisible() {
		if(textfield != null) {
			return textfield.isVisible();
		}
		return false;
	}
	
	public void setVisible(boolean isVisible) {
		if(textfield != null) {
			textfield.setVisible(isVisible);
		}
	}
	
	@Override
	public float getX() {
		if(textfield != null) {
			return textfield.getX();
		}
		return 0.0f;
	}
	
	@Override
	public float getY() {
		if(textfield != null) {
			return textfield.getY();
		}
		return 0.0f;
	}
	
	@Override
	public void setX(float x) {
		if(textfield != null) {
			textfield.setX(x);
		}
	}
	
	@Override
	public void setY(float y) {
		if(textfield != null) {
			textfield.setY(y);
		}
	}
	
	@Override
	protected boolean checkInput() {
		if(Gdx.input.justTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			if(MouseUtils.isTouched(this.getParent().getX() + this.getX(), this.getParent().getY() +  this.getY(), textfield.getTextWidth(), textfield.getTextHeight(), x, y)) {
				onInteract();
				return true;
			}
		}
		return false;
	}
	
	@Override
	protected void onInteract() {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		if(getParent().isVisible()) {
			if(isVisible()) {
				textfield.render(batch);
				if(isInteractable()) {
					checkInput();
				}
			}
		}
	}

	@Override
	public void update() {
		
	}

}
