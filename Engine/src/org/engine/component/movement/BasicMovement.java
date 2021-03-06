package org.engine.component.movement;

import java.util.HashMap;

import org.engine.app.GameAble;
import org.engine.component.GameComponent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import glib.util.vector.GVector2f;
import glib.util.vector.GVector3f;

public abstract class BasicMovement extends GameComponent{
	public final static String FORWARD = "forward";
	public final static String BACK = "back";
	public final static String LEFT = "left";
	public final static String RIGHT = "right";
	
	public final static String LOCK = "lock";
	public final static String UNLOCK = "unlock";
	
	public final static String TURN_RIGHT = "turnRight";
	public final static String TURN_LEFT = "turnLeft";
	
	public final static String UP = "up";
	public final static String DOWN = "down";
	
	protected HashMap<String, Integer> keys = new HashMap<String, Integer>(); 

//	protected int unlockMouseKey = Keyboard.KEY_K;
//	protected int lockMouseKey = Keyboard.KEY_L;
	
	protected boolean move;
	protected boolean rotate;

	protected GVector2f centerPosition = new GVector2f(Display.getWidth()/2,Display.getHeight()/2);

	protected boolean mouseLocked = false;
	

	private float moveSpeed = 0.3f;
	private float rotSpeed = 0.6f;
	private float zoomSpeed = 0.1f;
	
	public BasicMovement(GameAble parent, GVector3f speed) {
		super(parent);
		
		setDefaultKeys();
		
		moveSpeed = speed.getX();
		rotSpeed = speed.getY();
		zoomSpeed = speed.getZ();
	}
	
	private void setDefaultKeys(){
		keys.put(FORWARD, Keyboard.KEY_W);
		keys.put(BACK, Keyboard.KEY_S);
		keys.put(LEFT, Keyboard.KEY_A);
		keys.put(RIGHT, Keyboard.KEY_D);
		
		keys.put(UNLOCK, Keyboard.KEY_K);
		keys.put(LOCK, Keyboard.KEY_L);
		
		keys.put(TURN_RIGHT, Keyboard.KEY_E);
		keys.put(TURN_LEFT, Keyboard.KEY_Q);
	}
	
	protected void checkMouseLock(){
		if(Keyboard.isKeyDown(keys.get(LOCK))){
			lockMouse();
		}
		
		if(Keyboard.isKeyDown(keys.get(UNLOCK))){
			unlockMouse();
		}
	}
	
	private void lockMouse(){
		Mouse.setCursorPosition(centerPosition.getXi(),centerPosition.getYi());
		Mouse.setGrabbed(true);
		mouseLocked = true;
	}
	
	private void unlockMouse(){
		Mouse.setGrabbed(false);
		mouseLocked = false;
	}

	public float getRotSpeed() {return rotSpeed;}
	public float getMoveSpeed() {return moveSpeed;}
	public float getZoomSpeed() {return zoomSpeed;}
}
