package shooting;

import java.awt.Color;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class EnemyCircle extends EnemyAbstract {
	private static final int SPEED = 6;
	private static final Color color = Color.ORANGE;

	private Ellipse2D.Double oval;

	public EnemyCircle(int x, int y, int size) {
		int baseX = x - (size / 2);
		int baseY = y - (size / 2);
		this.oval = new Ellipse2D.Double(baseX, baseY, size, size);
	}
	
	@Override
	public void rotate() {
		
	}

	@Override
	public void damage() {
		this.grow(-1, -1);
	}

	private void grow(int dx, int dy) {
		this.oval.width += dx;
		this.oval.height += dy;
	}

	@Override
	public void move() {
		this.oval.x += 0;
		this.oval.y += EnemyCircle.SPEED;
	}

	@Override
	public Bullet shot() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Area getArea() {
		return new Area(oval);
	}

	@Override
	public boolean isCollision(ICollidable other) {
		return this.oval.intersects(other.getArea().getBounds());
	}

	@Override
	public Color getColor() {
		return color;
	}
}
