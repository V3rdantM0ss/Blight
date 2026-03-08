package verdant_moss.blight.handler;

import verdant_moss.blight.interfaces.GameLogic;
import verdant_moss.blight.interfaces.WindowLogic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
	public void render() {
		for(GameLogic gl : game_logic_list) {
			gl.render();
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
			System.out.println("Added object " + obj.getClass().getSimpleName() + " with interfaces: " + addedInterfaces);
		} else {
			System.out.println("Warning: object " + obj.getClass().getSimpleName() + " does not implement GameLogic " + "or" + " WindowLogic");
		}
	}
}
