package verdant_moss.blight;

import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.ui.Button;
import verdant_moss.hollow.Color;

import static verdant_moss.blight.BlightTest.TEST_AURORA;

public class TestButton extends Button {
	public TestButton() {
		super(200, 50, 200, 200);
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void onClick() {
		TEST_AURORA.debug("LES GO!!!!!!!!!!!");
	}
	
	@Override
	public void onRelease() {
		TEST_AURORA.debug("Release");
	}
	
	@Override
	public void render(Graphics g) {
		switch(state) {
			case Button.IDLE -> {
				g.setColor(Color.GRAY);
			}
			case Button.HOVER -> {
				g.setColor(Color.YELLOW);
			}
			case Button.PRESSED -> {
				g.setColor(Color.RED);
			}
		}
		g.outlineRect(bounds);
	}
}
