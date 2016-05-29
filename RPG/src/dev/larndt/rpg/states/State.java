package dev.larndt.rpg.states;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;

public abstract class State {
	protected Handler handler;
	
	public State(Handler handler) {
		this.handler = handler;
	}
	public abstract void tick();
	
	public abstract void render(Graphics g);
}
