package game.object;

import game.component.GameComponent;
import game.rendering.RenderingEngine;
import game.rendering.material.Material;
import game.rendering.model.Model;

public class GameObject extends GameComponent{
	protected Model model;
	protected Material material;

	public GameObject(Model model){
		this(model,new Material("texture.png"));
	}
	
	public GameObject(Model model, Material material) {
		super(GameComponent.GAME_OBJECT);
		this.model = model;
		this.material = material;
	}
	
	//OVERRIDES
	
	public void render(RenderingEngine renderingEngine) {
		renderingEngine.renderObject(this);
	}
	
	//GETTERS
	
	public Model getModel() {
		return model;
	}
	
	public Material getMaterial() {
		return material;
	}
}
