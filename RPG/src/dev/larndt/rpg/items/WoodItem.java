package dev.larndt.rpg.items;

import java.awt.Graphics;

import dev.larndt.rpg.gfx.Assets;

public class WoodItem extends Item{

	public WoodItem() {
		super(Assets.wood, 2,1);
		setEnergy(3);
	}

	public WoodItem(int x, int y) {
		super(Assets.wood, 2,1);
		this.x = x;
		this.y = y;
		setEnergy(3);
	}
	
	public void render(Graphics g) {
		super.render(g);
	}
	
	public void use() {
		this.handler.getPlayer().eat(this);
	}
}
