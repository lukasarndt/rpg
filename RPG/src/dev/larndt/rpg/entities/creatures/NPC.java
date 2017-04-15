package dev.larndt.rpg.entities.creatures;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.display.Textbox;

public class NPC extends Creature{
	
	private String text;
	
	public NPC(Handler handler, float x, float y, BufferedImage image) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.image= image;
		this.attackStrength = 0;
		this.text = "Hallo, ich bin ein NPC.";
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().actionKey 
				&& this.getCollisionBounds(0, 4).intersects(handler.getPlayer().getCollisionBounds(0, 0))) {
			interact();
		}
	}
	
	public void interact() {
		Textbox textbox = handler.getWorld().getTextbox();
		if(!textbox.isActive()) {
				textbox.setText(text);
				textbox.setActive(true);
			}
	}

	@Override
	public void render(Graphics g) {
		super.render(g);
		super.drawBounds(g);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}

}
