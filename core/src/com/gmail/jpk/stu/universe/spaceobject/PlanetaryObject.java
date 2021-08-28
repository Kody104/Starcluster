package com.gmail.jpk.stu.universe.spaceobject;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gmail.jpk.stu.inventory.ShopInventory;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.universe.EconomyStrength;

public class PlanetaryObject extends SpaceObject {

	private Sprite citySprite; // The interaction texture for this planet
	private ShopInventory tradeInventory; // The trade inventory of this planet
	private float population; // The population of this planet
	private int tradeCurrency; // The max trade currency this planet has
	private int currentCurrency; // The current trade currency this planet has
	private EconomyStrength economyStrength; // The planet's economy strength

	public PlanetaryObject(Sprite displaySprite, Sprite citySprite, String name, SpriteType spriteType, float x,
			float y, float population, int tradeCurrency, EconomyStrength economyStrength) {
		super(displaySprite, spriteType, name, x, y);
		this.citySprite = citySprite;
		setX(x);
		setY(y);
		setPosition(new Position(x, y));
		this.population = population;
		this.tradeCurrency = tradeCurrency;
		this.currentCurrency = tradeCurrency;
		this.economyStrength = economyStrength;
		this.tradeInventory = new ShopInventory(this);
	}

	public Sprite getCitySprite() {
		return citySprite;
	}

	public void setCitySprite(Sprite citySprite) {
		this.citySprite = citySprite;
	}

	public ShopInventory getTradeInventory() {
		return tradeInventory;
	}

	public void setTradeInventory(ShopInventory tradeInventory) {
		this.tradeInventory = tradeInventory;
	}

	public float getPopulation() {
		return population;
	}

	public void setPopulation(float population) {
		this.population = population;
	}

	public int getTradeCurrency() {
		return tradeCurrency;
	}

	public void setTradeCurrency(int tradeCurrency) {
		this.tradeCurrency = tradeCurrency;
	}

	public int getCurrentCurrency() {
		return currentCurrency;
	}

	public void setCurrentCurrency(int currentCurrency) {
		this.currentCurrency = currentCurrency;
	}

	public EconomyStrength getEconomyStrength() {
		return economyStrength;
	}

	public void setEconomyStrength(EconomyStrength economyStrength) {
		this.economyStrength = economyStrength;
	}
}
