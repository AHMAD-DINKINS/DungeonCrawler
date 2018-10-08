import java.util.*;

public class Slime extends Enemy
{
    /**
     * Constructor for Slime
     * @param p - Point for slime to spawn at
     */
    public Slime(Point p){
        super(2, 24, 1, p, "slime", 1);
    }
    /**
     * Constructor for Slime with an inventory
     * @param p - Point for slime to spawn at
     * @param e - ArrayList of items for the Slime to have
     */
    public Slime(Point p, ArrayList<Item> e){
        super(2, 24, 1, p, "slime", e, 1);
    }
    /**
     * Constructor for Slime with items and status'
     * @param p - The point the slime spawns at
     * @param e - ArrayList of items for the Slime to have
     * @param statuses - An ArrayList of status' for the slime to be affected by
     */
    public Slime(Point p, ArrayList<Item> e, ArrayList<Status> statuses){
        super(2, 24, 2, p, "slime", e, statuses, 1);
    }
    
    /**Makes the Slime into a Metal Slime
     * Postcondition: The Slime becomes a Metal Slime changing it's image and stats;
     * It becomes harder to defeat but has a higher drop rate and drops better Items.
     */
    public void makeMetal(){
        setImage("metalSlime");
        setAttack(getAttack()*4);
        setMaxHealth(getHealth()*5);
        setHealth(getMaxHealth());
        setDefense(getDefense()*5);
        setScore(getScore()*3);
        setDropRate(0.45);
    }
}
