package ca.uqac.poo.ljozereau;

public class SteeringBehavior {
	private MovingEntity entity;
	
	private Entity target = null;
	public Entity getTarget() { return target; }
	public void setTarget(Entity target) { this.target = target; }
	public SteeringBehavior(MovingEntity entity) {		
		this.entity = entity;
	}
	
	public Vector2D Seek() {
		if (target != null) {
			Vector2D directionNormalized = target.getPosition().substract(entity.getPosition()).normalize();
			Vector2D speed = directionNormalized.constMultiplier(entity.getMaxSpeed());
			
			return speed;
		}
		return new Vector2D();
	}
	
	public Vector2D Flee() {
		try {
			if (target != null && target.getPosition().substract(entity.getPosition()).norme() < entity.getDetectionRange() * entity.getDetectionRange() + target.getSize()/2 * target.getSize()/2) {
				Vector2D directionNormalized = target.getPosition().substract(entity.getPosition()).normalize();
				Vector2D speed = directionNormalized.constMultiplier(entity.getMaxSpeed());
				
				return (new Vector2D().substract(speed));
			}
			return new Vector2D();
		}
		catch (java.lang.NullPointerException e) {
			return new Vector2D();
		}
	}
	
	public Vector2D Wander() {
		if (target == null) {
			target = new Entity();
		}
		
		return Seek();		
	}
}
