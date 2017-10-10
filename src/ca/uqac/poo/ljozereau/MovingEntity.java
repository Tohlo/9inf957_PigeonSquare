package ca.uqac.poo.ljozereau;

public class MovingEntity extends Entity {
	final protected int detectionRange = 10;
	public int getDetectionRange() { return detectionRange; }
	
	protected Vector2D speed;
	public Vector2D getSpeed() { return speed; }
	public void setSpeed(Vector2D speed) { this.speed = speed; }
	
	final protected double maxSpeed = 0.2;
	public double getMaxSpeed() { return maxSpeed; }
}
