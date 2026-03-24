package verdant_moss.blight;

import verdant_moss.blight.graphics.Assets;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameLogic;

public class TestGame implements GameLogic {
	private static final String MOLDY_TOMATO_LOCATION = "assets/verdant_moss/blight/moldy_tomato.png";
	private final BlightTest blight_test;
	private int x;
	
	public TestGame(BlightTest blightTest) {
		this.blight_test = blightTest;
	}
	
	@Override
	public void create() {
		Assets.Load(MOLDY_TOMATO_LOCATION);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(0, 255, 0, 255);
		g.rect(222, 100, 200, 200);
		g.setColor(255, 0, 0, 255);
		g.rect(x, 100, 200, 200);
		g.image(Assets.Get(MOLDY_TOMATO_LOCATION), 0, 0);
	}
	
	@Override
	public void update(int cycle) {
		x++;
	}
}
