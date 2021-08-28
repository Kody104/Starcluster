package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.jpk.stu.ai.ShipController;
import com.gmail.jpk.stu.ai.tasks.ShipHuntTask;
import com.gmail.jpk.stu.components.ButtonComponent;
import com.gmail.jpk.stu.components.ComponentGui;
import com.gmail.jpk.stu.components.ComponentUtils;
import com.gmail.jpk.stu.components.InventoryComponent;
import com.gmail.jpk.stu.components.SlotComponent;
import com.gmail.jpk.stu.components.TextComponent;
import com.gmail.jpk.stu.components.TooltipComponent;
import com.gmail.jpk.stu.components.SpriteComponent;
import com.gmail.jpk.stu.game.Faction;
import com.gmail.jpk.stu.inventory.ShipInventory;
import com.gmail.jpk.stu.inventory.ShopInventory;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.items.Item;
import com.gmail.jpk.stu.items.ItemMaterial;
import com.gmail.jpk.stu.items.LootTable;
import com.gmail.jpk.stu.items.BaseItem.ItemType;
import com.gmail.jpk.stu.player.PlayerStats;
import com.gmail.jpk.stu.items.Equipment;
import com.gmail.jpk.stu.spaceships.ShipWeapon;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.spaceships.SpaceshipBlueprints;
import com.gmail.jpk.stu.spaceships.Weapons;
import com.gmail.jpk.stu.sprites.BasicSprite;
import com.gmail.jpk.stu.sprites.DisplayableText;
import com.gmail.jpk.stu.sprites.DisplayableText.TextType;
import com.gmail.jpk.stu.sprites.Minimap;
import com.gmail.jpk.stu.sprites.MultiText;
import com.gmail.jpk.stu.sprites.NotificationBox;
import com.gmail.jpk.stu.sprites.PlayerUI;
import com.gmail.jpk.stu.sprites.Position;
import com.gmail.jpk.stu.sprites.PositionSprite;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.sprites.WeaponFireSprite;
import com.gmail.jpk.stu.starcluster.Starcluster;
import com.gmail.jpk.stu.universe.EconomyStrength;
import com.gmail.jpk.stu.universe.SolarSystem;
import com.gmail.jpk.stu.universe.Universe;
import com.gmail.jpk.stu.universe.spaceobject.AsteroidObject;
import com.gmail.jpk.stu.universe.spaceobject.PlanetaryObject;
import com.gmail.jpk.stu.universe.spaceobject.SpaceObject;
import com.gmail.jpk.stu.utils.AssetUtils;
import com.gmail.jpk.stu.utils.AtlasData;
import com.gmail.jpk.stu.utils.DataUtils;
import com.gmail.jpk.stu.utils.GlobalUtility;
import com.gmail.jpk.stu.utils.KeyUtils;
import com.gmail.jpk.stu.utils.Mathematics;
import com.gmail.jpk.stu.utils.MouseUtils;
import com.gmail.jpk.stu.utils.SpriteData;

public class GameScreen extends BaseScreen {

	public boolean debugModeOn; // If debug mode is active or not

	private DisplayableText loadingText; // The loading text
	private boolean finishedLoading; // If the game is currently loading assets or not

	private Minimap minimap; // The bottom right minimap
	private NotificationBox notifBox; // The bottom left notification box
	private PlayerUI playerUI; // The player health display

	private ShipController aiController; // An ai to control the spaceships in system

	private ComponentGui interactScreen; // A menu gui that pops up when interacting with a homeworld
	private ComponentGui communicateScreen; // A gui that displays planet text to the player
	private ComponentGui tradeScreen; // A gui that displays the trade inventory of the planet
	private ComponentGui inventoryScreen; // A gui for the player's inventory

	private Sprite background; // The big background of stars we display behind the player sprite

	private MultiText damageText; // The text to display on damage hit

	private Faction systemFaction; // The faction that owns this system
	private SolarSystem currentSystem; // The current system that we are in
	private Spaceship player; // The player spaceship

	private float lastInteractTime; // The last time the player interacted with something

	public GameScreen(final Starcluster game) {
		super(game);
		debugModeOn = false;
		loadingText = new DisplayableText(this.game.font, "") {
			@Override
			public void render(SpriteBatch batch) {
				super.render(batch);
				this.setText(AssetUtils.getLoadText());
				this.setX(400 - (this.getTextWidth() / 2.0f));
				this.setY(240 + this.getTextHeight());
			}
		};
		// Using asset manager to load all important files
		AssetUtils.init();
		GlobalUtility.init();
		LootTable.createTables();

		lastInteractTime = 0.0f;
	}

	public Faction getSystemFaction() {
		return systemFaction;
	}

	public SolarSystem getCurrentSystem() {
		return currentSystem;
	}

	public Spaceship getPlayer() {
		return player;
	}

	public void setCurrentSystem(SolarSystem currentSystem) {
		if (this.currentSystem != null) {
			this.currentSystem.reset();
		}
		this.currentSystem = currentSystem;
		systemFaction = Universe.getSystemFactionOwner(currentSystem);
		if (!PlayerStats.hasVisitedSystem(currentSystem)) {
			PlayerStats.addVisitedSystem(currentSystem);
			notifBox.addNotification("Entering the " + currentSystem.getName() + " for the first time!");
		} else {
			notifBox.addNotification("Entering the " + currentSystem.getName() + "!");
		}
		if (systemFaction != null) {
			if (PlayerStats.getFactionOpinion(systemFaction).equalsIgnoreCase("hostile")) {
				notifBox.addNotification("This system appears hostile! Watch out!");
			}
		}
		this.reset();
		this.spawnTraffic();
	}

	/**
	 * Creates traffic for the system that the player is currently in
	 */
	private void spawnTraffic() {
		int medianTraffic = currentSystem.getAvgTraffic(); // The average traffic a system gets
		/*
		 * Generates an random number of ships based on the average traffic. traffic =
		 * avg + (avg / 2)
		 */
		Spaceship[] traffic = new Spaceship[(Mathematics.rand.nextInt(medianTraffic + 1) + ((medianTraffic + 1) / 2))];
		for (Spaceship s : traffic) {
			float x = Mathematics.rand.nextFloat() * 14000.0f;
			float y = Mathematics.rand.nextFloat() * 14000.0f;
			if (x > 7000.0f) { // If the number is above 7000, we reverse the position
				x -= 14000.0f;
			}
			if (y > 7000.0f) { // If the number is above 7000, we reverse the position
				y -= 14000.0f;
			}
			int num = Mathematics.rand.nextInt(AtlasData.SPACESHIP_ATLAS.getAtlas().createSprites().size);
			s = new Spaceship(AtlasData.createSprite(AtlasData.SPACESHIP_ATLAS, num), x, y,
					GlobalUtility.getShipBlueprintAt(num), systemFaction);
			aiController.addShip(s);
			currentSystem.addShipToSystem(s);
			if (s.getAllegiance() != null) {
				if (PlayerStats.getFactionOpinion(s.getAllegiance()).equals("hostile")) {
					aiController.createShipTask(s, new ShipHuntTask(s, player));
				}
			}
		}
		minimap.setSystemFaction(systemFaction);
		minimap.track(currentSystem.getHomeworld());
		minimap.track(currentSystem.getAsteroids());
		minimap.track(currentSystem.getExtras());
		minimap.track(currentSystem.getShipsInSystem());
		minimap.track(player);
	}

	/**
	 * Called after the assets are finished loading and the player presses a key.
	 * Inits all the data
	 */
	private void finishLoading() {
		// Initialize player stats, create all solar systems
		PlayerStats.init();
		Array<SolarSystem> systems = new Array<SolarSystem>();
		SolarSystem gios = new SolarSystem("Gios System",
				new PlanetaryObject(SpriteData.PLANET_ONE_M.getSprite(), SpriteData.CITYSCAPE_ONE.getSprite(),
						"Filmora", SpriteType.HOMEWORLD, 3000.0f, 3000.0f, 12, 15000, EconomyStrength.FAIR),
				new Position(12.2f, 13.9f), 6);
		systems.add(gios);
		SolarSystem terra = new SolarSystem("Terra System",
				new PlanetaryObject(SpriteData.PLANET_SIX_M.getSprite(), SpriteData.CITYSCAPE_TWO.getSprite(), "Earth",
						SpriteType.HOMEWORLD, -4580.0f, 100.0f, 0, 0, EconomyStrength.NONE),
				new Position(417.9f, 89.4f), 0);
		terra.addExtraToSystem(new PlanetaryObject(AtlasData.createSprite(AtlasData.S_PLANET_ATLAS, "planet-14-s"),
				SpriteData.CITYSCAPE_ONE.getSprite(), "Earth's Moon", SpriteType.MOON, -3280.0f, -100.0f, 0.75f, 10000,
				EconomyStrength.FAIR));
		systems.add(terra);
		SolarSystem igorias = new SolarSystem("Igorias System",
				new PlanetaryObject(AtlasData.createSprite(AtlasData.S_PLANET_ATLAS, "planet-15-s"),
						SpriteData.CITYSCAPE_TWO.getSprite(), "Igora", SpriteType.HOMEWORLD, 6098.0f, -6299.0f, 0, 0,
						EconomyStrength.NONE),
				new Position(12.6f, 453.2f), 1);
		systems.add(igorias);
		SolarSystem eikan = new SolarSystem("Eika'n System",
				new PlanetaryObject(SpriteData.PLANET_SIXTEEN_M.getSprite(), SpriteData.CITYSCAPE_ONE.getSprite(),
						"Rak'Fis", SpriteType.HOMEWORLD, 1622.0f, -1492.0f, 32, 45000, EconomyStrength.STEADY),
				new Position(502.8f, 311.0f), 7);
		systems.add(eikan);
		SolarSystem allorai = new SolarSystem("Allorai System",
				new PlanetaryObject(AtlasData.createSprite(AtlasData.X_PLANET_ATLAS, "planet-9-x"),
						SpriteData.CITYSCAPE_ONE.getSprite(), "Fessri", SpriteType.HOMEWORLD, -4767.0f, 3394.0f, 3,
						25000, EconomyStrength.FAIR),
				new Position(768.2f, 95.3f), 3);
		systems.add(allorai);
		SolarSystem tremyla = new SolarSystem("Tremyla System",
				new PlanetaryObject(SpriteData.PLANET_TWELVE.getSprite(), SpriteData.CITYSCAPE_ONE.getSprite(),
						"Frekirn", SpriteType.HOMEWORLD, 522.3f, 4825.1f, 9, 0, EconomyStrength.NONE),
				new Position(311.8f, 202.4f), 2);
		systems.add(tremyla);
		SolarSystem clerste = new SolarSystem("Clerste System",
				new PlanetaryObject(SpriteData.PLANET_THREE_M.getSprite(), SpriteData.CITYSCAPE_ONE.getSprite(),
						"IG 1.2.h1", SpriteType.HOMEWORLD, -1292.0f, 405.0f, 1, 5000, EconomyStrength.WEAK),
				new Position(692.1f, 33.4f), 3);
		systems.add(clerste);
		SolarSystem proxima = new SolarSystem("Proxima A  System",
				new PlanetaryObject(SpriteData.PLANET_THIRTEEN_M.getSprite(), SpriteData.CITYSCAPE_ONE.getSprite(),
						"Proxima", SpriteType.HOMEWORLD, -6048.0f, 309.2f, 0, 0, EconomyStrength.NONE),
				new Position(113.0f, 34.0f), 1);
		systems.add(proxima);
		SolarSystem filn = new SolarSystem("Fil'n System",
				new PlanetaryObject(AtlasData.createSprite(AtlasData.X_PLANET_ATLAS, "planet-5-x"),
						SpriteData.CITYSCAPE_ONE.getSprite(), "Ders'Ruktin", SpriteType.HOMEWORLD, 409.0f, -1202.0f, 4,
						10000, EconomyStrength.FAIR),
				new Position(764.2f, 451.4f), 4);
		systems.add(filn);
		sprinkleAsteroids(gios, terra, igorias, eikan, allorai, tremyla, clerste, proxima, filn);
		Universe.setAllSystems(systems);

		// Create all factions
		Array<Faction> factions = new Array<Faction>();
		Faction federation = new Faction("Federation");
		federation.addSystemToOwn(systems.get(0));
		federation.addSystemToOwn(systems.get(1));
		factions.add(federation);
		Faction pirates = new Faction("Pirate Coalition");
		pirates.addSystemToOwn(systems.get(2));
		factions.add(pirates);
		Faction eikadiplo = new Faction("Eika'n Dip lo'maci");
		eikadiplo.addSystemToOwn(systems.get(3));
		eikadiplo.addSystemToOwn(systems.get(8));
		factions.add(eikadiplo);
		Faction vallorant = new Faction("Vallorant");
		vallorant.addSystemToOwn(systems.get(4));
		factions.add(vallorant);
		Universe.setAllFactions(factions);

		// Create the factions relations to the player
		ArrayMap<Faction, Float> factionRelations = new ArrayMap<Faction, Float>();
		for (Faction f : factions) {
			if (f.getName().equalsIgnoreCase("Pirate Coalition")) {
				factionRelations.put(f, -75.0f);
			} else {
				factionRelations.put(f, 0.0f);
			}
		}
		PlayerStats.setFactionRelations(factionRelations);

		// Init the current system we are in
		currentSystem = Universe.getAllSystems().get(0);
		PlayerStats.addVisitedSystem(currentSystem);
		systemFaction = Universe.getSystemFactionOwner(currentSystem);

		// Load all necessary assets
		background = SpriteData.SPACE_BACKGROUND.getSprite();

		// Create minimap
		minimap = new Minimap(AtlasData.MINIMAP_ATLAS.getAtlas(), SpriteData.MINIMAP_BACKGROUND.getSprite(), 680.0f,
				20.0f, systemFaction);
		notifBox = new NotificationBox(game.font, 0.0f, 240.0f);

		// Create interact gui
		interactScreen = new ComponentGui();
		interactScreen.setX(150.0f);
		interactScreen.setY(65.0f);

		// Create communicate gui
		communicateScreen = new ComponentGui();
		communicateScreen.setX(150.0f);
		communicateScreen.setY(65.0f);

		// Create trade gui
		tradeScreen = new ComponentGui();
		tradeScreen.setX(150.0f);
		tradeScreen.setY(65.0f);

		SpriteComponent ivBg = new SpriteComponent(SpriteData.INVENTORY_BACKGROUND.getSprite());

		inventoryScreen = new ComponentGui();
		inventoryScreen.setX(400.0f - (ivBg.getDisplay().getWidth() / 2));
		inventoryScreen.setY(240.0f - (ivBg.getDisplay().getHeight() / 2));

		// Create player and center camera on it
		SpaceshipBlueprints scoutShip = GlobalUtility.getShipBlueprintAt(0);
		player = new Spaceship(AtlasData.createSprite(AtlasData.SPACESHIP_ATLAS, "smallorange"), 400.0f, 240.0f,
				scoutShip, null);
		player.setX(400.0f - (player.getDisplaySprite().getWidth() / 2));
		player.setY(240.0f - (player.getDisplaySprite().getHeight() / 2));
		player.setPosition(new Position(player.getX(), player.getY()));
		player.setShipWeapon(new ShipWeapon(Weapons.TORPEDO_MK_1));
		player.setSpriteType(SpriteType.PLAYERSHIP);
		player.setTradeCurrency(250);
		PlayerStats.setMaxShipFuel(scoutShip);
		PlayerStats.setCurShipFuel(PlayerStats.getMaxShipFuel());

		this.componentGuisReset(this.currentSystem.getHomeworld());

		damageText = new MultiText() {
			@Override
			public void render(SpriteBatch batch) {
				super.render(batch);
				if (isVisible()) {
					for (int i = 0; i < getTexts().length; i++) {
						DisplayableText t = getTexts()[i];
						t.render(batch);
						if (t.getRenderTime() > 2.0f) {
							removeText(i);
							i--;
							continue;
						}
					}
				}
			}
		};

		aiController = new ShipController();
		playerUI = new PlayerUI(game.font, player);

		// Add minimap tracking
		spawnTraffic();
		minimap.track(player);

		finishedLoading = true;
	}

	/**
	 * Creates asteroids and randomly puts them in the specified system
	 * 
	 * @param systems The systems to sprinkle asteroids in
	 */
	public void sprinkleAsteroids(SolarSystem... systems) {
		for (SolarSystem s : systems) {
			s.clearAsteroids();
			AsteroidObject[] asteroids = new AsteroidObject[Mathematics.rand.nextInt(6)];
			for (int i = 0; i < asteroids.length; i++) {
				AsteroidObject a = new AsteroidObject(SpriteData.ASTEROID.getSprite(), SpriteType.ASTEROID, 100.0f);
				asteroids[i] = a;
				float x = Mathematics.rand.nextInt(14000);
				float y = Mathematics.rand.nextInt(14000);
				if (x > 7000.0f) {
					x -= 14000.0f;
					x += a.getWidth();
				} else {
					x -= a.getWidth();
				}
				if (y > 7000.0f) {
					y -= 14000.0f;
					y += a.getHeight();
				} else {
					y -= a.getHeight();
				}
				a.getPosition().setX(x);
				a.getPosition().setY(y);
				s.addAsteroidToSystem(a);
			}
		}
	}

	/**
	 * Resets key variables in the game system
	 */
	private void reset() {
		minimap.reset();
		aiController.clear();
		player.setPosition(new Position(400.0f - (player.getDisplaySprite().getWidth() / 2),
				240.0f - (player.getDisplaySprite().getHeight() / 2)));
		player.setRotation(0.0f);
		player.setThrust(0.0f);
		lastInteractTime = 0.0f;
		renderTime = 0.0f;
		// this.centerCameraOn(player.getDisplaySprite());
		this.componentGuisReset(currentSystem.getHomeworld());
	}

	/**
	 * Resets and reinitalizes all component guis for the current system. Ran after
	 * changing systems.
	 */
	private void componentGuisReset(SpaceObject target) {
		if (target == null) {
			this.resetInteractScreen(null);
			this.resetCommunicateScreen(null);
			this.resetTradeScreen(null);
		} else {
			this.resetInteractScreen(target);
			this.resetCommunicateScreen(target);
			this.resetTradeScreen(target);
		}
		this.resetInventoryScreen();
		// this.resetEquipmentScreen();
	}

	/**
	 * Resets the interact gui according to the spaceobject given.
	 * 
	 * @param interaction The object to interact with
	 */
	private void resetInteractScreen(SpaceObject interaction) {
		interactScreen.clear();

		if (interaction == null) {
			return;
		}

		final PlanetaryObject pObject = (PlanetaryObject) interaction;
		TextureAtlas buttons = AtlasData.BUTTON_ATLAS.getAtlas();

		// Create and add all components to the interact gui
		SpriteComponent infoBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(), 0.0f, 0.0f);
		infoBg.setInteractable(false);
		interactScreen.addChild(infoBg);
		SpriteComponent btnBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(),
				infoBg.getDisplay().getWidth() + 5.0f, 0.0f);
		btnBg.setInteractable(false);
		interactScreen.addChild(btnBg);
		SpriteComponent civilization = new SpriteComponent(pObject.getCitySprite());
		civilization.setX((infoBg.getDisplay().getWidth() / 2.0f) - (civilization.getDisplay().getWidth() / 2.0f));
		civilization.setY((infoBg.getDisplay().getHeight() - civilization.getDisplay().getHeight()) - 5.0f);
		interactScreen.addChild(civilization);
		TextComponent pObjectName = new TextComponent(game.font, pObject.getName());
		pObjectName.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (pObjectName.getTextField().getTextWidth() / 2.0f));
		pObjectName.setY(civilization.getY() - 5.0f);
		interactScreen.addChild(pObjectName);
		TextComponent popText = new TextComponent(game.font,
				String.format("Population: %.2f Bn", pObject.getPopulation()));
		popText.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (popText.getTextField().getTextWidth() / 2.0f));
		popText.setY((pObjectName.getY() - popText.getTextField().getTextHeight()) - 5.0f);
		interactScreen.addChild(popText);
		TextComponent government = new TextComponent(game.font,
				"Government: " + (systemFaction != null ? systemFaction.getName() : "None"));
		government.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (government.getTextField().getTextWidth() / 2.0f));
		government.setY((popText.getY() - government.getTextField().getTextHeight()) - 5.0f);
		interactScreen.addChild(government);
		TextComponent currencyText = new TextComponent(game.font, "Currency: " + pObject.getCurrentCurrency());
		currencyText.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (currencyText.getTextField().getTextWidth() / 2.0f));
		currencyText.setY((government.getY() - currencyText.getTextField().getTextHeight()) - 5.0f);
		interactScreen.addChild(currencyText);
		ButtonComponent commButton = new ButtonComponent(buttons.createSprite("communicate-button"),
				buttons.createSprite("communicate-highlight"), buttons.createSprite("communicate-disabled")) {
			@Override
			protected void onInteract() {
				openComponentGui(communicateScreen);
			}
		};
		ComponentUtils.centerComponent(commButton, btnBg, 0.0f,
				((btnBg.getDisplay().getHeight() / 2.0f) - (commButton.getDisplay().getHeight() / 2.0f)) - 5.0f);
		interactScreen.addChild(commButton);
		ButtonComponent tradeButton = new ButtonComponent(buttons.createSprite("trade-button"),
				buttons.createSprite("trade-highlight"), buttons.createSprite("trade-disabled")) {
			@Override
			protected void onInteract() {
				openComponentGui(tradeScreen);
			}
		};
		ComponentUtils.centerComponent(tradeButton, btnBg, 0.0f, -2.5f);
		interactScreen.addChild(tradeButton);
		ButtonComponent refuelButton = new ButtonComponent(buttons.createSprite("refuel-button"),
				buttons.createSprite("refuel-highlight"), buttons.createSprite("refuel-disabled")) {
			@Override
			protected void onInteract() {
				final float fuelPerGallon = ItemMaterial.LARIUM.getBaseValue();
				float mod = 1.0f;
				switch (pObject.getEconomyStrength()) {
				case BOOMING: {
					mod -= 0.09f;
					break;
				}
				case THRIVING: {
					mod -= 0.03f;
					break;
				}
				case STEADY: {
					mod += 0.02f;
					break;
				}
				case FAIR: {
					mod += 0.08f;
					break;
				}
				case WEAK: {
					mod += 0.15f;
					break;
				}
				case NONE: {
					mod = 1.0f;
					break;
				}
				}
				float amount = PlayerStats.getMaxShipFuel() - PlayerStats.getCurShipFuel();
				int cost = (int) Math.ceil((amount * fuelPerGallon) * mod);
				if (cost > player.getTradeCurrency()) {
					amount = (player.getTradeCurrency() / (fuelPerGallon * mod));
					cost = (int) ((amount * fuelPerGallon) * mod);
				}
				if (player.getTradeCurrency() >= cost) {
					pObject.setTradeCurrency(pObject.getTradeCurrency() + cost);
					player.setTradeCurrency(player.getTradeCurrency() - cost);
					PlayerStats.setCurShipFuel(PlayerStats.getMaxShipFuel());
					componentGuisReset(pObject);
					notifBox.addNotification(String.format("Bought %.2f fuel for %d currency!", amount, cost));
				} else {
					notifBox.addNotification(String.format("Couldn't buy %.2f fuel for %d currency!", amount, cost));
				}
			}
		};
		ComponentUtils.centerComponent(refuelButton, btnBg, 0.0f,
				(-(btnBg.getDisplay().getHeight() / 2.0f) + (refuelButton.getDisplay().getHeight() / 2.0f)) + 5.0f);
		interactScreen.addChild(refuelButton);
		interactScreen.setVisible(false);
	}

	/**
	 * Resets the communicate screen gui according to the system homeworld.
	 */
	private void resetCommunicateScreen(SpaceObject communicate) {
		communicateScreen.clear();

		if (communicate == null) {
			return;
		}

		PlanetaryObject pObject = (PlanetaryObject) communicate;

		TextureAtlas buttons = AtlasData.BUTTON_ATLAS.getAtlas();

		// Create and add all components to communicate gui
		SpriteComponent infoBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(), 0.0f, 0.0f);
		infoBg.setInteractable(false);
		communicateScreen.addChild(infoBg);
		SpriteComponent displayBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(),
				infoBg.getDisplay().getWidth() + 5.0f, 0.0f);
		displayBg.setInteractable(false);
		communicateScreen.addChild(displayBg);
		SpriteComponent civilization = new SpriteComponent(pObject.getCitySprite());
		civilization.setX((infoBg.getDisplay().getWidth() / 2.0f) - (civilization.getDisplay().getWidth() / 2.0f));
		civilization.setY((infoBg.getDisplay().getHeight() - civilization.getDisplay().getHeight()) - 5.0f);
		communicateScreen.addChild(civilization);
		TextComponent pObjectName = new TextComponent(game.font, pObject.getName());
		pObjectName.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (pObjectName.getTextField().getTextWidth() / 2.0f));
		pObjectName.setY(civilization.getY() - 5.0f);
		communicateScreen.addChild(pObjectName);
		TextComponent popText = new TextComponent(game.font,
				String.format("Population: %.2f Bn", pObject.getPopulation()));
		popText.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (popText.getTextField().getTextWidth() / 2.0f));
		popText.setY((pObjectName.getY() - popText.getTextField().getTextHeight()) - 5.0f);
		communicateScreen.addChild(popText);
		TextComponent government = new TextComponent(game.font,
				"Government: " + (systemFaction != null ? systemFaction.getName() : "None"));
		government.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (government.getTextField().getTextWidth() / 2.0f));
		government.setY((popText.getY() - government.getTextField().getTextHeight()) - 5.0f);
		communicateScreen.addChild(government);
		TextComponent currencyText = new TextComponent(game.font, "Currency: " + pObject.getCurrentCurrency());
		currencyText.setX((civilization.getX() + (civilization.getDisplay().getWidth() / 2.0f))
				- (currencyText.getTextField().getTextWidth() / 2.0f));
		currencyText.setY((government.getY() - currencyText.getTextField().getTextHeight()) - 5.0f);
		communicateScreen.addChild(currencyText);
		TextComponent planetChat = new TextComponent(game.font,
				"This is the planet chat and we are talking to you the player."
						+ "We will eventually give quests and etc here but for now we are just"
						+ " gonna use a big block of text to test this code width instead for now!");
		planetChat.setX(displayBg.getX() + 5.0f);
		planetChat.setY(displayBg.getDisplay().getHeight() - 5.0f);
		planetChat.getTextField().setWrapping(true);
		planetChat.getTextField().setTargetWidth((int) (displayBg.getDisplay().getWidth() - 10.0f));
		communicateScreen.addChild(planetChat);
		ButtonComponent abortButton = new ButtonComponent(buttons.createSprite("abort-button"),
				buttons.createSprite("abort-highlight"), buttons.createSprite("abort-disabled")) {
			@Override
			protected void onInteract() {
				openComponentGui(interactScreen);
				// TODO: Code an abort function
			}
		};
		ComponentUtils.centerComponentX(abortButton, displayBg, 0.0f);
		abortButton.setY(displayBg.getY() + 5.0f);
		communicateScreen.addChild(abortButton);
		ButtonComponent confirmButton = new ButtonComponent(buttons.createSprite("confirm-button"),
				buttons.createSprite("confirm-highlight"), buttons.createSprite("confirm-disabled")) {
			@Override
			protected void onInteract() {
				openComponentGui(null);
				// TODO: Code a confirm function
			}
		};
		ComponentUtils.centerComponentX(confirmButton, displayBg, 0.0f);
		confirmButton.setY((abortButton.getY() + (confirmButton.getDisplay().getHeight())) + 5.0f);
		communicateScreen.addChild(confirmButton);
		communicateScreen.setVisible(false);
	}

	/**
	 * Resets the trade screen gui according to the system homeworld.
	 */
	private void resetTradeScreen(SpaceObject trader) {
		tradeScreen.clear();

		if (trader == null) {
			return;
		}

		final PlanetaryObject pObject = (PlanetaryObject) trader;

		TextureAtlas buttons = AtlasData.BUTTON_ATLAS.getAtlas();

		// Create and add all components to trade gui
		SpriteComponent shopBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(), 0.0f, 0.0f);
		shopBg.setInteractable(false);
		tradeScreen.addChild(shopBg);
		SpriteComponent playerBg = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(),
				shopBg.getDisplay().getWidth() + 5.0f, 0.0f);
		playerBg.setInteractable(false);
		tradeScreen.addChild(playerBg);
		// Create the slots for the shop inventory
		ShopInventory shop = pObject.getTradeInventory();
		final InventoryComponent shopInventory = new InventoryComponent(shop,
				ComponentUtils.createInventorySlots(shopBg, shop, 16));
		tradeScreen.addChild(shopInventory);
		// Create the slots for the player inventory
		ShipInventory inven = player.getInventory();
		final InventoryComponent playerInventory = new InventoryComponent(inven,
				ComponentUtils.createInventorySlots(playerBg, inven, 16));
		tradeScreen.addChild(playerInventory);
		ButtonComponent buyButton = new ButtonComponent(buttons.createSprite("buy-button"),
				buttons.createSprite("buy-highlight"), buttons.createSprite("buy-disable")) {
			@Override
			protected void onInteract() {
				if (shopInventory.getSelectedSlot() != null) {
					BaseItem baseSlot = shopInventory.getSelectedSlot().getInSlotItem();
					Item inSlot;
					if (baseSlot.getItemType() == ItemType.ITEM) {
						inSlot = (Item) baseSlot;
					} else {
						return;
					}
					ShopInventory shop = (ShopInventory) shopInventory.getInventory();
					int cost = Math.round(shop.getBuyValue(inSlot));
					if (player.getTradeCurrency() >= cost) {
						player.getInventory().addItem(inSlot);
						player.setTradeCurrency(player.getTradeCurrency() - cost);
						shopInventory.getInventory().removeItem(inSlot);
						pObject.setCurrentCurrency(pObject.getCurrentCurrency() + cost);
						componentGuisReset(pObject);
						resetInventoryScreen();
						tradeScreen.setVisible(true);
					} else {
						notifBox.addNotification("You don't have enough currency!");
					}
				}
			}
		};
		ComponentUtils.centerComponentX(buyButton, shopBg, 0.0f);
		buyButton.setY(shopBg.getY() + 5.0f);
		tradeScreen.addChild(buyButton);
		TextComponent shopCurrencyLabel = new TextComponent(game.font, "Currency: $" + pObject.getCurrentCurrency());
		shopCurrencyLabel.getTextField().setTextType(TextType.H6);
		shopCurrencyLabel.setX(shopBg.getX()
				+ ((shopBg.getDisplay().getWidth() / 2.0f) - (shopCurrencyLabel.getTextField().getTextWidth() / 2.0f)));
		shopCurrencyLabel.setY((buyButton.getY() + buyButton.getDisplay().getHeight())
				+ shopCurrencyLabel.getTextField().getTextHeight() + 5.0f);
		tradeScreen.addChild(shopCurrencyLabel);
		ButtonComponent sellButton = new ButtonComponent(buttons.createSprite("sell-button"),
				buttons.createSprite("sell-highlight"), buttons.createSprite("sell-disable")) {
			@Override
			protected void onInteract() {
				if (playerInventory.getSelectedSlot() != null) {
					BaseItem baseSlot = playerInventory.getSelectedSlot().getInSlotItem();
					Item inSlot;
					if (baseSlot.getItemType() == ItemType.ITEM) {
						inSlot = (Item) baseSlot;
					} else {
						return;
					}
					ShopInventory shop = (ShopInventory) shopInventory.getInventory();
					int cost = Math.round(shop.getSellValue(inSlot));
					if (pObject.getCurrentCurrency() >= cost) {
						pObject.getTradeInventory().addItem(inSlot);
						pObject.setCurrentCurrency(pObject.getCurrentCurrency() - cost);
						playerInventory.getInventory().removeItem(inSlot);
						player.setTradeCurrency(player.getTradeCurrency() + cost);
						componentGuisReset(pObject);
						resetInventoryScreen();
						tradeScreen.setVisible(true);
					} else {
						notifBox.addNotification("They don't have enough currency!");
					}
				}
			}
		};
		ComponentUtils.centerComponentX(sellButton, playerBg, 0.0f);
		sellButton.setY(playerBg.getY() + 5.0f);
		tradeScreen.addChild(sellButton);
		TextComponent playerCurrencyLabel = new TextComponent(game.font, "Currency: $" + player.getTradeCurrency());
		playerCurrencyLabel.getTextField().setTextType(TextType.H6);
		playerCurrencyLabel.setX(playerBg.getX() + ((playerBg.getDisplay().getWidth() / 2.0f)
				- (playerCurrencyLabel.getTextField().getTextWidth() / 2.0f)));
		playerCurrencyLabel.setY((sellButton.getY() + sellButton.getDisplay().getHeight())
				+ playerCurrencyLabel.getTextField().getTextHeight() + 5.0f);
		tradeScreen.addChild(playerCurrencyLabel);
		TooltipComponent tooltip = new TooltipComponent(game.font) {
			@Override
			protected boolean checkInput() {
				float x = Gdx.input.getX();
				float y = Gdx.input.getY();
				ShopInventory shop = (ShopInventory) shopInventory.getInventory();
				for (int i = 0; i < shopInventory.getSlots().length; i++) {
					SlotComponent slot = shopInventory.getSlots()[i];
					if (MouseUtils.isTouched(slot.getParent().getX() + slot.getX(),
							slot.getParent().getY() + slot.getY(), slot.getDisplay().getWidth(),
							slot.getDisplay().getHeight(), x, y)) {
						if (slot.getInSlotItem() != null) {
							if (slot.getInSlotItem().getItemType() == ItemType.ITEM) {
								Item inSlot = (Item) slot.getInSlotItem();
								String itemName = "Name: " + inSlot.getName();
								String amount = "\nAmount: " + inSlot.getAmount();
								String cost = String.format("\nPrice: $%d", Math.round(shop.getBuyValue(inSlot)));
								this.getText().setText(itemName + amount + cost);
								this.setX(x);
								this.setY(480.0f - this.getDisplay().getHeight() - y);
								return true;
							}
						}
					}
				}
				for (int i = 0; i < playerInventory.getSlots().length; i++) {
					SlotComponent slot = playerInventory.getSlots()[i];
					if (MouseUtils.isTouched(slot.getParent().getX() + slot.getX(),
							slot.getParent().getY() + slot.getY(), slot.getDisplay().getWidth(),
							slot.getDisplay().getHeight(), x, y)) {
						if (slot.getInSlotItem() != null) {
							if (slot.getInSlotItem().getItemType() == ItemType.ITEM) {
								Item inSlot = (Item) slot.getInSlotItem();
								String itemName = "Name: " + inSlot.getName();
								String amount = "\nAmount: " + inSlot.getAmount();
								String cost = String.format("\nPrice: $%d", Math.round(shop.getSellValue(inSlot)));
								this.getText().setText(itemName + amount + cost);
								this.setX(x);
								this.setY(480.0f - this.getDisplay().getHeight() - y);
								return true;
							}
						}
					}
				}
				return false;
			}
		};
		tradeScreen.addChild(tooltip);
		tradeScreen.setVisible(false);
	}

	private void resetInventoryScreen() {
		inventoryScreen.clear();
		SpriteComponent inventoryBg = new SpriteComponent(SpriteData.INVENTORY_BACKGROUND.getSprite(), 0.0f, 0.0f);
		inventoryBg.setInteractable(false);
		inventoryScreen.addChild(inventoryBg);
		final InventoryComponent playerInventory = new InventoryComponent(player.getInventory(),
				ComponentUtils.createInventorySlots(inventoryBg, player.getInventory(), 16));
		inventoryScreen.addChild(playerInventory);
		SpriteComponent equipmentBg = new SpriteComponent(SpriteData.EQUIPMENT_BACKGROUND.getSprite(), 0.0f, 0.0f);
		equipmentBg.setX(equipmentBg.getX() - equipmentBg.getDisplay().getWidth() - 5.0f);
		equipmentBg.setInteractable(false);
		inventoryScreen.addChild(equipmentBg);
		final InventoryComponent equipmentInventory = new InventoryComponent(player.getActiveEquipment(),
				ComponentUtils.createEquipmentSlots(equipmentBg, player.getActiveEquipment(), 4));
		inventoryScreen.addChild(equipmentInventory);
		TooltipComponent tooltip = new TooltipComponent(game.font) {
			@Override
			protected boolean checkInput() {
				float x = Gdx.input.getX();
				float y = Gdx.input.getY();
				for (int i = 0; i < playerInventory.getSlots().length; i++) {
					SlotComponent slot = playerInventory.getSlots()[i];
					if (MouseUtils.isTouched(slot.getParent().getX() + slot.getX(),
							slot.getParent().getY() + slot.getY(), slot.getDisplay().getWidth(),
							slot.getDisplay().getHeight(), x, y)) {
						if (slot.getInSlotItem() != null) {
							if (slot.getInSlotItem().getItemType() == ItemType.ITEM) {
								Item inSlot = (Item) slot.getInSlotItem();
								String itemName = "Item: " + inSlot.getName();
								String amount = "\nAmount: " + inSlot.getAmount();
								String cost = String.format("\nValue: $%d",
										Math.round((inSlot.getMaterial().getBaseValue() * inSlot.getAmount()) / 2.0f));
								this.getText().setText(itemName + amount + cost);
								this.setX(x);
								this.setY(480.0f - this.getDisplay().getHeight() - y);
								return true;
							}
						}
					}
				}
				for (int i = 0; i < equipmentInventory.getSlots().length; i++) {
					SlotComponent slot = equipmentInventory.getSlots()[i];
					if (MouseUtils.isTouched(slot.getParent().getX() + slot.getX(),
							slot.getParent().getY() + slot.getY(), slot.getDisplay().getWidth(),
							slot.getDisplay().getHeight(), x, y)) {
						if (slot.getInSlotItem() != null) {
							if (slot.getInSlotItem().getItemType() == ItemType.EQUIPMENT) {
								Equipment inSlot = (Equipment) slot.getInSlotItem();
								String itemName = inSlot.getName();
								String stats = "\n---Stats---";
								if(inSlot.getAccelerationAdd() != 0.0f) {
									stats += String.format("\nAccel: +%.2f", inSlot.getAccelerationAdd());
								}
								if(inSlot.getFuelEffAdd() != 0.0f) {
									stats += String.format("\nFuel Eff: +%.2f", inSlot.getFuelEffAdd());
								}
								if(inSlot.getMaxFuelAdd() != 0.0f) {
									stats += String.format("\nFuel Cap: +%.2f", inSlot.getMaxFuelAdd());
								}
								if(inSlot.getMaxHealthAdd() != 0.0f) {
									stats += String.format("\nHealth: +%.2f", inSlot.getMaxHealthAdd());
								}
								if(inSlot.getMaxShieldAdd() != 0.0f) {
									stats += String.format("\nShield: +%.2f", inSlot.getMaxShieldAdd());
								}
								if(inSlot.getMaxThrustAdd() != 0.0f) {
									stats += String.format("\nSpd: +%.2f", inSlot.getMaxThrustAdd());
								}
								if(inSlot.getRotateSpeedAdd() != 0.0f) {
									stats += String.format("\nRotation: +%.2f", inSlot.getRotateSpeedAdd());
								}
								if(inSlot.getStoppingPowerAdd() != 0.0f) {
									stats += String.format("\nBraking: +%.2f", inSlot.getStoppingPowerAdd());
								}
								if(inSlot.getMaxInventoryAdd() != 0.0f) {
									stats += String.format("\nShip Cap: +%d", inSlot.getMaxInventoryAdd());
								}
								String cost = String.format("\nValue: $%d",
										Math.round(inSlot.getValue() / 2.0f));
								this.getText().setText(itemName + stats + cost);
								this.setX(x);
								this.setY(480.0f - this.getDisplay().getHeight() - y);
								return true;
							}
						}
					}
				}
				return false;
			}
		};
		inventoryScreen.addChild(tooltip);
		inventoryScreen.setVisible(false);
	}

	/**
	 * Checks for a collision between the sprites
	 * 
	 * @return True/False
	 */
	private boolean doesCollide(BasicSprite one, BasicSprite two) {
		return one.overlaps(two);
	}

	/**
	 * Opens a map screen and handles anything that needs to be saved for a
	 * re-initialization of this class
	 */
	private void openMapScreen() {
		game.setScreen(new MapScreen(game, this));
		renderTime = 0.0f;
		lastInteractTime = 0.0f;
	}
	
	private void openTalentScreen() {
		game.setScreen(new TalentSelectionScreen(game, this));
		renderTime = 0.0f;
		lastInteractTime = 0.0f;
	}

	/**
	 * Open a specific component gui and close the rest.
	 * 
	 * @param gui The component gui to open
	 */
	private void openComponentGui(ComponentGui gui) {
		if (gui != null) {
			notifBox.setInteractable(false);
			if (interactScreen == gui) {
				interactScreen.setVisible(true);
				communicateScreen.setVisible(false);
				tradeScreen.setVisible(false);
				inventoryScreen.setVisible(false);
			} else if (communicateScreen == gui) {
				interactScreen.setVisible(false);
				communicateScreen.setVisible(true);
				tradeScreen.setVisible(false);
				inventoryScreen.setVisible(false);
			} else if (tradeScreen == gui) {
				interactScreen.setVisible(false);
				communicateScreen.setVisible(false);
				tradeScreen.setVisible(true);
				inventoryScreen.setVisible(false);
			} else if (inventoryScreen == gui) {
				interactScreen.setVisible(false);
				communicateScreen.setVisible(false);
				tradeScreen.setVisible(false);
				inventoryScreen.setVisible(true);
			}
		} else {
			notifBox.setInteractable(true);
			interactScreen.setVisible(false);
			communicateScreen.setVisible(false);
			tradeScreen.setVisible(false);
			inventoryScreen.setVisible(false);
		}
	}

	/**
	 * Force all ships in the system to hunt a target ship.
	 * 
	 * @param target The target
	 */
	private void setSystemHostile(Spaceship target) {
		for (Spaceship ship : currentSystem.getShipsInSystem()) {
			// If the ship is not a pirate, we set them hostile
			if (ship.getAllegiance() != null) {
				if (aiController.getCurrentTask(ship) != null) {
					// If the ship is already hunting something, we ignore this
					if (!(aiController.getCurrentTask(ship) instanceof ShipHuntTask)) {
						aiController.endCurrentTask(ship);
						aiController.createShipTask(ship, new ShipHuntTask(ship, target));
					}

					else {
						aiController.createShipTask(ship, new ShipHuntTask(ship, target));
					}
				}
				if (target.getSpriteType() == SpriteType.PLAYERSHIP) {
					minimap.getIcon(ship).set(AtlasData.createSprite(AtlasData.MINIMAP_ATLAS, "enemyship-icon"));
				}
			}
		}
	}

	/**
	 * Draws the background in relation to the player ship
	 */
	private void drawBackground() {
		float width = -background.getWidth() / 2;
		float height = -background.getHeight() / 2;
		float x = -(player.getPosition().getX() * 0.08f);
		float y = -(player.getPosition().getY() * 0.08f);
		game.batch.draw(background, (width + x) + 400.0f, (height + y) + 240.0f);
	}

	/**
	 * Draws the midground sprites in relation to the player ship
	 */
	private void drawMidground() {
		drawPositionSprite(currentSystem.getHomeworld());
		for (AsteroidObject ao : currentSystem.getAsteroids()) {
			drawPositionSprite(ao);
		}
		for (SpaceObject so : currentSystem.getExtras()) {
			drawPositionSprite(so);
		}
		for (Spaceship s : currentSystem.getShipsInSystem()) {
			drawPositionSprite(s);
			if (!s.isAlive()) {
				aiController.removeShip(s);
				currentSystem.removeShipFromSystem(s);
				minimap.remove(s);
				continue;
			}
		}
		drawDynamicSprites();
		drawPlayer();
	}

	/**
	 * Draws the overground sprites in relaiton to the player ship
	 */
	private void drawOverground() {
		damageText.render(game.batch);
		minimap.render(game.batch);
		notifBox.render(game.batch);
		interactScreen.render(game.batch);
		communicateScreen.render(game.batch);
		tradeScreen.render(game.batch);
		inventoryScreen.render(game.batch);
		playerUI.render(game.batch);
	}

	/**
	 * Renders the damage text based upon the shot and the spaceship hit
	 * 
	 * @param shot   The weapon shot fired
	 * @param damage The damage to display
	 */
	private void drawDamageText(WeaponFireSprite shot, float damage) {
		final int random = Mathematics.rand.nextInt(4) + 1; // Create a direction for the text to scroll
		DisplayableText addText = new DisplayableText(game.font, String.format("%.2f dmg", damage), TextType.H6) {
			@Override
			public void update() {
				/* Create text drag effect */
				if (random % 4 == 0) {
					setX(getX() + 0.5f);
					setY(getY() + 0.5f);
				} else if (random % 3 == 0) {
					setX(getX() - 0.5f);
					setY(getY() + 0.5f);
				} else if (random % 2 == 0) {
					setX(getX() - 0.5f);
					setY(getY() - 0.5f);
				} else {
					setX(getX() + 0.5f);
					setY(getY() - 0.5f);
				}
			}
		};
		addText.setX(shot.getX() + (addText.getTextWidth() / 2.0f));
		addText.setY(shot.getY() + (addText.getTextHeight() / 2.0f));
		addText.setColor(Color.YELLOW);
		damageText.addText(addText);
	}

	/**
	 * Draws the player ship on the screen.
	 */
	private void drawPlayer() {
		if (!player.isAlive()) {
			game.setScreen(new GameOverScreen(game));
			return;
		}
		if (player.getPosition().getX() > 7000.0f) {
			player.getPosition().setX(7000.0f);
		} else if (player.getPosition().getX() < -7000.0f) {
			player.getPosition().setX(-7000.0f);
		}
		if (player.getPosition().getY() > 7000.0f) {
			player.getPosition().setY(7000.0f);
		} else if (player.getPosition().getY() < -7000.0f) {
			player.getPosition().setY(-7000.0f);
		}
		player.render(game.batch);
	}

	/**
	 * Draws a sprite on screen if it's within acceptable rendering distance of the
	 * player.
	 * 
	 * @param sprite The sprite to draw
	 */
	private void drawPositionSprite(PositionSprite sprite) {
		sprite.setX(sprite.getPosition().getX() - player.getPosition().getX());
		sprite.setY(sprite.getPosition().getY() - player.getPosition().getY());
		if (sprite.isVisible()) {
			sprite.render(game.batch);
		}
	}

	/**
	 * . Draws all dynamic sprites to the screen and removes sprites that are off
	 * screen.
	 */
	private void drawDynamicSprites() {
		for (int i = 0; i < DataUtils.getWeaponSprites().length; i++) {
			WeaponFireSprite sprite = DataUtils.getWeaponSprites()[i];
			drawPositionSprite(sprite);
			if (sprite.getX() + sprite.getWidth() < -800.0f || sprite.getX() > (800.0f * 2.0f)
					|| sprite.getY() + sprite.getHeight() < -480.0f || sprite.getY() > (480.0f * 2.0f)) {
				// System.out.println("removed\n" + DataUtils.getWeaponSprites().length);
				DataUtils.removeWeaponFireSprite(i);
				i--;
				continue;
			}
			if (doesCollide(sprite, player)) {
				if (sprite.getParent().getParent() == player) {
					continue;
				} else {
					if (player.getLastHitTime() + 25L < System.currentTimeMillis()) {
						float damage = player.calculateDamage(sprite.getParent().getParent());
						drawDamageText(sprite, damage);
						if(player.getShield() - damage > 0.0f) {
							player.setShield(player.getShield() - damage);
						}
						else {
							float difference = damage - player.getShield();
							player.setShield(0.0f);
							player.setHealth(player.getHealth() - difference);
							if(player.getHealth() < 0.0f) {
								player.setHealth(0.0f);
							}
						}
						player.setLastHitTime(System.currentTimeMillis());
						DataUtils.removeWeaponFireSprite(i);
						i--;
						continue;
					}
				}

			}
			for (Spaceship s : currentSystem.getShipsInSystem()) {
				/* Removing sprite from the list, creating the damage text to display */
				if (sprite.getParent().getParent() == s) {
					continue;
				}
				if (doesCollide(sprite, s)) {
					if (s.getAllegiance() != null) {
						if (sprite.getParent().getParent().getAllegiance() != null) {
							if (sprite.getParent().getParent().getAllegiance() == s.getAllegiance()) {
								continue;
							}
						}
					}
					if (s.getLastHitTime() + 25L <= System.currentTimeMillis()) {
						if (s.getAllegiance() != null) {
							Faction f = s.getAllegiance();
							if (sprite.getParent().getParent().getSpriteType() == SpriteType.PLAYERSHIP) {
								float relation = PlayerStats.getFactionRelations(f);
								boolean changeNotif = PlayerStats.setFactionRelation(f, relation - 2.5f);
								if (changeNotif) {
									notifBox.addNotification(String.format("The %s's opinion of you has turned %s!",
											f.getName(), PlayerStats.getFactionOpinion(f)));
								}
							}
							if (f.getName().equalsIgnoreCase("pirate coalition")) {
								/*
								 * If this is a pirate system, the pirates attack. Otherwise no other ship cares
								 */
								if (currentSystem.getName().equalsIgnoreCase(f.getName())) {
									setSystemHostile(sprite.getParent().getParent());
								}
							}
							/* Any other faction is heavily frowned upon */
							else {
								setSystemHostile(sprite.getParent().getParent());
							}
						} else {
							/* The system government won't like you shooting at random 3rd parties */
							if (!(currentSystem.getName().equalsIgnoreCase("pirate coalition"))) {
								setSystemHostile(sprite.getParent().getParent());
							}
						}
						float damage = s.calculateDamage(sprite.getParent().getParent());
						drawDamageText(sprite, damage);
						if(s.getShield() - damage > 0.0f) {
							s.setShield(s.getShield() - damage);
						}
						else {
							float difference = damage - s.getShield();
							s.setShield(0.0f);
							s.setHealth(s.getHealth() - difference);
							if(s.getHealth() < 0.0f) {
								s.setHealth(0.0f);
							}
						}
						s.setLastHitTime(System.currentTimeMillis());
						if (aiController.getCurrentTask(s) != null) {
							if (!(aiController.getCurrentTask(s) instanceof ShipHuntTask)) {
								aiController.endCurrentTask(s);
								aiController.createShipTask(s, new ShipHuntTask(s, sprite.getParent().getParent()));
							}
						} else {
							aiController.createShipTask(s, new ShipHuntTask(s, sprite.getParent().getParent()));
						}
						DataUtils.removeWeaponFireSprite(i);
						i--;
						continue;
					}
				}
			}
			for (AsteroidObject asteroid : currentSystem.getAsteroids()) {
				if (doesCollide(sprite, asteroid)) {
					float damage = sprite.getParent().getBaseDmg();
					asteroid.setHealth(asteroid.getHealth() - damage);
					drawDamageText(sprite, damage);
					if (asteroid.getHealth() < 0.0f) {
						currentSystem.removeAsteroidFromSystem(asteroid);
						minimap.remove(asteroid);
						for (Item item : asteroid.getDroppedLoot()) {
							player.getInventory().addItem(item);
							notifBox.addNotification(String.format("We collected %d %s from the asteroid!",
									item.getAmount(), item.getName()));
						}
					}
					DataUtils.removeWeaponFireSprite(i);
					i--;
					continue;
				}
			}
		}
	}

	/**
	 * Draws text onto the screen.
	 * 
	 * @param font The font to draw on the screen
	 * @param str  The string to draw
	 * @param x    The x value on screen
	 * @param y    The y value on screen
	 */
	private void drawText(BitmapFont font, String str, float x, float y) {
		font.draw(game.batch, str, x, y);
	}

	/**
	 * Handles interaction with the sprite given.
	 * 
	 * @param sprite The sprite to interact with
	 */
	private void handleInteraction(PositionSprite sprite) {
		switch (sprite.getSpriteType()) {
		case HOMEWORLD: {
			componentGuisReset((SpaceObject) sprite);
			openComponentGui(interactScreen);
			break;
		}
		case MOON: {
			componentGuisReset((SpaceObject) sprite);
			openComponentGui(interactScreen);
			break;
		}
		default: {
			break;
		}
		}
	}

	/**
	 * Handles all inputs from keyboard and mouse
	 */
	private void handleInput() {
		if (KeyUtils.isKeyPressed(KeyUtils.KEY_TURN_R)) {
			if (KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_R)) {
				player.rotate(true, false);
			} else {
				player.rotate(false, false);
			}
		} else if (KeyUtils.isKeyPressed(KeyUtils.KEY_TURN_L)) {
			if (KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_R)) {
				player.rotate(true, true);
			} else {
				player.rotate(false, true);
			}
		}

		if (KeyUtils.isKeyJustPressed(KeyUtils.KEY_INVENTORY)) {
			if (inventoryScreen.isVisible()) {
				inventoryScreen.setVisible(false);
			} else {
				resetInventoryScreen();
				inventoryScreen.setVisible(true);
			}
		}
		
		if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_POWER_SHIELD)) {
			player.powerToSystem(1);
			playerUI.setSelectorState(2);
		}
		else if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_POWER_WEAPON)) {
			player.powerToSystem(2);
			playerUI.setSelectorState(3);
		}
		else if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_POWER_ENGINE)) {
			player.powerToSystem(3);
			playerUI.setSelectorState(1);
		}

		if (KeyUtils.isKeyPressed(KeyUtils.KEY_FORWARD)) {
			player.accelerate();
		} else if (KeyUtils.isKeyPressed(KeyUtils.KEY_BACKWARD)) {
			player.decelerate();
		}

		if (KeyUtils.isKeyPressed(KeyUtils.KEY_FIRE_WEAPON)) {
			WeaponFireSprite weaponShot = player.fireWeapon();
			if (weaponShot != null) {
				DataUtils.addWeaponFireSprite(weaponShot);
			}
		}

		if (KeyUtils.isKeyPressed(KeyUtils.KEY_INTERACT) && renderTime - lastInteractTime > 0.5f) {
			// Make sure we aren't already interacting with something
			if (!tradeScreen.isVisible() && !communicateScreen.isVisible() && !interactScreen.isVisible()) {
				for (PositionSprite sprite : currentSystem.getExtras()) {
					if (doesCollide(player, sprite)) {
						handleInteraction(sprite);
						lastInteractTime = renderTime;
						break;
					}
				}
				for (PositionSprite sprite : currentSystem.getShipsInSystem()) {
					if (doesCollide(player, sprite)) {
						handleInteraction(sprite);
						lastInteractTime = renderTime;
						break;
					}
				}
				if (doesCollide(player, currentSystem.getHomeworld())) {
					handleInteraction(currentSystem.getHomeworld());
					lastInteractTime = renderTime;
				}
			}
		} else if (KeyUtils.isKeyJustPressed(KeyUtils.KEY_EXIT)) {
			if (tradeScreen.isVisible() || communicateScreen.isVisible() || interactScreen.isVisible()
					|| inventoryScreen.isVisible()) {
				openComponentGui(null); // Exit any screen we are on
			}
		}

		if (KeyUtils.isKeyJustPressed(KeyUtils.KEY_MAP)) {
			openComponentGui(null);
			openMapScreen();
		}
		
		if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_TALENT)) {
			openComponentGui(null);
			openTalentScreen();
		}

		if (KeyUtils.isKeyPressed(KeyUtils.KEY_GRAVE) && KeyUtils.isKeyPressed(KeyUtils.KEY_BACKSLASH)) {
			debugModeOn = true;
		}
	}

	/**
	 * Handles input from debug commands
	 */
	private void handleDebugInput() {
		if (Gdx.input.justTouched()) {
			float x = player.getPosition().getX() + Gdx.input.getX();
			float y = player.getPosition().getY() + (480.0f - Gdx.input.getY());
			System.out.println("X: " + x + " Y: " + y);
			Position p = Mathematics.getCenterScreenPosition(player.getPosition());
			System.out.println("cX: " + p.getX() + " cY: " + p.getY());
		}
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f, 0.0f, 0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (!AssetUtils.assetManager.update()) {
			game.batch.begin();
			loadingText.render(game.batch);
			game.batch.end();
			finishedLoading = false;
			return;
		} else {
			if (!finishedLoading) {
				game.batch.begin();
				drawText(game.font, "Loaded!", 340.0f, 240.0f);
				drawText(game.font, "Use mouseclick or press any key to continue", 250.0f, 260.0f);
				game.batch.end();
				if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ANY_KEY)) {
					finishLoading();
				}
				return;
			}
		}

		renderTime += Gdx.graphics.getDeltaTime();

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		game.batch.begin();

		drawBackground();
		drawMidground();
		drawOverground();
		drawText(game.font, String.format("-Stardate: %s-", PlayerStats.getStardate().getStardateAsString()), 0.0f,
				480.0f);

		game.batch.end();
		game.batch.disableBlending();

		if (player.getThrust() != 0.0f) {
			if (interactScreen.isVisible() || communicateScreen.isVisible() || tradeScreen.isVisible()) {
				openComponentGui(null);
			}
		}

		aiController.update();
		playerUI.update();
		player.update();

		handleInput();
		if (debugModeOn) {
			handleDebugInput();
		}
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		
	}
}
