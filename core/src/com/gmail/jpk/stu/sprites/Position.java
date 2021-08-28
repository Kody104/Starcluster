package com.gmail.jpk.stu.sprites;

public class Position {
	
	private float x;
	private float y;
	
	public Position(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void setXY(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float[] getXY() {
		float[] toReturn = {x,y};
		return toReturn;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	/**
	 * Adds a position's points to this position's points.
	 * @param add	The position to add
	 * @return	A new position
	 */
	public Position add(Position add) {
		return new Position(this.getX() + add.getX(), this.getY() + add.getY());
	}
	
	/**
	 * Subtracts a position's points from this position's points.
	 * @param subtract	The position to subtract
	 * @return	A new position
	 */
	public Position subtract(Position subtraction) {
		return new Position(this.getX() - subtraction.getX(), this.getY() - subtraction.getY());
	}
}
