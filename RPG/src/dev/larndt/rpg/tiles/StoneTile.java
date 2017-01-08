package dev.larndt.rpg.tiles;

import java.awt.image.BufferedImage;

public class StoneTile extends Tile{

	public StoneTile(BufferedImage texture, int id) {
		super(texture, id);
		
		this.isSolid = true;
		
		if(id == 93) {
			this.isSolid = false;
		}
	}
}
