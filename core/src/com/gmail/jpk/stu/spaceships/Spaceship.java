package com.gmail.jpk.stu.spaceships;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ArrayMap;
import com.gmail.jpk.stu.game.Faction;
import com.gmail.jpk.stu.inventory.EquipmentInventory;
import com.gmail.jpk.stu.inventory.ShipInventory;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.items.Equipment;
import com.gmail.jpk.stu.spaceships.Weapons.ElementType;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.sprites.PositionSprite;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.sprites.WeaponFireSprite;
import com.gmail.jpk.stu.utils.AtlasData;
import com.gmail.jpk.stu.utils.Mathematics;

public class Spaceship extends PositionSprite {
	
	private float thrust; // This ships current thrust
	private float health; // This ships current health
	private float shield; // This ships current shield
	
	private ArrayMap<ElementType, Float> resistances; // This ship's resistances to weapon damage
	private float armor; // Flat resistance to all weapon damage
	private boolean isAlive; // If the ship is dead or not
	
	private Faction allegiance; // The allegiance this ship has to a government
	private int tradeCurrency; // The amount of currency that this ship has
	private ShipWeapon shipWeapon; // The ship  weapon this ship has
	
	private ShipInventory inventory; // The inventory for the ship
	private EquipmentInventory activeEquipment; // The active equipment that the ship has
	
	private float shieldPower; // The amount of power given to the ship's shield generator
	private float weaponPower; // The amount of power given the ship's weapons
	private float enginePower; // The amount of pwoer given to the ship's engines
	
	private long lastHitTime; // The time that this ship was last hit by a shot
	
	/**
	 * Constructor for a spaceship
	 * @param sprite	The mysprite to display
	 * @param x	The x value to display
	 * @param y	The y value to display
	 * @param shipStats	The ship stats
	 * @param allegiance	The allegiance this ship has to a govenrment
	 */
	public Spaceship(Sprite sprite, float x, float y, SpaceshipBlueprints blueprint, Faction allegiance) {
		super(sprite, SpriteType.SPACESHIP, x, y);
		// getDisplaySprite().setPosition(x, y);
		setPosition(new Position(x, y));
		this.resistances = new ArrayMap<ElementType, Float>();
		for(ElementType e : ElementType.values()) {
			this.resistances.put(e, 0.0f);
		}
		this.thrust = 0.0f;
		this.armor = 0.0f;
		this.isAlive = true;
		this.allegiance = allegiance;
		int inventorySize = 0;
		this.activeEquipment = new EquipmentInventory();
		for(Equipment e : blueprint.getShipEquipment()) {
			this.activeEquipment.addItem(e);
			inventorySize += e.getMaxInventoryAdd();
		}
		this.health = getMaxHealth();
		this.shield = getMaxShield();
		this.inventory = new ShipInventory(inventorySize);
		this.shipWeapon = new ShipWeapon(this, Weapons.PHASER_MK_1);
		this.powerToSystem(1); // Default to shields
		this.lastHitTime = 0L;
	}

	public float getMaxThrust() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getMaxThrustAdd();
		}
		float mod = (enginePower / 100.0f) * 1.35f; // Bonus from engine power
		return mod > 0.0f ? toReturn * mod : toReturn;
	}

	public float getRotateSpeed() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getRotateSpeedAdd();
		}
		return toReturn;
	}

	public float getAcceleration() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getAccelerationAdd();
		}
		return toReturn;
	}

	public float getBrakePower() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getStoppingPowerAdd();
		}
		return toReturn;
	}

	public float getThrust() {
		return thrust;
	}

	public void setThrust(float thrust) {
		float maxThrust = getMaxThrust();
		if(thrust > maxThrust) {
			thrust = maxThrust;
		}
		else if(thrust < 0) {
			thrust = 0;
		}
		this.thrust = thrust;
	}
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		float maxHealth = getMaxHealth();
		if(health > maxHealth) {
			health = maxHealth;
		}
		this.health = health;
		if(this.health < 0.0f) {
			this.isAlive = false;
		}
	}

	public float getCurrentShield() {
		return shield;
	}
	
	public float getShield() {
		return shield;
	}

	public void setShield(float shield) {
		float maxShield = getMaxShield();
		if(shield > maxShield) {
			shield = maxShield;
		}
		this.shield = shield;
	}

	public float getFuelEff() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getFuelEffAdd();
		}
		return toReturn;
	}

	public float getMaxHealth() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getMaxHealthAdd();
		}
		return toReturn;
	}

	public float getMaxShield() {
		float toReturn = 0.0f;
		for(BaseItem item : activeEquipment.getSlots()) {
			Equipment e = (Equipment) item;
			toReturn += e.getMaxShieldAdd();
		}
		return toReturn;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	
	public Faction getAllegiance() {
		return allegiance;
	}
	
	public void setAllegiance(Faction allegiance) {
		this.allegiance = allegiance;
	}

	public ShipWeapon getShipWeapon() {
		return shipWeapon;
	}
	
	public void setShipWeapon(ShipWeapon shipWeapon) {
		this.shipWeapon = shipWeapon;
		this.shipWeapon.setParent(this);
	}
	
	public ShipInventory getInventory() {
		return inventory;
	}
	
	public void setInventory(ShipInventory inventory) {
		this.inventory = inventory;
	}
	
	public EquipmentInventory getActiveEquipment() {
		return activeEquipment;
	}
	
	public void setActiveEquipment(EquipmentInventory activeEquipment) {
		this.activeEquipment = activeEquipment;
	}
	
	public int getTradeCurrency() {
		return tradeCurrency;
	}
	
	public void setTradeCurrency(int tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}
	
	public ArrayMap<ElementType, Float> getResistances() {
		return resistances;
	}

	public void setResistances(ArrayMap<ElementType, Float> resistances) {
		this.resistances = resistances;
	}
	
	public void setResistance(ElementType element, float resistance) {
		resistances.put(element, resistance);
	}

	public float getArmor() {
		float armor = 1.0f + this.armor;
		float mod = (shieldPower / 100.0f); // Bonus from having power in shields
		armor *= (1.0f + mod);
		return armor;
	}

	public void setArmor(float armor) {
		this.armor = armor;
	}
	
	public float getShieldPower() {
		return shieldPower;
	}
	
	public float getWeaponPower() {
		return weaponPower;
	}
	
	public float getEnginePower() {
		return enginePower;
	}
	
	/**
	 * Direct power to a specific system.
	 * @param system	1) shields 2) weapons 3) engines
	 */
	public void powerToSystem(int system) {
		switch(system) {
			case 1:
			{
				shieldPower = 100.0f;
				weaponPower = 0.0f;
				enginePower = 0.0f;
				break;
			}
			case 2:
			{
				shieldPower = 0.0f;
				weaponPower = 100.0f;
				enginePower = 0.0f;
				break;
			}
			case 3:
			{
				shieldPower = 0.0f;
				weaponPower = 0.0f;
				enginePower = 100.0f;
				break;
			}
			default:
			{
				break;
			}
		}
	}

	public long getLastHitTime() {
		return lastHitTime;
	}

	public void setLastHitTime(long lastHitTime) {
		this.lastHitTime = lastHitTime;
	}

	/**
	 * Calculates the damage against this ship from a specified source ship
	 * @param src	The attacking ship
	 * @return	The damage after all the calculations
	 */
	public float calculateDamage(Spaceship src) {
		float resistance = resistances.get(src.getShipWeapon().getElementType());
		resistance = (1.0f - (resistance / 255.0f));
		float armor = getArmor();
		float powerBonus = (0.1f * (src.getWeaponPower() / 100.0f)) + 1.0f; // Bonus from having power in weapons
		float damage = ((src.getShipWeapon().getBaseDmg() * powerBonus) - armor);
		damage *= resistance;
		return damage;
	}
	
	/**
	 * Moves the spaceship according to it's current thrust velocity.
	 */
	protected void move() {
		Position position = getPosition();
		// Radial movement
		float radian = (float)(getRotation() * (Math.PI / 180.0f));
		float changeX = (float)(getThrust() * Math.sin(radian));
		float changeY = (float)(getThrust() * Math.cos(radian));
		position.setX(position.getX() + -changeX);
		position.setY(position.getY() + changeY);
	}
	
	/**
	 * Rotates the spaceship according to it's stats.
	 * @param fast	If the rotation should be multiplied to be faster.
	 * @param isLeft	If the rotation is going left. Otherwise it's right.
	 */
	public void rotate(boolean fast, boolean isLeft) {
		float rotateSpeed = getRotateSpeed();
		if(fast) {
			if(isLeft) {
				setRotation(getRotation() + (rotateSpeed * 5.0f) * Gdx.graphics.getDeltaTime());
			}
			else {
				setRotation(getRotation() - ((rotateSpeed * 5.0f) * Gdx.graphics.getDeltaTime()));
			}
		}
		else {
			if(isLeft) {
				setRotation(getRotation() + (rotateSpeed * Gdx.graphics.getDeltaTime()));
			}
			else {
				setRotation(getRotation() - (rotateSpeed * Gdx.graphics.getDeltaTime()));
			}
		}
	}
	
	/**
	 * Accelerates the ship according to it's ship stats.
	 */
	public void accelerate() {
		float accelBonus = (1.0f + (enginePower / 100.0f));
		setThrust(getThrust() + (getAcceleration() * accelBonus));
	}
	
	/**
	 * Decelerates the ship according to it's ship stats.
	 */
	public void decelerate() {
		float brakeBonus = (1.0f + (enginePower / 100.0f));
		setThrust(getThrust() - (getBrakePower() * brakeBonus));
	}
	
	public WeaponFireSprite fireWeapon() {
		if(shipWeapon.getActivateTime() + shipWeapon.getCooldownTime() <= System.currentTimeMillis()) {
			WeaponFireSprite weaponShot;
			float rotation = getRotation();
			float radius = getHeight();
			switch(shipWeapon.getWeaponType()) {
				case LASER:
				{
					weaponShot = new WeaponFireSprite(AtlasData.createSprite(AtlasData.WEAPON_ATLAS, "red_laser"), shipWeapon, 30.0f);
					radius += weaponShot.getHeight();
					radius /= 2.0f;
					break;
				}
				case TORPEDO:
				{
					weaponShot = new WeaponFireSprite(AtlasData.createSprite(AtlasData.WEAPON_ATLAS, "blue_torpedo"), shipWeapon, 25.0f);
					radius += weaponShot.getHeight();
					radius /= 2.0f;
					break;
				}
				case BOMB:
				{
					rotation += 180.0f;
					if(rotation > 360.0f) {
						rotation -= 360.0f;
					}
					weaponShot = new WeaponFireSprite(AtlasData.createSprite(AtlasData.WEAPON_ATLAS, "red_bomb"), shipWeapon, 0.1f);
					//radius += weaponShot.getHeight();
					radius /= 2.0f;
					break;
				}
				case PHASER:
				{
					weaponShot = new WeaponFireSprite(AtlasData.createSprite(AtlasData.WEAPON_ATLAS, "yellow_phasers"), shipWeapon, 23.0f);
					radius += weaponShot.getHeight();
					radius /= 2.0f;
					break;
				}
				default:
				{
					return null;
				}
			}
			float halfWidth = weaponShot.getWidth() / 2.0f;
			float halfHeight = weaponShot.getHeight() / 2.0f;
			Position origin;
			if(getSpriteType() == SpriteType.PLAYERSHIP) {
				origin = Mathematics.getCenterScreenPosition(getPosition()); // Center screen without sprite calculations
			}
			else {
				origin = new Position(getPosition().getX() + (getWidth() / 2.0f), getPosition().getY() + (getHeight() / 2.0f));
			}
			//Center screen with sprite calculations
			origin.setX(origin.getX() - halfWidth); 
			origin.setY(origin.getY() - halfHeight);
			// Find the point to fire from
			Position position = Mathematics.findCirclePoint(rotation, radius, origin);
			weaponShot.setPosition(position);
			weaponShot.setRotation(rotation);
			weaponShot.setSpeed(weaponShot.getSpeed() + thrust);
			shipWeapon.setActivateTime(System.currentTimeMillis());
			return weaponShot;
		}
		return null;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isVisible()) {
			if(getThrust() > 0.0f) {
				move();
			}
		}
	}
	
	public void update() {
		if(getShield() < getMaxShield()) {
			if(lastHitTime + 5000L < System.currentTimeMillis()) {
				setShield(getShield() + 0.1f);
			}
		}
	}
}