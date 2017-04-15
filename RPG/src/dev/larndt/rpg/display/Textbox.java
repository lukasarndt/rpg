package dev.larndt.rpg.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;

public class Textbox {
	private Handler handler;
	
	private boolean active;
	
	private int timer, cooldown = 60; //in ticks

	private String text;
	
	public Textbox(Handler handler) {
		this.handler = handler;
	}
	
	public void tick() {
		timer++;
	}
	
	public void render(Graphics2D g) {
		if(active) {
			g.setColor(Color.YELLOW);
			g.fillRect(0, Game.HEIGHT-200, Game.WIDTH, 200);
			g.setColor(Color.BLACK);
			showText(g);
			
			if(handler.getKeyManager().actionKey) {
				setActive(false);
				handler.getPlayer().setCanMove(true);
			}
		}
	}
	
	public void showText(Graphics g) {
		handler.getPlayer().setCanMove(false);
		if(text != null) {
			g.drawString(text , 20, Game.HEIGHT-180);
		}
	}
	
	//GETTERS&SETTER
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		if(timer > cooldown) {
			this.active = active;
			timer = 0;
		}
			
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
