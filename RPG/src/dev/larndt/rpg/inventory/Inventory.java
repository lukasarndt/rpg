package dev.larndt.rpg.inventory;

import java.awt.Graphics;
import java.util.ArrayList;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.items.Item;

public class Inventory {
	public static final int WIDTH = 32, HEIGHT = 32;
	private int sizeX, sizeY;
	private ItemSlot[][] itemSlots;
	private int activeItemSlot;
	private Handler handler;
	private ArrayList<Item> items;
	
	private long lastTick, now;
	private int delta, delay = 50;
	
	public Inventory(Handler handler, int sizeX, int sizeY) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.handler = handler;
		
		itemSlots = new ItemSlot[sizeX][sizeY];
		items = new ArrayList<Item>();
		
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				itemSlots[i][j] = new ItemSlot();
			}
		}
		
		activeItemSlot = 0;
	}
	
	public void tick(){
		now = System.currentTimeMillis();
		delta += now - lastTick;
		lastTick = now;
		
		if(delta > delay) {
			delta = 0;
			if(handler.getKeyManager().right) {
				if(activeItemSlot < sizeX*sizeY - 1) {
					activeItemSlot++;
				}		
			}else if(handler.getKeyManager().left) {
				if(activeItemSlot > 0) {
					activeItemSlot--;
				}	
			}
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				if(activeItemSlot == (j * sizeX + i )) {
					g.drawImage(Assets.invSlotActive, i*WIDTH, j*HEIGHT, WIDTH, HEIGHT, null);
				} else {
					g.drawImage(Assets.invSlot, i*WIDTH, j*HEIGHT, WIDTH, HEIGHT, null);
				}
				
				if(itemSlots[i][j].getItem() != null) {
					itemSlots[i][j].getItem().render(g, i*WIDTH, j*HEIGHT);
				}
			}
		}
	}

	// GETTERS & SETTERS
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

	public ArrayList<Item> getItems() {
		return items;
	}
	
}
