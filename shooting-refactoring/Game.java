package shooting;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Game {
	public static Field FIELD;

	private final Player player;
	private final List<Enemy> enemyList;
	private final List<Bullet> bulletList;
	private final KeyInput keyInput;
	private int enemySize = 5;
	private int time = 0;
	private int score = 0;
	private boolean gameOver;

	public Game(int width, int height) {
		FIELD = new Field(width, height);
		this.keyInput = new KeyInput();
		this.player = new Player();
		this.enemyList = new LinkedList<Enemy>();
		this.bulletList = new LinkedList<Bullet>();
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
		this.score += 1;
		this.time += 1;
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

	public Rectangle getPlayerArea() {
		return this.player.getArea();
	}

	public List<Rectangle> getEnemyAreaList() {
		List<Rectangle> areaList = new ArrayList<Rectangle>();
		for(ICollisionable enemy : this.enemyList) {
			areaList.add(enemy.getArea());
		}
		return areaList;
	}

	public List<Rectangle> getBulletAreaList() {
		List<Rectangle> areaList = new ArrayList<Rectangle>();
		for(ICollisionable bullet : this.bulletList) {
			areaList.add(bullet.getArea());
		}
		return areaList;
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
		for (Enemy enemy : enemyList) {
			for (Bullet bullet : bulletList) {
				if(enemy.isCollision(bullet)) {
					enemy.damege();
				}
			}
		}
	}

	private void moveEnemy() {
		for (Enemy enemy : enemyList) {
			enemy.move();
		}
	}

	private void sweepEnemy() {
		List<Enemy> sweepList = new ArrayList<Enemy>();
		for (Enemy enemy : this.enemyList) {
			boolean isContainField = FIELD.isContains(enemy);
			boolean isCollitionField = FIELD.isCollision(enemy);
			Rectangle area = enemy.getArea();
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
			this.makeEnemy();
		}
	}

	private void makeEnemy() {
		Random rnd = new Random(System.currentTimeMillis());
		this.enemyList.add(new Enemy(rnd.nextInt(FIELD.getWidth()), 0, enemySize, 0));
		this.enemySize++;
	}

	private void enemyColision() {
		for (Enemy enemy : enemyList) {
			if(this.player.isCollision(enemy)) {
				this.gameOver = true;
			}
		}
	}
}
