package dev.larndt.rpg;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.display.Display;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.gfx.GameCamera;
import dev.larndt.rpg.gfx.MyGraphics;
import dev.larndt.rpg.gfx.MyImage;
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
	
	//LIGHTING STUFF
	private BufferedImage image;
	private MyGraphics myGraphics;
	
	public Game(String title, int width, int height) {
		//this.WIDTH = width;
		//this.height = height;
		this.title 		= title;
		
		keyManager 		= new KeyManager();
		mouseManager 	= new MouseManager();
		
		
	}
	
	private void init() {
		display = new Display(title, WIDTH, HEIGHT); 
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		Assets.init();
		
		handler 	= new Handler(this);
		gameCamera 	= new GameCamera(handler, 0, 0);
		gameState 	= new GameState(handler);
		menuState 	= new MenuState(handler);
		
		StateManager.setState(gameState);
		
		image			= new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		myGraphics		= new MyGraphics(handler);
	}

	public void run() {
		init();
		
		int fps = 60000;
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
		
		lightingRender();
		
		bs.show();
		g.dispose();
	}
	
	public void lightingRender() {
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		myGraphics.clear();
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				//myGraphics.setLightMap(x, y, image.get);
			}
		}
		
		MyImage player2 = new MyImage(Assets.slime);
		MyImage player1 = new MyImage(Assets.player1, true);
		
		myGraphics.drawImage(player2, 10, 10);
		myGraphics.drawImage(player1, mouseManager.getMouseX(), mouseManager.getMouseY());
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

	public BufferedImage getImage() {
		return image;
	}
}
