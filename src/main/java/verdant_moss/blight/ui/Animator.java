package verdant_moss.blight.ui;

import java.util.HashMap;
import java.util.Map;

public class Animator {
	public int frame;
	public int tick;
	public int state;
	private final int speed;
	private Map<Integer, Integer> maxFrames = new HashMap<>();
	
	public Animator(int speed) {
		this.speed = speed;
	}
	
	public void addState(int state, int maxFrames) {
		this.maxFrames.put(state, maxFrames);
	}
	
	public void update() {
		tick++;
		if(tick >= speed) {
			tick = 0;
			frame++;
			if(frame >= maxFrames.get(state)) {
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
