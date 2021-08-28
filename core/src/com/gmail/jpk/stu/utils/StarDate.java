package com.gmail.jpk.stu.utils;

public class StarDate {
	
	private int year;
	private int month;
	private int week;
	private int day;
	
	public StarDate() {
		year = 0;
		month = 0;
		week = 0;
		day = 0;
	}
	
	public StarDate(int year, int month, int week, int day) {
		this.year = year;
		this.month = month;
		this.week = week;
		this.day = day;
	}
	
	public String getStardateAsString() {
		return String.format("%d.%d.%d.%d", year, month, week, day);
	}
	
	public void increment(int days) {
		day += days;
		if(day > 7) {
			int wAdd = day / 7;
			day -= wAdd * 7;
			week += wAdd;
		}
		if(week > 4) {
			int mAdd = week / 4;
			week -= mAdd * 4;
			month += mAdd;
		}
		if(month > 12) {
			int yAdd = month / 12;
			month -= yAdd * 12;
			year += yAdd;
		}
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
}
