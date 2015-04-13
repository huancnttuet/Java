package game;

import java.util.ArrayList;

import game.object.GameObject;
import game.physics.Collisions;
import game.physics.Enviroment;
import game.physics.colliders.BasicCollider;
import game.physics.colliders.SphereCollider;
import game.rendering.model.Model;
import glib.util.vector.GVector3f;

public class GameObjectPhysics extends GameObject{
	private static ArrayList<GameObjectPhysics> objects = new ArrayList<GameObjectPhysics>();
	
	private BasicCollider collider;
	private float odrazivost = 0.5f;
	private float bouncingLimit = 0.02f;
	private float weight = 1;
	private GVector3f direction = new GVector3f();
	private GVector3f rotSpeed = new GVector3f();
	
	//CONSTRUCTORS
	
	public GameObjectPhysics(Model model) {
		super(model);
		weight = (float)Math.random() * 2; //0 - 2
		setScale(new GVector3f((float)Math.random()+0.5f));	//0.5 - 1.5
		collider = new SphereCollider(this);
		
		objects.add(this);
	}
	
	//OVERRIDES
	
	public void update() {
		move(direction.mul(Enviroment.SPEED));
		rotate(rotSpeed);
		
		direction = direction.add(Enviroment.GRAVITY.mul(weight).mul(Enviroment.SPEED).mul(Enviroment.FRICTION));
		collider.checkBorders(true);
		
		for(GameObjectPhysics g : objects){
			if(g.equals(this))
				continue;
			GVector3f pos = g.getPosition();
			if(Collisions.SphereSphereCollision(pos, ((SphereCollider)g.getCollider()).getRadius(), getPosition(), ((SphereCollider)collider).getRadius())){
				GVector3f centerOfCollision = getPosition().add(pos).div(2);
				
//				g.setDirection(g.getDirection().Rotate((centerOfCollision.sub(pos)), 180).mul(g.getOdrazivost()));
//				direction = direction.Rotate((centerOfCollision.sub(getPosition())), 180).mul(odrazivost);
				
				rotSpeed = getDirection();
				g.setRotation(direction);
				
				g.setDirection((g.getDirection().sub(getPosition().sub(centerOfCollision))).mul(g.getOdrazivost()));
				direction = (direction.sub(pos.sub(centerOfCollision))).mul(odrazivost);
				
				
			}
		}
	}

	//OTHERS
	
	//GETTERS
	
	public BasicCollider getCollider() {
		return collider;
	}

	//SETTERS
	
	public void setCollider(BasicCollider collider) {
		this.collider = collider;
	}

	public void setDirection(GVector3f direction) {
		this.direction = direction;
	}

	public GVector3f getDirection() {
		return direction;
	}

	public float getOdrazivost() {
		return odrazivost;
	}

	public GVector3f getRotSpeed() {
		return rotSpeed;
	}

	public void setRotSpeed(GVector3f rotSpeed) {
		this.rotSpeed = rotSpeed;
	}
	
}
