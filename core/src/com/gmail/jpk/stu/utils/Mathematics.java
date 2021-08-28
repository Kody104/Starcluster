package com.gmail.jpk.stu.utils;

import java.util.Random;

import com.gmail.jpk.stu.sprites.Position;

public class Mathematics {
	
	public static final Random rand = new Random();
	
	/**
	 * Finds a point on the diameter of a circle (represented by the angle) and returns it.
	 * @param angle	The angle of the circle to return the point of
	 * @param radius	The radius of the circle
	 * @param origin	The origin point of the circle (the exact middle)
	 * @return	The position of the point on the diameter
	 */
	public static Position findCirclePoint(float angle, float radius, Position origin) {
		/*
		 * Trig formula for finding a point on a circle
		 * a = center x of circle, b = center y of circle, r = radius, angle = radian angle
		 * 
		 * x = a + r  * sin(angle)
		 * y = b + r * cos(angle)
		 */
		angle = (float)(angle * (Math.PI / 180.0f)); // Convert angle to radians
		float pointX = (float)(origin.getX() + -radius * Math.sin(angle));
		float pointY = (float)(origin.getY() + radius * Math.cos(angle));
		return new Position(pointX, pointY);
	}
	
	/**
	 * Returns the position of the center of the screen. Can use the player ship's position as an anchor.
	 * @param anchor	The anchor to the middle of the screen
	 * @return	The position of the center of the screen
	 */
	public static Position getCenterScreenPosition(Position anchor) {
		return new Position((anchor.getX() + 400.0f), (anchor.getY() + 240.0f));
	}
}
