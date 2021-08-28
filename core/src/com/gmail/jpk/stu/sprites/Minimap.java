package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.game.Faction;
import com.gmail.jpk.stu.player.PlayerStats;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.utils.DebugHandler;
import com.gmail.jpk.stu.utils.DebugHandler.ErrorReason;

public class Minimap extends Renderable{
	
	private final TextureAtlas atlas; // The textures to load from
	private Faction systemFaction;	// The owner of the system to check the texture atlas against
	private Sprite background; // The background of the minimap
	private Array<PositionSprite> tracked; // All tracked sprites in the system that will show up on the minimap
	private Array<Sprite> icons; // The icons displayed on the minimap
	
	/**
	 * Constructor for the minimap.
	 * @param atlas	The textures to load from
	 * @param background	The background sprite for the minimap
	 * @param x	The x value of the minimap
	 * @param y	The y value of the minimap
	 * @param systemFaction	The current system faction owner 
	 */
	public Minimap(TextureAtlas atlas, Sprite background, float x, float y, Faction systemFaction) {
		this.atlas = atlas;
		this.systemFaction = systemFaction;
		this.background = background;
		setX(x);
		setY(y);
		// this.x = x;
		// this.y = y;
		tracked = new Array<PositionSprite>();
		icons = new Array<Sprite>();
	}
	
	/**
	 * Updates the position of the sprites of the minimap.
	 */
	@Override
	public void update() {
		for(int i = 0; i < tracked.size; i++) {
			PositionSprite track = tracked.get(i); // Tracking sprite
			Sprite icon = icons.get(i); // Icon for tracking sprite
			float width = background.getWidth();
			float height = background.getHeight();
			float drawY = track.getPosition().getX() == 7000.0f ? 0.0f : 1.0f; // If the player is at the max x position, drawX is 0. Else it is 1.
			float drawX = track.getPosition().getY() == 7000.0f ? 0.0f : 1.0f; // If the player is at the max y position, drawY is 0. Else it is 1.
			drawX += (getX() + (width * ((track.getPosition().getX() + 7000.0f) / 14000.0f))) - (icon.getWidth() / 2); // Calculates the ratio to the screen that this should be at.
			drawY += (getY() + (height * ((track.getPosition().getY() + 7000.0f) / 14000.0f)) - (icon.getHeight()) / 2); // Calculates the ratio to the screen that this should be at.
			icon.setX(drawX);
			icon.setY(drawY);
			icon.setRotation(track.getRotation());
		}
	}
	
	public void remove(PositionSprite sprite) {
		for(int i = 0; i < tracked.size; i++) {
			if(tracked.get(i) == sprite) {
				tracked.removeIndex(i);
				icons.removeIndex(i);
				break;
			}
		}
	}
	
	public void removeIndex(int index) {
		tracked.removeIndex(index);
		icons.removeIndex(index);
	}
	
	/**
	 * Resets the minimap so that it tracks nothing.
	 */
	public void reset() {
		tracked.clear();
		icons.clear();
		systemFaction = null;
	}
	
	/**
	 * Return the icon sprite assosicated with the track sprite.
	 * <br><b>Only works if the track sprite is tracked beforehand!</b>
	 * @param track	The tracked position sprite
	 * @return	The icon of the position sprite.
	 */
	public Sprite getIcon(PositionSprite track) {
		for(int i = 0; i < tracked.size; i++) {
			if(tracked.get(i) == track) {
				return getIcon(i);
			}
		}
		return null;
	}
	
	/**
	 * Returns the icon from the index number i.
	 * @param i	The index of the icon to return
	 * @return	The icon
	 */
	private Sprite getIcon(int i) {
		return icons.get(i);
	}
	
	/**
	 * Tracks sprites that are given to it to track
	 * @param toTrack	The sprites that are being tracked
	 */
	public void track(PositionSprite... toTrack) {
		for(PositionSprite track : toTrack) {
			PositionSprite icon = null;
			switch(track.getSpriteType()) {
				case PLAYERSHIP:
				{
					icon = new PositionSprite(atlas.createSprite("playership-icon"), SpriteType.MINIMAP_ICON);
					icon.setX(getX());
					icon.setY(getY());
					break;
				}
				case SPACESHIP:
				{
					Spaceship s = (Spaceship) track;
					if(s.getAllegiance() != null) {
						switch(PlayerStats.getFactionOpinion(s.getAllegiance())) {
							case "friendly":
							{
								icon = new PositionSprite(atlas.createSprite("friendlyship-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "neutral":
							{
								icon = new PositionSprite(atlas.createSprite("neutralship-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "hostile":
							{
								icon = new PositionSprite(atlas.createSprite("enemyship-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							default:
							{
								DebugHandler.err(ErrorReason.UNKNOWN_FACTION_OPINION);
								break;
							}
						}
					}
					else {
						icon = new PositionSprite(atlas.createSprite("neutralship-icon"), SpriteType.MINIMAP_ICON);
					}
					icon.setX(getX());
					icon.setY(getY());
					break;
				}
				case HOMEWORLD:
				{
					if(systemFaction != null) {
						switch(PlayerStats.getFactionOpinion(systemFaction)) {
							case "friendly":
							{
								icon = new PositionSprite(atlas.createSprite("friendlyplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "neutral":
							{
								icon = new PositionSprite(atlas.createSprite("neutralplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "hostile":
							{
								icon = new PositionSprite(atlas.createSprite("enemyplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							default:
							{
								DebugHandler.err(ErrorReason.UNKNOWN_FACTION_OPINION);
								break;
							}
						}
					}
					else {
						icon = new PositionSprite(atlas.createSprite("uncolonizedplanet-icon"), SpriteType.MINIMAP_ICON);
					}
					icon.setX(getX());
					icon.setY(getY());
					break;
				}
				case MOON:
				{
					if(systemFaction != null) {
						switch(PlayerStats.getFactionOpinion(systemFaction)) {
							case "friendly":
							{
								icon = new PositionSprite(atlas.createSprite("friendlyplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "neutral":
							{
								icon = new PositionSprite(atlas.createSprite("neutralplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							case "hostile":
							{
								icon = new PositionSprite(atlas.createSprite("enemyplanet-icon"), SpriteType.MINIMAP_ICON);
								break;
							}
							default:
							{
								DebugHandler.err(ErrorReason.UNKNOWN_FACTION_OPINION);
								break;
							}
						}
					}
					else {
						icon = new PositionSprite(atlas.createSprite("uncolonizedplanet-icon"), SpriteType.MINIMAP_ICON);
					}
					icon.setX(getX());
					icon.setY(getY());
					break;
				}
				case ASTEROID:
				{
					icon = new PositionSprite(atlas.createSprite("asteroid-icon"), SpriteType.ASTEROID_ICON);
					icon.setX(getX());
					icon.setY(getY());
					break;
				}
				default:
				{
					continue;
				}
			}
			tracked.add(track);
			icons.add(icon.getDisplaySprite());
		}
	}
	
	/**
	 * This is called to render the icons to the screen
	 * @param batch	The spritebatch to output with
	 */
	public void render(SpriteBatch batch) {
		update();
		batch.draw(background, getX(), getY());
		for(Sprite sprite : icons) {
			sprite.draw(batch);
		}
	}
	
	public void setSystemFaction(Faction systemFaction) {
		this.systemFaction = systemFaction;
	}
}
