import java.util.ArrayList;

public class Map
{
    private final boolean wall = false, open = true;
    private boolean[][] map;
    private int width, height, rooms;
    private Point startPosition,
                  stairPosition;

    private final double passagewayIncidence = 0.01;
    private final int minConnections = 1,
                      minRoomWidth = 2, minRoomHeight = 2,
                      maxRoomWidth = 7, maxRoomHeight = 7;
    
    private boolean roomsShouldBeSquareish = true;

    /**
     * Map constructor
     * @param w int width of the map
     * @param h int height of the map
     */
    public Map(int w, int h) {
        map = new boolean[h][w];
        width = w;
        height = h;
        initMap();
    }

    /**
     * Tester-- prints out a 40x20 map.
     * @param args {}
     */
    public static void main(String[] args) {
        Map m = new Map(40, 20);
        System.out.println(m);
    }

    /**
     * Initializes the map
     */
    private void initMap() {
        for (int y = 0; y < map.length; y++)
            for (int x = 0; x < map[0].length; x++)
                map[y][x] = wall;
        
        rooms = (height * width) / ((maxRoomWidth) * (maxRoomHeight));
        rooms = randInt(rooms - 3, rooms + 2);
        ArrayList<Point> midpoints = new ArrayList<Point>();
        
        for (int i = 0, iterations = 0; i < rooms; i++) {
            int x = randX(), y = randY(),
                h = randInt(minRoomHeight, maxRoomHeight + 1),
                w = randInt(minRoomWidth, maxRoomWidth + 1);
                
            if (roomsShouldBeSquareish) {
                if (Math.random() < 0.5)
                    h = randInt(w - 2, w + 3);
                else
                    w = randInt(h - 2, h + 3);
                    
                if (w < minRoomWidth)  w = minRoomWidth;
                if (h < minRoomHeight) h = minRoomHeight;
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
                if (Math.random() < passagewayIncidence) {
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
          if (numConnected[i] <= minConnections) {
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
            map[y][x1] = open;
        for (int x = sx; x <= bx; x++)
            map[y2][x] = open;
    }

    /**
     * Checks if a given square of space is open for room-creation
     * @param bx int top left x value
     * @param by int top left y value
     * @param w int width
     * @param h int height
     * @return boolean value
     */
    public boolean isBoxOpen(int bx, int by, int w, int h) {
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
     * Fill a given square of space with open blocks
     * @param bx int top left x value
     * @param by int top left y value
     * @param w int width
     * @param h int height
     */
    private void fillOpen(int bx, int by, int w, int h) {
        for (int y = by; y <= by + h; y++) {
            for (int x = bx; x <= bx + w; x++) {
                map[y][x] = open;
            }
        }
    }

    /**
     * Checks if a given x and y value is a wall
     * @param x int x
     * @param y int y
     * @return boolean value
     */
    public boolean isWall(int x, int y) {
        //check bounds
        if (x < 0 || y < 0 || x >= width || y >= height) return true;

        //return value
        return map[y][x] == wall;
    }

    /**
     * Checks if a given Point value is a wall
     * @param p Point p
     * @return boolean value
     */
    public boolean isWall(Point p) {return isWall(p.x(), p.y());}

    /**
     * Checks if a given x and y value are next to an open section
     * @param x int x
     * @param y int y
     * @return boolean value
     */
    public boolean isNextToRoom(int x, int y) {
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
     * Get a random integer from start (inclusive) to stop (exclusive)
     * @param start int start (inclusive)
     * @param stop int stop (exclusive)
     * @return int value
     */
    public int randInt(int start, int stop) {
        return (int)(Math.random() * (stop - start) + start);
    }

    /**
     * Get a random map X value
     * @return int x
     */
    public int randX() {
        return randInt(1, map[0].length - 1);
    }

    /**
     * Get a random map Y value
     * @return int y
     */
    public int randY() {
        return randInt(1, map.length - 1);
    }

    /**
     * Print out the map
     * @return String the printed map
     */
    public String toString() {
        String s = "";
        for (boolean[] row : map) {
            for (boolean bit : row) {
                if (!bit)
                    s += "█";
                else 
                    s += " ";
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Getter for width
     * @return int width
     */
    public int width() {
        return width;
    }

    /**
     * Getter for height
     * @return int height
     */
    public int height() {
        return height;
    }

    /**
     * Method to create a new map
     */
    public void newMap() {
        map = new boolean[height][width];
        initMap();
    }

    /**
     * Get the current map's player start position
     * @return Point object
     */
    public Point startPos() {
        return new Point(startPosition);
    }

    /**
     * Get the current map's stair start position
     * @return Point object
     */
    public Point stairPos() {
        return new Point(stairPosition);
    }

    /**
     * Get a free point (non-wall point)
     * @return Point object
     */
    public Point getFreePoint() {
        Point p = new Point(randX(), randY());
        while (isWall(p))
            p = new Point(randX(), randY());
        return p;
    }

    /**
     * Get a free non-spawn point (non-wall, non-stair or player spawn point)
     * @return Point object
     */
    public Point getFreeNonSpawnPoint() {
        Point p = new Point(randX(), randY());
        while (isWall(p) || stairPosition.equals(p) || startPosition.equals(p))
            p = new Point(randX(), randY());
        return p;
    }
    
    /**
     * WIP-- check if a given enemy has a direct line of sight to the  player
     */
    public boolean hasLineOfSight(Point a, Point b) {
        int x1 = a.x(), y1 = a.y(), x2 = b.x(), y2 = b.y();
        return true;
    }

    /**
     * Get the entire map
     * @return boolean[][] map object
     */
    public boolean[][] get(){
        return map;
    }
}