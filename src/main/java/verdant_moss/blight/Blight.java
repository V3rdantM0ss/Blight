package verdant_moss.blight;

import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;

public class Blight {
	public static final String BLIGHT_NAME = "Blight";
	public static final Version BLIGHT_VERSION = new Version(0,0,0,0, ReleaseType.ALPHA);
	public static final Color BLIGHT_COLOR = new Color(137, 77, 252);
	public static final Hollow BLIGHT_HOLLOW = new Hollow(BLIGHT_NAME, BLIGHT_VERSION, BLIGHT_COLOR);
	
	public Blight() {
		BLIGHT_HOLLOW.get_aurora().info("wawa :D");
	}
}
