package dev.larndt.rpg.gfx;

import java.awt.image.BufferedImage;

public class Assets {
	public static final int WIDTH = 32, HEIGHT = 32;
	
	public static BufferedImage player1, player2, dirt, grass, stone, tree, wood, invSlot, invSlotActive, swordLeft, swordRight, swordUp, swordDown, slime, blank;
	public static BufferedImage[] player_down, player_up, player_right, player_left;
	public static BufferedImage[] start_button;
	public static BufferedImage[] tree1;
	public static BufferedImage mountainsLT, mountainsLM, mountainsLB, mountainsRT, mountainsRM, mountainsRB;
	
	public static BufferedImage[][] mountainLandscapeArray;

	public static void init() {
		Spritesheet sheet = new Spritesheet(ImageLoader.loadImage("/textures/spritesheet.png"));
		Spritesheet sheet2 = new Spritesheet(ImageLoader.loadImage("/textures/spritesheet2.png"));
		Spritesheet mountains = new Spritesheet(ImageLoader.loadImage("/textures/mountains.png"));
		
		
		Spritesheet mountainLandscape = new Spritesheet(ImageLoader.loadImage("/textures/mountain_landscape.png"));

		start_button = new BufferedImage[2];
		mountainLandscapeArray = new BufferedImage[16][16];
		
		loadPlayerImages();
		loadTree();
		loadMountainLandscape(mountainLandscape);
		
		// Spritesheet 1
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
		
		//Spritesheet 2
		slime = sheet2.crop(1 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		blank = sheet2.crop(2 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		//Mountains
		mountainsLT = mountains.crop(0 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		mountainsLM = mountains.crop(0 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		mountainsLB = mountains.crop(0 * WIDTH, 2 * HEIGHT, WIDTH, HEIGHT);
	}
	
	private static void loadMountainLandscape(Spritesheet sheet) {
		for(int i = 0; i < 16; i++) {
			for(int j = 0; j < 16; j++) {
				mountainLandscapeArray[i][j] = sheet.crop(i * WIDTH, j * HEIGHT, WIDTH, HEIGHT);
			}
		}
	}

	public static void loadPlayerImages() {
		Spritesheet playerAnimationSheet = new Spritesheet(ImageLoader.loadImage("/textures/playerAnimation.png"));
		Spritesheet playerAnimationSheet2 = new Spritesheet(ImageLoader.loadImage("/textures/playerAnimation2.png"));
		
		player_down = new BufferedImage[9];	
		player_up = new BufferedImage[9];
		player_right = new BufferedImage[9];
		player_left = new BufferedImage[9];	
		
		player_down[0] = playerAnimationSheet.crop(0 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		player_down[1] = playerAnimationSheet.crop(1 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		for(int i = 0; i < 9; i++) {
			player_down[i] = playerAnimationSheet2.crop(i * WIDTH*2, 2 * HEIGHT*2, WIDTH*2, HEIGHT*2);
			player_up[i] = playerAnimationSheet2.crop(i * WIDTH*2, 0 * HEIGHT*2, WIDTH*2, HEIGHT*2);
			player_right[i] = playerAnimationSheet2.crop(i * WIDTH*2, 3 * HEIGHT*2, WIDTH*2, HEIGHT*2);
			player_left[i] = playerAnimationSheet2.crop(i * WIDTH*2, 1 * HEIGHT*2, WIDTH*2, HEIGHT*2);
		}

		/*player_up[0] = playerAnimationSheet.crop(2 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		player_up[1] = playerAnimationSheet.crop(3 * WIDTH, 0 * HEIGHT, WIDTH, HEIGHT);
		
		player_right[0] = playerAnimationSheet.crop(0 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		player_right[1] = playerAnimationSheet.crop(1 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		
		player_left[0] = playerAnimationSheet.crop(2 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);
		player_left[1] = playerAnimationSheet.crop(3 * WIDTH, 1 * HEIGHT, WIDTH, HEIGHT);*/
	}
	
	public static void loadTree() {
		tree1 = new BufferedImage[16];
		
		tree1[1] = ImageLoader.loadImage("/textures/tree1/0001.png");
		
		String s;
		
		for(int i = 1; i <= 16; i++) {
			if(i <10)
				s = "000" + i;
			else
				s = "00" + i;
			
			tree1[i-1] = ImageLoader.loadImage("/textures/tree1/" + s + ".png");
		}
	}
}
