package com.gmail.jpk.stu.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gmail.jpk.stu.components.ButtonComponent;
import com.gmail.jpk.stu.components.Component;
import com.gmail.jpk.stu.components.ComponentGui;
import com.gmail.jpk.stu.components.SpriteComponent;
import com.gmail.jpk.stu.components.TextComponent;
import com.gmail.jpk.stu.game.Faction;
import com.gmail.jpk.stu.player.PlayerStats;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.DisplayableText.TextType;
import com.gmail.jpk.stu.sprites.PositionSprite;
import com.gmail.jpk.stu.sprites.SpriteType;
import com.gmail.jpk.stu.starcluster.Starcluster;
import com.gmail.jpk.stu.universe.SolarSystem;
import com.gmail.jpk.stu.universe.Universe;
import com.gmail.jpk.stu.utils.AtlasData;
import com.gmail.jpk.stu.utils.DebugHandler;
import com.gmail.jpk.stu.utils.DebugHandler.ErrorReason;
import com.gmail.jpk.stu.utils.KeyUtils;
import com.gmail.jpk.stu.utils.MouseUtils;
import com.gmail.jpk.stu.utils.SpriteData;

public class MapScreen extends BaseScreen {
	
	private final GameScreen backScreen;
	private TextureAtlas textureAtlas;
	private Array<PositionSprite> allSystems;
	private SolarSystem currentSelected;
	
	private ComponentGui travelScreen;
	
	public MapScreen(final Starcluster game, final GameScreen backScreen) {
		super(game);
		this.backScreen = backScreen;
		
		textureAtlas = new TextureAtlas("./map/sprites.txt");
		allSystems = new Array<PositionSprite>();
		travelScreen = new ComponentGui();
		travelScreen.setX(400.0f);
		travelScreen.setY(240.0f);
		travelScreen.setVisible(false);
		
		// Looking through every system to see if they have a) been visited and b) have a faction that owns them
		for(SolarSystem system : Universe.getAllSystems()) {
			float x = system.getPosition().getX();
			float y = system.getPosition().getY();
			if(PlayerStats.hasVisitedSystem(system)) {
				boolean isOwned = false;
				for(Faction f : Universe.getAllFactions()) {
					if(!f.doesOwnSystem(system)) { // Skips if the faction doesn't own this system
						continue;
					}
					else {
						isOwned = true;
						switch(PlayerStats.getFactionOpinion(f)) {
							case "friendly":
							{
								allSystems.add(new PositionSprite(textureAtlas.createSprite("friendly-planet"), SpriteType.MAP_ICON, x, y));
								break;
							}
							case "neutral":
							{
								allSystems.add(new PositionSprite(textureAtlas.createSprite("neutral-planet"), SpriteType.MAP_ICON, x, y));
								break;
							}
							case "hostile":
							{
								allSystems.add(new PositionSprite(textureAtlas.createSprite("hostile-planet"), SpriteType.MAP_ICON, x, y));
								break;
							}
							default:
							{
								DebugHandler.err(ErrorReason.UNKNOWN_FACTION_OPINION);
								break;
							}
						}
						break;
					}
				}
				if(!isOwned) {
					allSystems.add(new PositionSprite(textureAtlas.createSprite("uncolonized-planet"), SpriteType.MAP_ICON, x, y));
				}
			}
			else {
				allSystems.add(new PositionSprite(textureAtlas.createSprite("unknown-planet"), SpriteType.MAP_ICON, x, y));
			}
		}
	}
	
	private void buildTravelScreen(SolarSystem target) {
		travelScreen.clear();
		
		boolean hasVisited = PlayerStats.hasVisitedSystem(target);
		
		if(backScreen.debugModeOn) {
			hasVisited = true;
		}
		
		SpriteComponent background = new SpriteComponent(SpriteData.COMPONENT_HALF_BACKGROUND.getSprite(), 0.0f, 0.0f);
		background.setInteractable(false);
		travelScreen.addChild(background);
		TextComponent systemName = new TextComponent(game.font, target.getName());
		if(!hasVisited) {
			systemName.getTextField().setText("???");
		}
		systemName.getTextField().setTextType(TextType.H1);
		systemName.setX((background.getDisplay().getWidth() / 2.0f) - (systemName.getTextField().getTextWidth() / 2.0f));
		systemName.setY(background.getDisplay().getHeight() - systemName.getTextField().getTextHeight());
		if(hasVisited) {
			if(Universe.getSystemFactionOwner(target) != null) {
				switch(PlayerStats.getFactionOpinion(Universe.getSystemFactionOwner(target))) {
					case "friendly":
					{
						systemName.getTextField().setColor(Color.GREEN);
						break;
					}
					case "neutral":
					{
						systemName.getTextField().setColor(Color.YELLOW);
						break;
					}
					case "hostile":
					{
						systemName.getTextField().setColor(Color.RED);
						break;
					}
					default:
					{
						systemName.getTextField().setColor(Color.PURPLE);
						break;
					}
				}
			}
		}
		travelScreen.addChild(systemName);
		TextComponent homeworldName = new TextComponent(game.font, "Homeworld: " + target.getHomeworld().getName());
		if(!hasVisited) {
			homeworldName.getTextField().setText("Homeworld: ???");
		}
		homeworldName.setX((background.getDisplay().getWidth() / 2.0f) - (homeworldName.getTextField().getTextWidth() / 2.0f));
		homeworldName.setY(((systemName.getY() - systemName.getTextField().getTextHeight()) - homeworldName.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(homeworldName);
		TextComponent population = new TextComponent(game.font, "Population: " + target.getHomeworld().getPopulation() + " Bn");
		if(!hasVisited) {
			population.getTextField().setText("Population: ???");
		}
		population.setX((background.getDisplay().getWidth() / 2.0f) - (population.getTextField().getTextWidth() / 2.0f));
		population.setY(((homeworldName.getY() - homeworldName.getTextField().getTextHeight()) - population.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(population);
		TextComponent economyStrength = new TextComponent(game.font, "Economy Strength: " + target.getHomeworld().getEconomyStrength().toString());
		if(!hasVisited) {
			economyStrength.getTextField().setText("Economy Strength: ???");
		}
		economyStrength.setX((background.getDisplay().getWidth() / 2.0f) - (economyStrength.getTextField().getTextWidth() / 2.0f));
		economyStrength.setY(((population.getY() - population.getTextField().getTextHeight()) - economyStrength.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(economyStrength);
		String government = "None";
		if(Universe.getSystemFactionOwner(target) != null) {
			government = Universe.getSystemFactionOwner(target).getName();
		}
		TextComponent factionName = new TextComponent(game.font, "Government: " + government);
		if(!hasVisited) {
			factionName.getTextField().setText("Governemnt: ???");
		}
		factionName.setX((background.getDisplay().getWidth() / 2.0f) - (factionName.getTextField().getTextWidth() / 2.0f));
		factionName.setY(((economyStrength.getY() - economyStrength.getTextField().getTextHeight()) - factionName.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(factionName);
		TextComponent trafficAmount = new TextComponent(game.font, String.format("Average Traffic: %d - %d ships", target.getAvgTraffic(), target.getAvgTraffic() * 2));
		if(!hasVisited) {
			trafficAmount.getTextField().setText("Average Traffic: ??? - ??? ships");
		}
		trafficAmount.setX((background.getDisplay().getWidth() / 2.0f) - (trafficAmount.getTextField().getTextWidth() / 2.0f));
		trafficAmount.setY(((factionName.getY() - factionName.getTextField().getTextHeight()) - trafficAmount.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(trafficAmount); 
		TextComponent travelTime = new TextComponent(game.font, String.format("Total trip time: %.2f solar days", travelTimeTo(target)));
		travelTime.setX((background.getDisplay().getWidth() / 2.0f) - (travelTime.getTextField().getTextWidth() / 2.0f));
		travelTime.setY(((trafficAmount.getY() - trafficAmount.getTextField().getTextHeight()) - travelTime.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(travelTime); 
		TextComponent travelDistance = new TextComponent(game.font, String.format("Distance to system: %.2f", distanceTo(target)));
		travelDistance.setX((background.getDisplay().getWidth() / 2.0f) - (travelDistance.getTextField().getTextWidth() / 2.0f));
		travelDistance.setY(((travelTime.getY() - travelTime.getTextField().getTextHeight()) - travelDistance.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(travelDistance);
		TextComponent travelCost = new TextComponent(game.font, String.format("Fuel Cost: %.2f fuel", fuelCost(target)));
		travelCost.setX((background.getDisplay().getWidth() / 2.0f) - (travelCost.getTextField().getTextWidth() / 2.0f));
		travelCost.setY(((travelDistance.getY() - travelDistance.getTextField().getTextHeight()) - travelCost.getTextField().getTextHeight()) - 5.0f);
		travelScreen.addChild(travelCost); 
		
		TextureAtlas buttons = AtlasData.BUTTON_ATLAS.getAtlas();
		ButtonComponent travelButton = new ButtonComponent(buttons.createSprite("travel-button"), buttons.createSprite("travel-highlight"),
				buttons.createSprite("travel-disabled")) {
			@Override
			protected void onInteract() {
				if(currentSelected == null) {
					return;
				}
				if(attemptTravel(currentSelected)) {
					backScreen.setCurrentSystem(currentSelected);
					game.setScreen(backScreen);
					dispose();
				}
			}
		};
		travelButton.setX((background.getDisplay().getWidth() / 2.0f) - (travelButton.getDisplay().getWidth() / 2.0f));
		travelButton.setY(background.getY() + 5.0f);
		travelScreen.addChild(travelButton);
		travelScreen.setVisible(true);
	}
	
	/**
	 * Calculates the fuel cost of going to a solar system.
	 * @param destination	The solar system to go to
	 * @return	The cost in fuel of going to the destination
	 */
	private float fuelCost(SolarSystem destination) {
		final float fuelToDistance = 0.05f;
		float totalDistance = distanceTo(destination);
		float totalFuelCost = totalDistance * fuelToDistance;
		return totalFuelCost;
	}
	
	/**
	 * Calculates the distance between the current solar system and the destination solar system
	 * @param destination	The solar system to go to
	 * @return	The distance to the solar system destination
	 */
	private float distanceTo(SolarSystem destination) {
		float xDistance = Math.abs(backScreen.getCurrentSystem().getPosition().getX() - destination.getPosition().getX());
		float yDistance = Math.abs(backScreen.getCurrentSystem().getPosition().getY() - destination.getPosition().getY());
		float totalDistance = (xDistance + yDistance) / 2.0f;
		return totalDistance;
	}
	
	/**
	 * Calculates the time to travel between the current solar system and the destination solar system
	 * @param destination	The solar system to go to
	 * @return	The time to get to the solar system destination
	 */
	private float travelTimeTo(SolarSystem destination) {
		Spaceship player = backScreen.getPlayer();
		final float timeToDistance = 2.65f;
		float totalTime = (distanceTo(destination) * timeToDistance) / player.getMaxThrust();
		return totalTime;
	}
	
	/**
	 * Calculates and subtracts the fuel cost of going to the target solar system.
	 * @param target	The solar system to go to
	 * @return	True if the player can afford the fuel, false otherwise
	 */
	private boolean attemptTravel(SolarSystem target) {
		float totalFuelCost = fuelCost(target);
		int travelTime = Math.round(travelTimeTo(target));
		if(PlayerStats.getCurShipFuel() >= totalFuelCost) {
			PlayerStats.setCurShipFuel(PlayerStats.getCurShipFuel() - totalFuelCost);
			PlayerStats.getStardate().increment(travelTime);
			if(travelTime > 14) {
				backScreen.sprinkleAsteroids(Universe.getAllSystemsAsBasic());
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Handles all interaction inputs
	 */
	private void handleInput() {
		float x = Gdx.input.getX();
		float y = 480.0f - Gdx.input.getY();
		boolean isTouching = false;
		if(Gdx.input.isTouched()) {
			for(int i = 0; i < allSystems.size; i++) {
				PositionSprite s = allSystems.get(i);
				if(MouseUtils.isTouched(s, x, y)) {
					currentSelected = Universe.getAllSystems().get(i);
				}
			}
		}
		else {
			if(currentSelected == null) {
				for(int i = 0; i < allSystems.size; i++) {
					PositionSprite s = allSystems.get(i);
					if(MouseUtils.isTouched(s, x, y)) {
						if(!travelScreen.isVisible()) {
							buildTravelScreen(Universe.getAllSystems().get(i));
						}
						Component bg = travelScreen.getChild(0);
						if(bg.getDisplay().getWidth() + x > 800.0f) {
							float xOffset = x - bg.getDisplay().getWidth() < 0 ? x - bg.getDisplay().getWidth() : 0.0f;
							travelScreen.setX(x - bg.getDisplay().getWidth() - xOffset);
						}
						else {
							travelScreen.setX(x);
						}
						if(bg.getDisplay().getHeight() + y > 480.0f) {
							float yOffset = y - bg.getDisplay().getHeight() < 0 ? y - bg.getDisplay().getHeight() : 0.0f ;
							travelScreen.setY(y - bg.getDisplay().getHeight() - yOffset);
						}
						else {
							travelScreen.setY(y);
						}
						isTouching = true;
						//TODO: Mouse-over gui
					}
				}
				if(!isTouching) {
					if(travelScreen.isVisible()) {
						travelScreen.setVisible(false);
					}
				}
			}
		}
		
		if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_MAP)) {
			game.setScreen(backScreen);
			dispose();
		}
		else if(KeyUtils.isKeyJustPressed(KeyUtils.KEY_EXIT)) {
			currentSelected = null;
		}
	}
	
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0.0f,  0.0f,  0.0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		game.batch.begin();
		for(PositionSprite s : allSystems) {
			s.render(game.batch);
		}
		travelScreen.render(game.batch);
		if(backScreen.debugModeOn) {
			game.font.draw(game.batch, "-Debug Mode-", 0.0f, 480.0f);
		}
		game.batch.end();
		game.batch.disableBlending();
		
		renderTime += Gdx.graphics.getDeltaTime();
		
		handleInput();
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
		textureAtlas.dispose();
		for(PositionSprite s : allSystems) {
			s.dispose();
		}
	}
}
