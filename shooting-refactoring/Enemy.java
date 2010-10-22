package shooting;

import java.awt.Rectangle;

public class Enemy implements ICollisionable, IEnemy, IShottable {
	private static final int SPEED = 5;

	private Rectangle area;

	public Enemy(int x, int y, int size, int kind) {
		int baseX = x - (size / 2);
		int baseY = y - (size / 2);
		this.area = new Rectangle(baseX, baseY, size, size);
	}

	public void damege() {
		this.area.grow(-1, -1);
	}

	public void move() {
		this.area.translate(0, Enemy.SPEED);
	}

	@Override
	public Rectangle getArea() {
		return this.area;
	}

	@Override
	public boolean isCollision(ICollisionable other) {
		return this.area.intersection(other.getArea()).isEmpty() == false;
	}

	@Override
	public Bullet shot() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
}
