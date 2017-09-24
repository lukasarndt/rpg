package dev.larndt.rpg.entities.creatures;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.display.Textbox;

public class NPC extends Creature{
	
	private String text, bubbleText;
	private boolean drawBubble = false;
	
	public NPC(Handler handler, float x, float y, BufferedImage image) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
		this.image= image;
		this.attackStrength = 0;
		this.text = "Hallo, ich bin ein NPC.";
		this.bubbleText = "";
	}

	@Override
	public void tick() {
		if(handler.getKeyManager().actionKey 
				&& this.getCollisionBounds(0, 4).intersects(handler.getPlayer().getCollisionBounds(0, 0))) {
			interact();
		}
		
		if(handler.getPlayer().drawAttack() && this.distanceFromPlayer() < 400) {
			drawBubble = true;
			bubbleText = "Weg mit der Waffe!";
		} else {
			drawBubble = false;
			bubbleText = "";
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
		this.drawBounds(g);
		if(drawBubble) {
			drawBubble(g, bubbleText);
		}
	}
	
	public void drawBubble(Graphics g, String bubbleText) {	
		g.setColor(Color.WHITE);
		g.fillRect((int) (x - 50 - handler.getGameCamera().getxOffset()), (int) (y - 100 - handler.getGameCamera().getyOffset()), Creature.DEFAULT_CREATURE_HEIGHT + 100, 70);
		g.setColor(Color.BLACK);
		g.drawString(bubbleText, (int) (x- 30 - handler.getGameCamera().getxOffset()), (int) (y - 80 - handler.getGameCamera().getyOffset()));
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}


	// ======================= GETTERS & SETTERS ==============================================
	public boolean isDrawBubble() {
		return drawBubble;
	}

	public void setDrawBubble(boolean drawBubble) {
		this.drawBubble = drawBubble;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setBubbleText(String bubbleText) {
		this.bubbleText = bubbleText;
	}
}
