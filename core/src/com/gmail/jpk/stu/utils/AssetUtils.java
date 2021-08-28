package com.gmail.jpk.stu.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetUtils {
	
	private static int loadState;
	
	public static final AssetManager assetManager = new AssetManager();
	
	/**
	 * Loads all files for the functioning of the game
	 */
	public static void init() {
		loadState = 1;
		AssetUtils.load("./map-background.png", Texture.class);
		AssetUtils.load("./map/icons/minimap-background.png", Texture.class);
		AssetUtils.load("./half-background.png", Texture.class);
		AssetUtils.load("./notification-background.png", Texture.class);
		AssetUtils.load("./planets/interaction/tooltip-background.png", Texture.class);
		AssetUtils.load("./planets/interaction/inventory-background.png", Texture.class);
		AssetUtils.load("./planets/interaction/equipment-background.png", Texture.class);
		loadState = 2;
		AssetUtils.load("./spaceships/FactionOne/sprites.txt", TextureAtlas.class);
		AssetUtils.load("./spaceships/weapons/weapons.txt", TextureAtlas.class);
		loadState = 3;
		AssetUtils.load("./planets/interaction/city-1.png", Texture.class);
		AssetUtils.load("./planets/interaction/city-2.png", Texture.class);
		AssetUtils.load("./planets/interaction/buttons.txt", TextureAtlas.class);
		AssetUtils.load("./talents/sprites.txt", TextureAtlas.class);
		AssetUtils.load("./planets/interaction/slot.png", Texture.class);
		AssetUtils.load("./planets/interaction/slot-disabled.png", Texture.class);
		AssetUtils.load("./planets/interaction/slot-highlight.png", Texture.class);
		loadState = 4;
		AssetUtils.load("./items/diamond-ore.png", Texture.class);
		AssetUtils.load("./items/diamond.png", Texture.class);
		loadState = 5;
		AssetUtils.load("./map/icons/minimap-sprites.txt", TextureAtlas.class);
		loadState = 6;
		AssetUtils.load("./planets/planet-1.png", Texture.class);
		AssetUtils.load("./planets/planet-2.png", Texture.class);
		AssetUtils.load("./planets/planet-3.png", Texture.class);
		AssetUtils.load("./planets/planet-4.png", Texture.class);
		AssetUtils.load("./planets/planet-5.png", Texture.class);
		AssetUtils.load("./planets/planet-6.png", Texture.class);
		AssetUtils.load("./planets/planet-7.png", Texture.class);
		AssetUtils.load("./planets/planet-8.png", Texture.class);
		AssetUtils.load("./planets/planet-9.png", Texture.class);
		AssetUtils.load("./planets/planet-10.png", Texture.class);
		AssetUtils.load("./planets/planet-11.png", Texture.class);
		AssetUtils.load("./planets/planet-12.png", Texture.class);
		AssetUtils.load("./planets/planet-13.png", Texture.class);
		AssetUtils.load("./planets/planet-14.png", Texture.class);
		AssetUtils.load("./planets/planet-15.png", Texture.class);
		AssetUtils.load("./planets/planet-16.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-1-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-2-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-3-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-4-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-5-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-6-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-7-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-8-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-9-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-10-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-11-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-12-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-13-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-14-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-15-m.png", Texture.class);
		AssetUtils.load("./planets/medium/planet-16-m.png", Texture.class);
		AssetUtils.load("./planets/small/sm-planets.txt", TextureAtlas.class);
		AssetUtils.load("./planets/xsmall/x-planets.txt", TextureAtlas.class);
		AssetUtils.load("./planets/asteroid.png", Texture.class);
		AssetUtils.load("./health_bar.png", Texture.class);
		AssetUtils.load("./health_bar_empty.png", Texture.class);
		AssetUtils.load("./shield_bar.png", Texture.class);
		AssetUtils.load("./shield_bar_empty.png", Texture.class);
	}
	
	public static String getLoadText() {
		String s = "Loading ";
		switch(loadState) {
			case 1:
			{
				s += "backgrounds...";
				break;
			}
			case 2:
			{
				s += "spaceships...";
				break;
			}
			case 3:
			{
				s += "planet interactions...";
				break;
			}
			case 4:
			{
				s += "items...";
				break;
			}
			case 5:
			{
				s += "minimap....";
				break;
			}
			case 6:
			{
				s += "planets...";
				break;
			}
			default:
			{
				s += "something...";
				break;
			}
		}
		float percLoad = AssetUtils.assetManager.getProgress() * 100.0f;
		s += String.format("\n%d percent complete...", (int) (percLoad));
		return s;
	}
	
	/**
	 * Disposes of the asset manager
	 */
	public static void dispose() {
		assetManager.dispose();
	}
	
	/**
	 * Loads a specified class type from the filepath
	 * @param filepath	The filepath to load from
	 * @param type	The class type to load
	 */
	public static <T> void load(String filepath, Class<T> type) {
		AssetUtils.assetManager.load(filepath, type);
	}
	
	/**
	 * Loads multiple specified class types from the filepaths given
	 * @param type	The class type of all the filepaths
	 * @param filepaths	The filepaths to load
	 */
	public static <T> void massLoad(Class<T> type, String... filepaths) {
		for(String s : filepaths) {
			AssetUtils.assetManager.load(s, type);
		}
	}
	
	/**
	 * Gets a specified class type from the given filepath and type
	 * @param filepath	The filepath to get the asset from
	 * @param type	The class type of asset it is
	 * @return	The class type loaded
	 */
	public static <T> T get(String filepath, Class<T> type) {
		return AssetUtils.assetManager.get(filepath, type);
	}
	
	/**
	 * Gets a texture atlas from the asset manager according to the filepath
	 * @param filepath	The filepath for the texture atlas
	 * @return	Texture atlas
	 */
	public static TextureAtlas getAtlas(String filepath) {
		return AssetUtils.assetManager.get(filepath, TextureAtlas.class);
	}
	
	/**
	 * Gets a texture from the asset manager according to the filepath
	 * @param filepath	The filepath for the texture
	 * @return	Texture
	 */
	public static Texture getTexture(String filepath) {
		return AssetUtils.assetManager.get(filepath, Texture.class);
	}
}
