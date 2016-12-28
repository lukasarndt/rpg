package dev.larndt.rpg.entities.creatures;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.tiles.Tile;

public abstract class Creature extends Entity{
	
	 
	public static final int	DEFAULT_CREATURE_WIDTH = 64,
							DEFAULT_CREATUE_HEIGHT = 64;
	
	protected static final float DEFAULT_SPEED = 3.0f;
	
	protected float speed, xMove, yMove;
	
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
			int tx = (int) (x + xMove + entityBounds.x + entityBounds.width)/Tile.TILE_WIDTH; // x Coordinate of the tiles we are about to move into.
					if(!collisionWithTile(tx, (int) (y + entityBounds.y)/Tile.TILE_HEIGHT) && 
							!collisionWithTile(tx, (int) (y + entityBounds.y + entityBounds.height)/Tile.TILE_HEIGHT)) {
						x += xMove;
					}else{
						x = tx * Tile.TILE_WIDTH - entityBounds.x - entityBounds.width - 1;
					}
		}else if(xMove < 0) { // Moving left
			int tx = (int) (x + xMove + entityBounds.x)/Tile.TILE_WIDTH; // x Coordinate of the tiles we are about to move into.
			if(!collisionWithTile(tx, (int) ((y + entityBounds.y)/Tile.TILE_HEIGHT)) && 
					!collisionWithTile(tx, (int) (y + entityBounds.y + entityBounds.height)/Tile.TILE_HEIGHT)) {
				x += xMove;
			}else{
				x = tx * Tile.TILE_WIDTH + Tile.TILE_WIDTH - entityBounds.x;
			}
		}
	}
	
	public void moveY() {
		if(yMove < 0) { // Moving up
			int ty = (int) (y + yMove + entityBounds.y)/Tile.TILE_HEIGHT; // x Coordinate of the tiles we are about to move into.
			if(!collisionWithTile((int)((x + entityBounds.x) / Tile.TILE_WIDTH) , ty) &&
					!collisionWithTile((int)((x + entityBounds.x + entityBounds.width) / Tile.TILE_WIDTH) , ty) ) {
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT + Tile.TILE_HEIGHT - entityBounds.y;
			}
		}else if(yMove > 0) { // Moving down
			int ty = (int) (y + yMove + entityBounds.y + entityBounds.height )/Tile.TILE_HEIGHT; // x Coordinate of the tiles we are about to move into.
			if(!collisionWithTile((int)((x + entityBounds.x) / Tile.TILE_WIDTH) , ty) &&
					!collisionWithTile((int)((x + entityBounds.x + entityBounds.width) / Tile.TILE_WIDTH) , ty) ) {
				y += yMove;
			}else{
				y = ty * Tile.TILE_HEIGHT - entityBounds.y - entityBounds.height - 1;
			}
		}
	}
	
	protected boolean collisionWithTile(int x, int y) {
		return handler.getWorld().getTile(x, y).isSolid();
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
	
	

}
