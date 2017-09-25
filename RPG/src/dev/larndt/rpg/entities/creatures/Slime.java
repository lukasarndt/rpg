package dev.larndt.rpg.entities.creatures;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.pathfinding.MyVector;

public class Slime extends Creature{
	
	public Slime(Handler handler, float x, float y) {
		super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_WIDTH);
		
		setBounds(1, 1, width-2, height-2);	
		speed = 1;
		attackStrength = 1;
		image = Assets.slime;
	}

	@Override
	public void tick() {
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
		
		/*xMove = 0;
		if((Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH) && handler.getPlayer().getX() < x) {
			this.xMove = -speed;
		} else if (Math.abs(x - handler.getPlayer().getX()) < 3* Creature.DEFAULT_CREATURE_WIDTH && handler.getPlayer().getX() > x) {
			this.xMove = speed;
		}
		this.move();*/
	}

	@Override
	public void die() {
		
	}

}
