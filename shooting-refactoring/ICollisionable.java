package shooting;

import java.awt.Rectangle;

public interface ICollisionable {
	Rectangle getArea();
	boolean isCollision(ICollisionable other);
}
