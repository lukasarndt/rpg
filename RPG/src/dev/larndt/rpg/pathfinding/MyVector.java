package dev.larndt.rpg.pathfinding;

public class MyVector {
	
	private int x, y;
	
	public MyVector() {
		x = 0;
		y = 0;
	}
	
	public MyVector(MyVector vector) {
		x = vector.getX();
		y = vector.getY();
	}
	
	public MyVector(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(MyVector v) {
		this.x += v.x;
		this.y += v.y;
	}
	
	public void substract(MyVector v) {
		this.x -= v.x;
		this.y -= v.y;
	}
	
	public boolean equals(Object object) {
		if(!(object instanceof MyVector)) return false;
		MyVector vector = (MyVector) object;
		if(vector.getX() == this.x && vector.getY() == this.y) return true;
		return false;
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
}
