package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{

	private static final String VERTEX_FILE = "vertexShader.txt";
	private static final String FRAGMENT_FILE = "fragmentShader.txt";
	
	private int location_transformationMatrix;
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		
	}

	@Override
	protected void getAllUniformsLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(location_transformationMatrix, matrix);
	}

}
