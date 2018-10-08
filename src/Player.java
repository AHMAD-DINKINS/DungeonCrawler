import java.util.ArrayList;

public class Player extends Entity
{  
    private String name;
    private boolean hasAttackBuff = false;
    /**
     * Constructor for the Player object which is waht the player controls in the game
     * @param n - The name of the player
     * @param a - The amount of attack damage the player does
     * @param h - The amount of health the player has
     * @param d - The amount of defense the player has
     * @param p - Point that the player spawns at
     * @param i - The name of the image that the player will be represented by
     */
    public Player(String n, int a, int h, int d, Point p, String i){
        super(a, h, d, p, i, 0);
        name = n;
        setFieldOfView(4);
    }
    
    /**
    * Method that will be called whenever the Player will attack.
    * @return int - an integer representing how much the Player will be attacking for.
    */
    public int attack(){
        // other multipliers added later
        int dmg = getAttack();
        ArrayList<Status> statuses = getStatuses();
        
        for (Status s : statuses) {
            if (s.getEffect().contains("attack")){
                hasAttackBuff = true;
                dmg += (int) Math.ceil(dmg * (s.amount()/100.0));
            }
        }
        
        return dmg;
    }
    
    /**
    * Method that will be called whenever the Player has the posiblity of losing health.
    * @param int - an integer representing how much the Player will be attacking for.
    */
    public void takeDamage(int damage){
        int defense = getBuffedDefense();
        
        if(damage-defense>0)
            setHealth(getHealth()-(damage-defense));
        else 
            setHealth(getHealth()-1);
    }

    public int getBuffedDefense() {
        int defense = getDefense();
        ArrayList<Status> statuses = getStatuses();

        for (Status s : statuses) {
            if (s.getEffect().contains("defense"))
                defense += (int) Math.ceil(defense * (s.amount()/100.0));
        }
        return defense;
    }
    
    /**
     * @return The name of the player
     */
    public String getName(){
        return name;
    }
    
    public Status getStatus(Status s) {
        for (Status s0 : getStatuses())
            if (s0.equals(s))
                return s0;
        return null;
    }
}