package shooting;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class Bullet implements ICollidable, IBullet, IColor {
	public final static int SIZE = 5;
	public final static int SPEED = 8;
	private final static Color color = Color.black;
	private Rectangle rect;

	public Bullet(int x, int y) {
		int baseX = x - (SIZE / 2);
		int baseY = y - (SIZE / 2);
		this.rect = new Rectangle(baseX, baseY, SIZE, SIZE);
	}

	public void move() {
		this.rect.translate(0, -1 * SPEED);
	}

	@Override
	public boolean isCollision(ICollidable other) {
		return false;
	}

	@Override
	public Area getArea() {
		return new Area(rect);
	}

	@Override
	public Color getColor() {
		return color;
	}
}
