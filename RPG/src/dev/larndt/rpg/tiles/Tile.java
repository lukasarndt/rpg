package dev.larndt.rpg.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import dev.larndt.rpg.gfx.Assets;
import dev.larndt.rpg.utilities.Utilities;

public class Tile {
	public static Tile[] tiles 		= new Tile[256];
	public static Tile blankTile	= new Tile(Assets.blank, 0);
	public static Tile grassTile 	= new GrassTile(43);
	public static Tile dirtTile 	= new DirtTile(107);
	public static Tile mountainsLT	= new StoneTile(Assets.mountainsLT, 63);
	public static Tile mountainsLM	= new StoneTile(Assets.mountainsLM, 78);
	public static Tile mountainsLB	= new StoneTile(Assets.mountainsLB, 93);
	
	public static Tile[][] mountainLandscape;
	
	public static final int TILE_WIDTH = 64,
							TILE_HEIGHT = 64;
	
	protected BufferedImage texture;
	protected final int id;
	protected boolean isSolid = false;
	
	protected boolean solidness[];	// Defines where a tile is solid and where it is not.
									// +---+---+
									// | 0 | 1 |
									// +---+---+
									// | 2 | 3 |
									// +---+---+
	
	public Tile(BufferedImage texture, int id) {
		this.texture = texture;
		this.id = id;
		
		solidness = new boolean[4];
		
		tiles[id] = this;
	}
	
	public static void init() {
		mountainLandscape();
	}
	
	private static void mountainLandscape() {
		mountainLandscape = new Tile[16][16];
		
		int[] solidTiles = {225,226};
		
		int[] topRightSolid = {96,247};
		int[] topLeftSolid = {100,104,248};
		int[] bottomRightSolid = {1,16};
		int[] bottomLeftSolid = {4,21};
		
		int[] bottomLeftNOTsolid = {73,86};
		int[] topRightNOTsolid = {70};
		
		int[] topSolid = {89,97,98,99,103,241,242,245};
		int[] bottomSolid = {2,3,71,72,};
		
		int[] leftSideSolid = {37,53,69,85,163,179,195};
		int[] rightSideSolid = {32,48,64,80,224};
		
		for(int j = 0; j < 16; j++) {
			for(int i = 0; i < 16; i++) {
				int currentTile = j*16+i;
				mountainLandscape[i][j] = new Tile(Assets.mountainLandscapeArray[i][j], currentTile);
				mountainLandscape[i][j].setSolid(true);
				if(Utilities.contains(solidTiles, j*16+i)) {
					mountainLandscape[i][j].setSolidness(true,true,true,true);
				} else if(Utilities.contains(topRightSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(false,true,false,false);
				} else if(Utilities.contains(topLeftSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(true,false,false,false);
				} else if(Utilities.contains(bottomRightSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(false,false,false,true);
				} else if(Utilities.contains(bottomLeftSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(false,false,true,false);
				} else if(Utilities.contains(bottomLeftNOTsolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(true,true,false,true);
				} else if(Utilities.contains(topRightNOTsolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(true,false,true,true);
				} else if(Utilities.contains(topSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(true,true,false,false);
				} else if(Utilities.contains(bottomSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(false,false,true,true);
				} else if(Utilities.contains(leftSideSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(true,false,true,false);
				} else if(Utilities.contains(rightSideSolid, currentTile)) {
					mountainLandscape[i][j].setSolidness(false,true,false,true);
				} else {
					mountainLandscape[i][j].setSolid(false);
				}
			}
		}
	}
	
	public boolean isSolid2(int x, int y) {
		x = x%Tile.TILE_WIDTH;
		y= y%Tile.TILE_HEIGHT;
		
		if(x < Tile.TILE_WIDTH/2 && y < Tile.TILE_HEIGHT/2) {
			return solidness[0];
		} else if(x >= Tile.TILE_WIDTH/2 && y < Tile.TILE_HEIGHT/2) {
			return solidness[1];
		} else if(x < Tile.TILE_WIDTH/2 && y >= Tile.TILE_HEIGHT/2) {
			return solidness[2];
		} else if(x >= Tile.TILE_WIDTH/2 && y >= Tile.TILE_HEIGHT/2) {
			return solidness[3];
		}
		return false;
	}
	

	public boolean isSolid() {
		return isSolid;
	}
	
	public void tick() {
		
	}
	
	public void render(Graphics g, int x, int y) {
		g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
		
		
		/*Color oldColor = g.getColor();
		g.setColor(Color.RED);
		g.drawRect(x, y, TILE_WIDTH, TILE_HEIGHT);
		g.setColor(oldColor);*/
	}
	
	// GETTERS & SETTERS
	public int getId() {
		return id;
	}
	
	public void setSolid(boolean solid) {
		this.isSolid = solid;
	}
	
	public void setSolidness(boolean UL, boolean UR, boolean BL, boolean BR) {
		this.solidness[0] = UL;
		this.solidness[1] = UR;
		this.solidness[2] = BL;
		this.solidness[3] = BR;
	}
}
