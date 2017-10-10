package ca.uqac.poo.ljozereau;

import java.util.Random;

public class Vector2D {
	static private Random rand = new Random();
	
	private double x;
	public double getX() { return x; }
	public void setX(double x) { this.x = x; }
	
	private double y;
	public double getY() { return y; }
	public void setY(double y) { this.y = y; }
	
	public Vector2D() {
		x = 0;
		y = 0;
	}
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;		
	}	
	
	public Vector2D add(Vector2D vector) {
		return new Vector2D(x + vector.getX(), y + vector.getY());
	}
	
	public Vector2D substract(Vector2D vector) {
		return new Vector2D(x - vector.getX(), y - vector.getY());
	}
	
	public Vector2D constMultiplier(double a) {
		return new Vector2D(a*x, a*y);
	}
	
	public double norme() {
		return Math.sqrt(x*x + y*y);
	}
	
	public Vector2D normalize() {
		return new Vector2D(x/norme(), y/norme());
	}
	
	public static Vector2D randNormalized() {
		double x = rand.nextFloat();
		double y = Math.sqrt(1 - x*x);
		
		if (rand.nextBoolean()) {
			x *= -1;
		}
		if (rand.nextBoolean()) {
			y *= -1;
		}
		
		return new Vector2D(x, y);
	}
	
	public static Vector2D randVector() {	
		return new Vector2D(rand.nextInt((int) (GameWorld.getDimension().getWidth() - GameWorld.getBorderSize())), 
							rand.nextInt((int) (GameWorld.getDimension().getHeight() - GameWorld.getBorderSize())));
	}
}
