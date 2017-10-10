package ca.uqac.poo.ljozereau;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameWorld extends JPanel implements MouseListener, MouseMotionListener {
	final static private Random rand = new Random();
	
	final static private Dimension dimension = new Dimension(1080, 720);
	public static Dimension getDimension() { return dimension; }
	
	final static private float borderSize = 10.0f;
	public static float getBorderSize() { return borderSize; }
	
	final static private int maxFood = 50;
	
	private static Vector<Food> food = new Vector<>();
	public static Vector<Food> getFood() { return food; }
	
	private static Danger danger = null;
	public static Danger getDanger() { return danger; }
	
	private Vector<Pigeon> pigeons = new Vector<>();
	
	
	GameWorld(int nbPigeons) {
		super();
		setPreferredSize(dimension);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		for (int i = 0; i < nbPigeons; ++i) {
			pigeons.add(new Pigeon());
		}
	}
	
	void update(long time) {
		for (Pigeon pigeon : pigeons) {
			(new Thread(pigeon)).start();
		}
		
		for (Food food : GameWorld.food) {
			food.update(time);
		}
		
		if (danger == null) {
			if (rand.nextInt(300) == rand.nextInt(300)) {
				danger = new Danger();
			}
		}
		else {
			danger.update(time);
			
			if (danger.isDestructed()) {
				danger = null;
				for (Pigeon pigeon : pigeons) {
					pigeon.getBehavior().setTarget(null);
					pigeon.updateTarget(food);
				}
			}
		}
		
		repaint();
	}
	
	public void verifyCollision() {
		for (Pigeon pigeon : pigeons) {
			if (pigeon.getBehavior() != null && pigeon.getBehavior().getTarget() != null) {
				if (pigeon.getPosition().substract(pigeon.getBehavior().getTarget().getPosition()).norme() <= pigeon.getBehavior().getTarget().getSize() + pigeon.getSize()) {
					food.remove(pigeon.getBehavior().getTarget());
					
					for (Pigeon pigeon2 : pigeons) {
						if (pigeon2.getBehavior().getTarget() == pigeon.getBehavior().getTarget()) {
							pigeon2.getBehavior().setTarget(null);
						}
					}
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// On définit le fond vert pour rappeler le parc
		g.setColor(new Color(0, 153, 0));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// On affiche les pigeons
				g.setColor(Color.darkGray);
				for (Pigeon pigeon : pigeons) {
					g.fillOval((int) pigeon.getPosition().getX(), (int) pigeon.getPosition().getY(), pigeon.getSize(), pigeon.getSize());
					pigeon.updateTarget(food);
				}
		
		// On dessine les bords du terrain
		Graphics2D g1 = (Graphics2D) g;
		g1.setColor(Color.black);
		
		BasicStroke line = new BasicStroke(borderSize);
		g1.setStroke(line);
		
		g1.drawRect(0, 0, this.getWidth(), this.getHeight());
		
		// On affiche la bouffe
		for (int i = 0; i < food.size(); ++i) {
			if (food.get(i).isDestructed()) {
				food.remove(food.get(i--));
				continue;
			}
			if (food.get(i).isFresh()) {
				g.setColor(Color.yellow);
			}
			else {
				g.setColor(new Color(153, 153, 0));
			}

			g.fillRect((int) food.get(i).getPosition().getX(), (int) food.get(i).getPosition().getY(), food.get(i).getSize(), food.get(i).getSize());
		}

		verifyCollision();
		
		g.setColor(Color.white); 
		if (danger != null) {
			g.fillOval((int) danger.getPosition().getX(), (int) danger.getPosition().getY(), danger.getSize(), danger.getSize());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getX() > borderSize && e.getX() < dimension.getWidth() - borderSize
				&& e.getY() > borderSize && e.getY() < dimension.getHeight() - borderSize
				&& food.size() < maxFood) {
			food.add(new Food(new Vector2D(e.getX(), e.getY())));
			
			for (Pigeon pigeon : pigeons) {
				pigeon.updateTarget(food);
			}
			
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (e.getX() > borderSize && e.getX() < dimension.getWidth() - borderSize
				&& e.getY() > borderSize && e.getY() < dimension.getHeight() - borderSize
				&& food.size() < maxFood) {
			food.add(new Food(new Vector2D(e.getX(), e.getY())));
			
			for (Pigeon pigeon : pigeons) {
				pigeon.updateTarget(food);
			}
			
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
