import java.awt.Point;


public class Enemy {
	final public static int SPEED = 5;
	
	static int mkSize = 1;
	
	private Point pos;
	private int size;
	
	Enemy(int x, int y, int size) {
		this.pos.x = x;
		this.pos.y = y;
		this.size = size;
	}
	
	public void minimizeSize() {
		size--;
	}
	
	public void moveEnemy() {
		this.pos.y += Enemy.SPEED;
	}
}
