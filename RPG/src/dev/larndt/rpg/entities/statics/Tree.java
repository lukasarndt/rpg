package dev.larndt.rpg.entities.statics;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.items.WoodItem;
import dev.larndt.rpg.tiles.Tile;
import dev.larndt.rpg.gfx.Animation;

public class Tree extends StaticEntity{

	private Animation anim;
	
	public Tree(Handler handler, float x, float y) {
		super(handler, x, y, 2 * Tile.TILE_WIDTH, 2 * Tile.TILE_HEIGHT);
		
		width = 300;
		height = 300;
		
		this.setBounds(width/2 - width/6 + 20, height - height/3 - 10, width/7, height/5);
		
		anim = new Animation(62, Assets.tree1);	
		
		health = 2;
	}

	@Override
	public void tick() {
		anim.tick();
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(anim.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		this.drawBounds(g);
		
	}

	@Override
	public void die() {
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x,(int)y));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x + 40,(int)y + 70));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-20,(int)y+40));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-120,(int)y+40));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-60,(int)y+40));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-40,(int)y+70));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-20,(int)y+40));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-220,(int)y+140));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-260,(int)y+10));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-260,(int)y+140));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-150,(int)y+140));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-20,(int)y+140));
		handler.getWorld().getItemManager().addItem(new WoodItem((int)x-120,(int)y+140));
	}
	
	

}
