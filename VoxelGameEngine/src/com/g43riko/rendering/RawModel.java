package com.g43riko.rendering;

public class RawModel {
	private int vaoID;
	private int vertexCount;
	
	public RawModel(int vao, int vertex){
		vaoID = vao;
		vertexCount = vertex;
	}

	public int getVaoID() {
		return vaoID;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
