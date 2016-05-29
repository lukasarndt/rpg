package dev.larndt.rpg;

public class Launcher {
	
	public static void main(String[] args) {
		Game game = new Game("RPG", 800, 600);
		
		game.start();
	}
}
