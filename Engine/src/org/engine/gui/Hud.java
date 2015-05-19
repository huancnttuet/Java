package org.engine.gui;

import org.engine.component.GameComponent;
import org.engine.rendering.RenderingEngine;
import org.engine.rendering.material.Texture2D;
import org.engine.rendering.model.Model;
import org.engine.utils.Loader;
import org.engine.utils.Maths;

import glib.util.vector.GMatrix4f;
import glib.util.vector.GVector2f;


public class Hud extends GameComponent{
	private final static Model MODEL = Loader.loadToVAO(new float[]{-1,1,-1,-1,1,1,1,-1});
	private Texture2D texture;
	
	private GVector2f position;
	private GVector2f scale;
	
	//CONSTRUCTORS
	
	public Hud(Texture2D texture, GVector2f position, GVector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
	}
	
	//OTHERS
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.renderHud(this);
	}

	//GETTERS

	public GMatrix4f getTransformationMatrix() {
		return Maths.MatrixToGMatrix(Maths.createTransformationMatrix(position, scale));
	}

	public Model getModel(){
		return MODEL;
		
	}
	
	public Texture2D getTexture() {
		return texture;
	}

	//SETTERS
	
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}
}
