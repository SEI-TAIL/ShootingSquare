package shooting;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
	public static Field FIELD;

	private final Player player;
	private final List<EnemyAbstract> enemyList;
	private final List<Bullet> bulletList;
	private final KeyInput keyInput;
	private final Score score;
	private int enemySize = 5;
	private int time = 0;
	private boolean gameOver;

	public Game(int width, int height) {
		FIELD = new Field(width, height);
		this.keyInput = new KeyInput();
		this.player = new Player();
		this.enemyList = new LinkedList<EnemyAbstract>();
		this.bulletList = new LinkedList<Bullet>();
		this.score = new Score();
		this.gameOver = false;
	}

	public void step() {
		this.movePlayer();
		this.moveBullet();
		this.shotBullet();
		this.sweepBullet();
		this.moveEnemy();
		this.sweepEnemy();
		this.callEnemy(time);
		this.bulletCollision();
		this.enemyColision();
		this.score.add(1);
		this.time += 1;
	}
	
	public int getScore() {
		return score.getScore();
	}
	
	public int getHighScore() {
		return score.getHighScore();
	}

	public void readHighScore(String fileName) {
		this.score.readHighScore(fileName);
	}

	public void writeHighScore(String fileName) {
		this.score.writeHighScore(fileName);
	}

	public boolean isGameOver() {
		return this.gameOver;
	}

	public boolean isExit() {
		return this.keyInput.isEsc();
	}

	public void keyDown(int keyCode) {
		keyInput.pressed(keyCode);
	}

	public void keyUp(int keyCode) {
		keyInput.released(keyCode);
	}

	public Shape getPlayerArea() {
		return this.player.getArea();
	}

	public List<DrawingData> getEnemyDrawingDataList() {
		List<DrawingData> drawingList = new ArrayList<DrawingData>();
		for(EnemyAbstract enemy : this.enemyList) {
			drawingList.add(new DrawingData(enemy.getColor(), enemy.getArea()));
		}
		return drawingList;
	}

	public List<DrawingData> getBulletDrawingDataList() {
		List<DrawingData> drawingList = new ArrayList<DrawingData>();
		for(Bullet bullet : this.bulletList) {
			drawingList.add(new DrawingData(bullet.getColor(), bullet.getArea()));
		}
		return drawingList;
	}

	private void movePlayer() {
		Point pos = new Point(0, 0);
		if (keyInput.isUp()) {
			pos.translate(0, -1);
		}
		if (keyInput.isDown()) {
			pos.translate(0, 1);
		}
		if (keyInput.isLeft()) {
			pos.translate(-1, 0);
		}
		if (keyInput.isRight()) {
			pos.translate(1, 0);
		}
		this.player.move(pos.x, pos.y);
	}

	private void shotBullet() {
		if (keyInput.isShoot()) {
			this.bulletList.add(this.player.shot());
		}
	}

	private void moveBullet() {
		for (Bullet bullet : bulletList) {
			bullet.move();
		}
	}

	private void sweepBullet() {
		List<Bullet> sweepList = new ArrayList<Bullet>();
		for (Bullet bullet : bulletList) {
			if(FIELD.isContains(bullet) == false) {
				sweepList.add(bullet);
			}
		}
		this.bulletList.removeAll(sweepList);
	}

	private void bulletCollision() {
		List<Bullet> sweepList = new ArrayList<Bullet>();
		for (EnemyAbstract enemy : enemyList) {
			for (Bullet bullet : bulletList) {
				if(enemy.isCollision(bullet)) {
					sweepList.add(bullet);
					this.score.add(1);
					enemy.damage();
				}
			}
		}
		this.bulletList.removeAll(sweepList);
	}

	private void moveEnemy() {
		for (EnemyAbstract enemy : enemyList) {
			enemy.move();
		}
	}

	private void sweepEnemy() {
		List<EnemyAbstract> sweepList = new ArrayList<EnemyAbstract>();
		for (EnemyAbstract enemy : this.enemyList) {
			boolean isContainField = FIELD.isContains(enemy);
			boolean isCollitionField = FIELD.isCollision(enemy);
			Rectangle area = enemy.getArea().getBounds();
			boolean isEmptyEnemy = area.width == 0 && area.height == 0;
			if((isContainField == false && isCollitionField == false) || isEmptyEnemy) {
				sweepList.add(enemy);
			}
		}
		this.enemyList.removeAll(sweepList);
	}

	private void callEnemy(int time) {
		boolean isPop = time % 20 == 0;
		if (isPop) {
			this.enemyList.add(this.makeEnemy());
		}
	}

	private EnemyAbstract makeEnemy() {
		Random rnd = new Random(System.currentTimeMillis());
		int kind = rnd.nextInt(2);
		EnemyAbstract enemy;
		if (kind == 0) {
			enemy = new EnemySquare(rnd.nextInt(FIELD.getWidth()), 0, enemySize);
		} else {
			enemy = new EnemyCircle(rnd.nextInt(FIELD.getWidth()), 0, enemySize);			
		}
		this.enemySize++;
		return enemy;
	}

	private void enemyColision() {
		for (EnemyAbstract enemy : enemyList) {
			if(enemy.isCollision(this.player)) {
				this.gameOver = true;
			}
		}
	}
}
