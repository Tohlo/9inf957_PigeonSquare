package ca.uqac.poo.ljozereau;

public class Food extends Entity {
	final static long timeLimit = 3000;
	final static long timeDestruction = 10000;
	
	public static long getTimeLimit() { return timeLimit; }
	public static long getTimeDestruction() { return timeDestruction; }
	
	private boolean fresh = true;
	private long time = 0;
	
	Food(Vector2D position) {
		super();
		size = 10;
		
		this.position = position.substract(new Vector2D(size/2, size/2));
	}

	public boolean isFresh() { return fresh; }
	public void setFresh(boolean fresh) { this.fresh = fresh; }
	
	public long getTime() { return time; }
	public void setTime(long time) { this.time = time; }
	public void incrementTime(long time) { 
		this.time += time; 
		
		fresh = this.time < timeLimit;
	}
	
	public boolean isDestructed() {
		return time >= timeDestruction;
	}
	
	public void update(long time) {
		incrementTime(time);
	}	
}
