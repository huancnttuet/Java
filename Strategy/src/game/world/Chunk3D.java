package game.world;

import java.util.ArrayList;

import org.json.JSONObject;

import game.object.GameObject;
import game.rendering.RenderingEngine;
import glib.util.vector.GVector3f;

public class Chunk3D extends GameObject{
	public static int NUM_X = 16;
	public static int NUM_Y = 16;
	public static int NUM_Z = 16;
	private Chunk3D[] neighboards;
	
	public Block[][][] blocks;
	
	public void setNeighboards(int i, int j, int k){
		Block b = blocks[i][j][k];
		if(b==null)
			return;
		
		if(i+1 < NUM_X)
			b.setNeighboard(1, blocks[i+1][j][k]);
		if(j+1 < NUM_Y)
			b.setNeighboard(0, blocks[i][j+1][k]);
		if(k+1 < NUM_Z)
			b.setNeighboard(4, blocks[i][j][k+1]);
		
		if(i > 0)
			b.setNeighboard(3, blocks[i-1][j][k]);
		if(j > 0)
			b.setNeighboard(2, blocks[i][j-1][k]);
		if(k > 0)
			b.setNeighboard(5, blocks[i][j][k-1]);
	}
	
	public void setNeighboardsAround(int i, int j, int k){
		if(exist(i+1, j, k,true))
			setNeighboards(i+1, j, k);
		if(exist(i-1, j, k,true))
			setNeighboards(i-1, j, k);
		if(exist(i, j+1, k,true))
			setNeighboards(i, j+1, k);
		if(exist(i, j-1, k,true))
			setNeighboards(i, j-1, k);
		if(exist(i, j, k+1,true))
			setNeighboards(i, j, k+1);
		if(exist(i, j, k-1,true))
			setNeighboards(i, j, k-1);
	}
	
	public void setNeighboards(){
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					setNeighboards(i, j, k);	
				}
			}
		}
	}
	
	public Chunk3D(GVector3f position) {
		super(position, 9);
		neighboards = new Chunk3D[4];
		blocks = new Block[NUM_X][NUM_Y][NUM_Z];
		create();
	}
	
	public Chunk3D(GVector3f position,String type) {
		super(position, 9);

		blocks = new Block[NUM_X][NUM_Y][NUM_Z];
		neighboards = new Chunk3D[4];
		
		if(type.equals("sandBox")){
			for(int i=0 ; i<NUM_X ; i++){
				for(int k=0 ; k<NUM_Z ; k++){
					blocks[i][0][k] = new Block(getPosition().add(new GVector3f(i,0/*(int)(height*10-5)*/,k).mul(new GVector3f(Block.WIDTH, Block.HEIGHT, Block.DEPTH).mul(2))),6);
				}
			}
		}
	}
	
	public Chunk3D(JSONObject data){
		super(new GVector3f((float)data.getDouble("posX"),(float)data.getDouble("posY"),(float)data.getDouble("posZ")),9);
		blocks = new Block[NUM_X][NUM_Y][NUM_Z];
		neighboards = new Chunk3D[4];
		create(data);
	}
	
	public JSONObject toJSON(){
		JSONObject o = new JSONObject();
		o.put("posX", getPosition().getX());
		o.put("posY", getPosition().getY());
		o.put("posZ", getPosition().getZ());
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					if(blocks[i][j][k] != null)
						o.put("block"+i+j+k, blocks[i][j][k].toJSON());
					else
						o.put("block"+i+j+k, "null");
				}
			}
		}
		return o;
	}
	
	private void create(JSONObject o){
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					if(o.get("block"+i+j+k).equals("null"))
						blocks[i][j][k] = null;
					else
						blocks[i][j][k] = new Block(o.getJSONObject("block"+i+j+k));
				}
			}
		}
	}
	
	private void create(){
		for(int i=0 ; i<NUM_X ; i++){
			for(int k=0 ; k<NUM_Z ; k++){
				float height = World.map[i+getPosition().getXi()/2][k+getPosition().getZi()/2]*NUM_Y/8;
				for(int j=0 ; j<NUM_Y ; j++){
					int type = (int)(Block.blockDatas.size()*Math.random())+1;
					if(type==0)
						blocks[i][j][k] = null;
					else if(height>j)
						blocks[i][j][k] = new Block(getPosition().add(new GVector3f(i,j/*(int)(height*10-5)*/,k).mul(new GVector3f(Block.WIDTH, Block.HEIGHT, Block.DEPTH).mul(2))),type);
					
				}
			}
		}
	}
	
	public boolean exist(int x, int y, int z, boolean checkNull){
		if(checkNull)
			return x>=0 && z>=0 && y>=0 && y<NUM_Y && x<NUM_X && z<NUM_Z && blocks[x][y][z]!=null;
		else
			return x>=0 && z>=0 && y>=0 && y<NUM_Y && x<NUM_X && z<NUM_Z;
	}
	
	public void render(RenderingEngine renderingEngine) {
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					Block b = blocks[i][j][k];
					if(b!=null && b.isActive() && b.getType()>0){
						b.render(renderingEngine);
					}
				}
			}
		}
	}
	
	public void setWorld(World world){
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					Block b = blocks[i][j][k];
					if(b==null){
						return;
					}
					b.setWorld(world);
				}
			}
		}
	}
	
	public void update(){
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					Block b = blocks[i][j][k];
					if(b!=null && b.isActive() && b.getType()>0){
						b.update();
					}
				}
			}
		}
	}
	
	public void setSides(){
		for(int i=0 ; i<NUM_X ; i++){
			for(int j=0 ; j<NUM_Y ; j++){
				for(int k=0 ; k<NUM_Z ; k++){
					setSide(i, j, k);
				}
			}
		}
	}
	
	public void setSide(int i, int j, int k){
		Block b = blocks[i][j][k];
		if(b==null){
			return;
		}
		b.setSide(0, !exist(i, j+1, k ,true));
		b.setSide(2, !exist(i, j-1, k ,true));
		
		b.setSide(3, !exist(i+1, j, k ,true));
		b.setSide(1, !exist(i-1, j, k ,true));
		
		b.setSide(4, !exist(i, j, k+1 ,true));
		b.setSide(5, !exist(i, j, k-1 ,true));
		
		if(j==0)
			b.setSide(2, false);
		
		if(i==0 && neighboards[3] != null && neighboards[3].exist(NUM_X-1, j, k,true))
			b.setSide(1, false);
		if(i+1 == NUM_X && neighboards[1] != null && neighboards[1].exist(0, j, k,true))
			b.setSide(3, false);
		
		if(k==0 && neighboards[2] != null && neighboards[2].exist(i, j, NUM_Z-1,true))
			b.setSide(5, false);
		if(k+1 == NUM_Z && neighboards[0] != null && neighboards[0].exist(i, j, 0,true))
			b.setSide(4, false);
	}
	
	public Block getBlock(int i, int j, int k){
		return blocks[i][j][k];
	}
	
	public Block getBlock(GVector3f sur){
		return blocks[sur.getXi()][sur.getYi()][sur.getZi()];
	}
	
	public Block getTop(int i, int k){
		for(int j=NUM_Y-1 ; j>=0 ; j--){
			if(blocks[i][j][k] != null)
				return blocks[i][j][k];
		}
		return null;
	}
	
	private void setSideAround(int i, int j, int k){
		if(exist(i+1, j, k,true))
			setSide(i+1, j, k);
		if(exist(i-1, j, k,true))
			setSide(i-1, j, k);
		if(exist(i, j+1, k,true))
			setSide(i, j+1, k);
		if(exist(i, j-1, k,true))
			setSide(i, j-1, k);
		if(exist(i, j, k+1,true))
			setSide(i, j, k+1);
		if(exist(i, j, k-1,true))
			setSide(i, j, k-1);
		
		if(i==0 && neighboards[3] != null && neighboards[3].exist(NUM_X-1, j, k,true))
			neighboards[3].setSide(NUM_X-1, j, k);
		if(i+1 == NUM_X && neighboards[1] != null && neighboards[1].exist(0, j, k,true))
			neighboards[1].setSide(0, j, k);
		
		if(k==0 && neighboards[2] != null && neighboards[2].exist(i, j, NUM_Z-1,true))
			neighboards[2].setSide(i, j, NUM_Z-1);
		if(k+1 == NUM_Z && neighboards[0] != null && neighboards[0].exist(i, j, 0,true))
			neighboards[0].setSide(i, j,0);
	}
	
	public void remove(GVector3f sur){
//		if(!exist(sur.getXi(), sur.getYi(), sur.getZi(), false));
		blocks[sur.getXi()][sur.getYi()][sur.getZi()].remove();
		blocks[sur.getXi()][sur.getYi()][sur.getZi()] = null;
		setSideAround(sur.getXi(),sur.getYi(),sur.getZi());
	}
	
	public void setNeighboard(int i, Chunk3D n){
		neighboards[i] = n;
	}

	public void add(GVector3f sur, Block block) {
		if(!exist(sur.getXi(), sur.getYi(), sur.getZi(), false))
			return;
		
		if(sur.getXi() == NUM_X && neighboards[1] != null)
			neighboards[1].add(new GVector3f(0,sur.getY(),sur.getZ()), block);
		
		if(sur.getZi() == NUM_Z && neighboards[0] != null)
			neighboards[0].add(new GVector3f(sur.getX(),sur.getY(),0), block);
		
		if(sur.getX() == -1 && neighboards[3] != null)
			neighboards[3].add(new GVector3f(NUM_X-1,sur.getY(),sur.getZ()), block);
		
		if(sur.getZ() == -1 && neighboards[2] != null)
			neighboards[2].add(new GVector3f(sur.getX(),sur.getY(),NUM_Z-1), block);
			
		
		block.setPosition(getPosition().add(sur.mul(new GVector3f(Block.WIDTH, Block.HEIGHT, Block.DEPTH).mul(2))));
		blocks[sur.getXi()][sur.getYi()][sur.getZi()] = block;
		
		setNeighboards(sur.getXi(), sur.getYi(), sur.getZi());
		setNeighboardsAround(sur.getXi(), sur.getYi(), sur.getZi());
		
		setSideAround(sur.getXi(),sur.getYi(),sur.getZi());
		setSide(sur.getXi(),sur.getYi(),sur.getZi());
	}
}
