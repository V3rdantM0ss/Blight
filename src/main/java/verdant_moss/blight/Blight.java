package verdant_moss.blight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.handler.LogicHandler;
import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;
import verdant_moss.hollow.aurora.Aurora;

public class Blight extends ApplicationAdapter {
	public static final String BLIGHT_NAME = "Blight";
	public static final Version BLIGHT_VERSION = new Version(0, 0, 1, 1, ReleaseType.ALPHA);
	public static final Color BLIGHT_COLOR = new Color(137, 77, 252);
	public static final Hollow BLIGHT_HOLLOW = new Hollow(BLIGHT_NAME, BLIGHT_VERSION, BLIGHT_COLOR);
	public static final Aurora BLIGHT_AURORA = BLIGHT_HOLLOW.get_aurora();
	private final int ups;
	private final double nanoPerUpdate;
	private final LogicHandler logicHandler;
	private String name;
	private int width, height;
	private long previousTime;
	private double deltaU = 0;
	private int cycle = 0;
	private Graphics graphics;
	
	public Blight(String name, int width, int height, int ups) {
		this.name = name;
		this.width = width;
		this.height = height;
		this.ups = ups;
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
			com.badlogic.gdx.Gdx.gl.glClearColor(0, 0, 0, 1f);
			com.badlogic.gdx.Gdx.gl.glClear(com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT);
			logicHandler.render(graphics);
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
		config.setWindowedMode(width, height);
		config.useVsync(true);
		BLIGHT_AURORA.system("Starting up Blight with window size: " + width + "x" + height + "; and ups: " + ups);
		new Lwjgl3Application(this, config);
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public String getName() {
		return name;
	}
	
	public int get_ups() {
		return ups;
	}
}