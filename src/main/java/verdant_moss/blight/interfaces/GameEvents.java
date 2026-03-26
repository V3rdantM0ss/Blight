package verdant_moss.blight.interfaces;

import verdant_moss.blight.graphics.Graphics;

public interface GameEvents {
	void create();
	void render(Graphics g);
	void update(int cycle);
}
