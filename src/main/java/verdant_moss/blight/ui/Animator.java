package verdant_moss.blight.ui;

import java.util.HashMap;
import java.util.Map;

public class Animator {
	private final Map<Integer, Integer> max_frames = new HashMap<>();
	public int frame;
	public int tick;
	public int state;
	public int speed;
	
	public Animator(int speed) {
		this.speed = speed;
	}
	
	public void addState(int state, int maxFrames) {
		this.max_frames.put(state, maxFrames);
	}
	
	public void removeState(int state) {
		max_frames.remove(state);
	}
	
	public void update() {
		tick++;
		if(tick >= speed) {
			tick = 0;
			frame++;
			if(frame >= max_frames.get(state)) {
				frame = 0;
			}
		}
	}
	
	public void setState(int state) {
		this.state = state;
		frame = 0;
		tick = 0;
	}
}
