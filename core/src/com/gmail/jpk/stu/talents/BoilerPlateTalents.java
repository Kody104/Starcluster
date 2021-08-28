package com.gmail.jpk.stu.talents;

public enum BoilerPlateTalents {
	
	DOMINATOR_1(1000, 1), DOMINATOR_2(1001, 3), DOMINATOR_3(1002, 1),
	DUELER_1(1100, 1), DUELER_2(1101, 1), DUELER_3(1102, 1), DUELER_4(1103, 2),
	IMMOBILIZATION_1(1200, 1), IMMOBILIZATION_2(1201, 2), IMMOBILIZATION_3(1202, 1), IMMOBILIZATION_4(1203, 1),
	BULWARK_1(1300, 1), BULWARK_2(1301, 1), BULWARK_3(1302, 1), BULWARK_4(1303, 1), BULWARK_5(1304, 1),
	RETALIATION_1(1400, 1), RETALIATION_2(1401, 3), RETALIATION_3(1402, 1),
	PARRY_1(1500, 1), PARRY_2(1501, 1), PARRY_3(1502, 1), PARRY_4(1503, 1), PARRY_5(1504, 1);
	
	private int uniqueID; // The unique id of this talent
	private int maxLvl; // The max level this talent can have
	
	BoilerPlateTalents(int uniqueID, int maxLvl) {
		this.uniqueID = uniqueID;
		this.maxLvl = maxLvl;
	}
	
	public int getUniqueID() {
		return uniqueID;
	}
	
	public int getMaxLvl() {
		return maxLvl;
	}
}
