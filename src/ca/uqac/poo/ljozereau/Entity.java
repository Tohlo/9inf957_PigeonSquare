package ca.uqac.poo.ljozereau;

import java.util.Random;

public class Entity {
	static protected int nextId = 0;
	protected int size;
	static protected Random rand = new Random();
	
	final protected int id;
	protected Vector2D position; // Centre de la forme qui les représente
	
	public Entity() {
		id = nextId++;
		
		position = Vector2D.randVector();
	}
	
	public void update(long time) {
		
	}
	
	public Vector2D getPosition() { return position; }
	public void setPosition(Vector2D position) { this.position = position; }
	
	public int getSize() { return size; }
	public int getId() { return id; } 
}
