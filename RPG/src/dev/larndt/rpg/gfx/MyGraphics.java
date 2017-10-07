package dev.larndt.rpg.gfx;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;

public class MyGraphics {
	private int width;
	private int height;
	private int[] pixels;
	
	public MyGraphics(Handler handler) {
		width = Game.WIDTH;
		height = Game.HEIGHT;
		pixels = ((DataBufferInt) handler.getGame().getImage().getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] += i;
		}
	}
	
	public void drawImage(BufferedImage image, int xOffset, int yOffset) {
		for(int y = 0; y < image.getHeight(); y++) {
			for(int x = 0; x < image.getWidth(); x++) {
				setPixel(x + xOffset, y + yOffset, image.getRGB(x, y));
			}
		}
	}
	
	/**
	 * Sets the color of the pixel at (x,y) on the screen to value.
	 * @param x			x Position of pixel on the screen
	 * @param y			y Position of pixel on the screen
	 * @param value		color the pixel is set to
	 */
	public void setPixel(int x, int y, int value) {
		if(x < 0 || x >= width || y < 0 || y >= height || value == 0xffff00ff) {
			return;
		}
		
		pixels[x + y * width] = value;
	}
}
