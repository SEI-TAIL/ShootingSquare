package shooting;

import java.awt.Rectangle;

public class Field implements ICollisionable {
	private final Rectangle field;

	public Field(int width, int height) {
		this.field = new Rectangle(width, height);
	}

	public boolean isContains(Rectangle rect) {
		return this.field.contains(rect);
	}

	public boolean isContains(ICollisionable other) {
		return this.field.contains(other.getArea());
	}

	@Override
	public boolean isCollision(ICollisionable other) {
		return this.field.intersection(other.getArea()).isEmpty() == false;
	}

	@Override
	public Rectangle getArea() {
		return this.field;
	}

	public int getWidth() {
		return this.field.width;
	}

	public int getHeight() {
		return this.field.height;
	}

}
