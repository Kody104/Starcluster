package com.gmail.jpk.stu.spaceships;

import java.util.Random;

import com.gmail.jpk.stu.spaceships.Weapons.ElementType;
import com.gmail.jpk.stu.spaceships.Weapons.WeaponType;

public class ShipWeapon {
	
	private Spaceship parent; // The parent ship of this weapon
	private String name; // The name of this weapon
	private WeaponType weaponType; // The weapon type of this weapon
	private ElementType elementType; // The element type of this weapon
	private long cooldownTime; // The time after each fire of this weapon before you can shoot again
	private long activateTime; // The last time we shot this weapon
	private float baseDmg; // The base dmg of this weapon without modifiers
	
	/**
	 * Constructor for a ship weapon.
	 * @param blueprint	The blueprint weapon for this weapon
	 */
	public ShipWeapon(Weapons blueprint) {
		this.parent = null;
		this.name = blueprint.getName();
		this.weaponType = blueprint.getWeaponType();
		if(blueprint.getElementType() != null) {
			this.elementType = blueprint.getElementType();
		}
		else {
			Random r = new Random();
			this.elementType = ElementType.values()[r.nextInt(ElementType.values().length)];
		}
		this.cooldownTime = blueprint.getCooldownTime();
		this.activateTime = 0L;
		this.baseDmg = blueprint.getBaseDmg();
	}
	
	/**
	 * Constructor for a ship weapon.
	 * @param blueprint	The blueprint weapon for this weapon
	 */
	public ShipWeapon(Spaceship parent, Weapons blueprint) {
		this.parent = parent;
		this.name = blueprint.getName();
		this.weaponType = blueprint.getWeaponType();
		if(blueprint.getElementType() != null) {
			this.elementType = blueprint.getElementType();
		}
		else {
			Random r = new Random();
			this.elementType = ElementType.values()[r.nextInt(ElementType.values().length)];
		}
		this.cooldownTime = blueprint.getCooldownTime();
		this.activateTime = 0L;
		this.baseDmg = blueprint.getBaseDmg();
	}


	public Spaceship getParent() {
		return parent;
	}

	public void setParent(Spaceship parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public void setWeaponType(WeaponType weaponType) {
		this.weaponType = weaponType;
	}

	public ElementType getElementType() {
		return elementType;
	}

	public void setElementType(ElementType elementType) {
		this.elementType = elementType;
	}

	public long getCooldownTime() {
		return cooldownTime;
	}

	public void setCooldownTime(long cooldownTime) {
		this.cooldownTime = cooldownTime;
	}

	public long getActivateTime() {
		return activateTime;
	}

	public void setActivateTime(long activateTime) {
		this.activateTime = activateTime;
	}

	public float getBaseDmg() {
		return baseDmg;
	}

	public void setBaseDmg(float baseDmg) {
		this.baseDmg = baseDmg;
	}
}
