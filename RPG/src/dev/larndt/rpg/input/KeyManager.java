package dev.larndt.rpg.input;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.display.Display;

public class KeyManager implements KeyListener{
	private boolean[] keys;
	public boolean up, down, left, right, space, i, e;
	
	private Handler handler;
	private Display display;
	
	public KeyManager(Handler handler) {
		keys = new boolean[256];
		this.handler = handler;
		this.display = handler.getGame().getDisplay();
	}
	
	public void tick() {
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		
		space = keys[KeyEvent.VK_SPACE];
		i = keys[KeyEvent.VK_I];
		e = keys[KeyEvent.VK_E];
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	private class KeyPressed extends AbstractAction{

		@Override
		public void actionPerformed(ActionEvent e) {
			//keys[e.get]
		}
		
	}
}
