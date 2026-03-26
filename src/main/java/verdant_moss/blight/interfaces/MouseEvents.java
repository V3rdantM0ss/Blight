package verdant_moss.blight.interfaces;

public interface MouseEvents {
	void mouseMoved(float x, float y);
	void mousePressed(int button, float x, float y);
	void mouseReleased(int button, float x, float y);
	void mouseScrolled(float valueX, float valueY);
}