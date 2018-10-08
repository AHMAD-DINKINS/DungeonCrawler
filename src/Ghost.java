import java.util.*;

public class Ghost extends Enemy
{
    /**
     * The constructor for a Ghost Object
     * Ghost extends Enemy
     * @param p - The point the ghost spawns at
     */
    public Ghost(Point p){
        super(12, 35, 6, p, "ghost", 5);
        setFieldOfView(8);
    }
    /**
     * The constructor for a ghost with items
     * @param p - The point at which the ghost spawns at
     * @param e - An ArrayList of items the ghost has
     */
    public Ghost(Point p, ArrayList<Item> e){
        super(12, 35, 6, p, "ghost", e, 5);
        setFieldOfView(8);
    }
    /**
     * The constructor for a ghost which has items and status'
     * @param p - The point at which the ghost spawns at
     * @param e - An ArrayList of items the ghost has
     * @param statuses - An ArrayList of status' the ghost has
     */
    public Ghost(Point p, ArrayList<Item> e, ArrayList<Status> statuses){
        super(12, 35, 6, p, "ghost", e, statuses, 5);
        setFieldOfView(8);
    }
    
    /**Makes the Ghost into a Demon
     * Postcondition: The Ghost becomes a Demon by setting demon to true and changing it's image;
     * It becomes harder to defeat but has a higher drop rate and drops better Items.
     */
    public void makeDemon(){
        setImage("demon");
        setAttack(getAttack()*4);
        setMaxHealth(getHealth()*5);
        setHealth(getMaxHealth());
        setDefense(getDefense()*6);
        setScore(getScore()*3);
        setDropRate(0.45);
    }
}
