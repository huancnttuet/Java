package glib.util.vector;

public class GVector4f {
	private float x;
	private float y;
	private float z;
	private float w;
	
	public GVector4f(double x, double y, double z, double w) {
		this((float)x, (float)y, (float)z, (float)w);
	}
	
	public GVector4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	public void addToX(float value){ x += value;}
	public void addToY(float value){ y += value;}
	public void addToZ(float value){ z += value;}
	public void addToW(float value){ w += value;}
	
	public float getX() {return x;}
	public float getY() {return y;}
	public float getZ() {return z;}
	public float getW() {return w;}
	
	public int getXi() {return (int)x;}
	public int getYi() {return (int)y;}
	public int getZi() {return (int)z;}
	public int getWi() {return (int)w;}

	public void setX(float x) {this.x = x;}
	public void setY(float y) {this.y = y;}
	public void setZ(float z) {this.z = z;}
	public void setW(float w) {this.w = w;}
	
	public String toString(){
		return "(" + x + " " + y + " " + z + " " + w + ")";
	}
	
}
