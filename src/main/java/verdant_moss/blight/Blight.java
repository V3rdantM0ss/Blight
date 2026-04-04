package verdant_moss.blight;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.graphics.ResizingMode;
import verdant_moss.blight.handler.EventHandler;
import verdant_moss.blight.handler.InputHandler;
import verdant_moss.blight.units.Size;
import verdant_moss.hollow.Color;
import verdant_moss.hollow.Hollow;
import verdant_moss.hollow.ReleaseType;
import verdant_moss.hollow.Version;
import verdant_moss.hollow.aurora.Aurora;

public abstract class Blight extends ApplicationAdapter {
	public static final String BLIGHT_NAME = "Blight";
	public static final Version BLIGHT_VERSION = new Version(0, 0, 6, 0, ReleaseType.ALPHA);
	public static final Color BLIGHT_COLOR = new Color(137, 77, 252);
	public static final Hollow BLIGHT_HOLLOW = new Hollow(BLIGHT_NAME, BLIGHT_VERSION, BLIGHT_COLOR);
	public static final Aurora BLIGHT_AURORA = BLIGHT_HOLLOW.get_aurora();
	private final int ups;
	private final double nano_per_update;
	private final EventHandler event_handler;
	private final InputHandler input_handler;
	private String name;
	private Size internalSize, scaledInternalSize, windowSize;
	private long previousTime;
	private double deltaU = 0;
	private int cycle = 0;
	private Graphics graphics;
	private int scale;
	private ResizingMode resizing_mode = ResizingMode.NONE;
	private boolean resizable = true;
	
	public Blight(String name, int ups, Size internalSize, int scale) {
		this.name = name;
		this.ups = ups;
		this.internalSize = internalSize;
		this.scale = scale;
		this.scaledInternalSize = internalSize.scale(scale);
		this.windowSize = internalSize.scale(scale);
		event_handler = new EventHandler();
		input_handler = new InputHandler(this);
		nano_per_update = 1_000_000_000.0 / ups;
	}
	
	@Override
	public void create() {
		previousTime = System.nanoTime();
		event_handler.create();
		graphics = new Graphics(this);
		graphics.setResizingMode(resizing_mode);
		Gdx.graphics.setResizable(resizable);
		com.badlogic.gdx.Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
		if(fullScreen) {
			Gdx.graphics.setFullscreenMode(currentMode);
		} else {
			Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
		}
	}
	
	@Override
	public void resize(int width, int height) {
		if(windowSize == null) {
			windowSize = new Size(width, height);
		} else {
			windowSize.width = width;
			windowSize.height = height;
		}
		if(graphics != null) {
			graphics.resize(width, height);
		}
	}
	
	@Override
	public void render() {
		long now = System.nanoTime();
		long delta = now - previousTime;
		previousTime = now;
		deltaU += delta / nano_per_update;
		boolean updated = false;
		while(deltaU >= 1) {
			event_handler.update(cycle);
			input_handler.checkForInputEvents(graphics.getFboOffset());
			updated = true;
			cycle++;
			if(cycle >= ups) {
				cycle = 0;
			}
			deltaU--;
		}
		if(updated || (graphics != null)) {
			graphics.begin();
			event_handler.render(graphics);
			graphics.end();
		}
	}
	
	@Override
	public void pause() {
		event_handler.lostFocus();
	}
	
	@Override
	public void resume() {
		event_handler.gainedFocus();
	}
	
	@Override
	public void dispose() {
		BLIGHT_AURORA.system("Shutting down Blight :3");
		event_handler.abandonedFocus();
	}
	
	public void addLogic(Object obj) {
		event_handler.addLogic(obj);
	}
	
	public void start() {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle(name);
		config.setWindowedMode((int)windowSize.width, (int)windowSize.height);
		config.useVsync(true);
		BLIGHT_AURORA.system("Starting up Blight with window size: " + (int)windowSize.width + "x" + (int)windowSize.height + "; and ups: " + ups);
		new Lwjgl3Application(this, config);
	}
	
	public void stop() {
		Gdx.app.exit();
	}
	
	public boolean isResizable() {
		return resizable;
	}
	
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
		
		if(Gdx.graphics != null) {
			Gdx.graphics.setResizable(resizable);
		}
	}
	
	public int getScale() {
		return scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
		if(graphics != null) {
			graphics.markSizingDirty();
		}
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}
	
	public EventHandler get_event_handler() {
		return event_handler;
	}
	
	public Size getInternalSize() {
		return internalSize;
	}
	
	public void setInternalSize(Size internalSize) {
		if(internalSize == null) {
			return;
		}
		if(this.internalSize == null) {
			this.internalSize = internalSize;
		} else {
			this.internalSize.width = internalSize.width;
			this.internalSize.height = internalSize.height;
		}
		if(graphics != null) {
			graphics.markSizingDirty();
		}
	}
	
	public Size getWindowSize() {
		return windowSize;
	}
	
	public void setWindowSize(Size windowSize) {
		if(windowSize == null) {
			return;
		}
		if(this.windowSize == null) {
			this.windowSize = windowSize;
		} else {
			this.windowSize.width = windowSize.width;
			this.windowSize.height = windowSize.height;
		}
		if(graphics != null) {
			graphics.resize((int)this.windowSize.width, (int)this.windowSize.height);
			graphics.markSizingDirty();
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int get_ups() {
		return ups;
	}
	
	public ResizingMode getResizingMode() {
		return resizing_mode;
	}
	
	public void setResizingMode(ResizingMode resizing_mode) {
		this.resizing_mode = resizing_mode == null ? ResizingMode.NONE : resizing_mode;
		if(graphics != null) {
			graphics.setResizingMode(this.resizing_mode);
		}
	}
	private boolean fullScreen;
	public void setFullScreen(boolean fullscreen) {
		BLIGHT_AURORA.system("Setting fullscreen to: " + fullscreen);
		this.fullScreen=fullscreen;
		if(Gdx.graphics != null) {
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		}
	}
	
	public boolean isFullScreen() {
		return fullScreen;
	}
}