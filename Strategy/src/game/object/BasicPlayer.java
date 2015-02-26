package game.object;

import org.lwjgl.input.Keyboard;

import game.components.Player;
import game.world.Block;
import game.world.World;
import glib.util.vector.GVector3f;

public class BasicPlayer extends GameObject{
	public BasicPlayer(GVector3f position) {
		super(position, 12);
	}

	protected World world;
	protected Camera camera;
	
	protected GVector3f dir = new GVector3f();
	
	public boolean rotate = false;
	public boolean move = false;
	
	protected int selectBlock = Block.GRASS;
	
	private int unlockMouseKey = Keyboard.KEY_K;
	private int lockMouseKey = Keyboard.KEY_L;
	
	private int forwardKey = Keyboard.KEY_W;
	private int backKey = Keyboard.KEY_S;
	private int leftKey = Keyboard.KEY_A;
	private int rightKey = Keyboard.KEY_D;
	
//	private int upKey = Keyboard.KEY_SPACE;
//	private int downKey = Keyboard.KEY_LSHIFT;
	
	private int jumpKey = Keyboard.KEY_SPACE;
	
	public boolean checkGround(){
		return (world.getBlock(getCamera().getPosition().add(new GVector3f(0,-Player.HEIGHT,0)))!=null);
	}
	
	public Block getFloor(){
		
		GVector3f pos = camera.getPosition().sub(new GVector3f(0,Player.HEIGHT+Block.HEIGHT*2,0));
		Block b = world.getBlock(pos);
//		stra�ne spomaluje hru
//		if(b == null)
//			b = world.getBlock(pos.add(new GVector3f(+Player.MIN_DIST_FROM_BLOCK,0,0)));
//		if(b == null)
//			b = world.getBlock(pos.add(new GVector3f(-Player.MIN_DIST_FROM_BLOCK,0,0)));
//		if(b == null)
//			b = world.getBlock(pos.add(new GVector3f(0,0,+Player.MIN_DIST_FROM_BLOCK)));
//		if(b == null)
//			b = world.getBlock(pos.add(new GVector3f(0,0,-Player.MIN_DIST_FROM_BLOCK)));
		return b;
	}
	
	public void move(GVector3f direction){
		GVector3f x = new GVector3f(dir.getX(), 0, 0);
		GVector3f z = new GVector3f(0, 0, dir.getZ());
		GVector3f pos = getCamera().getPosition();
		if(!x.isNull() && world.getBlock(pos.add(x).add(new GVector3f(Player.MIN_DIST_FROM_BLOCK,0,0)))==null &&
						  world.getBlock(pos.add(x).sub(new GVector3f(Player.MIN_DIST_FROM_BLOCK,0,0)))==null &&
						  world.getBlock(pos.add(x).add(new GVector3f(Player.MIN_DIST_FROM_BLOCK,-Player.HEIGHT,0)))==null &&
						  world.getBlock(pos.add(x).sub(new GVector3f(Player.MIN_DIST_FROM_BLOCK,+Player.HEIGHT,0)))==null){
			camera.move(x);
			move = true;
		}
		
		if(!z.isNull() && world.getBlock(pos.add(z).add(new GVector3f(0,0,Player.MIN_DIST_FROM_BLOCK)))==null && 
						  world.getBlock(pos.add(z).sub(new GVector3f(0,0,Player.MIN_DIST_FROM_BLOCK)))==null &&
						  world.getBlock(pos.add(z).add(new GVector3f(0,-Player.HEIGHT,Player.MIN_DIST_FROM_BLOCK)))==null && 
						  world.getBlock(pos.add(z).sub(new GVector3f(0,+Player.HEIGHT,Player.MIN_DIST_FROM_BLOCK)))==null){
			camera.move(z);
			move = true;
		}
	}
	
	public void input(){
		
		/*	MOVING KEYS
		 * 	W, A, S, D
		 */
		
		if(Keyboard.isKeyDown(forwardKey)){
			dir = dir.add(camera.getForwardVector());
		}
		
		if(Keyboard.isKeyDown(backKey)){
			dir = dir.add(camera.getBackVector());
		}
		
		if(Keyboard.isKeyDown(leftKey)){
			dir = dir.add(camera.getLeftVector());
		}
		
		if(Keyboard.isKeyDown(rightKey)){
			dir = dir.add(camera.getRightVector());
		}
		
//		if(Keyboard.isKeyDown(upKey)){
//			camera.goUp();
//			move = true;
//		}
		
//		if(Keyboard.isKeyDown(downKey)){
//			camera.goDown();
//			move = true;
//		}
		
		/*	BLOCK SELECTOR
		 *	1-9  
		 */
		
		for(int i=2; i<11 ; i++){
			if(Keyboard.isKeyDown(i)){
				selectBlock = i-1;
			}
		}
		
		
		/*	JUMPING
		 *  SPACE
		 */
		
		
		if(Keyboard.isKeyDown(jumpKey) && dir.getY()==0){
			dir = dir.add(new GVector3f(0,Player.JUMP_STRENG,0));
			move = true;
		}
		
		/*	MOUSE LOCK & UNLOCK
		 * 	M, N
		 */
		
		if(Keyboard.isKeyDown(lockMouseKey)){
			camera.lockMouse();
		}
		
		if(Keyboard.isKeyDown(unlockMouseKey)){
			camera.unlockMouse();
		}
		
		
		
		if(camera.isMouseLocked()){
			rotate = camera.mouseMove();
		}
		
		if(move || rotate){
			camera.updateForward();
			move = rotate = false;
		}
	}

	
	public World getWorld() {
		return world;
	}

	
	public Camera getCamera() {
		return camera;
	}

	public int getSelectBlock() {
		return selectBlock;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}
