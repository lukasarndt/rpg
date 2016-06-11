package dev.larndt.rpg;

import dev.larndt.rpg.entities.creatures.Player;
import dev.larndt.rpg.gfx.GameCamera;
import dev.larndt.rpg.input.KeyManager;
import dev.larndt.rpg.input.MouseManager;
import dev.larndt.rpg.worlds.World;

public class Handler {
	private Game game;
	private World world;
	
	public Handler(Game game) {
		this.game = game;
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public Player getPlayer() {
		return world.getEntityManager().getPlayer();
	}
	
	public KeyManager getKeyManager() {
		return game.getKeyManager();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public GameCamera getGameCamera() {
		return game.getGameCamera();
	}

	public Game getGame() {
		return game;
	}

	/*public void setGame(Game game) {
		this.game = game;
	}*/

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	
 }
