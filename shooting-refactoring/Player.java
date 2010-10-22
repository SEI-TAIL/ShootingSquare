package shooting;
import java.awt.Rectangle;

public class Player implements ICollisionable, IShottable {
	private final Rectangle area;
	public int size;
	public int speed;
	// TODO プレイヤー機は弾の撃ちわけを可能にするため、buttletTypeを持たせる事

	public Player() {
		this.size = 10;
		this.speed = 3;
		Rectangle fieldRect = Game.FIELD.getArea();
		int centerX = (int)fieldRect.getCenterX();
		int centerY = (int)fieldRect.getCenterY();
		int baseX = centerX - (this.size / 2);
		int baseY = centerY - (this.size / 2);
		this.area = new Rectangle(baseX, baseY, this.size, this.size);
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
}

