package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.sprites.DisplayableText.TextType;
import com.gmail.jpk.stu.utils.MouseUtils;
import com.gmail.jpk.stu.utils.SpriteData;

public class NotificationBox extends Renderable {

	private Sprite background; // The background of the notification box
	private final BitmapFont font; // The game font object
	private Array<DisplayableText> notifications; // The list of displayable texts in the box
	private long lastUpdateTime; // The last time we interacted with the fade booleans
	private boolean fadingOut; // If we are currently fading the box out
	private boolean fadingIn; // If we are currently fading the box in
	
	/**
	 * Constructor for the notification box.
	 * @param font	The game font object
	 * @param x	The box's x value
	 * @param y	The box's y value
	 */
	public NotificationBox(final BitmapFont font, float x, float y) {
		this.font = font;
		background = SpriteData.NOTIFICATION_BACKGROUND.getSprite();
		notifications = new Array<DisplayableText>();
		lastUpdateTime = System.currentTimeMillis();
		setVisible(true);
		setInteractable(true);
		this.setX(x);
		this.setY(y);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		background.setX(x);
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		background.setY(y);
	}
	
	/**
	 * Adds a notification text to the notification box. <br>
	 * <b> Colors aren't working yet! </b>
	 * @param text	The notification text to display
	 * @param color	The color of the text to display
	 */
	public void addNotification(String text, Color color) {
		DisplayableText display = new DisplayableText(font, text, TextType.NORMAL);
		display.setColor(color);
		addNotification(display);
	}
	
	/**
	 * Adds a notification text to the notification box.<br>
	 * <b> Colors aren't working yet! </b>
	 * @param text	The notification text to display
	 * @param textType	The format of the text to display
	 * @param color	The color of the text to display
	 */
	public void addNotification(String text, TextType textType, Color color) {
		DisplayableText display = new DisplayableText(font, text, textType);
		display.setColor(color);
		addNotification(display);
	}
	
	/**
	 * Adds a notification text to the notification box.
	 * @param text	The notification text to display
	 * @param textType	The format of the text to display
	 */
	public void addNotification(String text, TextType textType) {
		DisplayableText display = new DisplayableText(font, text, textType);
		addNotification(display);
	}
	
	/**
	 * Adds a notification text to the notification box.
	 * @param text	The notification text to display
	 */
	public void addNotification(String text) {
		DisplayableText display = new DisplayableText(font, text, TextType.NORMAL);
		addNotification(display);
	}
	
	/**
	 * Positions and handles all the notifications when adding a new notifcation
	 * @param text	The new text to display
	 */
	private void addNotification(DisplayableText text) {
		text.setX(background.getX() + 5.0f);
		text.setY(background.getY() + (text.getTextHeight() + 5.0f));
		text.setTargetWidth((int) (background.getWidth() - 10.0f));
		// Sort backwards through the notifications
		for(int i = notifications.size - 1; i >= 0; i--) {
			DisplayableText dt = notifications.get(i);
			float setY = dt.getY() + (dt.getTextHeight() + 5.0f);
			// If the notifications goes outside the window, delete it
			if(setY + dt.getTextHeight() > background.getY() + background.getHeight()) {
				notifications.removeIndex(i);
				continue;
			}
			else {
				dt.setY(setY);
			}
		}
		notifications.add(text);
		fadein();
	}
	
	/**
	 * Removes a notification text from the notification box.
	 * @param index	The index to remove
	 */
	public void removeNotification(int index) {
		notifications.removeIndex(index);
	}
	
	/**
	 * Make the notification box fade in.
	 */
	public void fadein() {
		if(fadingOut) {
			fadingOut = false;
		}
		fadingIn = true;
	}
	
	/**
	 * Make the notification box fade out.
	 */
	public void fadeout() {
		if(fadingIn) {
			fadingIn = false;
		}
		fadingOut = true;
	}
	
	/**
	 * Updates the notification window
	 */
	@Override
	public void update() {
		float x = Gdx.input.getX();
		float y = Gdx.input.getY();
		// If the box is touched by the mouse pointer, we make it fade in.
		if(Gdx.input.justTouched()) {
			if(MouseUtils.isTouched(background.getX(), background.getY(), background.getWidth(), background.getHeight(), x, y)) {
				if(background.getColor().a != 1.0f && !fadingOut && !fadingIn) {
					fadein();
				}
				lastUpdateTime = System.currentTimeMillis();
			}
		}
		// Else if the notification box is visible and our mouse is inside it, we keep it up.
		else if(MouseUtils.isTouched(background.getX(), background.getY(), background.getWidth(), background.getHeight(), x, y)) {
			if(background.getColor().a == 1.0f && !fadingOut && !fadingIn) {
				lastUpdateTime = System.currentTimeMillis();
			}
		}
		// Otherwise we wait four seconds until we make it fade out.
		else if(lastUpdateTime + 4000L < System.currentTimeMillis()) {
			if(background.getColor().a != 0.0f && !fadingOut && !fadingIn) {
				fadeout();
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(isVisible()) {
			background.draw(batch);
			// If we are currently fading the notification box out
			if(fadingOut) {
				// Calculate before doing to prevent overflow
				if((background.getColor().a - 0.01f) <= 0.0f) {
					background.setAlpha(0.0f);
					fadingOut = false;
					lastUpdateTime = System.currentTimeMillis();
				}
				// Fade it out
				else {
					background.setAlpha(background.getColor().a - 0.01f);
					lastUpdateTime = System.currentTimeMillis();
				}
			}
			// If we are currently fading the notification box in
			else if(fadingIn) {
				// Calculate before doing to prevent overflow
				if((background.getColor().a + 0.04f) >= 1.0f) {
					background.setAlpha(1.0f);
					fadingIn = false;
					lastUpdateTime = System.currentTimeMillis();
				}
				// Fade it in
				else {
					background.setAlpha(background.getColor().a + 0.04f);
					lastUpdateTime = System.currentTimeMillis();
				}
			}
			// Fade the text at the same rate
			for(DisplayableText text : notifications) {
				text.getFont().setColor(1.0f, 1.0f, 1.0f, background.getColor().a);
				text.render(batch);
				text.getFont().setColor(1.0f, 1.0f, 1.0f, 1.0f);
			}
			// Interact with it
			if(isInteractable()) {
				update();
			}
			/*else {
				if(background.getColor().a != 0.0f && !fadingOut) {
					fadeout();
				}
			}*/
		}
	}

}
