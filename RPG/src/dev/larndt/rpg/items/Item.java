package dev.larndt.rpg.items;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;

public class Item {
	// Handler
	public static Item[] items = new Item[256];
	public static Item woodItem = new Item(Assets.wood, "Wood", 0 , new Dimension(2,1));
	
	// Class
	public static final int ITEM_WIDTH = 64, ITEM_HEIGHT = 64, PICKEDUP = -1;
	
	protected Handler handler;
	protected BufferedImage texture;
	protected String name;
	protected final int id;
	
	protected int x, y, count;
	protected Dimension size;
	
	public Item(BufferedImage texture, String name, int id, Dimension size) {
		this.texture = texture;
		this.name = name;
		this.id = id;
		count = 1;
		this.size = size;
		
		items[id] = this;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(handler == null) {
			return;
		}
		render(g, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, ITEM_HEIGHT, ITEM_WIDTH, null);
	}
	
	public Item createNew(int x, int y) {
		Item i = new Item(texture, name, id, size);
		i.setPosition(x, y);
		return i;
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = x;
	}

	// GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getId() {
		return id;
	}
	
}
