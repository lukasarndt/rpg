package dev.larndt.rpg.collision;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import dev.larndt.rpg.entities.Entity;

public class Quadtree {
	private static final int MAX_OBJECTS = 10;
	private static final int MAX_LEVELS = 5;
	
	private int level;
	private List<Entity> objects;
	private Rectangle bounds;
	private Quadtree[] nodes;
	
	// Nodes look like this:
	// +---+---+
	// | 1 | 0 |
	// +---+---+
	// | 2 | 3 |
	// +---+---+
	
	/*
	 * Constructor
	 */
	public Quadtree(int level, Rectangle bounds) {
		this.level = level;
		objects = new ArrayList<Entity>();
		this.bounds = bounds;
		nodes = new Quadtree[4];
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
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();
		
		nodes[0] = new Quadtree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
		nodes[1] = new Quadtree(level+1, new Rectangle(x, y, subWidth, subHeight));
		nodes[2] = new Quadtree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
		nodes[3] = new Quadtree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
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
		boolean topQuadrant = (rect.getY() < horizontalMidpoint && (rect.getY() + rect.getHeight()) < horizontalMidpoint);
		
		// Object can completely fit within the bottom quadrants
		boolean bottomQuadrant = (rect.getX() > verticalMidpoint);
		
		// Object can completely fit within the left quadrants
		if(rect.getX() < verticalMidpoint && rect.getX() + rect.getWidth() < verticalMidpoint) {
			if(topQuadrant) {
				index = 1;
			} else if(bottomQuadrant) {
				index = 2;
			}
		}
		
		// Objects can completely fit within the right quadrants
		else if(rect.getX() > verticalMidpoint) {
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
		if(nodes[0] != null) {
			int index = getIndex(rect);
			
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
	 * Return all objects that could collide with the given object
	 */
	public List<Entity> retrieve(List<Entity> returnObjects, Entity entity) {
		int index = getIndex(entity.getCollisionBounds(0, 0));	
		if(index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, entity);
		}
		
		returnObjects.addAll(objects);
		
		return returnObjects;		
	}
}
