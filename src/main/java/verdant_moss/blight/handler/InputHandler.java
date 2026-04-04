package verdant_moss.blight.handler;

import verdant_moss.blight.Blight;
import verdant_moss.blight.units.Point;

public class InputHandler {
	private final Blight blight;
	private final boolean[] mouse_button_states = new boolean[8];
	private final boolean[] key_states = new boolean[256];
	private float lastMouseX = -1;
	private float lastMouseY = -1;
	
	public InputHandler(Blight blight) {
		this.blight = blight;
	}
	
	public void checkForInputEvents(Point fboOffset) {
		float rawX = com.badlogic.gdx.Gdx.input.getX() - fboOffset.x;
		float rawY = com.badlogic.gdx.Gdx.input.getY() - fboOffset.y;
		int scaledX = (int)(rawX / blight.getScale());
		int scaledY = (int)(rawY / blight.getScale());
		for(int i = 0; i < key_states.length; i++) {
			boolean pressed = com.badlogic.gdx.Gdx.input.isKeyPressed(i);
			if(pressed && !key_states[i]) {
				blight.get_event_handler().keyPressed(i);
			} else if(!pressed && key_states[i]) {
				blight.get_event_handler().keyReleased(i);
			}
			key_states[i] = pressed;
		}
		for(int button = 0; button <= 7; button++) {
			boolean pressed = com.badlogic.gdx.Gdx.input.isButtonPressed(button);
			if(pressed && !mouse_button_states[button]) {
				blight.get_event_handler().mousePressed(button, scaledX, scaledY);
			} else if(!pressed && mouse_button_states[button]) {
				blight.get_event_handler().mouseReleased(button, scaledX, scaledY);
			}
			mouse_button_states[button] = pressed;
		}
		if(rawX != lastMouseX || rawY != lastMouseY) {
			blight.get_event_handler().mouseMoved(scaledX, scaledY);
			lastMouseX = rawX;
			lastMouseY = rawY;
		}
		float scrollX = com.badlogic.gdx.Gdx.input.getDeltaX();
		float scrollY = com.badlogic.gdx.Gdx.input.getDeltaY();
		if(scrollX != 0 || scrollY != 0) {
			blight.get_event_handler().mouseScrolled(scrollX, scrollY);
		}
	}
}
