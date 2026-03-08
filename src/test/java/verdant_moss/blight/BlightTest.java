package verdant_moss.blight;

import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;
import verdant_moss.hollow.aurora.Aurora;
import verdant_moss.hollow.aurora.LoggingLevel;

public class BlightTest extends Blight {
	public static final String TEST_NAME = "Blight Test";
	public static final Version TEST_VERSION = new Version(5868830, 4755992, 621, 4679326, ReleaseType.ALPHA);
	public static final Color TEST_COLOR = new Color(1, 73, 149);
	public static final Hollow TEST_HOLLOW = new Hollow(TEST_NAME, TEST_VERSION, TEST_COLOR);
	public static final Aurora TEST_AURORA = TEST_HOLLOW.get_aurora();
	public static final int TEST_WIDTH = 1280;
	public static final int TEST_HEIGHT = 720;
	public static final int UPS = 100;
	private TestGame testGame;
	
	public static void main(String[] args) {
		Aurora.MinLevel = LoggingLevel.TRACE;
		new BlightTest();
	}
	
	public BlightTest() {
		super(TEST_NAME, TEST_WIDTH, TEST_HEIGHT, UPS);
		testGame = new TestGame(this);
		addLogic(testGame);
		start();
	}
	
	@Override
	public void create() {
		super.create();
	}
}
