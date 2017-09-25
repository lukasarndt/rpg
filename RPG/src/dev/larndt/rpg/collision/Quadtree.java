package dev.larndt.rpg.collision;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;

public class Quadtree {
	private static final int MAX_OBJECTS = 10;
	private static final int MAX_LEVELS = 5;
	
	public static boolean debug = true;
	
	private int level;
	private List<Entity> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;
	private Handler handler;

	// Nodes look like this:
	// +---+---+
	// | 1 | 0 |
	// +---+---+
	// | 2 | 3 |
	// +---+---+
	
	/*
	 * Constructor
	 */
	public Quadtree(Handler handler, int level, Rectangle bounds) {
		//System.out.println("New Quadtree with level " + level);
		this.level 		= level;
		objects 		= new ArrayList<Entity>();
		this.bounds 	= bounds;
		nodes 			= new Quadtree[4];
		this.handler 	= handler;
	}
	
	/*
	 * Clears the quadtrees recursively
	 */
	public void clear() {
		objects.clear();
		
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}
	
	/*
	 * Splits node into four subnodes
	 */
	private void split() {
		int subWidth = (int) (bounds.getWidth()/2);
		int subHeight = (int) (bounds.getHeight()/2);
		
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();
		
		nodes[0] = new Quadtree(handler, level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(handler, level+1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(handler, level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(handler, level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
	}
	
	/*
	 * Determine which node the object belongs to. -1 means
	 * object cannot completely fit within a child node and is part
	 * of the parent node
	 */
	private int getIndex(Rectangle rect) {
		int index = -1;
		double verticalMidpoint 	= bounds.getX() + (bounds.getWidth()/2);
		double horizontalMidpoint 	= bounds.getY() + (bounds.getHeight()/2);
		
		// Object can completely fit within the top quadrants
		boolean topQuadrant = ((rect.getY() - handler.getGameCamera().getyOffset()) < horizontalMidpoint 
				&& (rect.getY() - handler.getGameCamera().getyOffset() + rect.getHeight()) < horizontalMidpoint);
		
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = ((rect.getY() - handler.getGameCamera().getyOffset()) > horizontalMidpoint);
		
		// Object can completely fit within the left quadrants
		if((rect.getX() - handler.getGameCamera().getxOffset()) < verticalMidpoint 
				&& (rect.getX() - handler.getGameCamera().getxOffset() + rect.getWidth()) < verticalMidpoint) {
			if(topQuadrant) {
				index = 1;
			} else if(bottomQuadrant) {
				index = 2;
			}
		}
		
		// Objects can completely fit within the right quadrants
		else if((rect.getX() - handler.getGameCamera().getxOffset()) > verticalMidpoint) {
			if(topQuadrant) {
				index = 0;
			} else if(bottomQuadrant) {
				index = 3;
			}
		}
		
		return index;
	}
	
	/*
	 * Insert the object into the quadtree. If the node
	 * exceeds the capacity, it will split and add all
	 * objects to their corresponding nodes.
	 */
	public void insert(Entity entity) {
		Rectangle rect = entity.getCollisionBounds(0, 0);
		//System.out.println(entity.getClass().getSimpleName() + " index is " + getIndex(rect));
		if(nodes[0] != null) {
			int index = getIndex(rect);
			//System.out.println("Index of " + entity.getClass().getSimpleName() + " is " + index);
			
			if(index != -1) {
				nodes[index].insert(entity);
				
				return;
			}
		}
		
		objects.add(entity);
		
		if(objects.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if(nodes[0] == null) {
				split();
			}
			
			int i = 0;
			while (i < objects.size()) {
				int index = getIndex(objects.get(i).getCollisionBounds(0, 0));
				if(index != -1) {
					nodes[index].insert(objects.remove(i));
				} else {
					i++;
				}
			}
		}
	}
	
	/*
	 * Return all entities that could collide with the given entity
	 */
	public List<Entity> retrieve(List<Entity> returnObjects, Entity entity) {
		int index = getIndex(entity.getCollisionBounds(0, 0));
		if(nodes[0] != null) {
			if(index != -1) {
				nodes[index].retrieve(returnObjects, entity);
			} else {
				for(int i = 0; i < nodes.length; i++) {
					nodes[i].retrieve(returnObjects, entity);
				}
			}
		}
		
		returnObjects.addAll(objects);
		
		return returnObjects;
	}
	
	/**
	 * Renders nodes and prints their content to stdout. Used for debugging.
	 * @param g
	 */
	public void render(Graphics g) {
		// vertical
		g.drawLine((int)(bounds.getX() + bounds.getWidth()/2),
				(int) bounds.getY(), 
				(int) (bounds.getX() + bounds.getWidth()/2), 
				(int)(bounds.getY() + bounds.getHeight()));
		// horizontal
		g.drawLine((int)bounds.getX(), 
				(int)(bounds.getY() + bounds.getHeight()/2), 
				(int)(bounds.getX() + bounds.getWidth()), 
				(int) (bounds.getY() + bounds.getHeight()/2));
		
		/*if(level == 0) {
			System.out.println("------------------------------------");
			System.out.println("Node with index -1" 
					+ " on level " 
					+ level 
					+ " has " 
					+ objects.size() 
					+ " entities.");
			
			for(Entity e : objects) {
				System.out.println("* " + e.getClass().getSimpleName());
			}
		}*/
		
		for(int i = 0; i < nodes.length; i++) {
			if(nodes[i] != null) {
				/*System.out.println("Node with index " 
						+ i 
						+ " on level " 
						+ nodes[i].level + " has " 
						+ nodes[i].objects.size() 
						+ " entities.");
				
				for(Entity e : nodes[i].objects) {
					System.out.println("* " + e.getClass().getSimpleName());
				}*/
				
				if(nodes[i].nodes[0] != null) {
					nodes[i].render(g);
				}
			}
		}
	}
}
