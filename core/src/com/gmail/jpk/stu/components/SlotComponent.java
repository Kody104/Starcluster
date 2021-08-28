package com.gmail.jpk.stu.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.utils.SpriteData;

public class SlotComponent extends ButtonComponent {

	private BaseItem inSlot;
	private boolean isSelected;
	
	public SlotComponent(BaseItem inSlot) {
		this.inSlot = inSlot;
		isSelected = false;
		this.setDefaultSprite(SpriteData.SHOP_SLOT.getSprite());
		this.setHighlightSprite(SpriteData.SHOP_HIGHLIGHT.getSprite());
		this.setDisableSprite(SpriteData.SHOP_DISABLE.getSprite());
		this.setDisplay(this.getDefaultSprite());
	}
	
	public SlotComponent() {
		inSlot = null;
		isSelected = false;
		this.setDefaultSprite(SpriteData.SHOP_SLOT.getSprite());
		this.setHighlightSprite(SpriteData.SHOP_SLOT.getSprite());
		this.setDisableSprite(SpriteData.SHOP_SLOT.getSprite());
		this.setDisplay(this.getDefaultSprite());
	}
	
	public BaseItem getInSlotItem() {
		return inSlot;
	}
	
	public void setInSlotItem(BaseItem inSlot) {
		this.inSlot = inSlot;
	}
	
	public boolean isSelected() {
		return isSelected;
	}
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if(isInteractable()) {
			if(isSelected) {
				this.setDisplay(getHighlightSprite());
			}
			else {
				this.setDisplay(getDefaultSprite());
			}
		}
	}
	
	@Override
	public void setHovered(boolean isHovered) {
		if(!isSelected()) {
			super.setHovered(isHovered);
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		if(getParent().isVisible()) {
			if(isVisible()) {
				batch.draw(getDisplay(), this.getParent().getX() + this.getX(), this.getParent().getY() + this.getY());
				if(isInteractable()) {
					checkInput();
				}
				if(inSlot != null) {
					if(inSlot.getSprite() != null) {
						batch.draw(inSlot.getSprite(), this.getParent().getX() + this.getX() + 3.5f, this.getParent().getY() + this.getY() + 3.5f);
					}
				}
			}
		}
	}

	@Override
	protected void onInteract() {
		
	}

}
