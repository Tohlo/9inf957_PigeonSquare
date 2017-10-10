package ca.uqac.poo.ljozereau;

import javax.swing.JFrame;

public class PigeonSquare {
	

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		long timeStart = 0;
		long timeEnd = 0;
		int framerate = 30;
		long frameMs = 1000/framerate;
		boolean running = true;
		
		GameWorld gameWorld = new GameWorld(10);
		createMainWindow(gameWorld);
		
		while (running) {
			timeStart = System.currentTimeMillis();
			gameWorld.update(frameMs);
			timeEnd = System.currentTimeMillis();
			
			try {
				Thread.currentThread().sleep(frameMs - (timeEnd - timeStart));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void createMainWindow(GameWorld gameWorld) {
		// Cr�ation de la fen�tre principale
		JFrame frame = new JFrame("Pigeon Square");
		
		// D�finition de l'op�ration de fermeture
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		frame.getContentPane().add(gameWorld);
		
		// Mise � la bonne taille et affichage
		frame.pack();
		frame.setVisible(true);
	}
}
