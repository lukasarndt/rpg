package dev.larndt.rpg.gfx;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.larndt.rpg.Game;
import dev.larndt.rpg.Handler;

public class MyGraphics {
	private int screenWidth;
	private int screenHeight;
	private int[] pixels;
	
	private ArrayList<ImageRequest> imageRequestList = new ArrayList<ImageRequest>();
	private boolean processing = false;
	
	// Used for transparency
	private int[] zAxis;
	private int zDepth = 0;
	
	// Lighting
	private int[] lightMap;
	private int[] lightBlock;
	private int ambientColor = 0xff232323;
	
	public MyGraphics(Handler handler) {
		screenWidth 	= Game.WIDTH;
		screenHeight 	= Game.HEIGHT;
		pixels 			= ((DataBufferInt) handler.getGame().getImage().getRaster().getDataBuffer()).getData();
		zAxis 			= new int[pixels.length];
		lightMap		= new int[pixels.length];
		lightBlock		= new int[pixels.length];
	}
	
	/**
	 * Sets all pixels to black.
	 */
	public void clear() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] 		= 0;
			zAxis[i] 		= 0;
			lightMap[i] 	= ambientColor;
			lightBlock[i] 	= 0;
		}
	}
	
	public void process() {
		processing = true;
		
		Collections.sort(imageRequestList, new Comparator<ImageRequest>() {

			@Override
			public int compare(ImageRequest o1, ImageRequest o2) {
				if(o1.zDepth < o2.zDepth)
					return -1;
				if(o1.zDepth > o2.zDepth)
					return 1;
				return 0;
			}
			
		});
		
		for(int i = 0; i < imageRequestList.size(); i++) {
			ImageRequest imageRequest = imageRequestList.get(i);
			setzDepth(imageRequest.zDepth);
			drawImage(imageRequest.getImage(), imageRequest.x, imageRequest.y);
		}
		
		// Merging light map and pixel map
		for(int i = 0; i < pixels.length; i++) {
			float red = ((lightMap[i] >> 16) & 0xff) / 255f;
			float green = ((lightMap[i] >> 8) & 0xff) / 255f;
			float blue = (lightMap[i] & 0xff) / 255f;
			
			pixels[i] = ((int) (((pixels[i] >> 16) & 0xff) * red) << 16 |
					(int) (((pixels[i] >> 8) & 0xff) * green) << 8 |
					(int) ((pixels[i] & 0xff) * blue));
		}
		
		imageRequestList.clear();
		processing = false;
	}
	
	/**
	 * Draw image at position (x,y) on the screen.
	 * @param image		image to be drawn
	 * @param x			x-coordinate of top left corner of the image
	 * @param y			y-coordinate of top left corner of the image
	 */
	public void drawImage(MyImage image, int x, int y) {
		if(image.isAlpha() && !processing) {
			imageRequestList.add(new ImageRequest(image, zDepth, x, y));
			return;
		}
		
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
				setLightBlock(i + x, j + y, image.getLightBlock());
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
		
		int index = x + y * screenWidth;
		if(zAxis[index] > zDepth) {
			return;
		}
		
		zAxis[index] = zDepth;
		
		if(alpha == 255) {
			pixels[index] = value;
		} else {
			int pixelColor 	= pixels[index];
			int red = ((pixelColor >> 16) & 0xff) - 
					(int) ((((pixelColor >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha/255f));
			int green = ((pixelColor >> 8) & 0xff) - 
					(int) ((((pixelColor >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha/255f));
			int blue = ((pixelColor) & 0xff) - 
					(int) (((pixelColor & 0xff) - (value & 0xff)) * (alpha/255f));
			
			pixels[index] = (red << 16 | green << 8 | blue);
		}	
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param value
	 */
	public void setLightMap(int x, int y, int value) {
		if(x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) {
			return;
		}
		
		int baseColor 	= lightMap[x + y * screenWidth];
		
		int maxRed		= Math.max((baseColor >> 16) & 0xff, (value >> 16) & 0xff);
		int maxGreen	= Math.max((baseColor >> 8) & 0xff, (value >> 8) & 0xff);
		int maxBlue		= Math.max(baseColor & 0xff, value & 0xff);
		
		lightMap[x + y * screenWidth] = (maxRed << 16 | maxGreen << 8 | maxBlue);
	}
	
	public void setLightBlock(int x, int y, int value) {
		if(x < 0 || x >= screenWidth || y < 0 || y >= screenHeight) {
			return;
		}
		
		if(zAxis[x + y * screenWidth] > zDepth) {
			return;
		}
		
		lightBlock[x + y * screenWidth] = value;
	}
	
	public void drawLight(Light light, int x, int y) {
		for(int i = 0; i <= light.getDiameter(); i++) {
			drawLightLine(light, light.getRadius(), light.getRadius(), i, 0, x , y);
			drawLightLine(light, light.getRadius(), light.getRadius(), i, light.getDiameter(), x, y);
			drawLightLine(light, light.getRadius(), light.getRadius(), 0 , i, x, y);
			drawLightLine(light, light.getRadius(), light.getRadius(), light.getDiameter(), i, x, y);
		}
	}
	
	public void drawLightLine(Light light, int x0, int y0, int x1, int y1, int x, int y) {
		// Using Bresenham algorithm
		int dx = Math.abs(x1-x0);
		int dy = Math.abs(y1-x0);
		
		int sx = x0 < x1 ? 1 : -1;
		int sy = y0 < y1 ? 1 : -1;
		
		int err = dx- dy;
		int e2;
		
		while(true) {
			int screenX = x0 - light.getRadius() + x;
			int screenY = y0 - light.getRadius() + y;
			if(screenX < 0 || screenX >= screenWidth || screenY < 0 || screenY >= screenHeight) {
				return;
			}
			
			int lightColor = light.getLight(x0, y0);
			if(lightColor == 0) {
				return;
			}
			
			if(lightBlock[screenX + screenY * screenWidth] == Light.FULL) {
				return;
			}
			
			setLightMap(screenX, screenY, lightColor);
			
			if(x0 == x1 && y0 == y1) {
				break;
			}
			
			e2 = 2 * err;
			if(e2 > -1 * dy) {
				err -= dy;
				x0 += sx;
			}
			
			if(e2 < dx) {
				err += dx;
				y0 += sy;
			}
		}
	}
	/**
	 * Draws a rectangle.
	 * @param x			x-coordinate of top left corner
	 * @param y			y-coordinate of top left corner
	 * @param width		width if rectangle
	 * @param height		height of rectangle
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
	 * @param height		height of rectangle
	 * @param color		color of rectangle
	 */
	public void fillRect(int x, int y, int width, int height, int color) {
		for(int j = 0; j < height; j++) {
			for(int i = 0; i < width; i++) {
				setPixel(i + x, j + y, color);
			}
		}
	}

	public int getzDepth() {
		return zDepth;
	}

	public void setzDepth(int zDepth) {
		this.zDepth = zDepth;
	}

	public int[] getLightMap() {
		return lightMap;
	}
}