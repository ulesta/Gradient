import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements KeyListener, Runnable {
	
	public static int WIDTH = 400;
	public static int HEIGHT = 400;
	private Graphics2D g;
	private BufferedImage image;
	private boolean running;
	private Thread thread;
	
	private int FPS = 30;
	private double avgFPS;
	private double expectedTime = 1000.0 / 30;
	
	
	public GamePanel() {
		super();
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
	}

	public void addNotify(){
		super.addNotify();
		if (thread == null){
			thread = new Thread(this);
			thread.start();
		}
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		init();
		
		long startTime;
		double URDTimeMillis;
		double sleepTime;
		long totalTime = 0;
		int frames = 0;
		
		running = true;
		while(true) {
			startTime = System.nanoTime();
			update();
			render();
			int[] c1 = {255,255,255};
			int[] c2 = {255,100,255};
			fillGradient(20, 20, WIDTH/2, HEIGHT/2, c1, c2, g);
			draw();
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			if ( URDTimeMillis < expectedTime ) {				// sleep if the URD finished
				sleepTime = expectedTime - URDTimeMillis;
				try {
						Thread.sleep((long)sleepTime);
						System.out.println((long)sleepTime);
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
			}
			totalTime += System.nanoTime() - startTime;
			frames++;
			if (frames == FPS) {								// calculate average fps
				avgFPS = 1000.0 / ((totalTime / FPS) / 1000000);
				frames = 0;
				totalTime = 0;
			}
			
		}
	}
	
	public void init() {
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
	}

	
	public void update() {
		
	}
	
	public void render() {
		g.setColor(Color.blue);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		g.drawString("Average FPS: " + avgFPS, 10, 20);
		g.setColor(Color.white);
	}


	public void draw() {
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void fillGradient(int startX, int startY, int endX, int endY, int[] c1, int[] c2, Graphics2D g) {
		int r1 = c1[0], g1 = c1[1], b1 = c1[2];
		int r2 = c2[0], g2 = c2[1], b2 = c2[2];
		
		for (int i = startY; i < endY; i++) {
			double ratio = i / (double)endY;
			System.out.println("Ratio: " + ratio);
			g.setColor(new Color(
						(int) (r1 + (r2 * ratio) - (r1*ratio)),
						(int) (g1 + (g2 * ratio) - (g1*ratio)),
						(int) (b1 + (b2 * ratio) - (b1*ratio))
					));
			g.fillRect(startX, i, endX, 1);
		}
		
	}
	
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
