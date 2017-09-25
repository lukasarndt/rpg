package dev.larndt.rpg.entities.creatures;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;
import dev.larndt.rpg.gfx.Animation;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.inventory.Inventory;
import dev.larndt.rpg.items.Item;
import dev.larndt.rpg.tiles.Tile;

public class Player extends Creature{
	public static final int PLAYER_UP = 0, PLAYER_RIGHT = 1, PLAYER_DOWN = 2, PLAYER_LEFT = 3;
	
	private Animation animDown, animUp, animLeft, animRight;

	private int direction = PLAYER_DOWN;
	private int frameCounter;
	private int hunger, maxFood = 10;
	
	private boolean canMove;
	
	// Attack
	private int attackCounter = 0;
	private int attackAnimLength = 20;
	private boolean drawAttacks = false;
	private boolean canAttack = true;
	private Rectangle attackRectangle = new Rectangle();
	
	// Inventory
	private Inventory inventory;
	private boolean lastInventoryKeyState = false;
	private boolean currentInventoryKeyState = false;
	
	// Attack timer
	private long lastAttackTimer, attackCooldown = 1000, attackTimer = attackCooldown;
	private boolean lastAttackKeyState = false;
	private boolean currentAttackKeyState = false;
	
	public Player(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_WIDTH);
		
		this.setBounds(2, 2, width-4, height-4);
		
		speed = 3;
		canMove = true;
		hunger = maxFood;
		
		animDown = new Animation(500, Assets.player_down);
		animUp = new Animation(500, Assets.player_up);
		animLeft = new Animation(500, Assets.player_left);
		animRight = new Animation(500, Assets.player_right);
		
		inventory = new Inventory(handler);
	}

	@Override
	public void tick() {
		frameCounter++;
		
		tickAnimation();
		getInput();
		
		if(!this.inventory.isActive() && canMove) {
			move();
		}else if (this.inventory.isActive()){
			inventory.tick();
		}
		
		handler.getGameCamera().centerOnEntity(this);
		
		//Attack
		if(!this.inventory.isActive() && !handler.getWorld().getTextbox().isActive()) {
			checkAttacks();
		}
		
		if(frameCounter%1200 == 0) {
			changeHunger(-1);
		}
		if(frameCounter%180 == 0 && (hunger == maxFood || hunger == maxFood-1)) {
			heal(1);
		}
		
		if(hunger <= 3) {
			this.setSpeed(1);
		} else {
			this.setSpeed(3);
		}
	}
	
	@Override
	public void render(Graphics g) {
		image = getCurrentAnimationFrame();
		g.drawImage(image, (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		this.castShadow(g);
		if(debug) {
			this.drawBounds(g);
		}
		// Draw Attacks
		if(drawAttacks) {			
			canMove = false;

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
			
			attackCounter++;
			if(attackCounter >= attackAnimLength) {
				attackCounter = 0;
				drawAttacks = false;
				canMove = true;
			}
		}
		
		if(this.inventory.isActive()) {
			inventory.render(g);
		}
		
		/*
		 * Health and hunger bar are drawn in the world!
		 */
	}
	
	public void eat(Item item) {
		changeHunger(item.getEnergy());
	}
	
	private void checkAttacks() {
		attackTimer += System.currentTimeMillis() - lastAttackTimer;
		lastAttackTimer = System.currentTimeMillis();
		
		// This prevents the player from holding down the attack key to attack.
		lastAttackKeyState = currentAttackKeyState;
		currentAttackKeyState = handler.getKeyManager().attackKey;
		
		if(attackTimer > attackCooldown) {
			canAttack = true;
			attackTimer = 0;
		}
		
		if(currentAttackKeyState && !lastAttackKeyState && canAttack) {
			//Rectangle cb = getCollisionBounds(0,0);
			attackRectangle.width = Tile.TILE_WIDTH;
			attackRectangle.height = Tile.TILE_HEIGHT;
			
			if(!drawAttacks) {
				drawAttacks = true;
			}
			
			if(direction == PLAYER_DOWN) {
				attackRectangle.x = (int) (x);
				attackRectangle.y = (int) (y + Tile.TILE_HEIGHT);
			} else if(direction == PLAYER_UP) {
				attackRectangle.x = (int) (x);
				attackRectangle.y = (int) (y - Tile.TILE_HEIGHT);
			} else if(direction == PLAYER_LEFT) {
				attackRectangle.x = (int) (x - Tile.TILE_WIDTH);
				attackRectangle.y = (int) (y);
			} else if(direction == PLAYER_RIGHT) {
				attackRectangle.x = (int) (x + Tile.TILE_WIDTH);
				attackRectangle.y = (int) (y);
			}
			canAttack = false;
		}
		
		for(Entity e : handler.getWorld().getEntityManager().getEntities()) {
			if(e.equals(this)) {
				continue;
			}
			if(e.getCollisionBounds(-2, 0).intersects(this.getCollisionBounds(0, 0)) || e.getCollisionBounds(2, 0).intersects(this.getCollisionBounds(0, 0))) {
				this.hurt(e.getAttackStrength());
			}
			if(e.getCollisionBounds(0, 0).intersects(attackRectangle)) {
				e.hurt(1);
				//return; // Player can only hurt 1 entity at a time!
			}	
		}
		
		attackRectangle.width = 0;
		attackRectangle.height = 0;
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
		
		if(!this.inventory.isActive() && canMove) {
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
		
		lastInventoryKeyState = currentInventoryKeyState;
		currentInventoryKeyState = handler.getKeyManager().inventoryKey;
		if(currentInventoryKeyState && !lastInventoryKeyState) {
			this.inventory.setActive(!this.inventory.isActive());
		}
	}
	
	@Override
	public void die() {
		System.out.println("You died.");
	}
	
	BufferedImage lastAnimationFrame = Assets.player_down[0];
	private BufferedImage getCurrentAnimationFrame() {
		if(xMove < 0) {
			lastAnimationFrame = animLeft.getCurrentFrame();
		}else if(xMove > 0) {
			lastAnimationFrame = animRight.getCurrentFrame();
		} else if(yMove < 0) {
			lastAnimationFrame = animUp.getCurrentFrame();
		} else if(yMove > 0){
			lastAnimationFrame = animDown.getCurrentFrame();
		}
		return lastAnimationFrame;
	}

	public void changeHunger(int delta) {
		hunger += delta;
		if(hunger < 0) hunger = 0;
		if(hunger > maxFood) hunger = maxFood;
	}
	
	// ======================= GETTERS & SETTERS ==============================
	public Inventory getInventory() {
		return inventory;
	}

	public boolean isCanMove() {
		return canMove;
	}

	public void setCanMove(boolean canMove) {
		this.canMove = canMove;
	}

	public int getHunger() {
		return hunger;
	}

	public void setHunger(int hunger) {
		if(hunger > maxFood) hunger = maxFood;
		if(hunger < 0) hunger = 0;
		this.hunger = hunger;
	}
	
	public int getMaxFood() {
		return maxFood;
	}
	
	public boolean drawAttack() {
		return drawAttacks;
	}
}
