package org.engine.ai;

import glib.util.vector.GVector3f;

import org.engine.component.GameComponent;
import org.engine.object.GameObject;

public class Follower extends GameComponent{
	private GameObject parent;
	private GameComponent target;

	private float speed = 0.1f;
	
	private boolean onGroung;
	
	//CONSTRUCTORS
	
	public Follower(GameObject parent, GameComponent target) {
		this.parent = parent;
	}
	
	//OVERRIDES
	
	@Override
	public void update() {
		if(target == null)
			return;

		GVector3f dirToTarget = target.getPosition().sub(parent.getPosition());
		
		if(onGroung)
			dirToTarget.setY(0);
		
		if(dirToTarget.getLength() < speed)
			if(onGroung)
				parent.setPosition(new GVector3f(target.getPosition().getX(), parent.getPosition().getY(), target.getPosition().getZ()));
			else
				parent.setPosition(target.getPosition());
		else
			parent.move(dirToTarget.Normalized().mul(speed));
		
	}

	//SETTERS
	
	public void setTarget(GameComponent target) {
		this.target = target;
	}

	public void setOnGroung(boolean onGroung) {
		this.onGroung = onGroung;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	} 
	
	//GETTERS
	
	public GameComponent getTarget() {
		return target;
	}
}