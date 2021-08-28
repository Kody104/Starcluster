package com.gmail.jpk.stu.talents;

public abstract class BaseTalent {
	
	private final int uniqueID;
	private String name;
	private String description;
	private int curLvl;
	
	protected abstract void initTalent();
	
	public BaseTalent(BoilerPlateTalents key) {
		this.uniqueID = key.getUniqueID();
		curLvl = 0;
	}
	
	/**
	 * Returns the boiler plate talent that was used as the key for this talent
	 * @return	The boiler plate talent
	 */
	public BoilerPlateTalents getKey() {
		for(BoilerPlateTalents bpt : BoilerPlateTalents.values()) {
			if(bpt.getUniqueID() == uniqueID) {
				return bpt;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCurLvl() {
		return curLvl;
	}

	public void setCurLvl(int curLvl) {
		BoilerPlateTalents key = getKey();
		if(key == null) {
			return;
		}
		if(curLvl > key.getMaxLvl()) {
			curLvl = key.getMaxLvl();
		}
		this.curLvl = curLvl;
	}

	public int getUniqueID() {
		return uniqueID;
	}
}
