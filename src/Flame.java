import java.util.*;

public class Flame extends Enemy
{
    //Don't forget King Pyron!!!!
    /**
     * Constructor for a Flame object
     * Flame extends Enemy
     * @param p - The point a flame spawns at
     */
    public Flame(Point p){
        super(15, 60, 16, p, "flame", 20);
    }
    /**
     * Constructor for a Flame object with items
     * Flame extends Enemy
     * @param p - The point a flame spawns at
     * @param e - The ArrayList of items
     */
    public Flame(Point p, ArrayList<Item> e){
        super(15, 60, 16, p, "flame", e, 100);
    }
    /**
     * Constructor for a Flame object with items
     * Flame extends Enemy
     * @param p - The point a flame spawns at
     * @param e - The ArrayList of items
     * @param statuses - The ArrayList of status'
     */
    public Flame(Point p, ArrayList<Item> e, ArrayList<Status> statuses){
        super(15, 60, 16, p, "flame", e, statuses, 100);
    }
    
    /**Makes the Flame into a Pyron
     * Postcondition: The Flame becomes a Pyron by setting pyron to true and changing it's image;
     * It becomes harder to defeat but has a higher drop rate and drops better Items.
     */
    public void makePyron(){
        setImage("pyron");
        setAttack(getAttack()*2);
        setMaxHealth(getHealth()*5);
        setHealth(getMaxHealth());
        setDefense(getDefense()*6);
        setScore(getScore()*3);
        setDropRate(0.45);
    }
    
}
