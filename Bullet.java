import java.awt.Point;


public class Bullet {
	public final static int SIZE = 1;
	public final static int SPEED = 8;
	public int n;
	private Point pos = new Point();

	Bullet(int x, int y, int n) {
		this.pos.x = x;
		this.pos.y = y;
		this.n = n;
	}
	
	public Point getPos() {
		return this.pos;
	}
	
	public void moveBullet() {
		this.pos.y -= SPEED;
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
}
