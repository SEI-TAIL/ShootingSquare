package shooting;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

public class EnemySquare extends EnemyAbstract {
	private static final int SPEED = 5;
	private static final Color color = Color.blue;

	private Rectangle rect;
	private double angle;
	private double angularVelocity;

	public EnemySquare(int x, int y, int size) {
		int baseX = x - (size / 2);
		int baseY = y - (size / 2);
		this.rect = new Rectangle(baseX, baseY, size, size);
		this.angle = 0.0;
		this.angularVelocity = 2.0;
	}

	@Override
	public void damage() {
		this.rect.grow(-1, -1);
	}

	@Override
	public void move() {
		this.rect.translate(0, EnemySquare.SPEED);
		// TODO rotateを呼ぶ場所を考えなおす
		this.rotate();
	}

	@Override
	public void rotate() {
		this.angle += this.angularVelocity;
	}

	@Override
	public Area getArea() {
		Area area = new Area(this.rect);
		AffineTransform af = new AffineTransform();
		af.setToRotation(this.angle * Math.PI / 180, this.rect.getCenterX(), this.rect.getCenterY());
		area.transform(af);
		return area;
	}

	@Override
	public boolean isCollision(ICollidable other) {
		Area area = this.getArea();
		area.intersect(other.getArea());
		return area.isEmpty() == false;
	}

	@Override
	public Bullet shot() {
		return null;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
