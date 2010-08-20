import java.awt.Point;

public class MySquare {
	private Point pos;
	public static final int SIZE = 10;
	public static final int SPEED = 3;
	
	MySquare(int x, int y) {
		pos = new Point(x, y);
	}
	
	public void setPos(Point pos) {
		this.pos.x = pos.x;
		this.pos.y = pos.y;
	}
	public Point getPos() {
		return this.pos;
	}
	
	public void moveMe(int x, int y) {
		this.pos.x += SPEED * x;
		this.pos.y += SPEED * y;
	}
}
