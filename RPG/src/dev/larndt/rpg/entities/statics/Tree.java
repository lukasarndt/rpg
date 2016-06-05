package dev.larndt.rpg.entities.statics;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.items.WoodItem;
import dev.larndt.rpg.tiles.Tile;

public class Tree extends StaticEntity{

	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, 2 * Tile.TILE_WIDTH, 2 * Tile.TILE_HEIGHT);

		this.setBounds(35, 89, 40, 2*19);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		this.drawBounds(g);
		
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x,(int)y));
	}
	
	

}
