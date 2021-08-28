package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.spaceships.Spaceship;
import com.gmail.jpk.stu.sprites.DisplayableText.TextType;
import com.gmail.jpk.stu.utils.AtlasData;
import com.gmail.jpk.stu.utils.SpriteData;

public class PlayerUI extends Renderable {
	
	private Array<Sprite> healthBars;
	private Array<Sprite> shieldBars;
	private Array<DisplayableText> powerTexts;
	private Sprite selector;
	private int selectorState;
	private final Spaceship player;
	
	public PlayerUI(final BitmapFont gameFont, Spaceship player) {
		this.player = player;
		this.healthBars = new Array<Sprite>();
		this.shieldBars = new Array<Sprite>();
		this.powerTexts = new Array<DisplayableText>();
		this.selector = new Sprite(AtlasData.createSprite(AtlasData.MINIMAP_ATLAS, "playership-icon"));
		this.selectorState = 2;
		DisplayableText engineText = new DisplayableText(gameFont, "Engines", TextType.S1);
		engineText.setX(360.0f);
		engineText.setY(30.0f);
		DisplayableText shieldText = new DisplayableText(gameFont, "Shields", TextType.S1);
		shieldText.setX(410.0f);
		shieldText.setY(65.0f);
		shieldText.setColor(Color.YELLOW);
		DisplayableText weaponText = new DisplayableText(gameFont, "Weapons", TextType.S1);
		weaponText.setX(460.0f);
		weaponText.setY(30.0f);
		powerTexts.add(engineText);
		powerTexts.add(shieldText);
		powerTexts.add(weaponText);
		selector.setX(425.0f);
		selector.setY(30.0f);
		selector.setRotation(0.0f);
	}
	
	public void setSelectorState(int selectorState) {
		this.selectorState = selectorState;
	}
	
	public void update() {
		updateHealthAndShield();
		updateSelector();
	}
	
	private void updateHealthAndShield() {
		healthBars.clear();
		shieldBars.clear();
		float maxHp = player.getMaxHealth();
		float curHp = player.getHealth();
		int goodHpBars = (int)Math.round((curHp / maxHp) * 10);
		if(goodHpBars == 0 && curHp > 0.0f) {
			goodHpBars = 1;
		}
		float maxShield = player.getMaxShield();
		float curShield = player.getShield();
		int goodShieldBars = (int)Math.round((curShield / maxShield) * 10);
		if(goodShieldBars == 0 && curShield > 0.0f) {
			goodShieldBars = 1;
		}
		for(int i = 0; i < 10; i++) {
			float x = 5.0f + (i * 30.0f);
			float y = 5.0f;
			if(i < goodHpBars) {
				Sprite sprite = new Sprite(SpriteData.HEALTH_BAR.getSprite());
				sprite.setX(x);
				sprite.setY(y);
				healthBars.add(sprite);
			}
			else {
				Sprite sprite = new Sprite(SpriteData.HEALTH_BAR_EMPTY.getSprite());
				sprite.setX(x);
				sprite.setY(y);
				healthBars.add(sprite);
			}
			if(i < goodShieldBars) {
				Sprite sprite = new Sprite(SpriteData.SHIELD_BAR.getSprite());
				sprite.setX(x);
				sprite.setY(y + 20.0f);
				shieldBars.add(sprite);
			}
			else {
				Sprite sprite = new Sprite(SpriteData.SHIELD_BAR_EMPTY.getSprite());
				sprite.setX(x);
				sprite.setY(y + 20.0f);
				shieldBars.add(sprite);
			}
		}
	}
	
	private void updateSelector() {
		if(selectorState == 1) {
			if(selector.getRotation() != 110.0f) {
				if(selector.getRotation() < 110.0f) {
					selector.setRotation(selector.getRotation() + 10.0f);
				}
				else {
					selector.setRotation(selector.getRotation() - 10.0f);
				}
				if(selector.getRotation() == 110.0f) {
					powerTexts.get(0).setColor(Color.YELLOW);
					powerTexts.get(1).setColor(Color.WHITE);
					powerTexts.get(2).setColor(Color.WHITE);
				}
			}
		}
		else if(selectorState == 2) {
			if(selector.getRotation() != 0.0f) {
				if(selector.getRotation() > 249.0f) {
					selector.setRotation(selector.getRotation() + 10.0f);
				}
				else {
					selector.setRotation(selector.getRotation() - 10.0f);
				}
				if(selector.getRotation() == 0.0f || selector.getRotation() == 360.0f) {
					powerTexts.get(0).setColor(Color.WHITE);
					powerTexts.get(1).setColor(Color.YELLOW);
					powerTexts.get(2).setColor(Color.WHITE);
					selector.setRotation(0.0f);
				}
			}
		}
		else if(selectorState == 3) {
			if(selector.getRotation() != 250.0f) {
				if(selector.getRotation() == 0.0f) selector.setRotation(360.0f);
				if(selector.getRotation() < 250.0f) {
					selector.setRotation(selector.getRotation() + 10.0f);
				}
				else {
					selector.setRotation(selector.getRotation() - 10.0f);
				}
				if(selector.getRotation() == 250.0f) {
					powerTexts.get(0).setColor(Color.WHITE);
					powerTexts.get(1).setColor(Color.WHITE);
					powerTexts.get(2).setColor(Color.YELLOW);
				}
			}
		}
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		for(Sprite s : healthBars) {
			s.draw(batch);
		}
		for(Sprite s : shieldBars) {
			s.draw(batch);
		}
		for(DisplayableText dt: powerTexts) {
			dt.render(batch);
		}
		selector.draw(batch);
	}
}
