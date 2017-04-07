package dev.larndt.rpg.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener{
	private boolean[] keys;
	public boolean up, down, left, right, attackKey, inventoryKey, actionKey, enter;
	
	private long lastTime, now, counter = 0;
	
	public KeyManager() {
		keys = new boolean[256];
		now = System.currentTimeMillis();
	}
	
	public void tick() {
		lastTime = now;
		now = System.currentTimeMillis();
		counter += now - lastTime;
		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		
		attackKey = keys[KeyEvent.VK_SPACE];
		inventoryKey = keys[KeyEvent.VK_I];
		actionKey = keys[KeyEvent.VK_E];
		enter = keys[KeyEvent.VK_ENTER];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(counter > 100 && e.getKeyCode()==KeyEvent.VK_SPACE) {
			keys[e.getKeyCode()] = true;
			counter = 0;
		} else if ( e.getKeyCode()!=KeyEvent.VK_SPACE) {
			keys[e.getKeyCode()] = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
