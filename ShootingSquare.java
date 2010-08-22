import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.awt.Point;
import java.io.*;
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
	private int score = 0;
	private int highScore = 0;
	private boolean isGameOver = false;
	
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
		readHighScore();
		while (!isGameOver) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			moveMe();
			moveBullet();
			shotMyBullet();
			if (this.makeEnemy(Time, enemySize)) {
				enemySize++;
			}
			moveEnemy();
			score++;
			Time++;
			collision();
			repaint();
			ExitGame();
		}
		
		writeHighScore();
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			ExitGame();
		}
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

	public void moveEnemy() {
		Enemy bufEnemy;
		for (Iterator<Enemy> ite = enemyList.iterator(); ite.hasNext() == true;) {
			bufEnemy = ite.next();
			bufEnemy.moveEnemy();
			if (bufEnemy.delCheckBullet() == true) {
				bufEnemy = null;
				ite.remove();
			}
		}
	}
	
	public void moveBullet() {
		Bullet bufBullet;
		for (Iterator<Bullet> ite = bulletList.iterator(); ite.hasNext() == true;) {
			bufBullet = ite.next();
			bufBullet.moveBullet();
			if (bufBullet.delCheckBullet() == true) {
				bufBullet = null;
				ite.remove();
			}
		}
	}
	
	public boolean makeEnemy(int Time, int enemySize) {
		if (Time % 20 == 0) {
			Random rnd = new Random();
			System.out.println(rnd.nextInt(ShootingSquare.FRAME_SIZE));
			Enemy bufEn = new Enemy(rnd.nextInt(ShootingSquare.FRAME_SIZE),
					0,
					enemySize,
					0);
			enemyList.add(bufEn);
			return true;
		}
		return false;
	}
	
	public void collision() {
		for (Iterator<Enemy> iteEnemy = enemyList.iterator(); iteEnemy.hasNext();) {
			Enemy bufEnemy = iteEnemy.next();
			Point bufEnemyPos = bufEnemy.getPos();
			for (Iterator<Bullet> iteBullet = bulletList.iterator(); iteBullet.hasNext();) {
				Bullet bufBullet = iteBullet.next();
				Point bufBulletPos = bufBullet.getPos();
				if (hitEnemyBullet(bufEnemy, bufEnemyPos, bufBulletPos)) {
					score++;
					bufBullet = null;
					bufEnemy.minimizeSize();
					iteBullet.remove();
				}
			}
			
			Point bufMyPos = me.getPos();
			if (hitEnemyMe(bufEnemy, bufEnemyPos, bufMyPos)) {
				isGameOver = true;
				System.out.println("Hit me");
			}
		}
	}

	private boolean hitEnemyMe(Enemy bufEnemy, Point bufEnemyPos, Point bufMyPos) {
		return bufMyPos.x + MySquare.SIZE >= bufEnemyPos.x &&
				bufMyPos.x <= bufEnemyPos.x + bufEnemy.getSize() &&
				bufMyPos.y + MySquare.SIZE >= bufEnemyPos.y &&
				bufMyPos.y <= bufEnemyPos.y + bufEnemy.getSize();
	}

	private boolean hitEnemyBullet(Enemy bufEnemy, Point bufEnemyPos,
			Point bufBulletPos) {
		return hitEnemyMe(bufEnemy, bufEnemyPos, bufBulletPos);
	}
	
	private void readHighScore() {
        try{
            FileReader fr = new FileReader("ShootingSquare.txt");
            BufferedReader br = new BufferedReader(fr);
        
            String tmp_str = br.readLine();
            System.out.println(tmp_str);
            highScore = Integer.parseInt(tmp_str);
            br.close();
        } catch (IOException e) {
        	e.printStackTrace();
        } catch (NumberFormatException e) {
        	e.printStackTrace();
        }
	}
	private void writeHighScore() {
		if (score > highScore) {
			System.out.println(score + " " + highScore);
			try {
				/*
		      DataOutputStream out = new DataOutputStream(new FileOutputStream("ShootingSquare.txt"));
		      out.writeInt(score);
		      out.close();
		      */
		        FileWriter fileWr = new FileWriter("ShootingSquare.txt");
		        fileWr.write(Integer.toString(score));
		        fileWr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void ExitGame() {
		if (keyInput.isEsc() == true) {
			System.exit(0);
		}
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
			g.setColor(Color.RED);
			g.drawRect(bufMyPos.x, bufMyPos.y, MySquare.SIZE, MySquare.SIZE);
			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(score), 10, 10);
			g.drawString(Integer.toString(highScore), 350, 10);
			for (Iterator<Bullet> ite = bulletList.iterator(); ite.hasNext();) {
				Bullet bufBul = ite.next();
				Point bufPos = bufBul.getPos();
				g.drawRect(bufPos.x, bufPos.y, Bullet.SIZE, Bullet.SIZE);
			}
			g.setColor(Color.BLUE);
			for (Iterator<Enemy> ite = enemyList.iterator(); ite.hasNext();) {
				Enemy bufEnemy = ite.next();
				Point bufPos = bufEnemy.getPos();
				g.drawRect(bufPos.x, bufPos.y, bufEnemy.getSize(), bufEnemy.getSize());				
			}
			g.setColor(Color.BLACK);
			if (isGameOver == true) {
				g.drawString("Game Over.", ShootingSquare.FRAME_SIZE/2, ShootingSquare.FRAME_SIZE/2);
			}
		}
	}
}
