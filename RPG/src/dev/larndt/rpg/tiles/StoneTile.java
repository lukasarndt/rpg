package dev.larndt.rpg.tiles;

import dev.larndt.rpg.gfx.Assets;

public class StoneTile extends Tile{

	public StoneTile(int id) {
		super(Assets.stone, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		return true;
	}
}
