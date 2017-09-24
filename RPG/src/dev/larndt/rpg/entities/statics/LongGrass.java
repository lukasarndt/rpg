package dev.larndt.rpg.entities.statics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.gfx.Assets;

public class LongGrass extends Entity{

	private BufferedImage img;
	private Boolean isCut = false;
	
	public LongGrass(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		this.handler = handler;
		img = Assets.grassLong;
		isSolid = false;
	}

	@Override
	public void tick() {
		img = Assets.grassLong;
		if(isCut) {
			img = Assets.grassCut;
		} else if(colliding) {
			img = Assets.grassBent;
		}
	}
	

	@Override
	public void render(Graphics g) {
		this.drawBounds(g);
		g.drawImage(img, (int) (x - handler.getGameCamera().getxOffset()), (int) (y -  handler.getGameCamera().getyOffset()), width, height, null);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void hurt(int amt) {
		isCut = true;
	}

}
