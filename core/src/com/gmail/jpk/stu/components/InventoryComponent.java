package com.gmail.jpk.stu.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.gmail.jpk.stu.inventory.BasicInventory;
import com.gmail.jpk.stu.items.BaseItem;
import com.gmail.jpk.stu.utils.KeyUtils;
import com.gmail.jpk.stu.utils.MouseUtils;

public class InventoryComponent extends Component {

	private BasicInventory inventory;
	private Array<SlotComponent> slots;
	private int selectedAmount;
	
	public InventoryComponent(BasicInventory inventory, SlotComponent[] slots) {
		this.inventory = inventory;
		this.slots = new Array<SlotComponent>();
		for(int i = 0; i < slots.length; i++) {
			this.slots.add(slots[i]);
		}
		this.selectedAmount = 0;
	}
	
	@Override
	public void setParent(ComponentGui gui) {
		super.setParent(gui);
		for(SlotComponent sc : slots) {
			sc.setParent(gui);
		}
	}
	
	@Override
	protected boolean checkInput() {
		if(Gdx.input.justTouched()) {
			float x = Gdx.input.getX();
			float y = Gdx.input.getY();
			SlotComponent selected = getSelectedSlot();
			for(SlotComponent sc : slots) {
				if(MouseUtils.isTouched(getParent().getX() + sc.getX(), getParent().getY() + sc.getY(), sc.getDisplay().getWidth(), sc.getDisplay().getHeight(), x, y)) {
					if(selected != null) {
						if(sc == selected) {
							continue;
						}
						else {
							if(sc.getInSlotItem() != null) {
								BaseItem item = sc.getInSlotItem();
								if(KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_R)) {
									selectedAmount = (int)Math.floor(item.getAmount() / 2);
								}
								else if(KeyUtils.isKeyPressed(KeyUtils.KEY_CTRL_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_CTRL_R)) {
									selectedAmount = 1;
								}
								else {
									selectedAmount = item.getAmount();
								}
								selected.setSelected(false);
								sc.setSelected(true);
							}
						}
					}
					else {
						if(sc.getInSlotItem() != null) {
							BaseItem item = sc.getInSlotItem();
							if(KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_SHIFT_R)) {
								selectedAmount = (int)Math.floor(item.getAmount() / 2);
							}
							else if(KeyUtils.isKeyPressed(KeyUtils.KEY_CTRL_L) || KeyUtils.isKeyPressed(KeyUtils.KEY_CTRL_R)) {
								selectedAmount = 1;
							}
							else {
								selectedAmount = item.getAmount();
							}
							sc.setSelected(true);
						}
					}
				}
			}
		}
		return false;
	}
	
	@Override
	public void render(SpriteBatch batch) {
		if(getParent().isVisible()) {
			if(isVisible()) {
				for(SlotComponent sc : slots) {
					sc.render(batch);
				}
				if(checkInput()) {
					onInteract();
				}
			}
		}
	}
	
	/**
	 * Returns the slot that is currently selected in this inventory
	 * @return	The selected slot
	 */
	public SlotComponent getSelectedSlot() {
		for(SlotComponent sc : slots) {
			if(sc.isSelected()) {
				return sc;
			}
		}
		return null;
	}
	
	/**
	 * Returns the index of the given slot component
	 * @param slot	The slot to find the index of
	 * @return	The index
	 */
	public int getSlotIndex(SlotComponent slot) {
		for(int i = 0; i < slots.size; i++) {
			if(slots.get(i) == slot) {
				return i;
			}
		}
		return -1;
	}
	
	public void addSlot(SlotComponent slot) {
		slots.add(slot);
	}
	
	public BasicInventory getInventory() {
		return inventory;
	}
	
	public void setInventory(BasicInventory inventory) {
		this.inventory = inventory;
	}
	
	public SlotComponent[] getSlots() {
		SlotComponent[] toReturn = new SlotComponent[slots.size];
		for(int i = 0; i < slots.size; i++) {
			toReturn[i] = slots.get(i);
		}
		return toReturn;
	}
	
	public void setSlots(Array<SlotComponent> slots) {
		this.slots = slots;
	}
	
	public int getSelectedAmount() {
		return selectedAmount;
	}
	
	public void setSelectedAmount(int selectedAmount) {
		this.selectedAmount = selectedAmount;
	}
	
	@Override
	protected void onInteract() {
		// do nothing
	}

	@Override
	public void update() {
		
	}

}
