package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gmail.jpk.stu.utils.AtlasData;

public class TalentSprite extends BasicSprite {
	
	public TalentSprite(String talentName) {
		Sprite talentSprite = AtlasData.createSprite(AtlasData.TALENT_ATLAS, talentName);
		if(talentSprite != null) {
			this.setDisplaySprite(talentSprite);
		}
		else {
			this.setDisplaySprite(null);
		}
		this.setSpriteType(SpriteType.TALENT_ICON);
		this.setVisible(true);
		this.setInteractable(true);
		this.setX(0.0f);
		this.setY(0.0f);
	}
	
	public boolean isValid() {
		return (this.getDisplaySprite() != null ? true : false);
	}
}
