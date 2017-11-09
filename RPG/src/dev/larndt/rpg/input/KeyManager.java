package dev.larndt.rpg.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dev.larndt.rpg.collision.Quadtree;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.utilities.Timer;

public class KeyManager implements KeyListener{
	private boolean[] keys;
	public boolean up, down, left, right, attackKey, inventoryKey, actionKey, enter;
	
	private Timer timer;
	
	public KeyManager() {
		keys = new boolean[256];
		timer = new Timer(500);
	}
	
	public void tick() {
		
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
		if(timer.check() && 
				(e.getKeyCode()==KeyEvent.VK_SPACE 
					|| e.getKeyCode()==KeyEvent.VK_E 
					|| e.getKeyCode()==KeyEvent.VK_ENTER)) {
			keys[e.getKeyCode()] = true;
		} else {
			keys[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if(e.getKeyCode() == KeyEvent.VK_1) {
			Entity.debug = !Entity.debug;
		} else if(e.getKeyCode() == KeyEvent.VK_2) {
			Quadtree.debug = !Quadtree.debug;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
