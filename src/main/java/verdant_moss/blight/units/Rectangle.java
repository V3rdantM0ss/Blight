package verdant_moss.blight.units;

public class Rectangle {
	public Point position;
	public Size size;
	
	public Rectangle(float x, float y, float width, float height) {
		position = new Point(x, y);
		size = new Size(width, height);
	}
	
	public Rectangle(Point position, Size size) {
		this.position = position;
		this.size = size;
	}
	
	public Rectangle() {
		position = new Point(0, 0);
		size = new Size(0, 0);
	}
	
	@Override
	public String toString() {
		return "Rectangle{" + "position=" + position + ", size=" + size + '}';
	}
	
	public float centerX() {
		return position.x + size.width / 2f;
	}
	
	public float centerY() {
		return position.y + size.height / 2f;
	}
	
	public boolean contains(Point point) {
		return contains(point.x, point.y);
	}
	
	public boolean contains(float x, float y) {
		return x >= left() && x < right() && y >= top() && y < bottom();
	}
	
	public float left() {
		return position.x;
	}
	
	public float right() {
		return position.x + size.width;
	}
	
	public float top() {
		return position.y;
	}
	
	public float bottom() {
		return position.y + size.height;
	}
	
	public boolean contains(Rectangle other) {
		return other.left() >= left() && other.right() <= right() && other.top() >= top() && other.bottom() <= bottom();
	}
	
	public boolean intersects(Rectangle other) {
		return other.left() < right() && other.right() > left() && other.top() < bottom() && other.bottom() > top();
	}
}
