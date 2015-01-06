package GameEngine.rendering;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import GameEngine.core.Matrix4f;
import GameEngine.core.ResourceLoader;
import GameEngine.core.Transform;
import GameEngine.core.Util;
import GameEngine.core.Vector3f;

public class Shader {
	private int program;
	private HashMap<String, Integer>uniforms;
	
	public Shader(String fileName){
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		if(program == 0){
			System.err.println("Shader creation failed");
			System.exit(1);
		}
		addVertexShader(loadShader(fileName+".vs"));
		addFragmentShader(loadShader(fileName+".fs"));
	}
	
	public Shader(){
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		if(program == 0){
			System.err.println("Shader creation failed");
			System.exit(1);
		}
	}
	
	public void setAttribLocation(String AttributName,int location){
		glBindAttribLocation(program,location,AttributName);
	}
	
	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine){
		
	};
	
	public void bind(){
		glUseProgram(program);
	}
	
	public void addUniform(String uniform){
		int uniformLocation = glGetUniformLocation(program, uniform);
		if(uniformLocation == 0xFFFFFFFF){
			System.out.println("Error neviem najst uniform: "+uniform);
			System.exit(1);
		}
		uniforms.put(uniform, uniformLocation);
	}
	
	public void addAllAttributes(String shaderText){
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1) {
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length()+1;
			int end = shaderText.indexOf(";",begin);
			
			String attributeLine = shaderText.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(" ")+1, attributeLine.length());
			
			setAttribLocation(attributeName, attribNumber);
			attribNumber++;
//			addUniform(attributeName);
			
			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD,attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}
	
	public void addAllUniforms(String shaderText){
		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1) {
			int begin = uniformStartLocation + UNIFORM_KEYWORD.length()+1;
			int end = shaderText.indexOf(";",begin);
			
			String uniformLine = shaderText.substring(begin, end);
			String uniformName = uniformLine.substring(uniformLine.indexOf(" ")+1, uniformLine.length());
			
			addUniform(uniformName);
			
			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD,uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}
	
	public void addVertexShader(String text){
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	public void addGeometryShader(String text){
		addProgram(text, GL_GEOMETRY_SHADER);
	}

	public void addFragmentShader(String text){
		addProgram(text, GL_FRAGMENT_SHADER);
	}
	
	public void compileShader(){
		glLinkProgram(program);
		if(glGetProgrami(program,GL_LINK_STATUS)==GL_FALSE){
			System.err.println("TU TO BLBNE"+glGetProgramInfoLog(program,1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgrami(program,GL_VALIDATE_STATUS)==GL_FALSE){
			System.err.println("a� tu to blbne"+glGetProgramInfoLog(this.program,1024));
			System.exit(1);
		}
	}
	
	public void setUniformf(String uniformName, float value){
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3f value){
		glUniform3f(uniforms.get(uniformName), value.GetX(), value.GetY(), value.GetZ());
	}
	
	public void setUniform(String uniformName, Matrix4f value){
		glUniformMatrix4(uniforms.get(uniformName), true,Util.createFlippedBuffer(value));
	}
	
	public static String loadShader(String filename){
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";
		
		try{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + filename));
			String line;
			while((line = shaderReader.readLine()) != null){
				if(line.startsWith(INCLUDE_DIRECTIVE)){
					shaderSource.append(loadShader(line.substring(INCLUDE_DIRECTIVE.length()+2,line.length()-1)));
				}
				else
				shaderSource.append(line).append("\n");
			}
			shaderReader.close();
		}
		catch(Exception e){
			System.out.println(e);
			System.out.println("tuto to je");
			System.exit(1);
			System.out.println("aj tu");
		}
		return shaderSource.toString();
	}
	
	private void addProgram(String text, int type){
		int shader = glCreateShader(type);
		if(shader==0){
			System.err.println("Shader creation failed");
			System.exit(1);
		}
		glShaderSource(shader,text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader,GL_COMPILE_STATUS)==0){
			System.err.println("BLBNE TO"+glGetShaderInfoLog(shader,1024));
			System.exit(1);
		}
		
		//glAttachShader(shader,this.program);
		glAttachShader(program,shader);
	}
}
