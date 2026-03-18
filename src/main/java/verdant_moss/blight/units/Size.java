package verdant_moss.blight.units;

public class Size {
	public float width, height;
	
	public Size() {
	}
	
	public Size(float width, float height) {
		this.width = width;
		this.height = height;
	}
	
	public Size scale(float scale) {
		return new Size(width * scale, height * scale);
	}
}
