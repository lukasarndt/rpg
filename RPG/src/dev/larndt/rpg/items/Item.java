package dev.larndt.rpg.items;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;

public abstract class Item {
	
	// Class
	public static final int ITEM_WIDTH = 64, ITEM_HEIGHT = 64, PICKEDUP = -1;
	
	protected Handler handler;
	protected BufferedImage texture;
	
	protected int x, y, sizeX, sizeY;
	protected int Energy;
	
	public Item(BufferedImage texture, int sizeX, int sizeY) {
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	public Item(BufferedImage texture, int x, int y, int sizeX, int sizeY) {
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g) {
		if(handler == null) {
			return;
		}
		render(g, (int)(x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), ITEM_WIDTH, ITEM_HEIGHT);
	}
	
	public void render(Graphics g, int x, int y, int width, int height) {
		g.drawImage(texture, x, y, width, height, null);
	}
	
	public void setPosition(int x, int y) {
		this.x = x;
		this.y = x;
	}
	
	public void use() {
		
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

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getEnergy() {
		return Energy;
	}

	public void setEnergy(int energy) {
		Energy = energy;
	}
	
	
}
