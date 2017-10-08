package dev.larndt.rpg.gfx;

import java.awt.image.BufferedImage;

public class MyImage {
	private int width;
	private int height;
	private boolean alpha = false;
	
	private int[] pixels;
	
	BufferedImage image;
	
	public MyImage(String path) {
		image 	= ImageLoader.loadImage(path);
		width	= image.getWidth();
		height	= image.getHeight();
		pixels	= image.getRGB(0, 0, width, height, null, 0, width);
		
		image.flush();
	}
	
	public MyImage(BufferedImage image) {
		this.image	= image;	
		width		= image.getWidth();
		height		= image.getHeight();
		pixels		= image.getRGB(0, 0, width, height, null, 0, width);
		
		image.flush();
	}
	
	public MyImage(BufferedImage image, boolean alpha) {
		this(image);
		this.alpha = alpha;
	}
	
	public MyImage(String path, boolean alpha) {
		this(path);
		this.alpha = alpha;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int[] getPixels() {
		return pixels;
	}

	public void setPixels(int[] pixels) {
		this.pixels = pixels;
	}

	public boolean isAlpha() {
		return alpha;
	}

	public void setAlpha(boolean alpha) {
		this.alpha = alpha;
	}

}
