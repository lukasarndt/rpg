package dev.larndt.rpg.entities;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;
import dev.larndt.rpg.tiles.Tile;

public abstract class Entity {
	public static final int DEFAULT_HEALTH = 3;
	
	public static boolean debug = false;
			
	protected Handler		handler;
	protected int 			width, height, health;
	protected int			maxHealth = 5, attackStrength = 0, counter, delta = 1000;
	protected float 		x, y;
	protected Rectangle 	entityBounds;
	protected Boolean 		active = true, isSolid = true, colliding= false;
	protected long 			lastTime, now;
	protected int 			logX, logY; // These are the base 2 logarithms of the size of a tile.
	protected double		distanceToTick;
	protected BufferedImage image;
	
	public Entity(Handler handler, float x, float y, int width, int height) {
		this.handler	= handler;
		this.x 			= x;
		this.y 			= y;
		this.width 		= width;
		this.height 	= height;
		health 			= maxHealth;
		entityBounds	= new Rectangle(0, 0, width, height);
		logX 			= (int) (Math.log(Tile.TILE_WIDTH)/Math.log(2));
		logY 			= (int) (Math.log(Tile.TILE_HEIGHT)/Math.log(2));
		now 			= System.currentTimeMillis();
		lastTime 		= now;
		distanceToTick  = Math.max(Game.HEIGHT, Game.WIDTH);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);

	public void hurt(int amt) {
		now = System.currentTimeMillis();
		counter += now - lastTime;
		lastTime = now;
		if(counter > delta) {
			health -= amt;
			if(health <= 0) {
				die();
				active = false;			
			}
			counter = 0;
		}
	}
	
	public void heal(int amt) {
		health += amt;
		if(health > maxHealth) {
			health = maxHealth;
		}
	}
	
	public abstract void die();

	public void drawBounds(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.red);
		g.drawRect((int) (entityBounds.x + x - handler.getGameCamera().getxOffset()), 
				(int) (entityBounds.y  + y - handler.getGameCamera().getyOffset()), 
				entityBounds.width, entityBounds.height);
		g.setColor(c);
	}
	
	// x and y Offset are for telling the collisionBounds where we want to move to.
	public boolean checkEntityCollisions(float xOffset, float yOffset) { 
		List<Entity> entities = new ArrayList<Entity>();
		entities.clear();
		handler.getWorld().getEntityManager().getQuadtree().retrieve(entities, this);
		
		//System.out.println("Entity list size of " + getClass().getSimpleName() + ": " + entities.size());
		
		for(Entity e : entities) {
			//System.out.println("* " + e.getClass().getSimpleName());
			e.colliding = false;
			if(e.equals(this)) {
				continue;
			}
			/*System.out.println(this.getClass().getSimpleName() + 
				" is checking collision with " 
				+ e.getClass().getSimpleName());*/
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset))) {
				e.colliding = true;
				if(!e.isSolid) {
					return false;
				}
				return true;
			}
		}
		return false;
	}

	public double distanceFromPlayer() {
		if(handler.getPlayer() != null) {
			return Math.sqrt(Math.pow((this.getX() - handler.getPlayer().getX()), 2) 
					+ Math.pow((this.getY() - handler.getPlayer().getY()), 2));
		}
		
		return 0;
	}
	
	public void castShadow(Graphics g) {	
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage shadow = colorImage(copyImage(image), 0, 0, 0);
		float opacity = 0.2f;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));	
		g2d.drawImage(shadow, 
				(int)(this.x - handler.getGameCamera().getxOffset()), 
				(int)(this.y - handler.getGameCamera().getyOffset() + this.height), 
				this.width, 
				this.height, 
				null);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	public static BufferedImage copyImage(BufferedImage source){
	    BufferedImage b = new BufferedImage(source.getWidth(), 
	    		source.getHeight(), 
	    		source.getType());
	    Graphics g = b.getGraphics();
	    g.drawImage(source, 0, 0, null);
	    g.dispose();
	    return b;
	}
	
	private BufferedImage colorImage(BufferedImage image, int r, int g, int b) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = r;
                pixels[1] = g;
                pixels[2] = b;
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }
	
	// ======================================== GETTERS & SETTERS =======================
	public float getX() {
		return x;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset) {
		return new Rectangle((int) (x + entityBounds.x + xOffset), 
				(int) (y + entityBounds.y + yOffset) , 
				entityBounds.width, 
				entityBounds.height);
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setBounds(int x, int y, int width, int height) {
		entityBounds.x = x;
		entityBounds.y = y;
		entityBounds.width = width;
		entityBounds.height = height;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getAttackStrength() {
		return attackStrength;
	}

	public void setAttackStrength(int attackStrength) {
		this.attackStrength = attackStrength;
	}
	
	public double getDistanceToTick() {
		return distanceToTick;
	}
	// --------------------------------------------------------------------
}
