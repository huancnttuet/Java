package main;

import org.lwjgl.util.vector.Vector3f;

public class Face {

	public Vector3f vertex = new Vector3f(); //indicie nie vertexy
	public Vector3f normal = new Vector3f();
	
	public Face(Vector3f vertex, Vector3f normal) {
		this.normal = normal;
		this.vertex = vertex;
	}

	public Vector3f getVertex() {
		return vertex;
	}

	public Vector3f getNormal() {
		return normal;
	}

}
