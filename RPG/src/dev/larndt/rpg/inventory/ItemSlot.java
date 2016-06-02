package dev.larndt.rpg.inventory;

import dev.larndt.rpg.items.Item;

public class ItemSlot {
	private boolean occupied;
	private Item item;

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
	
}
