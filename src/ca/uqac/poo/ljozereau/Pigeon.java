package ca.uqac.poo.ljozereau;

import java.util.Vector;

public class Pigeon extends MovingEntity implements Runnable {	
	private SteeringBehavior behavior = new SteeringBehavior(this);
	public SteeringBehavior getBehavior() { return behavior; }
	
	Pigeon() {
		super();
		size = 20;
		
		position = new Vector2D(rand.nextInt((int) (GameWorld.getDimension().getWidth() - size - GameWorld.getBorderSize())) 
				+ size + GameWorld.getBorderSize(),
								rand.nextInt((int) (GameWorld.getDimension().getHeight() - size - GameWorld.getBorderSize())) 
				+ size + GameWorld.getBorderSize());

		speed = new Vector2D();
	}
	
	public void verifyPosition() {
		if (position.getX() < size + GameWorld.getBorderSize()) { 
			position.setX(size + GameWorld.getBorderSize()); 
		}
		else if (position.getX() > GameWorld.getDimension().getWidth() - size - GameWorld.getBorderSize()) { 
			position.setX(GameWorld.getDimension().getWidth() - size - GameWorld.getBorderSize()); 
		}
		
		if (position.getY() < size + GameWorld.getBorderSize()) { 
			position.setY(size + GameWorld.getBorderSize()); 
		}
		else if (position.getY() > GameWorld.getDimension().getHeight() - size - GameWorld.getBorderSize()) { 
			position.setY(GameWorld.getDimension().getHeight() - size - GameWorld.getBorderSize()); 
		}
	}
	
	public void move(long time) {
		position = position.add(speed.constMultiplier(time));
	}
	
	public void update(long time) {
		move(time);	
		verifyPosition();
		
		if (GameWorld.getDanger() != null) {
			speed = behavior.Flee();
			
			if (speed == new Vector2D() && !GameWorld.getFood().isEmpty()) {
				updateTarget(GameWorld.getFood());
				speed = behavior.Wander();
			}
		}
		else if (!GameWorld.getFood().isEmpty()) {
			updateTarget(GameWorld.getFood());
			speed = behavior.Wander();
		}
		else {
			speed = new Vector2D();
		}
		
		if (speed.norme() >= maxSpeed) {
			speed = speed.normalize().constMultiplier(maxSpeed);
		}
	}
	@Override
	public void run() {
		update(1000/30);
	}
	
	synchronized public void updateTarget(Vector<Food> food) {
		if (GameWorld.getDanger() != null && behavior.getTarget() != GameWorld.getDanger()) {
			behavior.setTarget(GameWorld.getDanger());
		}
		else if (food != null && !food.isEmpty()) {
			try {
				Food tmpFood = null;
				double tmpNorme = Double.POSITIVE_INFINITY;
				
				for (Food f : food) {
					if (f.isFresh() && position.substract(f.getPosition()).norme() < detectionRange * detectionRange) {
						if (position.substract(f.getPosition()).norme() < tmpNorme) {
							tmpFood = f;
							tmpNorme = position.substract(tmpFood.getPosition()).norme();					
						}
					}
				}
				
				if (tmpFood != null) {
					behavior.setTarget(tmpFood);
				}
			}
			catch (java.util.ConcurrentModificationException e) {
				
			}
		}
	}
}
