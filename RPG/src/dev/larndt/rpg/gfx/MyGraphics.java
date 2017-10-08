package dev.larndt.rpg.gfx;

import java.awt.image.DataBufferInt;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;

public class MyGraphics {
	private int screenWidth;
	private int screenHeight;
	private int[] pixels;
	
	// Used for transparency
	private int[] zAxis;
	private int zDepth = 0;
	
	public MyGraphics(Handler handler) {
		screenWidth = Game.WIDTH;
		screenHeight = Game.HEIGHT;
		pixels = ((DataBufferInt) handler.getGame().getImage().getRaster().getDataBuffer()).getData();
		zAxis = new int[pixels.length];
	}
	
	/**
	 * Sets all pixels to black.
	 */
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
			zAxis[i] = 0;
		}
	}
	
	/**
	 * Draw image at position (x,y) on the screen.
	 * @param image		image to be drawn
	 * @param x			x-coordinate of top left corner of the image
	 * @param y			y-coordinate of top left corner of the image
	 */
	public void drawImage(MyImage image, int x, int y) {
		// Do not draw if image is completely off the screen.
		if(x < -image.getWidth() || y < -image.getHeight() || x >= screenWidth || y >= screenHeight) {
			return;
		}
		
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();
		int xStart = 0;
		int yStart = 0;
		
		// Draw only the part of the image that is on screen.
		if(x < 0) {
			xStart -= x;
		}
		if(y < 0) {
			yStart -= y;
		}
		if(imageWidth + x >= screenWidth) {
			imageWidth = screenWidth - x;
		}
		if(imageHeight + y >= screenHeight) {
			imageHeight = screenHeight - y;
		}
		
		// Draw the image.
		for(int j = yStart; j < imageHeight; j++) {
			for(int i = xStart; i < imageWidth; i++) {
				setPixel(i + x, j + y, image.getPixels()[i + j * image.getWidth()]);
			}
		}
	}
	
	/**
	 * Sets the color of the pixel at (x,y) on the screen to "value".
	 * @param x			x Position of pixel on the screen
	 * @param y			y Position of pixel on the screen
	 * @param value		color the pixel is set to
	 */
	public void setPixel(int x, int y, int value) {
		int alpha = ((value >> 24) & 0xff);
		
		if(x < 0 || x >= screenWidth || y < 0 || y >= screenHeight || alpha == 0) {
			return;
		}
		
		if(zAxis[x + y * screenWidth] > zDepth) {
			return;
		}
		
		if(alpha == 255) {
			pixels[x + y * screenWidth] = value;
		} else {
			int pixelColor 	= pixels[x + y * screenWidth];
			int red 		= ((pixelColor >> 16) & 0xff) - (int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha/255f));
			int green 		= ((pixelColor >> 8) & 0xff) - (int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha/255f));
			int blue 		= ((pixelColor) & 0xff) - (int) (((pixelColor & 0xff) - (value & 0xff)) * (alpha/255f));
			
			pixels[x + y * screenWidth] = (255 << 24 | red << 16 | green << 8 | blue);
		}
		
	}
	
	/**
	 * Draws a rectangle.
	 * @param x			x-coordinate of top left corner
	 * @param y			y-coordinate of top left corner
	 * @param width		width if rectangle
	 * @param height	height of rectangle
	 * @param color		color of rectangle
	 */
	public void drawRect(int x, int y, int width, int height, int color) {
		for(int j = 0; j <= height; j++) {
			setPixel(x, j + y, color);
			setPixel(x + width, j + y, color);
		}
		for(int i = 0; i <= width; i++) {
			setPixel(i + x, y, color);
			setPixel(i + x, y + height, color);
		}
	}
	
	/**
	 * Fills a rectangle.
	 * @param x			x-coordinate of top left corner
	 * @param y			y-coordinate of top left corner
	 * @param width		width if rectangle
	 * @param height	height of rectangle
	 * @param color		color of rectangle
	 */
	public void fillRect(int x, int y, int width, int height, int color) {
		for(int j = 0; j <= height; j++) {
			for(int i = 0; i <= width; i++) {
				setPixel(i + x, j + y, color);
			}
		}
	}
}
