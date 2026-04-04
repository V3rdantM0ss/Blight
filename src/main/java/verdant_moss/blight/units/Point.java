package verdant_moss.blight.units;

public class Point {
	public float x, y;
	
	public Point() {
	}
	
	public Point(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Point{" + "x=" + x + ", y=" + y + '}';
	}
}
