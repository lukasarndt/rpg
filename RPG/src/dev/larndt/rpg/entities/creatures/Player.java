package dev.larndt.rpg.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.gfx.Animation;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.tiles.Tile;

public class Player extends Creature{
	public static final int PLAYER_UP = 0, PLAYER_RIGHT = 1, PLAYER_DOWN = 2, PLAYER_LEFT = 3;
	
	private Animation animDown, animUp, animLeft, animRight;
	
	private boolean lastKeyState = false;
	private boolean currentKeyState = false;
	
	private int direction = PLAYER_DOWN;
	
	private int counter = 0;
	private int attackAnimLength = 20;
	private boolean draw = false;
	private boolean canAttack = true;
	
	//Attack timer
	private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_WIDTH);
		
		this.setBounds(2, 2, width-4, height-4);
		
		speed = 5;
		
		animDown = new Animation(500, Assets.player_down);
		animUp = new Animation(500, Assets.player_up);
		animLeft = new Animation(500, Assets.player_left);
		animRight = new Animation(500, Assets.player_right);
	}

	@Override
	public void tick() {
		tickAnimation();
		getInput();
		move();
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		checkAttacks();
	}
	
	@Override
	public void render(Graphics g) {
		g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		this.drawBounds(g);
		
		// Draw Attacks
		if(draw) {
			if(direction == PLAYER_DOWN) {
				g.drawImage(Assets.swordDown, (int) (x - handler.getGameCamera().getxOffset()), 
						(int) (y + Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
			} else if(direction == PLAYER_LEFT){
				g.drawImage(Assets.swordLeft, (int)(x - Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()), 
						(int) (y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
			} else if(direction == PLAYER_UP) {
				g.drawImage(Assets.swordUp, (int) (x - handler.getGameCamera().getxOffset()), 
						(int) (y - Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
			} else if(direction == PLAYER_RIGHT) {
				g.drawImage(Assets.swordRight, (int) (x + Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()), 
						(int) (y - handler.getGameCamera().getyOffset()), Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
			}
			counter++;
			if(counter >= attackAnimLength) {
				counter = 0;
				draw = false;
			}
		}
	}
	
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		lastKeyState = currentKeyState;
		currentKeyState = handler.getKeyManager().space;
		
		if(attackTimer > attackCooldown) {
			canAttack = true;
			attackTimer = 0;
		}
		
		Rectangle ar = new Rectangle();
		//Rectangle cb = getCollisionBounds(0,0);
		ar.width = Tile.TILE_WIDTH;
		ar.height = Tile.TILE_HEIGHT;
		
		if(currentKeyState && !lastKeyState && canAttack) {
			if(!draw) {
				draw = true;
			}
			
			if(direction == PLAYER_DOWN) {
				ar.x = (int) (x);
				ar.y = (int) (y + Tile.TILE_HEIGHT);
			} else if(direction == PLAYER_UP) {
				ar.x = (int) (x);
				ar.y = (int) (y - Tile.TILE_HEIGHT);
			} else if(direction == PLAYER_LEFT) {
				ar.x = (int) (x - Tile.TILE_WIDTH);
				ar.y = (int) (y);
			} else if(direction == PLAYER_RIGHT) {
				ar.x = (int) (x + Tile.TILE_WIDTH);
				ar.y = (int) (y);
			}
			canAttack = false;
		}
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.getCollisionBounds(0, 0).intersects(ar)) {
				e.hurt(1);
				return; // Player can only hurt 1 entity at a time!
			}
		}
	}
	
	public void tickAnimation() {
		animDown.tick();
		animUp.tick();
		animLeft.tick();
		animRight.tick();
	}
	
	private void getInput() {
		xMove = 0;
		yMove = 0;
		
		if(handler.getKeyManager().up) {
			yMove = -speed;
			direction = PLAYER_UP;
		}
		if(handler.getKeyManager().down) {
			yMove = speed;
			direction = PLAYER_DOWN;
		}
		if(handler.getKeyManager().left) {
			xMove = -speed;
			direction = PLAYER_LEFT;
		}
		if(handler.getKeyManager().right) {
			xMove = speed;
			direction = PLAYER_RIGHT;
		}
	}
	
	@Override
	public void die() {
		System.out.println("You died.");
	}
	
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			return animLeft.getCurrentFrame();
		}else if(xMove > 0) {
			return animRight.getCurrentFrame();
		} else if(yMove < 0) {
			return animUp.getCurrentFrame();
		} else {
			return animDown.getCurrentFrame();
		}
	}

}
