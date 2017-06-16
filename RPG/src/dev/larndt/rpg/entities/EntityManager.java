package dev.larndt.rpg.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;
import dev.larndt.rpg.collision.Quadtree;
import dev.larndt.rpg.entities.creatures.Player;

public class EntityManager {
	private Handler handler;
	private Player player;
	private ArrayList<Entity> entities;
	private Quadtree quadtree;
	private Comparator<Entity> renderSorter = new Comparator<Entity>() {

		@Override
		public int compare(Entity a, Entity b) {
			if(a.getY() + a.getHeight() < b.getY() + b.getHeight()) {
				return -1;
			}else{
				return 1;
			}
		}	
	};
	
	public EntityManager(Handler handler, Player player) {
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		entities.add(player);
		quadtree = new Quadtree(0, new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT));
	}
	
	
	public void tick() {
		Iterator<Entity> it = entities.iterator();
		while(it.hasNext()){
			Entity e = it.next();
			//System.out.println("Distance of " + e.getClass().getSimpleName() + " from Player is: " + e.distanceFromPlayer());
			if(e.distanceFromPlayer() < e.getDistanceToTick()) {
				e.tick();
			}
			if(!e.isActive()) {
				it.remove();
			}
		}
		entities.sort(renderSorter);
		
		quadtree.clear();
		for(int i = 0; i < entities.size(); i++) {
			quadtree.insert(entities.get(i));
		}
	}
	
	public void render(Graphics g) {
		for(Entity e:entities){
			e.render(g);
		}
	}
	
	public void addEntitiy(Entity e) {
		entities.add(e);
	}

	// ====================================== GETTERS & SETTERS =========================================
	
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Quadtree getQuadtree() {
		return quadtree;
	}
	// --------------------------------------------------------------------------------------------------
}
