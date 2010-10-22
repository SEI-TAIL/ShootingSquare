package shooting;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.RepaintManager;

// TODO making super class of Bullet and Enemy

public class ShootingSquare extends JFrame implements Runnable, KeyListener {

	private static final long serialVersionUID = -6521027446732154114L;

	public static final int FRAME_SIZE = 400;
	private static final int WAIT = 1000 / 30;

	private final MainPanel mainPanel;
	private final Game game;

	private int score = 0;
	private int highScore = 0;

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
		readHighScore();
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

		writeHighScore();

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



	private void readHighScore() {
		try {
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
				 * DataOutputStream out = new DataOutputStream(new
				 * FileOutputStream("ShootingSquare.txt")); out.writeInt(score);
				 * out.close();
				 */
				FileWriter fileWr = new FileWriter("ShootingSquare.txt");
				fileWr.write(Integer.toString(score));
				fileWr.close();
			} catch (IOException e) {
				e.printStackTrace();
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
			Rectangle player = game.getPlayerArea();
			g.setColor(Color.RED);
			g.drawRect(player.x, player.y, player.width, player.height);

			g.setColor(Color.BLACK);
			for(Rectangle bullet : game.getBulletAreaList()) {
				g.drawRect(bullet.x, bullet.y, bullet.width, bullet.height);
			}

			g.setColor(Color.BLUE);
			for(Rectangle enemy : game.getEnemyAreaList()) {
				g.drawRect(enemy.x, enemy.y, enemy.width, enemy.height);
			}

			g.setColor(Color.BLACK);
			g.drawString(Integer.toString(score), 10, 10);
			g.drawString(Integer.toString(highScore), 350, 10);

			g.setColor(Color.BLACK);
			if (game.isGameOver()) {
				g.drawString("Game Over.", ShootingSquare.FRAME_SIZE / 2,
						ShootingSquare.FRAME_SIZE / 2);
			}
		}
	}
}
