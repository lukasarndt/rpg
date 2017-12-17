package dev.larndt.rpg.tiles;

import java.awt.Graphics;

import dev.larndt.rpg.gfx.Animation;
import dev.larndt.rpg.gfx.Assets;

public class WaterTile extends Tile {

	private Animation anim;
	
	public WaterTile(int id) {
		super(Assets.water[0], id);
		
		anim = new Animation(500, Assets.water);
	}
	
	public void tick() {
		anim.tick();
	}

	public void render(Graphics g, int x, int y) {
		super.render(anim.getCurrentFrame(), g, x, y);
	}
}
