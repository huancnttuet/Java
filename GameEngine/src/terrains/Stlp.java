package terrains;

import java.util.ArrayList;

import main.Game;
import renderers.Renderer;
import shaders.StaticShader;

/*
 * +void add(Block b)
 * +void add(Block b,int y)
 * +void draw(Renderer renderer, Shader shader)
 * +Block getTop()
 * -void remove()
 */


public class Stlp {
	private int numX;
	private int numZ;
	private ArrayList<Block> blocks;
	
	public Stlp(int x, int z){
		blocks = new ArrayList<Block>();
		this.numX = x;
		this.numZ = z;
	}
	
	public void add(Block b){
		blocks.add(b);
	}
	public void add(int type){
		blocks.add(new Block(numX,blocks.size(),numZ,type));
	}
	
	public void add(Block b,int y){
		blocks.set(y, b);
	}
	
	public void draw(Renderer renderer, StaticShader shader) {
		for(Block b:blocks){
			if(b.getType()!=0)
			renderer.render(b, shader);
		}
	}
	
	public Block getTop(){
		return blocks.get(blocks.size()-1);
	}
	
	public boolean exist(int y){
		return blocks.contains(y);
	}
	
	public void removeTop(){
		if(blocks.size()>1)
			blocks.remove(blocks.size()-1);
	}
	
	public int getNum(){
		return blocks.size();
	}
	
}