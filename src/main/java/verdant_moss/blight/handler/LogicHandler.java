package verdant_moss.blight.handler;

import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.interfaces.GameLogic;
import verdant_moss.blight.interfaces.WindowLogic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class LogicHandler implements GameLogic, WindowLogic {
	private final List<GameLogic> game_logic_list = new CopyOnWriteArrayList<>();
	private final List<WindowLogic> window_logic_list = new CopyOnWriteArrayList<>();
	
	public LogicHandler() {
	}
	
	@Override
	public void create() {
		for(GameLogic gl : game_logic_list) {
			gl.create();
		}
	}
	
	@Override
	public void render(Graphics g) {
		for(GameLogic gl : game_logic_list) {
			gl.render(g);
		}
	}
	
	@Override
	public void update(int cycle) {
		for(GameLogic gl : game_logic_list) {
			gl.update(cycle);
		}
	}
	
	@Override
	public void lostFocus() {
		for(WindowLogic wl : window_logic_list) {
			wl.lostFocus();
		}
	}
	
	@Override
	public void gainedFocus() {
		for(WindowLogic wl : window_logic_list) {
			wl.gainedFocus();
		}
	}
	
	@Override
	public void abandonedFocus() {
		for(WindowLogic wl : window_logic_list) {
			wl.abandonedFocus();
		}
	}
	
	public void addLogic(Object obj) {
		boolean added = false;
		StringBuilder addedInterfaces = new StringBuilder();
		if(obj instanceof GameLogic gl) {
			game_logic_list.add(gl);
			added = true;
			addedInterfaces.append("GameLogic");
		}
		if(obj instanceof WindowLogic wl) {
			window_logic_list.add(wl);
			added = true;
			if(!addedInterfaces.isEmpty()) {
				addedInterfaces.append(", ");
			}
			addedInterfaces.append("WindowLogic");
		}
		if(added) {
			BLIGHT_AURORA.trace("Added object " + obj.getClass().getSimpleName() + " with interfaces: " + addedInterfaces);
		} else {
			BLIGHT_AURORA.warning("Warning: object " + obj.getClass().getSimpleName() + " does not implement GameLogic" +
					" " + "or" + " WindowLogic");
		}
	}
}
