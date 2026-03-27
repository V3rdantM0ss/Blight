package verdant_moss.blight.interfaces;

public interface MouseEvents {
	void mouseMoved(int x, int y);
	void mousePressed(int button, int x, int y);
	void mouseReleased(int button, int x, int y);
	void mouseScrolled(float valueX, float valueY);
}