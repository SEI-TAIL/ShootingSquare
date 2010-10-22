package shooting;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Area;

public class Player implements ICollidable, IShottable, IColor{
	private final Rectangle area;
	private static final Color color = Color.red;
	public int size;
	public int speed;
	// TODO プレイヤー機は弾の撃ちわけを可能にするため、buttletTypeを持たせる事

	public Player() {
		this.size = 10;
		this.speed = 3;
		Rectangle fieldRect = Game.FIELD.getArea().getBounds();
		int centerX = (int)fieldRect.getCenterX();
		int centerY = (int)fieldRect.getCenterY();
		int baseX = centerX - (this.size / 2);
		int baseY = centerY - (this.size / 2);
		this.area = new Rectangle(baseX, baseY, this.size, this.size);
	}

	@Override
	public Area getArea() {
		return new Area(this.area);
	}

	@Override
	public boolean isCollision(ICollidable other) {
		return false;
	}

	@Override
	public Bullet shot() {
		int centerX = (int)this.area.getCenterX();
		int centerY = (int)this.area.getCenterY();
		return new Bullet(centerX, centerY);
	}

	public void move(int x, int y) {
		Rectangle rect = new Rectangle(this.area);
		rect.translate(speed * x, speed * y);
		if(Game.FIELD.isContains(rect)) {
			this.area.translate(speed * x, speed * y);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}
}

