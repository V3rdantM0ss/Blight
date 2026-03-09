package verdant_moss.blight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameLogic;

import static verdant_moss.blight.BlightTest.TEST_AURORA;

public class TestGame implements GameLogic {
	private final BlightTest blight_test;
	private int x;
	
	public TestGame(BlightTest blightTest) {
		this.blight_test = blightTest;
	}
	
	@Override
	public void create() {
	}
	
	@Override
	public void render(Graphics g) {
		g.renderRectangle(x, 100, 200, 200, 255, 0, 0);
	}
	
	@Override
	public void update(int cycle) {
		x++;
	}
}
