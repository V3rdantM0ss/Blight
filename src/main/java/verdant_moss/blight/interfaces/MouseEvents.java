package verdant_moss.blight.interfaces;

public interface MouseEvents {
	void mouseMoved(int x, int y);
	void mousePressed(int mouseButton, int x, int y);
	void mouseReleased(int mouseButton, int x, int y);
	void mouseScrolled(float valueX, float valueY);
}