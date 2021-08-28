package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;

public enum SpriteData {
	// Background Sprites
	SPACE_BACKGROUND("./map-background.png"), MINIMAP_BACKGROUND("./map/icons/minimap-background.png"), COMPONENT_HALF_BACKGROUND("./half-background.png"),
	TOOLTIP_BACKGROUND("./planets/interaction/tooltip-background.png"), NOTIFICATION_BACKGROUND("./notification-background.png"), 
	INVENTORY_BACKGROUND("./planets/interaction/inventory-background.png"), EQUIPMENT_BACKGROUND("./planets/interaction/equipment-background.png"),
	// Interaction Sprites
	CITYSCAPE_ONE("./planets/interaction/city-1.png"), CITYSCAPE_TWO("./planets/interaction/city-2.png"), SHOP_SLOT("./planets/interaction/slot.png"),
	SHOP_DISABLE("./planets/interaction/slot-disabled.png"), SHOP_HIGHLIGHT("./planets/interaction/slot-highlight.png"),
	// Item sprites
	DIAMOND_ORE_SPRITE("./items/diamond-ore.png"), DIAMOND_SPRITE("./items/diamond.png"),
	// Large Planet Sprites
	PLANET_ONE("./planets/planet-1.png"), PLANET_TWO("./planets/planet-2.png"), PLANET_THREE("./planets/planet-3.png"),
	PLANET_FOUR("./planets/planet-4.png"), PLANET_FIVE("./planets/planet-5.png"), PLANET_SIX("./planets/planet-6.png"),
	PLANET_SEVEN("./planets/planet-7.png"), PLANET_EIGHT("./planets/planet-8.png"), PLANET_NINE("./planets/planet-9.png"),
	PLANET_TEN("./planets/planet-10.png"), PLANET_ELEVEN("./planets/planet-11.png"), PLANET_TWELVE("./planets/planet-12.png"),
	PLANET_THIRTEEN("./planets/planet-13.png"), PLANET_FOURTEEN("./planets/planet-14.png"),PLANET_FIFTEEN("./planets/planet-15.png"),
	PLANET_SIXTEEN("./planets/planet-16.png"),
	// Medium Planet Sprites
	PLANET_ONE_M("./planets/medium/planet-1-m.png"), PLANET_TWO_M("./planets/medium/planet-2-m.png"), 
	PLANET_THREE_M("./planets/medium/planet-3-m.png"), PLANET_FOUR_M("./planets/medium/planet-4-m.png"), 
	PLANET_FIVE_M("./planets/medium/planet-5-m.png"), PLANET_SIX_M("./planets/medium/planet-6-m.png"),
	PLANET_SEVEN_M("./planets/medium/planet-7-m.png"), PLANET_EIGHT_M("./planets/medium/planet-8-m.png"),
	PLANET_NINE_M("./planets/medium/planet-9-m.png"), PLANET_TEN_M("./planets/medium/planet-10-m.png"), 
	PLANET_ELEVEN_M("./planets/medium/planet-11-m.png"), PLANET_TWELVE_M("./planets/medium/planet-12-m.png"),
	PLANET_THIRTEEN_M("./planets/medium/planet-13-m.png"), PLANET_FOURTEEN_M("./planets/medium/planet-14-m.png"), 
	PLANET_FIFTEEN_M("./planets/medium/planet-15-m.png"), PLANET_SIXTEEN_M("./planets/medium/planet-16-m.png"),
	// Asteroid
	ASTEROID("./planets/asteroid.png"),
	// Player UI
	HEALTH_BAR("./health_bar.png"), HEALTH_BAR_EMPTY("./health_bar_empty.png"),
	SHIELD_BAR("./shield_bar.png"), SHIELD_BAR_EMPTY("./shield_bar_empty.png");
	
	private final Sprite sprite;
	
	SpriteData(String filepath) {
		sprite = new Sprite(AssetUtils.getTexture(filepath));
	}
	
	public Sprite getSprite() {
		Sprite clone = new Sprite(sprite.getTexture());
		return clone;
	}
}
