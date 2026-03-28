package verdant_moss.blight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import verdant_moss.blight.graphics.Assets;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.handler.EventHandler;
import verdant_moss.blight.units.Size;
import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;
import verdant_moss.hollow.aurora.Aurora;

public class Blight extends ApplicationAdapter {
	public static final String BLIGHT_NAME = "Blight";
	public static final Version BLIGHT_VERSION = new Version(0, 0, 4, 0, ReleaseType.ALPHA);
	public static final Color BLIGHT_COLOR = new Color(137, 77, 252);
	public static final Hollow BLIGHT_HOLLOW = new Hollow(BLIGHT_NAME, BLIGHT_VERSION, BLIGHT_COLOR);
	public static final Aurora BLIGHT_AURORA = BLIGHT_HOLLOW.get_aurora();
	private final int ups;
	private final double nanoPerUpdate;
	private final EventHandler eventHandler;
	private final boolean[] mouseButtonStates = new boolean[8];
	private final boolean[] keyStates = new boolean[256];
	private String name;
	private Size internalSize, windowSize;
	private long previousTime;
	private double deltaU = 0;
	private int cycle = 0;
	private Graphics graphics;
	private int scale;
	private float lastMouseX = -1;
	private float lastMouseY = -1;
	
	public Blight(String name, int ups, Size internalSize, int scale) {
		this.name = name;
		this.ups = ups;
		this.internalSize = internalSize;
		this.scale = scale;
		this.windowSize = internalSize.scale(scale);
		eventHandler = new EventHandler();
		nanoPerUpdate = 1_000_000_000.0 / ups;
	}
	
	@Override
	public void create() {
		previousTime = System.nanoTime();
		eventHandler.create();
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
			eventHandler.update(cycle);
			checkForInputEvents();
			updated = true;
			cycle++;
			if(cycle >= ups) {
				cycle = 0;
			}
			deltaU--;
		}
		if(updated) {
			graphics.begin();
			eventHandler.render(graphics);
			graphics.end();
		}
	}
	
	private void checkForInputEvents() {
		float rawX = com.badlogic.gdx.Gdx.input.getX();
		float rawY = com.badlogic.gdx.Gdx.input.getY();
		int scaledX = (int)(rawX / scale);
		int scaledY = (int)(rawY / scale);
		for(int i = 0; i < keyStates.length; i++) {
			boolean pressed = com.badlogic.gdx.Gdx.input.isKeyPressed(i);
			if(pressed && !keyStates[i]) {
				eventHandler.keyPressed(i);
			} else if(!pressed && keyStates[i]) {
				eventHandler.keyReleased(i);
			}
			keyStates[i] = pressed;
		}
		for(int button = 0; button <= 7; button++) {
			boolean pressed = com.badlogic.gdx.Gdx.input.isButtonPressed(button);
			if(pressed && !mouseButtonStates[button]) {
				eventHandler.mousePressed(button, scaledX, scaledY);
			} else if(!pressed && mouseButtonStates[button]) {
				eventHandler.mouseReleased(button, scaledX, scaledY);
			}
			mouseButtonStates[button] = pressed;
		}
		if(rawX != lastMouseX || rawY != lastMouseY) {
			eventHandler.mouseMoved(scaledX, scaledY);
			lastMouseX = rawX;
			lastMouseY = rawY;
		}
		float scrollX = com.badlogic.gdx.Gdx.input.getDeltaX();
		float scrollY = com.badlogic.gdx.Gdx.input.getDeltaY();
		if(scrollX != 0 || scrollY != 0) {
			eventHandler.mouseScrolled(scrollX, scrollY);
		}
	}
	
	@Override
	public void pause() {
		eventHandler.lostFocus();
	}
	
	@Override
	public void resume() {
		eventHandler.gainedFocus();
	}
	
	@Override
	public void dispose() {
		graphics.dispose();
		eventHandler.abandonedFocus();
		Assets.Dispose();
	}
	
	public void addLogic(Object obj) {
		eventHandler.addLogic(obj);
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
	
	public EventHandler get_logicHandler() {
		return eventHandler;
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