import java.util.*;

public class ChestMap
{
    //This holds all the chest
    private ArrayList<Chest> e = new ArrayList<Chest>();
    
    private Chest c;
    private static final int EASY = 1, NORMAL = 2, HARD = 3;
    private int difficulty = 1;
    private int maxNumChests = 1;
    private int minNumChests = 1;
    
    /**
     * Constructor for the ChestMap
     */
    public ChestMap(String s){
        if(s.equals("Normal"))
            difficulty = EASY;
        else if(s.equals("Hard"))
            difficulty = NORMAL;
        else if(s.equals("INSANE!!!!"))
            difficulty = HARD;
        else    
            difficulty = EASY;
    }
    /**
     * Adds a new chest to the ArrayList that contains all the chests
     * @param y - The chest to be added
     */
    public void add(Chest y){
        e.add(y);
    }
    public ArrayList<Chest> chests(){
        return e;
    }
    
    /**
     * Finds and returns the first chest found in the ArrayList that has specified x & y coordinates
     * @param int x, int y
     * @return Chest or null
     */
    public Chest get(int x, int y){
        for(Chest m : e)
            if(m.getPoint().x()==x && m.getPoint().y()==y)
                return m;
        return null;
    }
    public Chest get(Point p){
        return get(p.x(), p.y());
    }

   /**
     * Checks whether or not a chest exists in given point position
     * @param Point
     * @return boolean
     */
    public boolean exists(int x, int y){
        return get(x, y) != null;
    }
    public boolean exists(Point p){
        return get(p.x(), p.y()) != null;
    }
    
    /**Spawns a certain amount of items depending on the level and difficulty
     * @param int level - the current level the player is on.
     * @pram Map m - The current Map the player is on.
     */
    public void spawn(int level, Map m, EnemyMap eMap, ItemMap iMap){
        e.clear();

        if((difficulty == EASY && level % 4 == 0 ||
            difficulty == NORMAL && level % 3 == 0 ||
            difficulty == HARD && level % 2 == 0)){
            maxNumChests++;
            if((difficulty == EASY && level % 8 == 0 ||
            difficulty == NORMAL && level % 6 == 0 ||
            difficulty == HARD && level % 4 == 0)){
                minNumChests++;
            }
        }
        
        int num = (int)(Math.random()*maxNumChests) + minNumChests; 
        int typeOfChest = (int)(Math.random() * 100) + 1;
            
        for(int count = 0;  count < num; count++){
            if((difficulty == EASY && level >= 10 ||
               difficulty == NORMAL && level >= 7 ||
               difficulty == HARD && level >= 4) && typeOfChest <= SpecialChest.getRate()){
                c = new SpecialChest(getFreePoint(m, eMap, iMap));
                e.add(c);
            }
            else if(typeOfChest <= NormalChest.getRate()){
                c = new NormalChest(getFreePoint(m, eMap, iMap));
                e.add(c);
            }
        }
    }
    
    /**
     * Get a free non-spawn point (non-wall, non-stair or player spawn point)
     * @return Point object
     */
    public Point getFreePoint(Map map, EnemyMap eMap, ItemMap iMap) {
        Point p = map.getFreeNonSpawnPoint();
        while (eMap.exists(p) || iMap.exists(p))
            p = map.getFreeNonSpawnPoint();
        return p;
    }
}