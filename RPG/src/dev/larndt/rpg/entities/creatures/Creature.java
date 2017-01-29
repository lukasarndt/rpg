package dev.larndt.rpg.entities.creatures;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.pathfinding.Node;
import dev.larndt.rpg.tiles.Tile;

public abstract class Creature extends Entity{
	
	 
	public static final int	DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATURE_HEIGHT = 64;
	
	protected static final float DEFAULT_SPEED = 3.0f;
	
	protected float speed, xMove, yMove;
	protected int healthBarThickness = 1, healthBarWidth = DEFAULT_CREATURE_WIDTH, healthBarHeight = 10, time;
	
	protected Color healthBarColor = Color.BLACK;
	
	protected List<Node> path = null;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		speed = DEFAULT_SPEED;
		xMove = 0f;
		yMove = 0f;

	}
	
	public void move() {
		if(!checkEntityCollisions(xMove, 0)) {
			moveX();
		}
		if(!checkEntityCollisions(0, yMove)) {
			moveY();
		}
	}
	
	public void moveX() {
		if(xMove > 0) { // Moving right
			int tx = (int) (x + xMove + entityBounds.width); // x Coordinate of the tiles we are about to move into.
					if(!collisionWithTile(tx, (int) (y + entityBounds.y)) && 
							!collisionWithTile(tx, (int) (y + entityBounds.y + entityBounds.height))) {
						x += xMove;
					}else{
						x = tx * Tile.TILE_WIDTH - entityBounds.x - entityBounds.width - 1;
					}
		}else if(xMove < 0) { // Moving left
			int tx = (int) (x + xMove + entityBounds.x); // x Coordinate of the tiles we are about to move into.
			if(!collisionWithTile(tx, (int) ((y + entityBounds.y))) && 
					!collisionWithTile(tx, (int) (y + entityBounds.y + entityBounds.height))) {
				x += xMove;
			}else{
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - entityBounds.x;
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) { // Moving up
			int ty = (int) (y + yMove + entityBounds.y); // x Coordinate of the tiles we are about to move into.
			if(!collisionWithTile((int)((x + entityBounds.x)) , ty) &&
					!collisionWithTile((int)((x + entityBounds.x + entityBounds.width)) , ty) ) {
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - entityBounds.y;
			}
		}else if(yMove > 0) { // Moving down
			int ty = (int) (y + yMove + entityBounds.y + entityBounds.height ); // y Coordinate of the tiles we are about to move into.
			if(!collisionWithTile((int)((x + entityBounds.x)) , ty) &&
					!collisionWithTile((int)((x + entityBounds.x + entityBounds.width)) , ty) ) {
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT - entityBounds.y - entityBounds.height - 1;
			}
		}
	}
	
	public void drawHealthBar(Graphics g) {
		float healthFraction = (float)health/(float)maxHealth;
		Graphics2D g2 = (Graphics2D) g;
		
		Stroke oldStroke = g2.getStroke();
		Color oldColor = g2.getColor();
		
		g2.setStroke(new BasicStroke(healthBarThickness));
		g2.setColor(Color.RED);
		g2.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - 20 - handler.getGameCamera().getyOffset()), (int) (healthBarWidth*healthFraction), (int)healthBarHeight);
		g2.setColor(healthBarColor);
		g2.drawRect((int) (x - handler.getGameCamera().getxOffset()), (int) (y - 20 - handler.getGameCamera().getyOffset()), (int) healthBarWidth, (int) healthBarHeight);	
		g2.setColor(oldColor);
		g2.setStroke(oldStroke);
	}
	
	protected boolean collisionWithTile(int x, int y) {
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("x/w = " + x/Tile.TILE_WIDTH);
		System.out.println("y/h = " + y/Tile.TILE_HEIGHT);
		return (handler.getWorld().getTile(x/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT, 1).isSolid2(x,y) 
				|| handler.getWorld().getTile(x/Tile.TILE_WIDTH, y/Tile.TILE_HEIGHT, 2).isSolid2(x,y));
	}
	
	public void printPosition(int i) {
		if(i == 0) {
			System.out.println("Position in pixels: (" + (int)this.x + "," + (int)this.y + ")");
		} else {
			System.out.println("Position in tiles: (" + (int)this.x / Tile.TILE_WIDTH + "," + (int)this.y/ Tile.TILE_HEIGHT + ")");
		}
	}
	
	// GETTERS & SETTERS
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public float getxMove() {
		return xMove;
	}

	public void setxMove(float xMove) {
		this.xMove = xMove;
	}

	public float getyMove() {
		return yMove;
	}

	public void setyMove(float yMove) {
		this.yMove = yMove;
	}

	public List<Node> getPath() {
		return path;
	}
}
