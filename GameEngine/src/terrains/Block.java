package terrains;


import org.lwjgl.util.vector.Vector3f;

import utils.FileLoader;
import entities.TexturedEntity;

public class Block extends TexturedEntity{
	public static int WIDTH = 3;
	public static int HEIGHT = 1;
	public static int DEPTH = 3;
	private int type;
	private static int[] textures = new int[]{0,FileLoader.textureLoader("dirt.jpg")};
	
	public Block(float x, float y, float z,int type){
		super(x, y, z, 0, 0, 0, WIDTH, HEIGHT, DEPTH, 1);
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = type;
		this.setTexture(-1);
		this.setTexture(textures[type]);
		this.setRepeat(true);
	}
	
	public Block(float x, float y, float z) {
		this(x,y,z,0);
	}

	public int getType() {
		return type;
	}
}
