package com.gmail.jpk.stu.utils;

public class DebugHandler {
	
	public enum ErrorReason {
		UNKNOWN_FACTION_OPINION;
	}
	
	/**
	 * Prints an error with the string given.
	 * @param s	The error output
	 */
	public static void err(String s) {
		System.err.println(s);
	}
	
	/**
	 * Prints a generic error depending on the ErrorReason given.
	 * @param e	The error reason
	 */
	public static void err(ErrorReason e) {
		switch(e) {
			case UNKNOWN_FACTION_OPINION:
			{
				DebugHandler.err("Unknown output from PlayerStats.getFactionOpinion(f)!");
				break;
			}
		}
	}
}
