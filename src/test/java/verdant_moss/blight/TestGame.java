package verdant_moss.blight;

import com.badlogic.gdx.Input;
import verdant_moss.blight.graphics.Assets;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameEvents;
import verdant_moss.blight.interfaces.KeyEvents;

import static verdant_moss.blight.BlightTest.TEST_AURORA;
import static verdant_moss.blight.graphics.Assets.NOTO_SANS_PATH;

public class TestGame implements GameEvents, KeyEvents {
	public static final String NOTO_SANS_MEDIUM_PATH = NOTO_SANS_PATH + "/NotoSans-Medium.ttf";
	private static final String MOLDY_TOMATO_LOCATION = "assets/verdant_moss/blight/moldy_tomato.png";
	private final BlightTest blight_test;
	private int x;
	
	public TestGame(BlightTest blightTest) {
		this.blight_test = blightTest;
	}
	
	@Override
	public void create() {
		Assets.LoadTexture(MOLDY_TOMATO_LOCATION);
		Assets.LoadFont(NOTO_SANS_MEDIUM_PATH, 24);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(0, 255, 0, 255);
		g.rect(222, 256, 200, 200);
		g.setColor(255, 0, 0, 255);
		g.rect(x, 256, 200, 200);
		g.image(Assets.GetTexture(MOLDY_TOMATO_LOCATION), 0, 0);
		g.setFont(Assets.GetFont(NOTO_SANS_MEDIUM_PATH, 24));
		g.drawString("Hello World", 0, 64);
	}
	
	@Override
	public void update(int cycle) {
		x++;
	}
	
	@Override
	public void keyPressed(int key) {
		switch(key) {
			case Input.Keys.W -> TEST_AURORA.info("Pressed W (up)");
			case Input.Keys.A -> TEST_AURORA.info("Pressed A (left)");
			case Input.Keys.S -> TEST_AURORA.info("Pressed S (down)");
			case Input.Keys.D -> TEST_AURORA.info("Pressed D (right)");
			case Input.Keys.SPACE -> TEST_AURORA.info("Pressed SPACE");
			case Input.Keys.Y -> TEST_AURORA.info("Pressed Y");
			case Input.Keys.Z -> TEST_AURORA.info("Pressed Z");
			default -> TEST_AURORA.info("Pressed key code: " + key);
		}
	}
	
	@Override
	public void keyReleased(int key) {
		switch(key) {
			case Input.Keys.W -> TEST_AURORA.info("Released W (up)");
			case Input.Keys.A -> TEST_AURORA.info("Released A (left)");
			case Input.Keys.S -> TEST_AURORA.info("Released S (down)");
			case Input.Keys.D -> TEST_AURORA.info("Released D (right)");
			case Input.Keys.SPACE -> TEST_AURORA.info("Released SPACE");
			case Input.Keys.Y -> TEST_AURORA.info("Released Y");
			case Input.Keys.Z -> TEST_AURORA.info("Released Z");
			default -> TEST_AURORA.info("Released key code: " + key);
		}
	}
}
