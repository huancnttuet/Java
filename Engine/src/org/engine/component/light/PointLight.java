package org.engine.component.light;

import glib.util.vector.GVector3f;

public class PointLight extends BasicLight{
	private GVector3f attenuation;
	private float range;
	
	//CONSTRUCTORS
	
	public PointLight(GVector3f position) {
		this(position, new GVector3f(1), new GVector3f(1,0,0));
	}
	
	public PointLight(GVector3f position, GVector3f color){
		this(position, color, new GVector3f(1,0,0));
	}
	
	
	public PointLight(GVector3f position, GVector3f color, GVector3f attenuation) {
		super(position, color);
		this.attenuation = attenuation;
		this.range = calcRange(attenuation);
	}

	//OTHERS
	
	private float calcRange(GVector3f a){
		return (float)(-a.getY() + Math.sqrt(a.getY() * a.getY() -4 * a.getX() * a.getZ())) / 2 * a.getX();
	}

	//GETTERS
	
	public GVector3f getColor() {
		return color;
	}

	public GVector3f getAttenuation() {
		return attenuation;
	}

	public float getRange() {
		return range;
	}
	
	//SETTERS

	public void setColor(GVector3f color) {
		setChange(true);
		this.color = color;
	}

	public void setAttenuation(GVector3f attenuation) {
		setChange(true);
		this.attenuation = attenuation;
		this.range = calcRange(attenuation);
	}
}