package com.gmail.jpk.stu.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class MultiText extends Renderable {
	
	private Array<DisplayableText> texts; // The texts to display
	
	public MultiText() {
		texts = new Array<DisplayableText>();
		this.setVisible(true);
	}
	
	public DisplayableText[] getTexts() {
		DisplayableText[] toReturn = new DisplayableText[texts.size];
		for(int i = 0; i < texts.size; i++) {
			toReturn[i] = texts.get(i);
		}
		return toReturn;
	}
	
	public void addText(DisplayableText text) {
		texts.add(text);
	}
	
	public void removeText(int index) {
		texts.removeIndex(index);
	}
	
	@Override
	public void update() {
		
	}
	
	@Override
	public void render(SpriteBatch batch) {
		super.render(batch);
		if(isVisible()) {
			for(int i = 0; i < texts.size; i++) {
				DisplayableText t = texts.get(i);
				t.render(batch);
			}
		}
	}
}
