package com.voxel.render.mesh;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource {
	private int vbo;
	private int ibo;
	private int size = 0;
	private int refCount;
	
	public MeshResource(int size){
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		refCount = 1;
	}
	
	@Override
	protected void finalize(){
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}

	public int getVbo() {
		return vbo;
	}
	
	public void addReference(){
		refCount++;
	}
	
	public boolean removeReference(){
		refCount--;
		return refCount == 0;
	}

	public int getIbo() {
		return ibo;
	}

	public int getSize() {
		return size;
	}

//	public void setSize(int size) {
//		this.size = size;
//	}
}
