package dev.larndt.rpg;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import dev.larndt.rpg.display.Display;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.gfx.GameCamera;
import dev.larndt.rpg.input.KeyManager;
import dev.larndt.rpg.input.MouseManager;
import dev.larndt.rpg.states.GameState;
import dev.larndt.rpg.states.MenuState;
//import dev.larndt.rpg.states.MenuState;
import dev.larndt.rpg.states.State;
import dev.larndt.rpg.states.StateManager;

public class Game implements Runnable{	
	public static final int WIDTH = 800, HEIGHT = 600;
	
	private Display display;
	private String title;
	
	private boolean running = false;
	private Thread thread;
	
	private BufferStrategy bs;
	private Graphics g;

	private State gameState, menuState;
	
	private KeyManager keyManager;
	private MouseManager mouseManager;	
	
	private GameCamera gameCamera;
	
	private Handler handler;
	
	public Game(String title, int width, int height) {
		//this.WIDTH = width;
		//this.height = height;
		this.title = title;
		
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
	}
	
	private void init() {
		display = new Display(title, WIDTH, HEIGHT); 
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		Assets.init();
		handler = new Handler(this);
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
				ticks++;
				delta -= timePerTick;
			}
			if(timer >= 1000000000) {
				//System.out.println("ticks: " + ticks);
				display.getFrame().setTitle(title + " | fps: " + ticks);
				
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
		// Get Graphics object
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		
		g = bs.getDrawGraphics();

		// Clear Screen
		g.clearRect(0, 0, WIDTH, HEIGHT);
		// Start Drawing
		if(StateManager.getState() != null) {
			StateManager.getState().render(g);
		}
		// End Drawing
		
		bs.show();
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
	
	//GETTERS & SETTER
	
	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public int getWidth() {
		return WIDTH;
	}

	/*public void setWidth(int width) {
		this.WIDTH = width;
	}*/

	public int getHeight() {
		return HEIGHT;
	}

	/*public void setHeight(int height) {
		this.height = height;
	}*/

	public State getGameState() {
		return gameState;
	}

	public State getMenuState() {
		return menuState;
	}
	
	
	
}
