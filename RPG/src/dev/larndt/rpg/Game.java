package dev.larndt.rpg;

import java.awt.Graphics;
import java.awt.Image;

import dev.larndt.rpg.display.Display;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.gfx.GameCamera;
import dev.larndt.rpg.input.KeyManager;
import dev.larndt.rpg.input.MouseManager;
import dev.larndt.rpg.states.GameState;
import dev.larndt.rpg.states.MenuState;
import dev.larndt.rpg.states.State;
import dev.larndt.rpg.states.StateManager;

public class Game implements Runnable{
	private Display display;
	private int width, height;
	private String title;
	
	private boolean running = false;
	private Thread thread;
	
	private Graphics g;
	private Image screen;

	private State gameState;
	private State menuState;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;	
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	private void init() {
		handler = new Handler(this);
		
		display = new Display(title, width, height); 
		
		Assets.init();
		
		keyManager = new KeyManager(handler);
		mouseManager = new MouseManager();
		
		gameCamera = new GameCamera(handler, 0, 0);
		
		gameState = new GameState(handler);
		menuState = new MenuState(handler);
		StateManager.setState(menuState);
	}

	public void run() {
		init();
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;	
		
		while(running) {
			now = System.nanoTime();
			delta += now - lastTime;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= timePerTick) {
				tick();
				render();
				display.repaint();
				
				ticks++;
				delta -= timePerTick;
			}
			if(timer >= 1000000000) {
				System.out.println("fps: " + ticks);
				ticks = 0;
				timer = 0;
			}	
		}
	}
	
	private void tick() {
		keyManager.tick();
		
		if(StateManager.getState() != null) {
			StateManager.getState().tick();
		}
	}
	
	private void render() {
		if(screen == null) {
			screen = display.createImage(width, height);
			if(screen == null) {
				System.out.println("Screen is null");
				return;
			}else {
				g = screen.getGraphics();
			}
		}
		
		// Clear Screen
		g.clearRect(0, 0, width, height);
		
		// Start Drawing
		if(StateManager.getState() != null) {
			StateManager.getState().render(g);
		}
		// End Drawing
		
		display.setScreen(screen);
		g.dispose();
	}
	
	public synchronized void start() {
		if(running) {
			return;
		}
		
		running = true;
		
		thread = new Thread(this);
		thread.start(); // Calls the run method.
	}
	
	public synchronized void stop() {
		if(!running) {
			return;
		}
		
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS & SETTERS
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public Display getDisplay() {
		return display;
	}

	public void setDisplay(Display display) {
		this.display = display;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public State getGameState() {
		return gameState;
	}

	public State getMenuState() {
		return menuState;
	}
}
