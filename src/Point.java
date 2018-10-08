public class Point
{
    private int x, y;

    /**
     * Constructor for creating a Point
     * @param a integer x value
     * @param b integer y value
     */
    public Point(int a, int b) {
        x = a;
        y = b;
    }

    /**
     * Overloaded constructor for copying a Point object
     * @param p Point object
     */
    public Point(Point p) {
        x = p.x();
        y = p.y();
    }

    /**
     * Distance formula method
     * @param a Point object
     * @return double distance between the two points
     */
    public double distanceTo(Point a) {
        return Math.pow(Math.pow(a.x - x, 2) + Math.pow(a.y - y, 2), 0.5);
    }

    /**
     * Overloaded distance formula method
     * @param a integer x value
     * @param b integer y value
     * @return double distnace between the two points
     */
    public double distanceTo(int a, int b) {
        return Math.pow(Math.pow(a - x, 2) + Math.pow(b - y, 2), 0.5);
    }

    /**
     * Getter for the x value
     * @return int x
     */
    public int x() {return x;}

    /**
     * Getter for the y value
     * @return int y
     */
    public int y() {return y;}

    /**
     * Increment or decrement the x value by a number
     * @param i int value
     */
    public void changeX(int i) {
        x += i;
    }

    /**
     * Increment or decrement the y value by a number
     * @param i int value
     */
    public void changeY(int i) {
        y += i;
    }

    /**
     * Overridden .equals() method that checks if two points are at the same location
     * @param p Point object
     * @return boolean value
     */
    public boolean equals(Point p) {
        return x == p.x() && y == p.y();
    }

    /**
     * Overloaded .equals() method that checks if the point is at given location (x, y)
     * @param x integer x value
     * @param y integer y value
     * @return boolean value
     */
    public boolean equals(int x, int y) {
        return this.x == x && this.y == y;
    }
    
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}