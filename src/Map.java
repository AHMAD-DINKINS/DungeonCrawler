import java.util.ArrayList;
import java.util.Scanner;

public class Map
{
    /**The maximum height the rooms can be.*/
    private static final int MAX_ROOM_HEIGHT = 7;
    /**The maximum width the rooms can be.*/
    private static final int MAX_ROOM_WIDTH = 7;
    /**The minimum height the rooms can be.*/
    private static final int MIN_CONNECTIONS = 1;
    /**The minimum height the rooms can be.*/
    private static final int MIN_ROOM_HEIGHT = 2;
    /**The minimum width the rooms can be.*/
    private static final int MIN_ROOM_WIDTH = 2;
    /**Represents an open room on the map.*/
    private static final boolean OPEN = true;
    /**Represents the tendency for a passageway to occur.*/
    private static final double PASSAGEWAY_INCIDENCE = 0.01;
    /**Represents if the rooms are square like.*/
    private static final boolean ROOMS_SHOULD_BE_SQUARISH = true;
    /**Represents a wall on the map.*/
    private static final boolean WALL = false;
    /**The height of the map.*/
    private int height;
    /**The map.*/
    private boolean[][] map;
    /**The width of the map.*/
    private int width;
    /**The number of rooms within the map.*/
    private int rooms;
    /**The starting position of the player.*/
    private Point startPosition;
    /**The position of the stairs.*/
    private Point stairPosition;
    /**
     * Map constructor
     * @param setWidth int width of the map
     * @param setHeight int height of the map
     */
    Map(int setWidth, int setHeight) {
        map = new boolean[setHeight][setWidth];
        width = setWidth;
        height = setHeight;
        initMap();
    }
    /**
     * Connects points given two Point values
     * @param a Point a
     * @param b Point b
     */
    private void connectPoints(Point a, Point b) {
        int y1 = a.y(), y2 = b.y();
        int x1 = a.x(), x2 = b.x();

        int bx = Math.max(x1, x2), sx = Math.min(x1, x2);
        int by = Math.max(y1, y2), sy = Math.min(y1, y2);

        for (int y = sy; y <= by; y++)
            map[y][x1] = OPEN;
        for (int x = sx; x <= bx; x++)
            map[y2][x] = OPEN;
    }
    /**
     * Fill a given square of space with open blocks
     * @param bx int top left x value
     * @param by int top left y value
     * @param w int width
     * @param h int height
     */
    private void fillOpen(int bx, int by, int w, int h) {
        for (int y = by; y <= by + h; y++) {
            for (int x = bx; x <= bx + w; x++) {
                map[y][x] = OPEN;
            }
        }
    }
    /**
     * Checks if a given square of space is open for room-creation
     * @param bx int top left x value
     * @param by int top left y value
     * @param w int width
     * @param h int height
     * @return boolean value
     */
    private boolean isBoxOpen(int bx, int by, int w, int h) {
        if (bx + w >= width - 1 || by + h >= height - 1)
            return false;

        for (int y = by - 1; y <= by + h + 1; y++) {
            for (int x = bx - 1; x <= bx + w + 1; x++) {
                if (!isWall(x, y))
                    return false;
            }
        }
        return true;
    }
    /**
     * Initializes the map
     */
    private void initMap() {
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[0].length; x++)
                map[y][x] = WALL;
        
        rooms = (height * width) / ((MAX_ROOM_WIDTH) * (MAX_ROOM_HEIGHT));
        rooms = randInt(rooms - 3, rooms + 2);
        ArrayList<Point> midpoints = new ArrayList<>();
        
        for (int i = 0, iterations = 0; i < rooms; i++) {
            int x = randX(), y = randY(),
                h = randInt(MIN_ROOM_HEIGHT, MAX_ROOM_HEIGHT + 1),
                w = randInt(MIN_ROOM_WIDTH, MAX_ROOM_WIDTH + 1);
                
            if (ROOMS_SHOULD_BE_SQUARISH) {
                if (Math.random() < 0.5)
                    h = randInt(w - 2, w + 3);
                else
                    w = randInt(h - 2, h + 3);
                    
                if (w < MIN_ROOM_WIDTH)  w = MIN_ROOM_WIDTH;
                if (h < MIN_ROOM_HEIGHT) h = MIN_ROOM_HEIGHT;
            }
            
            if (isBoxOpen(x, y, w, h)) {
                fillOpen(x, y, w, h);
                //set midpoints
                midpoints.add(new Point(x + w/2, y + h/2));
            } else {
                i--;
                iterations++;
            }
            
            if (iterations >= rooms*10)
                //give up
                break;
        }
        
        initPassages(midpoints.toArray(new Point[midpoints.size()]));
    }
    /**
     * Initializes passages given Point[] of midpoints
     * @param m Point[] midpoints
     */
    private void initPassages(Point[] m) {
        Point a, b;
        int[] numConnected = new int[m.length];
        startPosition = m[randInt(0, m.length)];
        stairPosition = m[randInt(0, m.length)];
        boolean stairAndStartConnected = false;
        
        while (stairPosition.equals(startPosition)) {
            stairPosition = m[randInt(0, m.length)];
        }
        
        for (int i = 0; i < m.length; i++) {
            a = m[i];
            for (int j = 0; j < m.length; j++) {
                if (i == j) continue;
                b = m[j];
                if (Math.random() < PASSAGEWAY_INCIDENCE) {
                  connectPoints(a, b);
                  if ((a.equals(startPosition) && b.equals(stairPosition)) || (a.equals(stairPosition) && b.equals(startPosition)))
                    stairAndStartConnected = true;
                  numConnected[i]++;
                  numConnected[j]++;
                }
            }
        }
        
        //Ensure every room has a high probability of being accessed
        for (int i = 0; i < numConnected.length; i++)
          if (numConnected[i] <= MIN_CONNECTIONS) {
            int connectTo = i;
            while (connectTo == i && rooms > 1)
              connectTo = (int)(Math.random() * m.length);
            connectPoints(m[i], m[connectTo]);
          }
          
        //failsafe - even if everything's not connected at least the player can go from the start to the stairs
        if (!stairAndStartConnected)
            connectPoints(startPosition, stairPosition);
    }
    /**
     * Checks if a given Point value is a wall
     * @param p Point p
     * @return boolean value
     */
    private boolean isWall(Point p) {return isWall(p.x(), p.y());}
    /**
     * Get a random integer from start (inclusive) to stop (exclusive)
     * @param start int start (inclusive)
     * @param stop int stop (exclusive)
     * @return int value
     */
    private int randInt(int start, int stop) {
        return (int)(Math.random() * (stop - start) + start);
    }
    /**
     * Get a random map X value
     * @return int x
     */
    private int randX() {
        return randInt(1, map[0].length - 1);
    }
    /**
     * Get a random map Y value
     * @return int y
     */
    private int randY() {
        return randInt(1, map.length - 1);
    }
    /**
     * Get a free non-spawn point (non-wall, non-stair or player spawn point)
     * @return Point object
     */
    Point getFreeNonSpawnPoint() {
        Point p = new Point(randX(), randY());
        while (isWall(p) || stairPosition.equals(p) || startPosition.equals(p))
            p = new Point(randX(), randY());
        return p;
    }
    /**
     * Getter for height
     * @return int height
     */
    int getHeight() {
        return height;
    }
    /**
     * Getter for width
     * @return int width
     */
    int getWidth() {
        return width;
    }
    /**
     * Checks if a given x and y value are next to an open section
     * @param x int x
     * @param y int y
     * @return boolean value
     */
    boolean isNextToRoom(int x, int y) {
        return (
                !isWall(x - 1, y) ||
                        !isWall(x + 1, y) ||
                        !isWall(x, y - 1) ||
                        !isWall(x, y + 1) ||
                        !isWall(x - 1, y - 1) ||
                        !isWall(x + 1, y + 1) ||
                        !isWall(x - 1, y + 1) ||
                        !isWall(x + 1, y - 1)
        );
    }
    /**
     * Checks if a given x and y value is a wall
     * @param x int x
     * @param y int y
     * @return boolean value
     */
    boolean isWall(int x, int y) {
        //check bounds
        if (x < 0 || y < 0 || x >= width || y >= height) return true;

        //return value
        return map[y][x] == WALL;
    }
    /**
     * Method to create a new map
     */
    void newMap() {
        map = new boolean[height][width];
        initMap();
    }
    /**
     * Get the current map's player start position
     * @return Point object
     */
    Point startPos() {
        return new Point(startPosition);
    }
    /**
     * Get the current map's stair start position
     * @return Point object
     */
    Point stairPos() {
        return new Point(stairPosition);
    }
    /**
     * Print out the map
     * @return String the printed map
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (boolean[] row : map) {
            for (boolean bit : row) {
                if (!bit) {
                    s.append("█");
                } else
                    s.append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
    /** Main method.
     * @param args Not used
     */
    public static void main(String[] args) {
        Map m = new Map(40, 20);
        System.out.println(m);
    }
}