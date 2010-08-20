import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

// TODO making super class of Bullet and Enemy

public class ShootingSquare extends JFrame implements Runnable, KeyListener {
	
	static final public int FRAME_SIZE = 400;
	final private MainPanel mainPanel = new MainPanel();
	
	private MySquare me = new MySquare(FRAME_SIZE/2, FRAME_SIZE/2);
	private KeyInput keyInput = new KeyInput();
	private List<Bullet> bulletList = new ArrayList<Bullet>();
	private List<Enemy> enemyList = new ArrayList<Enemy>();
	private int enemySize = 5;
	private int Time = 0;
	
	ShootingSquare() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(mainPanel);
		mainPanel.setVisible(true);
		this.addKeyListener(this);
		Thread threadShotSqr = new Thread(this);
		threadShotSqr.start();
	}
	
	public static void main(String[] args) {
		JFrame shotSqr = new ShootingSquare();
		shotSqr.setTitle("Shooting Square");
		shotSqr.setVisible(true);
		shotSqr.setSize(FRAME_SIZE, FRAME_SIZE);
	}

	@Override
	public void run() {
		while (!this.isEndGame()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			this.moveMe();
			this.moveBullet();
			this.shotMyBullet();
			this.Time++;
			this.repaint();
		}
		System.exit(0);
	}

	public void shotMyBullet() {
		if (keyInput.isZ()) {
			Point bufMyPos = me.getPos();
			int n = bulletList.size();
			Bullet bul = new Bullet(bufMyPos.x + MySquare.SIZE/2, bufMyPos.y + MySquare.SIZE/2, n);
			bulletList.add(bul);
		}
	}
	
	public void moveMe() {
		if (keyInput.isUp()) {
			me.moveMe(0, -1);
		}
		if (keyInput.isDown()) {
			me.moveMe(0, 1);
		}
		if (keyInput.isLeft()) {
			me.moveMe(-1, 0);
		}
		if (keyInput.isRight()) {
			me.moveMe(1, 0);
		}
	}

	/* TODO enemy moving and fadeout
	public void moveEnemy() {
		Enemy bufEnemy;
		for (Iterator<Enemy> ite = enemyList.iterator();
			 ite.hasNext() == true;) {
			bufEnemy = ite.next();
			bufEnemy.moveEnemy();
			if (bufEnemy.delCheckBullet() == true) {
				ite.remove();
			}
		}
	}
	*/
	
	public void moveBullet() {
		Bullet bufBullet;
		for (Iterator<Bullet> ite = bulletList.iterator();
			 ite.hasNext() == true;) {
			bufBullet = ite.next();
			bufBullet.moveBullet();
			if (bufBullet.delCheckBullet() == true) {
				ite.remove();
			}
		}
	}
	
	public void makeEnemy(int Time, int enemySize) {
		if (Time % 20 == 0) {
			Random rnd = new Random();
			Enemy bufEn = new Enemy(rnd.nextInt(ShootingSquare.FRAME_SIZE),
									rnd.nextInt(ShootingSquare.FRAME_SIZE),
									enemySize);
			enemyList.add(bufEn);
		}
	}
	
	public boolean isEndGame() {
		return keyInput.isEsc();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keyInput.Pressed(e.getKeyCode());
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keyInput.Released(e.getKeyCode());
	}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	class MainPanel extends JPanel {
		MainPanel() {
			RepaintManager.currentManager(this).setDoubleBufferingEnabled(true);
			setOpaque(true);
			setVisible(false);
		}
		
		public void paintComponent(Graphics g) {
			Point bufMyPos = me.getPos();
			g.setColor(Color.BLACK);
			g.drawOval(bufMyPos.x, bufMyPos.y, MySquare.SIZE, MySquare.SIZE);
			for (Iterator<Bullet> ite = bulletList.iterator();
				 ite.hasNext();) {
				Bullet bufBul = ite.next();
				Point bufPos = bufBul.getPos();
				g.drawRect(bufPos.x, bufPos.y, Bullet.SIZE, Bullet.SIZE);
			}
		}
	}

	
}
