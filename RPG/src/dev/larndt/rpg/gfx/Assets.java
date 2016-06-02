package dev.larndt.rpg.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	public static final int WIDTH = 32, HEIGHT = 32;
	
	public static BufferedImage player1, player2, dirt, grass, stone, tree, wood, invSlot, invSlotActive, swordLeft, swordRight, swordUp, swordDown;
	public static BufferedImage[] player_down, player_up, player_right, player_left;
	public static BufferedImage[] start_button;

	public static void init() {
		Spritesheet sheet = new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png"));
		
		loadPlayerImages();
		
		start_button = new BufferedImage[2];
		
		player1 = sheet.crop(0 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		player2 = sheet.crop(1 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		dirt 	= sheet.crop(2 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		grass	= sheet.crop(3 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		stone			= sheet.crop(0 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		tree			= sheet.crop(1 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		start_button[0] = sheet.crop(2 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		start_button[1] = sheet.crop(3 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		
		wood			= sheet.crop(0 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
		invSlot			= sheet.crop(1 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
		invSlotActive 	= sheet.crop(2 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
		
		swordLeft = sheet.crop(0 * WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);
		swordRight = sheet.crop(1 * WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);
		swordDown = sheet.crop(2 * WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);
		swordUp = sheet.crop(3 * WIDTH, 3 * HEIGHT, WIDTH, HEIGHT);
	}
	
	public static void loadPlayerImages() {
		Spritesheet playerAnimationSheet = new Spritesheet(ImageLoader.loadImage("/textures/playerAnimation.png"));
		
		player_down = new BufferedImage[2];	
		player_down[0] = playerAnimationSheet.crop(0 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		player_down[1] = playerAnimationSheet.crop(1 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		player_up = new BufferedImage[2];	
		player_up[0] = playerAnimationSheet.crop(2 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		player_up[1] = playerAnimationSheet.crop(3 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		player_right = new BufferedImage[2];	
		player_right[0] = playerAnimationSheet.crop(0 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		player_right[1] = playerAnimationSheet.crop(1 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		
		player_left = new BufferedImage[2];	
		player_left[0] = playerAnimationSheet.crop(2 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		player_left[1] = playerAnimationSheet.crop(3 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
	}
}
