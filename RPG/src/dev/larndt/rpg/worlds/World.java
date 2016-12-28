package dev.larndt.rpg.worlds;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.EntityManager;
import dev.larndt.rpg.entities.creatures.Player;
import dev.larndt.rpg.entities.statics.Tree;
import dev.larndt.rpg.items.ItemManager;
import dev.larndt.rpg.tiles.Tile;
import dev.larndt.rpg.utilities.Utilities;

public class World {
	private Handler handler;
	private int width, height; // In terms of tiles!
	private int spawnX, spawnY; 
	private int[][] tiles; // Holds the IDs of the tile at every position in the world.
	private EntityManager entityManager;
	private ItemManager itemManager;
	
	private float playerHealthBarThickness = 2f, playerHealthFraction = 1f, playerHealthBarWidth = 300f, playerHealthBarHeight = 25f;
	private Color playerHealthBarColor = Color.BLACK;
	private Stroke oldStroke;
	private Color oldColor;

	public World(Handler handler, String path) {
		this.handler = handler;
		
		itemManager = new ItemManager(handler);
		
		entityManager = new EntityManager(handler, new Player(handler, 50, 50));
		
		entityManager.addEntitiy(new Tree(handler, 8*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT));
		
		loadWorld(path);
		
		entityManager.getPlayer().setX(spawnX);
		entityManager.getPlayer().setY(spawnY);
	}
	
	public void tick() {
		entityManager.tick();
		itemManager.tick();
	}
	
	public void render(Graphics g) {
		int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
		int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset()+ handler.getWidth()) / Tile.TILE_WIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
		int yEnd = (int) Math.min(width, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);
		
		// Renders the Tile at position (x,y) in the world.
		for(int y = yStart; y < yEnd; y++) {
			for(int x = xStart; x < xEnd; x++) {
				getTile(x,y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()) , 
						(int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
			}
		}
		
		
		//Render the Entites & Items
		itemManager.render(g);
		entityManager.render(g);
		
		
		//Render health bar
		playerHealthFraction = (float)handler.getPlayer().getHealth()/(float)handler.getPlayer().getMaxHealth();
		Graphics2D g2 = (Graphics2D) g;
		
		oldStroke = g2.getStroke();
		oldColor = g2.getColor();
		
		g2.setStroke(new BasicStroke(playerHealthBarThickness));
		g2.setColor(Color.RED);
		g2.fillRect((int) (Game.WIDTH/2 - playerHealthBarWidth/2), (int) Game.HEIGHT - 50, (int) (playerHealthBarWidth*playerHealthFraction), (int)playerHealthBarHeight);
		g2.setColor(playerHealthBarColor);
		g2.drawRect((int) (Game.WIDTH/2 - playerHealthBarWidth/2), Game.HEIGHT - 50, (int) playerHealthBarWidth, (int) playerHealthBarHeight);	
		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
	}
	
	/**
	 * 
	 * Returns the tile at position x,y in the world.
	 * @param x
	 * @param y
	 * @return
	 */
	public Tile getTile(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.grassTile;
		}
		
		Tile t = Tile.tiles[tiles[x][y]];
		
		if(t == null) {
			return Tile.dirtTile;
		} else {
			return t;
		}
	}
	
	
	/**
	 * Loads the world from a text file.
	 * @param path
	 */
	private void loadWorld(String path) {
		String file = Utilities.loadFileAsString(path);
		String[] tokens = file.split("\\s+"); // Splits up the file whenever there is whitespace.
		
		width = Utilities.parseInt(tokens[0]);
		height = Utilities.parseInt(tokens[1]);
		spawnX = Utilities.parseInt(tokens[2]);
		spawnY = Utilities.parseInt(tokens[3]);
		
		tiles = new int[width][height];
		
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				tiles[x][y] = Utilities.parseInt(tokens[(x + y * width) + 4]);
			}
		}
	}
	
	// GETTERS & SETTERS
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

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	public ItemManager getItemManager() {
		return itemManager;
	}

}
