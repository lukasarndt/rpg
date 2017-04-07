package dev.larndt.rpg.inventory;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.items.Item;

public class Inventory {
	public static final int WIDTH = 32, HEIGHT = 32, START_X = 0, START_Y = 0;
	private static final int DELAY = 80;
	
	private Handler handler;
	
	private int 	sizeX = 3, sizeY = 3; 	// Size of the inventory.
	private 		ItemSlot[][] itemSlots;
	private int 	activeItemSlot;
	
	private boolean active = false;

	private ArrayList<Item> items;
	
	private long 	lastTick, now;
	private int 	delta;
	
	private boolean itemGrabbed = false, lastKeyState, currentKeyState;
	private Item itemToGrab = null;

	public Inventory(Handler handler) {
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
		getInput();
	}
	
	public void render(Graphics g){
		
		// Draw the slots and Items.
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				if(activeItemSlot == (j * sizeX + i )) {
					g.drawImage(Assets.invSlotActive, i*WIDTH, j*HEIGHT, WIDTH, HEIGHT, null);
				} else {
					g.drawImage(Assets.invSlot, i*WIDTH, j*HEIGHT, WIDTH, HEIGHT, null);
				}
				
				if(itemSlots[i][j].getItem() != null) {
					Item item = itemSlots[i][j].getItem();
					item.render(g, i*WIDTH, j*HEIGHT, WIDTH, HEIGHT);
				}
			}
		}
	}
	
	
	public void getInput() {
		now = System.currentTimeMillis();
		delta += now - lastTick;
		lastTick = now;
		
		// Input
		
		// Move around in the inventory.
		if(delta > DELAY) {
			delta = 0;
			if(handler.getKeyManager().right) {
				if(activeItemSlot < sizeX*sizeY - 1 && (activeItemSlot%sizeX) != sizeX-1) {
					activeItemSlot++;
				}		
			}else if(handler.getKeyManager().left) {
				if(activeItemSlot > 0 && (activeItemSlot%sizeX) != 0 ) {
					activeItemSlot--;
				}	
			}else if(handler.getKeyManager().down) {
				if(activeItemSlot < sizeX*sizeY - sizeX) {
					activeItemSlot += sizeX;
				}
			}else if(handler.getKeyManager().up) {
				if(activeItemSlot > sizeX - 1) {
					activeItemSlot -= sizeX;
				}
			}
		}
		
		// Grab items.
		lastKeyState = currentKeyState;
		currentKeyState = handler.getKeyManager().attackKey;
		ItemSlot itemSlot = itemSlots[activeItemSlot%sizeX][(int)activeItemSlot/sizeX];
		if(currentKeyState && !lastKeyState && itemGrabbed) {
			if (!itemSlot.isOccupied() && itemToGrab != null) {
				itemSlot.setItem(itemToGrab);
				//itemSlot.setOccupied(true);
				itemGrabbed = false;
				itemToGrab = null;
				System.out.println("Item dropped.");
			}
		} else if(currentKeyState && !lastKeyState && !itemGrabbed && itemSlot.getItem() != null) {
			itemToGrab = itemSlot.getItem();
			itemSlot.setItem(null);
			//itemSlot.setOccupied(false);
			itemGrabbed = true;
		}
		
		// Drop items
		if(handler.getKeyManager().actionKey) {
			if(itemSlot.getItem() != null) {
				itemSlot.getItem().setX((int)handler.getPlayer().getX());
				itemSlot.getItem().setY((int)handler.getPlayer().getY()+ HEIGHT);
				handler.getWorld().getItemManager().addItem(itemSlot.getItem());
			}
			itemSlot.setItem(null);
		}
		
		// Use items
		if(handler.getKeyManager().enter) {
			if(itemSlot.getItem() != null) {
				itemSlot.getItem().use();
				itemSlot.setItem(null);
			}
		}
	}
	
	public void addItem(Item item) {
		Rectangle itemRect = new Rectangle(0, 0, (int) (item.getSizeX() * WIDTH) - 1, (int) (item.getSizeY() * HEIGHT) - 1);
		Item it;
		Rectangle r = new Rectangle();
		for(int j = 0; j < sizeY; j++) {
			for(int i = 0; i < sizeX; i++) {
				if(itemSlots[i][j].isOccupied()) {
					continue;
				}
				it = itemSlots[i][j].getItem();
				if(it == null) {
					r = new Rectangle(0,0,0,0);
				} else {
					r = new Rectangle(i*WIDTH, j*HEIGHT, (int) it.getSizeX() * WIDTH, (int) it.getSizeY() * WIDTH);
				}
				
				if(itemRect.intersects(r)) {
					if(i < sizeX - 1) {
						itemRect.setLocation((i+1) * WIDTH, j);
					}else if(j < sizeY -1){
						itemRect.setLocation(0, (j+1) * WIDTH);
					}else{
						System.out.println("No room in inventory");
						return;
					}
				}else{
					if(i+item.getSizeX() <= sizeX && j+item.getSizeX() <= sizeY) {
						itemSlots[i][j].setItem(item);
						for(int k = 0; k < item.getSizeX(); k++) {
							for(int l = 0; l < item.getSizeY(); l++) {
								itemSlots[i+k][j+l].setOccupied(true);
							}
						}
						return;
					}
				}
			}
		}
		items.add(item);
	}
	
	public boolean addItem2(Item item) {
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				if(!itemSlots[j][i].isOccupied()) {
					itemSlots[j][i].setItem(item);
					return true;
				}
			}
		}
		
		System.out.println("The inventory is full!");
		return false;
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
}
