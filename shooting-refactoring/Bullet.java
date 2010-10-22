package shooting;
import java.awt.Rectangle;

public class Bullet implements ICollisionable, IBullet {
	public final static int SIZE = 5;
	public final static int SPEED = 8;
	private Rectangle area;

	public Bullet(int x, int y) {
		int baseX = x - (SIZE / 2);
		int baseY = y - (SIZE / 2);
		this.area = new Rectangle(baseX, baseY, SIZE, SIZE);
	}

	public void move() {
		this.area.translate(0, -1 * SPEED);
	}

	@Override
	public boolean isCollision(ICollisionable other) {
		return this.area.intersection(other.getArea()).isEmpty() == false;
	}

	@Override
	public Rectangle getArea() {
		return this.area;
	}
}
