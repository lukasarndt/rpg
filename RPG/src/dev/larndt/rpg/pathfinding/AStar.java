package dev.larndt.rpg.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.tiles.Tile;
import dev.larndt.rpg.worlds.World;

public class AStar {
	private Handler handler;
	private World world;
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {

		@Override
		public int compare(Node n1, Node n2) {
			if(n1.getfCost() < n2.getfCost()) return -1;
			if(n1.getfCost() > n2.getfCost()) return 1;
			return 0;
		}
		
	};
	
	public AStar(Handler handler, World world) {
		this.world = world;
		this.handler = handler;
	}
	
	public List<Node> findPath(MyVector start, MyVector destination) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		
		Node current = new Node(handler, start, null, 0, 0);
		openList.add(current);
		
		while(openList.size() > 0) {
			System.out.println(openList.size());
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			
			if(current.getVector().equals(destination)) {
				List<Node> path = new ArrayList<Node>();
				while(current.getParent() != null) {
					path.add(current);
					current = current.getParent();
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			
			openList.remove(current);
			closedList.add(current);
			
			/*
			 *  This checks if the adjacent tiles are valid candidates (i.e. not solid etc). They look like this:
			 *  +---+---+---+
			 *  | 0 | 1 | 2 |
			 *  +---+---+---+
			 *  | 3 |(4)| 5 |	(4) is marked because it is the current tile.
			 *  +---+---+---+
			 *  | 6 | 7 | 8 |
			 *  +---+---+---+
			 *  
			 *  So there are 9 tiles to be checked, where 4 is the current tile.
			 */
			for(int i = 0; i < 9; i++) {
				if(i == 4) { continue; }
				int x = current.getVector().getX();
				int y = current.getVector().getY();
				int xi = (i%3) - 1; // These will be either -1, 0 or 1, depending 
				int yi = (i/3) - 1; // on which direction we want to move.
				Tile tileToCheck1 = world.getTile(x + xi, y + yi,1); 
				Tile tileToCheck2 = world.getTile(x + xi, y + yi,2);
				//if(tileToCheck1 == null) { continue; }
				//if(tileToCheck1.isSolid()) {continue;}
				if(tileToCheck2 == null) { continue;}
				if(tileToCheck2.isSolid()) {continue;}
				MyVector v = new MyVector(x + xi, y + yi);
				double gCost = current.getgCost() + getDistance(current.getVector(), v) == 1 ? 1 : 0.9;
				double hCost = getDistance(v, destination);
				Node node = new Node(handler, v, current, gCost, hCost);
				if(vectorInList(closedList, v)) { continue; }
				if(!vectorInList(openList, v)) { openList.add(node); }
			}
		}
		
		closedList.clear();
		return null;
	}
	
	private boolean vectorInList(List<Node> list, MyVector v) {
		for(Node node : list) {
			if(node.getVector().equals(v)) { return true; }
		}
		return false;
	}
	
	private double getDistance(MyVector v1, MyVector v2) {
		double dx = v1.getX() -  v2.getX();
		double dy = v1.getY() - v2.getY();
		
		return Math.abs(dx*dx + dy*dy);
	}
}
