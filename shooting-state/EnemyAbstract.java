package shooting;

abstract class EnemyAbstract implements ICollidable, IShottable, IColor {
	abstract void damage();
	abstract void move();
	abstract void rotate();
}