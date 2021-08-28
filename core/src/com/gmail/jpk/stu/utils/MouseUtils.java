package com.gmail.jpk.stu.utils;

import com.gmail.jpk.stu.sprites.PositionSprite;

public class MouseUtils {
	
	/**
	 * If an area of the screen is touched by the mouse pointer
	 * @param targetX	The x corner of the box
	 * @param targetY	The y corner of the box
	 * @param targetWidth	The width of the box
	 * @param targetHeight	The height of the box
	 * @param mouseX	The mouse's x
	 * @param mouseY	The mouse's y
	 * @return	True/False
	 */
	public static boolean isTouched(float targetX, float targetY, float targetWidth, float targetHeight, float mouseX, float mouseY) {
		mouseY = 480.0f - mouseY;
		if(targetX < mouseX && targetX + targetWidth > mouseX &&
				targetY < mouseY && targetY + targetHeight > mouseY) {
			return true;
		}
		return false;
	}
	
	/**
	 * If a sprite on the screen is touched by the mouse pointer
	 * @param sprite	The sprite to check for
	 * @param pointerX	The mouse x
	 * @param pointerY	The mouse y
	 * @return	True/False
	 */
	public static boolean isTouched(PositionSprite sprite, float mouseX, float mouseY) {
		if(sprite.getPosition().getX() < mouseX && sprite.getPosition().getX() + sprite.getDisplaySprite().getWidth() > mouseX &&
				sprite.getPosition().getY() < mouseY && sprite.getPosition().getY() + sprite.getDisplaySprite().getHeight() > mouseY) {
			return true;
		}
		return false;
	}
}
