package dev.larndt.rpg.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {
	public static Tile[] tiles 		= new Tile[256];
	public static Tile grassTile 	= new GrassTile(0);
	public static Tile dirtTile 	= new DirtTile(1);
	public static Tile stoneTile 	= new StoneTile(2);
	
	public static final int TILE_WIDTH = 64,
							TILE_HEIGHT = 64;
	
	protected BufferedImage texture;
	protected final int id;
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		tiles[id] = this;
	}
	
	public void tick() {
		
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
		
		/*Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(oldColor);*/
	}
	
	// GETTERS & SETTERS
	public int getId() {
		return id;
	}
}
