import java.awt.Point;


public class Enemy {
	final public static int SPEED = 5;
	
	static int mkSize = 1;
	private Point pos = new Point();
	private int size;
	
	Enemy(int x, int y, int size) {
		this.pos.x = x;
		this.pos.y = y;
		this.size = size;
	}
	
	public void minimizeSize() {
		if (size > 2) {
			this.pos.x++;
			this.pos.y++;
			size -= 2;
		}
	}
	
	public void moveEnemy() {
		this.pos.y += Enemy.SPEED;
	}

	public boolean delCheckBullet() {
		if (this.pos.y > ShootingSquare.FRAME_SIZE ||
				this.pos.y < 0 ||
				this.pos.x > ShootingSquare.FRAME_SIZE ||
				this.pos.x < 0) {
				return true;
			}
		return false;
	}
	
	public Point getPos() {
		return this.pos;
	}
	public int getSize() {
		return this.size;
	}
}
