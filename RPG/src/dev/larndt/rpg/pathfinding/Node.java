package dev.larndt.rpg.pathfinding;

public class Node {

	private MyVector vector; // This is a vector pointing to a tile.
	private Node parent;
	private double gCost, hCost, fCost;
	
	public Node (MyVector v, Node parent, double gCost, double hCost) {
		this.vector = v;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		
		this.fCost = this.gCost + this.hCost;
	}

	public MyVector getVector() {
		return vector;
	}

	public void setVector(MyVector v) {
		this.vector = v;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public double getgCost() {
		return gCost;
	}

	public void setgCost(double gCost) {
		this.gCost = gCost;
	}

	public double gethCost() {
		return hCost;
	}

	public void sethCost(double hCost) {
		this.hCost = hCost;
	}

	public double getfCost() {
		return fCost;
	}

	public void setfCost(double fCost) {
		this.fCost = fCost;
	}
	
}
