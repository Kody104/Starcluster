package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DisplayableText extends Renderable {
	
	public enum TextType {
		H1, H2, H3, H4, H5, H6, NORMAL, S1, S2, S3;
	}
	
	private final BitmapFont font; // The game font object
	private Color color; // The color of the text
	private GlyphLayout layout; // Used for getting text width and height
	private boolean wrapping; // If we should wrap the words around or not
	private int targetWidth; // The target width to wrap around
	private String text; // The displayed text
	private TextType textType; // The format of the displayed text
	
	/**
	 * Constructor for a displayable text.
	 * @param font	The game font object
	 * @param text	The text to display
	 */
	public DisplayableText(final BitmapFont font, String text) {
		this.font = font;
		this.color = font.getColor();
		this.setVisible(true);
		this.layout = new GlyphLayout();
		this.wrapping = false;
		this.text = text;
		this.textType = TextType.NORMAL;
		this.layout.setText(this.font, this.text);
		this.setX(0.0f);
		this.setY(0.0f);
	}
	
	/**
	 * Constructor for a displayable text.
	 * @param font	The game font object
	 * @param text	The text to display
	 * @param textType	The format for the text
	 */
	public DisplayableText(final BitmapFont font, String text, TextType textType) {
		this.font = font;
		this.color = font.getColor();
		this.setVisible(true);
		this.layout = new GlyphLayout();
		this.wrapping = false;
		this.text = text;
		this.textType = textType;
		this.layout.setText(this.font, this.text);
		this.setX(0.0f);
		this.setY(0.0f);
	}
	
	/**
	 * Constructor for a displayable text.
	 * @param font	The game font object
	 * @param text	The text to display
	 * @param x	The x value of this text
	 * @param y	The y value of this text
	 */
	public DisplayableText(final BitmapFont font, String text, float x, float y) {
		this.font = font;
		this.color = font.getColor();
		this.setVisible(true);
		this.layout = new GlyphLayout();
		this.wrapping = false;
		this.text = text;
		this.textType = TextType.NORMAL;
		this.layout.setText(this.font, this.text);
		this.setX(x);
		this.setY(y);
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setColor(float r, float g, float b, float a) {
		this.color = new Color(r, g, b, a);
	}
	
	public GlyphLayout getLayout() {
		return layout;
	}

	public void setLayout(GlyphLayout layout) {
		this.layout = layout;
	}

	public boolean isWrapping() {
		return wrapping;
	}

	public void setWrapping(boolean wrapping) {
		this.wrapping = wrapping;
	}

	public int getTargetWidth() {
		return targetWidth;
	}

	public void setTargetWidth(int targetWidth) {
		this.targetWidth = targetWidth;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		layout.setText(font, text);
	}

	public TextType getTextType() {
		return textType;
	}

	public void setTextType(TextType textType) {
		this.textType = textType;
	}

	public BitmapFont getFont() {
		return font;
	}
	
	/**
	 * Returns the text width of this object.
	 * @return	The text width
	 */
	public float getTextWidth() {
		font.getData().setScale(getFontScale());
		layout.setText(font, text);
		float width = layout.width;
		font.getData().setScale(1.0f);
		return width;
	}
	
	/**
	 * Returns the text height of this object.
	 * @return	The text height
	 */
	public float getTextHeight() {
		font.getData().setScale(getFontScale());
		layout.setText(font, text);
		float height = layout.height;
		font.getData().setScale(1.0f);
		return height;
	}
	
	/**
	 * Returns the font scale depending on the texttype.
	 * @return	The font scale
	 */
	public float getFontScale() {
		switch(textType) {
			case H1:
			{
				return 2.5f;
			}
			case H2:
			{
				return 2.25f;
			}
			case H3:
			{
				return 2.0f;
			}
			case H4:
			{
				return 1.75f;
			}
			case H5:
			{
				return 1.5f;
			}
			case H6:
			{
				return 1.25f;
			}
			case NORMAL:
			{
				return 1.0f;
			}
			case S1:
			{
				return 0.8f;
			}
			case S2:
			{
				return 0.6f;
			}
			case S3:
			{
				return 0.4f;
			}
			default:
			{
				return 0.1f;
			}
		}
	}
	
	/**
	 * <b> This needs to be overidden to do things </b>
	 */
	@Override
	public void update() {
		
	}

	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isVisible()) {
			font.getData().setScale(getFontScale());
			font.setColor(color);
			if(wrapping) {
				font.draw(batch, text, getX(), getY(), targetWidth, 1, wrapping);
			}
			else {
				font.draw(batch, text, getX(), getY());
			}
			font.getData().setScale(1.0f);
			font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
			update();
		}
	}
}
