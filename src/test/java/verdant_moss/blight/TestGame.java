package verdant_moss.blight;

import com.badlogic.gdx.Input;
import verdant_moss.blight.graphics.Assets;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameEvents;
import verdant_moss.blight.interfaces.KeyEvents;
import verdant_moss.blight.interfaces.MouseEvents;
import verdant_moss.blight.interfaces.WindowEvents;
import verdant_moss.blight.ui.TextButton;
import verdant_moss.blight.units.Rectangle;

import static verdant_moss.blight.BlightTest.TEST_AURORA;
import static verdant_moss.blight.graphics.Assets.NOTO_SANS_PATH;

public class TestGame implements GameEvents, KeyEvents, MouseEvents, WindowEvents {
	public static final String NOTO_SANS_MEDIUM_PATH = NOTO_SANS_PATH + "/NotoSans-Medium.ttf";
	private static final String MOLDY_TOMATO_LOCATION = "assets/verdant_moss/blight/moldy_tomato.png";
	private final BlightTest blight_test;
	private int x;
	private int xMouse, yMouse;
	private Rectangle greenRect;
	private Rectangle redRect;
	private TestButton button;
	private TextButton blegh;
	
	public TestGame(BlightTest blightTest) {
		this.blight_test = blightTest;
	}
	
	@Override
	public void create() {
		Assets.LoadTexture(MOLDY_TOMATO_LOCATION);
		Assets.LoadFont(NOTO_SANS_MEDIUM_PATH, 24);
		greenRect = new Rectangle(222, 256, 200, 200);
		redRect = new Rectangle(0, 256, 200, 200);
		button = new TestButton();
		blegh = new TextButton(52, 52, "GAYYYY2", Assets.GetFont(Assets.NOTO_SANS_MEDIUM, 24));
		blegh.setOnClick(() -> TEST_AURORA.debug("AAAAAAAAAAAAAAAAAA"));
	}
	
	@Override
	public void render(Graphics g) {
		redRect.position.x = x;
		boolean mouseInsideGreen = greenRect.contains(xMouse, yMouse);
		if(mouseInsideGreen) {
			g.setColor(128, 0, 128, 255);
		} else {
			g.setColor(0, 255, 0, 255);
		}
		g.outlineRect(greenRect.position.x, greenRect.position.y, greenRect.size.width, greenRect.size.height);
		g.setColor(255, 0, 0, 255);
		g.outlineRect(redRect.position.x, redRect.position.y, redRect.size.width, redRect.size.height);
		g.image(Assets.GetTexture(MOLDY_TOMATO_LOCATION), 0, 0);
		g.setFont(Assets.GetFont(NOTO_SANS_MEDIUM_PATH, 24));
		g.string("Hello World", 0, 64);
		button.render(g);
		blegh.render(g);
	}
	
	@Override
	public void update(int cycle) {
		x++;
		button.update(cycle);
		blegh.update(cycle);
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
			case Input.Keys.ESCAPE -> blight_test.stop();
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
	
	@Override
	public void mouseMoved(int x, int y) {
		xMouse = x;
		yMouse = y;
		button.mouseMoved(x, y);
		blegh.mouseMoved(x, y);
	}
	
	@Override
	public void mousePressed(int mouseButton, int x, int y) {
		button.mousePressed(mouseButton, x, y);
		blegh.mousePressed(mouseButton, x, y);
	}
	
	@Override
	public void mouseReleased(int mouseButton, int x, int y) {
		button.mouseReleased(mouseButton, x, y);
		blegh.mouseReleased(mouseButton, x, y);
	}
	
	@Override
	public void mouseScrolled(float valueX, float valueY) {
	}
	
	@Override
	public void lostFocus() {
	}
	
	@Override
	public void gainedFocus() {
	}
	
	@Override
	public void abandonedFocus() {
	}
}