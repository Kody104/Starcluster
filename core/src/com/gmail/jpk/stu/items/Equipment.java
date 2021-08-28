package com.gmail.jpk.stu.items;

public class Equipment extends BaseItem {

	private EquipmentType equipmentType; // The type of equipment this is
	private float maxThrustAdd; // Max thrust stat add
	private float rotateSpeedAdd; // Rotate speed stat add
	private float accelerationAdd; // Acceleration stat add
	private float stoppingPowerAdd; // Stoppingpower stat add
	private float maxFuelAdd; // Max fuel stat add
	private int maxInventoryAdd; // Max inventory stat add
	private float maxHealthAdd; // Max health stat add
	private float maxShieldAdd; // Max shield stat add
	private float fuelEffAdd; // Fuel efficiency stat add

	/**
	 * Constructor to clone equipment
	 * 
	 * @param toClone The equipment to clone
	 */
	public Equipment(Equipment toClone) {
		this.equipmentType = toClone.equipmentType;
		setItemType(toClone.getItemType());
		setName(toClone.getName());
		setAmount(toClone.getAmount());
		set(toClone.getMaxThrustAdd(), toClone.getRotateSpeedAdd(), toClone.getAccelerationAdd(),
				toClone.getStoppingPowerAdd(), toClone.getMaxFuelAdd(), toClone.getMaxInventoryAdd(),
				toClone.getMaxHealthAdd(), toClone.getMaxShieldAdd(), toClone.getFuelEffAdd());
	}

	/**
	 * Constructor for an equipment item.
	 * 
	 * @param equipmentType The type of equipment
	 */
	public Equipment(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
		setItemType(ItemType.EQUIPMENT);
		setName(equipmentType.name());
		setAmount(1);
	}

	/**
	 * Constructor for an equipment item.
	 * 
	 * @param equipmentType The type of equipment
	 * @param name          The equipment name
	 */
	public Equipment(EquipmentType equipmentType, String name) {
		this.equipmentType = equipmentType;
		setItemType(ItemType.EQUIPMENT);
		setName(name);
		setAmount(1);
	}

	/**
	 * Sets all the modifiers for this equipment.
	 * 
	 * @param maxThrust
	 * @param rotateSpeed
	 * @param acceleration
	 * @param stoppingPower
	 * @param maxFuel
	 * @param maxInventory
	 * @param maxHealth
	 * @param maxShield
	 * @param fuelEff
	 */
	public void set(float maxThrust, float rotateSpeed, float acceleration, float stoppingPower, float maxFuel,
			int maxInventory, float maxHealth, float maxShield, float fuelEff) {
		maxThrustAdd = maxThrust;
		rotateSpeedAdd = rotateSpeed;
		accelerationAdd = acceleration;
		stoppingPowerAdd = stoppingPower;
		maxFuelAdd = maxFuel;
		maxInventoryAdd = maxInventory;
		maxHealthAdd = maxHealth;
		maxShieldAdd = maxShield;
		fuelEffAdd = fuelEff;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(EquipmentType equipmentType) {
		this.equipmentType = equipmentType;
	}

	public float getValue() {
		float value = 0.0f;
		value += (maxThrustAdd * 500.0f); // Max thrust is 500 per point
		value += (rotateSpeedAdd * 50.0f); // Rotate speed is 50 per point
		value += ((accelerationAdd * 100.0f) * 15000.0f); // Acceleration is 15000 per 0.1 points
		value += ((stoppingPowerAdd * 100.0f) * 5000.0f); // Stopping power is 5000 per 0.1 point
		value += (maxFuelAdd * 75.0f); // Max fuel is 75 per point
		value += (maxInventoryAdd * 10000.0f); // Max inventory is 10000 per slot
		value += (maxHealthAdd * 1500.0f); // Max health is 1500 per point
		value += (maxShieldAdd * 2500.0f); // Max shield is 2500 per point
		value += ((fuelEffAdd * 100.0f) * 7500.0f); // Fuel efficiency is 7500 per 0.1 point
		return value;
	}

	public float getMaxThrustAdd() {
		return maxThrustAdd;
	}

	public void setMaxThrustAdd(float maxThrustAdd) {
		this.maxThrustAdd = maxThrustAdd;
	}

	public float getRotateSpeedAdd() {
		return rotateSpeedAdd;
	}

	public void setRotateSpeedAdd(float rotateSpeedAdd) {
		this.rotateSpeedAdd = rotateSpeedAdd;
	}

	public float getAccelerationAdd() {
		return accelerationAdd;
	}

	public void setAccelerationAdd(float accelerationAdd) {
		this.accelerationAdd = accelerationAdd;
	}

	public float getStoppingPowerAdd() {
		return stoppingPowerAdd;
	}

	public void setStoppingPowerAdd(float stoppingPowerAdd) {
		this.stoppingPowerAdd = stoppingPowerAdd;
	}

	public float getMaxFuelAdd() {
		return maxFuelAdd;
	}

	public void setMaxFuelAdd(float maxFuelAdd) {
		this.maxFuelAdd = maxFuelAdd;
	}

	public int getMaxInventoryAdd() {
		return maxInventoryAdd;
	}

	public void setMaxInventoryAdd(int maxInventoryAdd) {
		this.maxInventoryAdd = maxInventoryAdd;
	}

	public float getMaxHealthAdd() {
		return maxHealthAdd;
	}

	public void setMaxHealthAdd(float maxHealthAdd) {
		this.maxHealthAdd = maxHealthAdd;
	}

	public float getMaxShieldAdd() {
		return maxShieldAdd;
	}

	public void setMaxShieldAdd(float maxShieldAdd) {
		this.maxShieldAdd = maxShieldAdd;
	}

	public float getFuelEffAdd() {
		return fuelEffAdd;
	}

	public void setFuelEffAdd(float fuelEffAdd) {
		this.fuelEffAdd = fuelEffAdd;
	}
}
