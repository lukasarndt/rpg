package dev.larndt.rpg.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;

public class Textbox {
	private Handler handler;
	
	private boolean active;
	
	private long timer, lastTime;
	private int cooldown = 2000;

	private String text;
	
	public Textbox(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics2D g) {
		if(active) {
			/*if(!initialised) {
				height -= 200;
				initialised = true;
			}*/
			g.setColor(Color.YELLOW);
			g.fillRect(0, Game.HEIGHT-200, Game.WIDTH, 200);
			g.setColor(Color.BLACK);
			showText(g);
			
		}
	}
	
	public void showText(Graphics g) {
		handler.getPlayer().setCanMove(false);
		if(text != null) {
			g.drawString(text , 20, Game.HEIGHT-180);
		}
		
		if(timer > cooldown) {
			active = false;
			timer = 0;
			handler.getPlayer().setCanMove(true);
		}
		
		timer += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
	}
	
	//GETTERS&SETTER
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
