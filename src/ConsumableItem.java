import java.util.ArrayList;
/**
 * ConsumableItem.java  
 *
 * @author:
 * Assignment #:
 * 
 * Brief Program Description:
 * Class for Consumable items(potions) that will contain an ArrayList of Status as well as
 * how long they will last
 */
public class ConsumableItem extends Item{
    private ArrayList<Status> m_status;
    private int m_effectLasts;
    private boolean used = false;
    
    /**
     * The Constructor for a consumable item which gives the Player a status which affects their
     * statistics. ConsumableItem extends Item
     * @param String name - A string for the name of the item
     * @param ArrayList<Status> status- An ArrayList of status that the item will give the player 
     */
    //Three different overloaded versions: one for ArrayList of Status, 
    //one for a String that has the name of the Status, one for just one Status
    public ConsumableItem(String name, ArrayList<Status> status){
        super(name);
        m_status = status;
    }
    /**
     * The Constructor for a consumable item which gives the Player a status which affects their
     * statistics. ConsumableItem extends Item
     * @param String name - A string for the name of the item
     * @param String status- The status that the item will give the player, represented by a String 
     */
    public ConsumableItem(String name, String status){
        super(name);
        m_status = Status.parseStatuses(status);
    }
    /**
     * The Constructor for a consumable item which gives the Player a status which affects their
     * statistics. ConsumableItem extends Item
     * @param String name - A string for the name of the item
     * @param Status status- The status that the item will give the player 
     */
    public ConsumableItem(String name, Status status){
        super(name);
        m_status = new ArrayList<Status>();
        m_status.add(status);
    }
    
    /**
     * Changes the boolean used to true, implying the player has taken the item
     * @param none
     * @return none
     */
    public void consume(){
        used = true;
    }
    
    /**
     * Returns whether or not the item has been used
     * @param none
     * @return used - The boolean that refers to if the item was used
     */
    public boolean isUsed(){
        return used;
    }
    
    public String toString(){
        return super.toString() + "\n" +
               "Status Code: " + m_status + "\n" +
               "Effect Lasts: " + m_effectLasts;
    }
    
    /**
     * returns an ArrayList of the status
     * @param none
     * @return ArrayList<Status> m_status - The status of the object
     */
    public ArrayList<Status> getM_status() {
        return m_status;
    }
    
    /**
     * Sets the status on the item
     * @param m_status - The new status for the consumable item
     * @return none
     */
    public void setM_status(ArrayList<Status> m_status) {
        this.m_status = m_status;
    }
    
    /**
     * returns how long it lasts
     * @param none
     * @return m_effectLasts - The effect length of the consumable item
     */
    public int getM_effectLasts() {
        return m_effectLasts;
    }

    /**
     * Set the length of the effect
     * @param m_effectLasts - The new length of the effect
     * @return none
     */
    public void setM_effectLasts(int m_effectLasts) {
        this.m_effectLasts = m_effectLasts;
    }
}
