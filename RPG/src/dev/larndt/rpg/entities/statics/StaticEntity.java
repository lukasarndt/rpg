package dev.larndt.rpg.entities.statics;

import dev.larndt.rpg.Handler;
import dev.larndt.rpg.entities.Entity;

public abstract class StaticEntity extends Entity{

	public StaticEntity(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
	}
}
