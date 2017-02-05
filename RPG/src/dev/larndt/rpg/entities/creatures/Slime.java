package dev.larndt.rpg.entities.creatures;

import java.awt.Graphics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Animation;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.pathfinding.MyVector;
import dev.larndt.rpg.pathfinding.Node;

public class Slime extends Creature{
	
	public Slime(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_WIDTH);
		
		this.setBounds(20, 40, width-40, height-40);
		
		this.speed = 1;
		
		animation = new Animation(300, Assets.slime);
	}

	@Override
	public void tick() {
		time++;
		int playerX = (int) handler.getWorld().getPlayer().getX() + Creature.DEFAULT_CREATURE_WIDTH/2; // These are in pixels, not in tiles!
		int playerY = (int) handler.getWorld().getPlayer().getY() + Creature.DEFAULT_CREATURE_HEIGHT/2; //
		
		MyVector currentPosition = new MyVector((int) (this.getX() + Creature.DEFAULT_CREATURE_WIDTH/2) >> this.logX, (int) (this.getY() + Creature.DEFAULT_CREATURE_HEIGHT/2) >> this.logY);
		MyVector playerPosition = new MyVector(playerX >> this.logX, playerY >> this.logY);
		
		path = handler.getWorld().getPathfinder().findPath(currentPosition, playerPosition);
		if(path != null) {
			if(path.size() > 0) {
				MyVector vector = path.get(path.size() - 1).getVector();
				if(x <= vector.getX() << this.logX) this.xMove = speed;
				if(x > vector.getX() << this.logX) this.xMove = -speed;
				if(y <= vector.getY() << this.logY) this.yMove = speed;
				if(y > vector.getY() << this.logY) this.yMove = -speed;
			}
			this.move();
		}
		
		animation.tick();
		/*xMove = 0;
		if((Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH) && handler.getPlayer().getX() < x) {
			this.xMove = -speed;
		} else if (Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH && handler.getPlayer().getX() > x) {
			this.xMove = speed;
		}
		this.move();*/
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(animation.getCurrentFrame(), (int) (x - handler.getGameCamera().getxOffset()), 
				(int) (y - handler.getGameCamera().getyOffset()), width, height, null);
		
		//this.drawBounds(g);
		this.drawHealthBar(g);
		
		if(path != null) {
			for(Node node : path) {
				node.render(g);
			}
		}
	}

	@Override
	public void die() {
		
	}

}
