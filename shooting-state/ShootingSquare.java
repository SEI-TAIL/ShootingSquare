package shooting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

// TODO making super class of Bullet and Enemy

public class ShootingSquare extends JFrame implements Runnable, KeyListener {

	private static final long serialVersionUID = -6521027446732154114L;

	private static final int FRAME_SIZE = 400;
	private static final int WAIT = 1000 / 30;
	private static final String FILE_NAME = "ShootingSquare.txt";

	private final MainPanel mainPanel;
	public final Game game;
	public int test = 0;

	ShootingSquare(int width, int height) {
		this.mainPanel = new MainPanel();
		this.mainPanel.setVisible(true);
		this.getContentPane().add(mainPanel);
		this.getContentPane().setPreferredSize(new Dimension(width, height));
		this.pack();
		this.game = new Game(width, height);
		this.addKeyListener(this);
	}

	public static void main(String[] args) {
		JFrame shotSqr = new ShootingSquare(FRAME_SIZE, FRAME_SIZE);
		shotSqr.setTitle("Shooting Square");
		shotSqr.setResizable(false);
		shotSqr.setDefaultCloseOperation(EXIT_ON_CLOSE);
		shotSqr.setVisible(true);
		Thread threadShotSqr = new Thread((Runnable)shotSqr);
		threadShotSqr.start();
	}

	@Override
	public void run() {
		long nextFrame;
		game.readHighScore(FILE_NAME);
		nextFrame = System.currentTimeMillis();
		while (true) {
			if(game.isGameOver()) {
				break;
			}
			long elapsed = System.currentTimeMillis();
			if (elapsed >= nextFrame) {
				// 入力処理、タスク、描画、サウンド等の処理
				this.game.step();
				if(this.game.isExit()) {
					System.exit(0);
				}
				if (elapsed < nextFrame + WAIT) {
					this.repaint();
				}
				nextFrame += WAIT;
			}
		}

		game.writeHighScore(FILE_NAME);

		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
			if (this.game.isExit()) {
				System.exit(0);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		game.keyDown(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		game.keyUp(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	class MainPanel extends JPanel {

		private static final long serialVersionUID = -877184827460217757L;

		public MainPanel() {
			RepaintManager.currentManager(this).setDoubleBufferingEnabled(true);
			this.setOpaque(true);
			this.setVisible(false);
		}

		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			Shape player = game.getPlayerArea();
			g2.setColor(Color.RED);
			g2.draw(player);

			for(DrawingData bullet : game.getBulletDrawingDataList()) {
				g2.setColor(bullet.color);
				g2.draw(bullet.shape);
			}

			for(DrawingData enemy : game.getEnemyDrawingDataList()) {
				g2.setColor(enemy.color);
				g2.draw(enemy.shape);
			}

			g2.setColor(Color.BLACK);
			g2.drawString(Integer.toString(game.getScore()), 10, 10);
			g2.drawString(Integer.toString(game.getHighScore()), 350, 10);

			g2.setColor(Color.BLACK);
			if (game.isGameOver()) {
				g2.drawString("Game Over.", ShootingSquare.FRAME_SIZE / 2,
						ShootingSquare.FRAME_SIZE / 2);
			}
		}
	}
}