package dev.larndt.rpg.utilities;

public class Timer {
	private long 	now;
	private long 	lastTime;
	private int 	delta;
	
	public Timer(int milliseconds) {
		lastTime = System.currentTimeMillis();
		delta = milliseconds;
	}
	
	public boolean check() {
		now = System.currentTimeMillis();
		
		if((now - lastTime) > delta) {
			lastTime = now;
			return true;
		} else {
			return false;
		}
	}

}
