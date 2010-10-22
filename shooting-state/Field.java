package shooting;

import java.awt.Rectangle;
import java.awt.geom.Area;

public class Field implements ICollidable {
	private final Rectangle rect;

	public Field(int width, int height) {
		this.rect = new Rectangle(width, height);
	}

	public boolean isContains(Rectangle rect) {
		return this.rect.contains(rect);
	}

	public boolean isContains(ICollidable other) {
		return this.rect.contains(other.getArea().getBounds());
	}

	@Override
	public boolean isCollision(ICollidable other) {
		return this.rect.intersects(other.getArea().getBounds());
	}

	@Override
	public Area getArea() {
		return new Area(this.rect);
	}

	public int getWidth() {
		return this.rect.width;
	}

	public int getHeight() {
		return this.rect.height;
	}

}
