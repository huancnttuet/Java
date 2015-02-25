#version 130

in vec3 position;
in vec2 textureCoords;

out vec2 pass_textureCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

void main(){
	gl_Position =  viewMatrix * transformationMatrix * vec4(position,1);
	pass_textureCoords = textureCoords;
}