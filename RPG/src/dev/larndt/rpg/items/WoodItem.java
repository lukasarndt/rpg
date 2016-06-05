package dev.larndt.rpg.items;

import dev.larndt.rpg.gfx.Assets;

public class WoodItem extends Item{

	public WoodItem() {
		super(Assets.wood, 2,1);
	}


	public WoodItem(int x, int y) {
		super(Assets.wood, 2,1);
		this.x = x;
		this.y = y;
	}
}
