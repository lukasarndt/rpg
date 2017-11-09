package dev.larndt.rpg.gfx;

public class ImageRequest {
	public MyImage image;
	public int zDepth;
	public int x;
	public int y;
	
	public ImageRequest(MyImage image, int zDepth, int x, int y) {
		this.image = image;
		this.zDepth = zDepth;
		this.x = x;
		this.y = y;
	}

	public MyImage getImage() {
		return image;
	}

	public void setImage(MyImage image) {
		this.image = image;
	}
	
	
}
