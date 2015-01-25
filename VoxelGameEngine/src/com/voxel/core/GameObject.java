package com.voxel.core;

import java.util.ArrayList;

import com.voxel.component.GameComponent;
import com.voxel.render.RenderingEngine;
import com.voxel.render.shader.Shader;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private CoreEngine engine;
	
	public GameObject(){
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}
	
	public void addChild(GameObject child) {
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
	}
	
	public void setEngine(CoreEngine engine) {
		if(this.engine != engine){
			this.engine = engine;
			for(GameComponent component:components){
				component.addToEngine(engine);;
			}
			for(GameObject child:children){
				child.setEngine(engine);;
			}
		}
	}
	
	public ArrayList<GameObject> getAllAttached(){
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		
		for(GameObject child:children)
			result.addAll(child.getAllAttached());
		
		result.add(this);
		return result;
	}
	
	public GameObject addComponent(GameComponent component){
		component.setParent(this);
		components.add(component);
		return this;
	}
	
	public void inputAll(){
		input();
		for(GameObject child:children){
			child.inputAll();
		}
	}
	
	public void updateAll(float delta){
		update(delta);
		for(GameObject child:children){
			child.updateAll(delta);
		}
	}

	public void renderAll(Shader shader, RenderingEngine renderingEngine){
		render(shader,renderingEngine);
		for(GameObject child:children){
			child.renderAll(shader,renderingEngine);
		}
	}
	
	public void render(Shader shader, RenderingEngine renderingEngine){
		for(GameComponent component:components){
			component.render(shader,renderingEngine);
		}
	}

	public void input(){
		transform.update();
		for(GameComponent component:components){
			component.input();
		}
	}
	
	public void update(float delta){
		for(GameComponent component:components){
			component.update(delta);
		}
	}

	public Transform getTransform() {
		return transform;
	}

}
