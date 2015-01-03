package terrains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entity;
import renderers.Renderer;
import shaders.StaticShader;
import main.Game;
import main.Loader;

public class Map {
//	private Block[][][] mapa;
	private HashMap<String,ArrayList<Block>> mapa;
	private int numX,numY,numZ;
	private Block[][] terrain;
	private Loader loader=null;
	
	public Map(int x, int y, int z){
		numX = x;
		numY = y;
		numZ = z;
		mapa = new HashMap<String,ArrayList<Block>>();
		//terrain = new Block[x][z];
	};
	
	public void initDefaultMap(Loader loader){
		if(this.loader==null){
			this.loader = loader;
		}
		initDefaultMap();
	}
	
	public void initDefaultMap(){
		Block.init(this.loader);
		int half = numY/2;
		int i,j,k;
		for(i=0 ; i<numX ; i++){
			for(k=0 ; k<numZ ; k++){
				int dist = half+((int)(Math.random()*numY/2)-numY/2/2);
				mapa.put(address(i,k), new ArrayList<Block>());
				for(j=0 ; j<dist ; j++){
					mapa.get(address(i,k)).add(new Block(i,j, k,1));
				}
			}
		}
//		for(i=0 ; i<numX ; i++){
//			for(k=0 ; k<numZ ; k++){
//				boolean ground = false;
//				int dist = half+((int)(Math.random()*numY/2)-numY/2/2);
//				for(j=0 ; j<dist ; j++){
//					mapa[i][j][k] = new Block(i,j, k,1);
//				}
//				for( ; j<numY ; j++){
//					mapa[i][j][k] = new Block(i,j, k);
//				}
//			}
//		}
		createTerrain();
	}

	public void draw(Renderer renderer, StaticShader shader) {
//		int pocet = 0;
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numZ ; j++){
				for(Block b:mapa.get(address(i,j))){
					if(Game.isLoading)
						return;
					if(isHide(i,j+1,b.getSurY())&&isHide(i+1,j,b.getSurY())&&isHide(i-1,j,b.getSurY())&&isHide(i,j,b.getSurY()+1)&&isHide(i,j,b.getSurY()-1)){
						continue;
					}
					renderer.render(b, shader);
				}
//				for(int k=0 ; k<numZ ; k++){
//					if(Game.isLoading)
//						return;
//					Block c = mapa[i][j][k];
//					if(c.getType()!=0){
//						if(isHide(i,j+1,k)&&isHide(i+1,j,k)&&isHide(i-1,j,k)&&isHide(i,j,k+1)&&isHide(i,j,k-1)){
//							continue;
//						}
//						//u,d,l,r,f,b
//						boolean[] faces = new boolean[6];
////						u=d=l=r=f=b=false;
////						if(isHide(i,j+1,k));
////							faces[0]=true;
////						if(isHide(i,j-1,k));
////							faces[1]=true;
////						if(isHide(i-1,j,k));
////							faces[2]=true;
////						if(isHide(i+1,j,k));
////							faces[3]=true;
////						if(isHide(i,j,k-1));
////							faces[4]=true;
////						if(isHide(i,j,k+1));
////							faces[5]=true;
//						renderer.render(c, shader);
////						pocet++;
//					}
//				}
			}
		}
//		System.out.println("vykreslilo to "+pocet+" kr�t");
	}
	
	public void createTerrain(){
		terrain = new Block[numX][numZ];
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numZ ; j++){
				for(Block b:mapa.get(address(i,j))){
					if(b.getType()!=0){
						terrain[i][j] = b;
						break;
					}
				}
//				for(int k=numY-1 ; k>=0 ; k-- ){
//					if(mapa[i][k][j].getType()!=0){
//						terrain[i][j] = mapa[i][k][j];
//						break;
//					}
//				}
			}
		}
	}
	
	public boolean exist(int x,int y,int z){
		if(x>0&&x<numX&&y>0&&y<numY&&z>0&&z<numZ){
			return true;
		}
		return false;
	}
	
	private boolean isHide(int x,int y,int z){
//		if(exist(x,y,z)&&mapa[x][y][z].getType()!=0){
//			return true;
//		}
		if(mapa.containsKey(address(x,z))&&mapa.get(address(x,z)).contains(y)){
			return true;
		}
		return false;
	}
	
	public String address(int x, int z){
		return String.valueOf(x)+"  "+String.valueOf(z);
	}
	
	public Block[][] getTerrain(){
		return terrain;
	}

	public String saveMap(){
		/*#S = Sizes
		 *#B = Blocks
		 * S numX numY numZ
		 * 
		 * B x y z type surX surY surZ
		 * B x y z type surX surY surZ
		 */
		String file="#S = Sizes \n#B = Blocks \n\n";
		file +="S "+numX+" "+numY+" "+numZ+"\n\n";
		
		for(int i=0 ; i<numX ; i++){
			for(int j=0 ; j<numY ; j++){
				for(Block b:mapa.get(new int[]{i,j})){
					file += "B "+b.getSurX()+" "+b.getSurY()+" "+b.getSurZ()+" "+b.getType()+"\n";
				}
			}
		}
		return file;
	}
	
	public void loadMap(File file){
		Game.isLoading = true;
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String line;
		
//		try {
//			while((line = reader.readLine())!=null){
//				if(line.startsWith("S")){
//					numX = Integer.parseInt(line.split(" ")[1]);
//					numY = Integer.parseInt(line.split(" ")[2]);
//					numZ = Integer.parseInt(line.split(" ")[3]);
//					mapa = new Block[numX][numY][numZ];
//				}
//				if(line.startsWith("B")){
//					String[] l = line.split(" "); 
//					Block block = new Block(Integer.parseInt(l[1]),Integer.parseInt(l[2]),Integer.parseInt(l[3]),Integer.parseInt(l[4]));
//					mapa[Integer.parseInt(l[1])][Integer.parseInt(l[2])][Integer.parseInt(l[3])] = block;
//				}
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		createTerrain();
		Game.isLoading = false;
	}

	public Block getMapa(int x, int y, int z) {
//		System.out.println(x+" "+y+" "+z);
//		System.out.println((mapa.containsKey(address(x,z))));
		
		if(mapa.containsKey(address(x,z))){
//			System.out.println(mapa.get(address(x,z)).get(y));
			Block b = mapa.get(address(x,z)).get(y);
			if(b!=null)
				return mapa.get(address(x,z)).get(y);
		}
		return null;
	}
	
	public ArrayList<Block> getStlp(int x,int z) {
		if(mapa.containsKey(address(x,z)))
			return mapa.get(address(x,z));
		return null;
	}
	

	public int getNumX() {
		return numX;
	}

	public int getNumY() {
		return numY;
	}

	public int getNumZ() {
		return numZ;
	}

	public void set(int i, int j, int k, Block block) {
		if(mapa.containsKey(address(i,k))){
			if(mapa.get(address(i,k)).contains(j)){
				if(block==null){
					mapa.get(address(i,k)).remove(j);
					return;
				}
				mapa.get(address(i,k)).set(j, block);
				return;
			}
			if(block!=null)
				mapa.get(address(i,k)).add(block);
		}
		
	}
}
