package dev.larndt.rpg.entities.creatures;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;

public class Slime extends Creature{
	
	public Slime(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_WIDTH);
		
		this.setBounds(2, 2, width-4, height-4);
		
		speed = 2;
	}

	@Override
	public void tick() {
		xMove = 0;
		if((Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH) && handler.getPlayer().getX() < x) {
			this.xMove = -speed;
		} else if (Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH && handler.getPlayer().getX() > x) {
			this.xMove = speed;
		}
		this.move();
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Assets.slime, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		//this.drawBounds(g);
		this.drawHealthBar(g);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
