package GameEngine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

import java.util.ArrayList;

import GameEngine.components.BaseLight;
import GameEngine.components.DirectionalLight;
import GameEngine.components.PointLight;
import GameEngine.components.SpotLight;
import GameEngine.core.GameObject;
import GameEngine.core.Vector3f;

public class RenderingEngine {
	private Camera mainCamera;
	private Vector3f ambientLight;
	
	private ArrayList<BaseLight> lights;
	private BaseLight activeLight;
	
	public RenderingEngine(){
		lights = new ArrayList<BaseLight>();
		
		glClearColor(0.0f,0.0f,0.0f,0.0f);
		
		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		
		glEnable(GL_DEPTH_CLAMP);
		
		glEnable(GL_TEXTURE_2D);
		
		mainCamera = new Camera((float)Math.toRadians(70),(float)Window.getWidth()/(float)Window.getHeight(),0.1f,1000f);
		
		ambientLight = new Vector3f(0.2f,0.2f,0.2f);
		//glEnable(GL_FRAMEBUFFER_SRGB);
	}
	
	public void input(float delta){
		mainCamera.input(delta);
	}
	
	public void render(GameObject object){
		clearScreen();
		lights.clear();
		object.addToRenderingEngine(this);
		Shader forwardAmbient = ForwardAmbient.getInstance();
		forwardAmbient.setRenderingEngine(this);
		object.render(forwardAmbient);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE,GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(BaseLight light:lights){
			light.getShader().setRenderingEngine(this);
			activeLight = light;
			object.render(light.getShader());
		}
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	public void addLight(BaseLight light){
		lights.add(light);
	}
	
	public Vector3f getAmbientLight(){
		return ambientLight;
	}
	
	private static void clearScreen(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	private static void setTexture(boolean enabled){
		if(enabled){
			glEnable(GL_TEXTURE_2D);
		}
		else{
			glDisable(GL_TEXTURE_2D);
		}
	}
	
	public static String getOpenGLVersion(){
		return glGetString(GL_VERSION);
	}

	private static void unbindTextures() {
		glBindTexture(GL_TEXTURE_2D,0);
	}

	public static void setClearColor(Vector3f color) {
		glClearColor(color.GetX(),color.GetY(),color.GetZ(),1.0f);
	}
	
	public Camera getMainCamera() {
		return mainCamera;
	}
	
	public void setCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}
	
	public BaseLight getActiveLight(){
		return activeLight;
	}
}
