package com.gmail.jpk.stu.spaceships;

public enum Weapons {
	LASER_MK_1("Laser Mk1", WeaponType.LASER, 500L, 8.0f), LASER_MK_2("Laser Mk2", WeaponType.LASER, 475L, 19.0f),
	LASER_MK_3("Laser Mk3", WeaponType.LASER, 450L, 30.0f),
	TORPEDO_MK_1("Torpedo Mk1", WeaponType.TORPEDO, 600L, 14.0f),
	TORPEDO_MK_2("Torpedo Mk2", WeaponType.TORPEDO, 550L, 28.5f),
	TORPEDO_MK_3("Torpedo Mk3", WeaponType.TORPEDO, 500L, 42.0f), BOMB_MK_1("Bomb Mk1", WeaponType.BOMB, 1000L, 80.0f),
	BOMB_MK2("Bomb Mk2", WeaponType.BOMB, 800L, 300.0f), PHASER_MK_1("Phaser Mk1", WeaponType.PHASER, 250L, 5.0f),
	PHASER_MK_2("Phaser Mk2", WeaponType.PHASER, 225L, 6.75f), PHASER_MK_3("Phaser Mk3", WeaponType.PHASER, 200L, 8.5f),
	PHASER_MK_4("Phaser Mk4", WeaponType.PHASER, 175L, 10.25f);

	public enum WeaponType {
		LASER, TORPEDO, BOMB, PHASER;
	}

	public enum ElementType {
		FISSION, PHOTON, PHASER, NUCLEAR, EXPLOSIVE, ANTI_MATTER, PLASMA;
	}

	private String name;
	private WeaponType weaponType;
	private ElementType elementType;
	private long cooldownTime;
	private float baseDmg;

	Weapons(String name, WeaponType weaponType, long cooldownTime, float baseDmg) {
		this.name = name;
		this.weaponType = weaponType;
		this.cooldownTime = cooldownTime;
		this.baseDmg = baseDmg;
	}

	Weapons(String name, WeaponType weaponType, ElementType elementType, long cooldownTime, float baseDmg) {
		this.name = name;
		this.weaponType = weaponType;
		this.elementType = elementType;
		this.cooldownTime = cooldownTime;
		this.baseDmg = baseDmg;
	}

	public String getName() {
		return name;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public long getCooldownTime() {
		return cooldownTime;
	}

	public float getBaseDmg() {
		return baseDmg;
	}
}
