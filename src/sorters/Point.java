package sorters;

/**
 *  
 * @author akashsetti
 *
 */

/**
 * This class defines a point. This allows us to create a point either randomly
 * or through a txt file. This class also implements the comparable interface
 * which allows us to compare a point with one passed through the class.
 *
 */

public class Point implements Comparable<Point> {
	/*
	 * Instance variable for the x value for the coordinate.
	 */
	private int x;
	/*
	 * Instance variable for the y value for the coordinate.
	 */
	private int y;

	/**
	 * Boolean instance variable that allows us to know if we are checking x's or
	 * y's
	 */
	public static boolean xORy; // compare x coordinates if xORy == true and y coordinates otherwise
								// To set its value, use Point.xORy = true or false.

	/**
	 * default constructor
	 */
	public Point() // default constructor
	{
		// x and y get default value 0
	}

	/**
	 * Point constructor that takes in a x and y value.
	 * 
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Point(Point p) { // copy constructor
		x = p.getX();
		y = p.getY();
	}

	/**
	 * Gets the x value.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y value.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the value of the static instance variable xORy.
	 * 
	 * @param xORy
	 */
	public static void setXorY(boolean xORy) {
		if (xORy) {
			Point.xORy = true;
		} else {
			Point.xORy = false;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Point other = (Point) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Compare this point with a second point q depending on the value of the static
	 * variable xORy
	 * 
	 * @param q
	 * @return -1 if (xORy == true && (this.x < q.x || (this.x == q.x && this.y <
	 *         q.y))) || (xORy == false && (this.y < q.y || (this.y == q.y && this.x
	 *         < q.x))) 0 if this.x == q.x && this.y == q.y) 1 otherwise
	 */
	public int compareTo(Point q) {
		if ((xORy == true && (this.x < q.x || (this.x == q.x && this.y < q.y)))
				|| (xORy == false && (this.y < q.y || (this.y == q.y && this.x < q.x)))) {
			return -1;
		} else if (this.x == q.x && this.y == q.y) {
			return 0;
		}
		return 1;
	}

	/**
	 * Output a point in the standard form (x, y).
	 */
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
