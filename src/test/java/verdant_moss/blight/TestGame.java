package verdant_moss.blight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import verdant_moss.blight.interfaces.GameLogic;

import static verdant_moss.blight.BlightTest.TEST_AURORA;

public class TestGame implements GameLogic {
	private final BlightTest blight_test;
	private ShapeRenderer shapeRenderer;
	private int x;
	
	public TestGame(BlightTest blightTest) {
		this.blight_test = blightTest;
	}
	
	@Override
	public void create() {
		shapeRenderer = new ShapeRenderer();
	}
	
	@Override
	public void render() {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(x, 100, 200, 200);
		shapeRenderer.end();
	}
	
	@Override
	public void update(int cycle) {
		x++;
		if(cycle == 0) {
			TEST_AURORA.debug("Hehe :3");
		}
	}
}
