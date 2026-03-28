package verdant_moss.blight.ui;

import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.units.Rectangle;

public abstract class Button {
	public static final int IDLE = 0;
	public static final int HOVER = 1;
	public static final int PRESSED = 2;
	protected boolean mouseHovering, mousePressed;
	protected Rectangle bounds;
	protected int state;
	
	public Button(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}
	
	public abstract void dispose();
	
	public void lostFocus() {
		mouseHovering = false;
		mousePressed = false;
	}
	
	public void mouseMoved(int x, int y) {
		mouseHovering = bounds.contains(x, y);
	}
	
	public void mousePressed(int mouseButton, int x, int y) {
		if(mouseButton == 0) {
			mousePressed = true;
			if(mouseHovering) {
				onClick();
			}
		}
	}
	
	public void mouseReleased(int mouseButton, int x, int y) {
		if(mouseHovering && mousePressed) {
			onRelease();
		}
		if(mouseButton == 0) {
			mousePressed = false;
		}
	}
	
	public abstract void onClick();
	public abstract void onRelease();
	public abstract void render(Graphics g);
	
	public void update(int cycle) {
		state = IDLE;
		if(mouseHovering) {
			state = HOVER;
		}
		if(mouseHovering && mousePressed) {
			state = PRESSED;
		}
	}
}
