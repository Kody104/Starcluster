package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class KeyUtils {
	public static int KEY_MAP = Keys.M;
	public static int KEY_INVENTORY = Keys.I;
	public static int KEY_TALENT = Keys.N;
	public static int KEY_INTERACT = Keys.F;
	public static int KEY_FORWARD = Keys.W;
	public static int KEY_BACKWARD = Keys.S;
	public static int KEY_TURN_R = Keys.D;
	public static int KEY_TURN_L = Keys.A;
	public static int KEY_FIRE_WEAPON = Keys.SPACE;
	public static int KEY_EXIT = Keys.ESCAPE;
	public static int KEY_SHIFT_L = Keys.SHIFT_LEFT;
	public static int KEY_SHIFT_R = Keys.SHIFT_RIGHT;
	public static int KEY_CTRL_L = Keys.CONTROL_LEFT;
	public static int KEY_CTRL_R = Keys.CONTROL_RIGHT;
	public static int KEY_POWER_SHIELD = Keys.K;
	public static int KEY_POWER_WEAPON = Keys.L;
	public static int KEY_POWER_ENGINE = Keys.J;
	public static int KEY_GRAVE = Keys.GRAVE;
	public static int KEY_BACKSLASH = Keys.BACKSLASH;
	
	public static boolean isKeyPressed(int keyCode) {
		return Gdx.input.isKeyPressed(keyCode);
	}
	
	public static boolean isKeyJustPressed(int keyCode) {
		return Gdx.input.isKeyJustPressed(keyCode);
	}
}
