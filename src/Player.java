import java.util.ArrayList;
public class Player extends Entity
{
    /**The name of the Player.*/
    private String name;
    /**
     * Constructor for Player
     * @param setName - The name of the player
     * @param setAttack - The amount of attack damage the player does
     * @param setHealth - The amount of health the player has
     * @param setDefense - The amount of defense the player has
     * @param setSpawnPoint - Point that the player spawns at
     * @param setImage - The name of the image that the player will be represented by
     */
    Player(String setName, int setAttack, int setHealth, int setDefense, Point setSpawnPoint, String setImage){
        super(setAttack, setHealth, setDefense, setSpawnPoint, setImage, 0);
        name = setName;
        setFieldOfView(4);
    }
    /**
    * Method that will be called whenever the Player will attack.
    * @return int - an integer representing how much the Player will be attacking for.
    */
    public int attack(){
        int dmg = getAttack();
        ArrayList<Status> statuses = getStatuses();
        for (Status s : statuses) {
            if (s.getEffect().contains("attack")){
                dmg += (int) Math.ceil(dmg * (s.amount()/100.0));
            }
        }
        return dmg;
    }
    /**
    * Method that will be called whenever the Player has the posiblity of losing health.
    * @param damage - an integer representing how much the Player will be attacking for.
    */
    public void takeDamage(int damage){
        int defense = getBuffedDefense();
        if(damage-defense>0)
            setHealth(getHealth()-(damage-defense));
        else 
            setHealth(getHealth()-1);
    }
    /**
     * @return The defeense after applied buffs.
     */
    int getBuffedDefense() {
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
    String getName(){
        return name;
    }
    Status getStatus(Status s) {
        for (Status s0 : getStatuses())
            if (s0.equals(s))
                return s0;
        return null;
    }
}