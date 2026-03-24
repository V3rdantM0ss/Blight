package verdant_moss.blight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.handler.LogicHandler;
import verdant_moss.blight.units.Size;
import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;
import verdant_moss.hollow.aurora.Aurora;

public class Blight extends ApplicationAdapter {
	public static final String BLIGHT_NAME = "Blight";
	public static final Version BLIGHT_VERSION = new Version(0, 0, 2, 1, ReleaseType.ALPHA);
	public static final Color BLIGHT_COLOR = new Color(137, 77, 252);
	public static final Hollow BLIGHT_HOLLOW = new Hollow(BLIGHT_NAME, BLIGHT_VERSION, BLIGHT_COLOR);
	public static final Aurora BLIGHT_AURORA = BLIGHT_HOLLOW.get_aurora();
	private final int ups;
	private final double nanoPerUpdate;
	private final LogicHandler logicHandler;
	private String name;
	private Size internalSize, windowSize;
	private long previousTime;
	private double deltaU = 0;
	private int cycle = 0;
	private Graphics graphics;
	private int scale;
	
	public Blight(String name, int ups, Size internalSize, int scale) {
		this.name = name;
		this.ups = ups;
		this.internalSize = internalSize;
		this.scale = scale;
		this.windowSize = internalSize.scale(scale);
		logicHandler = new LogicHandler();
		nanoPerUpdate = 1_000_000_000.0 / ups;
	}
	
	@Override
	public void create() {
		previousTime = System.nanoTime();
		logicHandler.create();
		graphics = new Graphics(this);
	}
	
	@Override
	public void render() {
		long now = System.nanoTime();
		long delta = now - previousTime;
		previousTime = now;
		deltaU += delta / nanoPerUpdate;
		boolean updated = false;
		while(deltaU >= 1) {
			logicHandler.update(cycle);
			cycle++;
			if(cycle >= ups) {
				cycle = 0;
			}
			deltaU--;
			updated = true;
		}
		if(updated) {
			graphics.begin();
			logicHandler.render(graphics);
			graphics.end();
		}
	}
	
	@Override
	public void pause() {
		logicHandler.lostFocus();
	}
	
	@Override
	public void resume() {
		logicHandler.gainedFocus();
	}
	
	@Override
	public void dispose() {
		graphics.dispose();
		logicHandler.abandonedFocus();
	}
	
	public void addLogic(Object obj) {
		logicHandler.addLogic(obj);
	}
	
	public void start() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(name);
		config.setWindowedMode((int)windowSize.width, (int)windowSize.height);
		config.useVsync(true);
		BLIGHT_AURORA.system("Starting up Blight with window size: " + (int)windowSize.width + "x" + (int)windowSize.height + "; and ups: " + ups);
		new Lwjgl3Application(this, config);
	}
	
	public int getScale() {
		return scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	public LogicHandler get_logicHandler() {
		return logicHandler;
	}
	
	public Size getInternalSize() {
		return internalSize;
	}
	
	public void setInternalSize(Size internalSize) {
		this.internalSize = internalSize;
	}
	
	public Size getWindowSize() {
		return windowSize;
	}
	
	public void setWindowSize(Size windowSize) {
		this.windowSize = windowSize;
	}
	
	public String getName() {
		return name;
	}
	
	public int get_ups() {
		return ups;
	}
}