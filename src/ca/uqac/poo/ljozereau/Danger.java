package ca.uqac.poo.ljozereau;

public class Danger extends Entity {
	final private long timeLife = 1000; 
	private long time = 0;
	
	Danger() {
		size = 40;
		position = Vector2D.randVector();
	}
	
	public long getTime() { return time; }
	public void setTime(long time) { this.time = time; }
	public void incrementTime(long time) { 
		this.time += time; 
	}
	
	public boolean isDestructed() {
		return time >= timeLife;
	}
	
	public void update(long time) {
		incrementTime(time);
	}
}
