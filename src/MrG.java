import java.util.ArrayList;

public class MrG extends Enemy
{
    /**
     * Constructor for MrG
     * @param p - The Point that MrG spawns at
     */
     public MrG(Point p){
        super(50, 250, 40, p, "mr_g1", 100);
        setFieldOfView(2);
    }
    /**
     * Constructor for MrG with items
     * @param p - The point MrG spawns at
     * @param e - An ArrayList of items MrG will use
     */
    public MrG(Point p, ArrayList<Item> e){
        super(50, 250, 40, p, "mr_g1", e, 100);
        setFieldOfView(2);
    }
    
    /**
     * Constuctor for MrG with items and status'
     * @param p - The point MrG spawns at
     * @param e - An ArrayList of items MrG will use
     * @param statuses - An ArrayList of statuses that MrG will have
     */
    public MrG(Point p, ArrayList<Item> e, ArrayList<Status> statuses){
        super(50, 250, 40, p, "mr_g1", e, statuses, 100);
        setFieldOfView(2);
    }
    /**
     * Moves MrG while adjusting the image he is set to depending on proximity to player
     * @param map - Map he is on
     * @param eMap - An enemy map for him to move on
     * @param p - The player he will check proximity to
     */
    public void move(Map map, EnemyMap eMap, ChestMap cMap, Player p){
        if(isWithinView(p.getPoint()))
            setImage("mr_g2");
        else
            setImage("mr_g1");
        super.move(map, eMap, cMap, p);
    }
}
