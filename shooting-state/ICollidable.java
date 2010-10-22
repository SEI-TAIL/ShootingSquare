package shooting;

import java.awt.geom.Area;

public interface ICollidable {
	Area getArea();
	boolean isCollision(ICollidable other);
}
