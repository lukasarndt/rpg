package dev.larndt.rpg.items;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;

import dev.larndt.rpg.Handler;

public class ItemManager { 	// Handles items in the world, not the inventory!
							// Items in the inventory are handled by "Inventory".
	private Handler handler;
	private ArrayList<Item> items;	
	
	public ItemManager(Handler handler) {
		this.handler = handler;
		items = new ArrayList<Item>();
	}
	
	public void tick() {
		Iterator<Item> it = items.iterator();
		
		while(it.hasNext()) {
			Item i = it.next();
			i.tick();

			if(handler.getKeyManager().e) {
				Rectangle r = new Rectangle(i.getX(), i.getY(), Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
				if(r.intersects(handler.getPlayer().getCollisionBounds(0, 0)) && !handler.getPlayer().getInventory().isActive() && handler.getPlayer().getInventory().addItem2(i)) {
					it.remove();
				}
			}
		}
	}
	
	public void render(Graphics g) {
		for(Item i : items) {
			i.render(g);
			//Rectangle r = new Rectangle(i.getX(), i.getY(), Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
			g.drawRect((int) (i.getX() - handler.getGameCamera().getxOffset()),(int) (i.getY() - handler.getGameCamera().getyOffset()), Item.ITEM_WIDTH, Item.ITEM_HEIGHT);
		}
	}
	
	public void addItem(Item i) {
		i.setHandler(handler);
		items.add(i);
	}
	
	
	
	// GETTERS & SETTERS
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void setItems(ArrayList<Item> items) {
		this.items = items;
	}
	
	
}
