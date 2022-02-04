package pt.upskill.projeto1.rogue.utils;

/**
 * @author POO2016
 *
 * Cardinal directions
 *
 */
public enum Direction {
	LEFT(false),
	UP(false),
	RIGHT(false),
	DOWN(false),
	LEFT_UP(true),
	RIGHT_UP(true),
	LEFT_DOWN(true),
	RIGHT_DOWN(true);


	private boolean diagonal;

	private Direction(boolean diagonal) {
		this.diagonal = diagonal;
	}

	public boolean isDiagonal() {
		return diagonal;
	}


	public Vector2D asVector() {
		if(this==Direction.UP) return new Vector2D(0, -1);
		if(this==Direction.DOWN) return new Vector2D(0, 1);
		if(this==Direction.LEFT) return new Vector2D(-1, 0);
		if(this==Direction.RIGHT) return new Vector2D(1, 0);
		if(this==Direction.LEFT_UP) return Direction.LEFT.asVector().plus(Direction.UP.asVector());
		if(this==Direction.RIGHT_UP) return Direction.RIGHT.asVector().plus(Direction.UP.asVector());
		if(this==Direction.LEFT_DOWN) return Direction.LEFT.asVector().plus(Direction.DOWN.asVector());
		if(this==Direction.RIGHT_DOWN) return Direction.RIGHT.asVector().plus(Direction.DOWN.asVector());
		return null;
	}
}
