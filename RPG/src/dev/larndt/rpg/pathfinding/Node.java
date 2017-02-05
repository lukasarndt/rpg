package dev.larndt.rpg.pathfinding;

import java.awt.Font;
import java.awt.Graphics;

import dev.larndt.rpg.Handler;

public class Node {

	private MyVector vector; // This is a vector pointing to a tile.
	private Node parent;
	private double gCost, hCost, fCost;
	
	private Handler handler;
	
	public static long count;
	
	public Node (Handler handler, MyVector v, Node parent, double gCost, double hCost) {
		this.handler = handler;
		this.vector = v;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		
		this.fCost = this.gCost + this.hCost;
		
		count++;
		//System.out.println("New Node created: " + count);
	}
	
	public void render(Graphics g) {
		Font test = new Font("Arial",Font.BOLD,20);
		g.setFont(test);
		g.drawString(""+vector.getX(),(int) (vector.getX() * 64 + 5- handler.getGameCamera().getxOffset()), (int) (vector.getY() * 64 + 20 - handler.getGameCamera().getyOffset()));
		g.drawString(""+vector.getY(),(int) (vector.getX() * 64 + 35 - handler.getGameCamera().getxOffset()), (int) (vector.getY() * 64 + 20 - handler.getGameCamera().getyOffset()));
		//g.drawString(""+gCost,(int) (vector.getX() * 64 - handler.getGameCamera().getxOffset()), (int) (vector.getY() * 64 - handler.getGameCamera().getyOffset()));
	}
	
	// GETTERS & SETTERS
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
