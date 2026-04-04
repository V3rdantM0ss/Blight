package verdant_moss.blight.handler;

import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameEvents;
import verdant_moss.blight.interfaces.KeyEvents;
import verdant_moss.blight.interfaces.MouseEvents;
import verdant_moss.blight.interfaces.WindowEvents;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class EventHandler implements GameEvents, WindowEvents, KeyEvents, MouseEvents {
	private final List<GameEvents> game_event_list = new CopyOnWriteArrayList<>();
	private final List<WindowEvents> window_event_list = new CopyOnWriteArrayList<>();
	private final List<KeyEvents> key_listeners = new CopyOnWriteArrayList<>();
	private final List<MouseEvents> mouse_listeners = new CopyOnWriteArrayList<>();
	
	public EventHandler() {
	}
	
	@Override
	public void create() {
		for(GameEvents ge : game_event_list) {
			ge.create();
		}
	}
	
	@Override
	public void render(Graphics g) {
		for(GameEvents ge : game_event_list) {
			ge.render(g);
		}
	}
	
	@Override
	public void update(int cycle) {
		for(GameEvents ge : game_event_list) {
			ge.update(cycle);
		}
	}
	
	@Override
	public void lostFocus() {
		for(WindowEvents we : window_event_list) {
			we.lostFocus();
		}
	}
	
	@Override
	public void gainedFocus() {
		for(WindowEvents we : window_event_list) {
			we.gainedFocus();
		}
	}
	
	@Override
	public void abandonedFocus() {
		for(WindowEvents we : window_event_list) {
			we.abandonedFocus();
		}
	}
	
	@Override
	public void keyPressed(int key) {
		for(KeyEvents ke : key_listeners) {
			ke.keyPressed(key);
		}
	}
	
	@Override
	public void keyReleased(int key) {
		for(KeyEvents ke : key_listeners) {
			ke.keyReleased(key);
		}
	}
	
	@Override
	public void mouseMoved(int x, int y) {
		for(MouseEvents me : mouse_listeners) {
			me.mouseMoved(x, y);
		}
	}
	
	@Override
	public void mousePressed(int mouseButton, int x, int y) {
		for(MouseEvents me : mouse_listeners) {
			me.mousePressed(mouseButton, x, y);
		}
	}
	
	@Override
	public void mouseReleased(int mouseButton, int x, int y) {
		for(MouseEvents me : mouse_listeners) {
			me.mouseReleased(mouseButton, x, y);
		}
	}
	
	@Override
	public void mouseScrolled(float valueX, float valueY) {
		for(MouseEvents me : mouse_listeners) {
			me.mouseScrolled(valueX, valueY);
		}
	}
	
	
	public void addLogic(Object obj) {
		boolean added = false;
		StringBuilder addedInterfaces = new StringBuilder();
		if(obj instanceof GameEvents ge) {
			game_event_list.add(ge);
			added = true;
			addedInterfaces.append("GameEvents");
		}
		if(obj instanceof WindowEvents we) {
			window_event_list.add(we);
			added = true;
			if(!addedInterfaces.isEmpty()) {
				addedInterfaces.append(", ");
			}
			addedInterfaces.append("WindowEvents");
		}
		if(obj instanceof KeyEvents ke) {
			key_listeners.add(ke);
			added = true;
			if(!addedInterfaces.isEmpty()) {
				addedInterfaces.append(", ");
			}
			addedInterfaces.append("KeyEvents");
		}
		if(obj instanceof MouseEvents me) {
			mouse_listeners.add(me);
			added = true;
			if(!addedInterfaces.isEmpty()) {
				addedInterfaces.append(", ");
			}
			addedInterfaces.append("MouseEvents");
		}
		if(added) {
			BLIGHT_AURORA.trace("Added object " + obj.getClass().getSimpleName() + " with interfaces: " + addedInterfaces);
		} else {
			BLIGHT_AURORA.warning("Warning: object " + obj.getClass().getSimpleName() + " does not implement any known" +
					" event interface");
		}
	}
}