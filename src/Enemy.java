import java.util.ArrayList;

public abstract class Enemy extends Entity
{ 
    /**
     * Constructor for Enemy
     * Enemy extends Entity
     * @param setAttack - Attack damage
     * @param setHealth - Health of enemy
     * @param setDefense - Defense of enemy
     * @param setSpawnPoint - The point at which the enemy spawns
     * @param setImage - The image name of the enemy
     */
    Enemy(final int setAttack, final int setHealth, final int setDefense, final Point setSpawnPoint, final String setImage, final int setScore){
        super(setAttack, setHealth, setDefense, setSpawnPoint, setImage, setScore);
    }
    /**
     * Constructor for an Enemy object with items
     * Enemy extends Entity
     * @param setAttack - Attack damage
     * @param setHealth - Health of enemy
     * @param setDefense - Defense of enemy
     * @param setSpawnPoint - The point at which the enemy spawns
     * @param setImage - The image name of the enemy
     * @param setItems - The ArrayList of items the enemy has
     */
    Enemy(int setAttack, int setHealth, int setDefense, Point setSpawnPoint, String setImage, ArrayList<Item> setItems, int setScore){
        super(setAttack, setHealth, setDefense, setSpawnPoint, setImage, setItems, setScore);
    }
    /**
     * Constructor for an Enemy object with items and status'
     * Enemy extends Entity
     * @param setAttack - Attack damage
     * @param setHealth - Health of enemy
     * @param setDefense - Defense of enemy
     * @param setSpawnPoint - The point at which the enemy spawns
     * @param setImage - The image name of the enemy
     * @param setItems - The ArrayList of items the enemy has
     * @param setStatuses - The status' the enemy has in an ArrayList
     */
    Enemy(final int setAttack, final int setHealth, final int setDefense, final Point setSpawnPoint, final String setImage, final ArrayList<Item> setItems, final ArrayList<Status> setStatuses, final int setScore){
        super(setAttack, setHealth, setDefense, setSpawnPoint, setImage, setItems, setStatuses, setScore);
    }
    /** Randomly changes the x or y value of the Enemy.
     * @param map the Map containing values that are either true or false
     * depending on if there is a wall there or not.
     * @param eMap the EnemyMap that contains all of the existing enemies.
     * @param point the Point of the player.
     * Postcondition: The Enemy's Point is radomly incremented or decremented.
     */
    public void move(Map map, EnemyMap eMap, ChestMap cMap, Player point){
        if(getHealth() <= 0)
            return;
        Point p = point.getPoint();
        
        // If the Enemy can't see the player it will move randomly
        if(!isWithinView(p)){
            int num = 1 + (int) (Math.random() * 5);
            //to the right of
            if(!map.isWall(getPoint().x() + 1, getPoint().y()) &&
               !eMap.exists(getPoint().x() + 1, getPoint().y()) &&
               !cMap.exists(getPoint().x() + 1, getPoint().y()) &&
               getPoint().x() + 1 != p.x() && num == 1)
                changeX(1);
            // below
            else if(!map.isWall(getPoint().x(), getPoint().y() + 1) &&
                    !eMap.exists(getPoint().x(), getPoint().y() + 1) &&
                    !cMap.exists(getPoint().x(), getPoint().y() + 1) &&
                    getPoint().y() + 1 != p.y() && num == 2) 
                changeY(1);
            // to the left of
            else if(!map.isWall(getPoint().x() - 1, getPoint().y()) &&
                    !eMap.exists(getPoint().x() - 1, getPoint().y()) &&
                    !cMap.exists(getPoint().x() - 1, getPoint().y()) &&
                    getPoint().x() - 1 != p.x() && num == 3)
                changeX(-1);
            // above
            else if(!map.isWall(getPoint().x(), getPoint().y() - 1) &&
                    !eMap.exists(getPoint().x(), getPoint().y() - 1) &&
                    !cMap.exists(getPoint().x(), getPoint().y() - 1) &&
                    getPoint().y() - 1 != p.y() && num == 4)
                changeY(-1);
        }
        // otherwise if standing next to the player the Enemy will attack.
        else if(isWithinView(p) && (getPoint().equals(p.x() + 1, p.y()) || getPoint().equals(p.x() - 1, p.y())||
                                      getPoint().equals(p.x(), p.y() + 1) || getPoint().equals(p.x(), p.y() - 1))){
            attackPlayer(point);
        }
        else if(isWithinView(p)){
            int num = 1 + (int) (Math.random() * 10);
            // to the right of
            if(!map.isWall(getPoint().x() + 1, getPoint().y()) &&
               !eMap.exists(getPoint().x() + 1, getPoint().y()) &&
               !cMap.exists(getPoint().x() + 1, getPoint().y()) &&
               getPoint().x() < p.x() && (num >= 1 && num <= 8)){
                    //System.out.println(getPoint() + " is following Right\n");
                    changeX(1);
            }
            // to the left of
            else if(!map.isWall(getPoint().x() - 1, getPoint().y()) &&
                    !eMap.exists(getPoint().x() - 1, getPoint().y()) &&
                    !cMap.exists(getPoint().x() - 1, getPoint().y()) &&
                    getPoint().x() > p.x() && (num >= 1 && num <= 6)){
                    //System.out.println(getPoint() + " is following Left\n");
                    changeX(-1);   
            }
            // above
            else if(!map.isWall(getPoint().x(), getPoint().y() - 1) &&
                    !eMap.exists(getPoint().x(), getPoint().y() - 1) &&
                    !cMap.exists(getPoint().x(), getPoint().y() - 1) &&
                    getPoint().y() > p.y() && (num >= 1 && num <= 6)){
                    //System.out.println(getPoint() + " is following Up\n");
                    changeY(-1);
            }
            // below
            else if(!map.isWall(getPoint().x(), getPoint().y() + 1) &&
                    !eMap.exists(getPoint().x(), getPoint().y() + 1) &&
                    !cMap.exists(getPoint().x(), getPoint().y() + 1) &&
                    getPoint().y() < p.y() && (num >= 1 && num <= 6)){
                    //System.out.println(getPoint() + " is following Down\n");
                    changeY(1);
            }
        }
    }
    /**
    * Returns the item to be dropped
    * @param  item - The specified Item.
    * @return The Item to be dropped.
    */
    public Item Drop(Item item){
        if(getDropRate() > Math.random()) // checks to see if the Item will be dropped.
            return super.Drop(item);
        return null;
    }
    /**
    * Method that will be called whenever the Enemy will attack.
    * @return int - an integer representing how much the Enemy will be attacking for.
    */
    public int attack(){
        return getAttack();
    }
    /**
     * @param player The player
     */
    private void attackPlayer(Player player){
        player.takeDamage(attack());
    }
    /**
    * Method that will be called whenever the Enemy has the possibility of losing health.
    * @param damage - an integer representing how much the Enemy will be attacking for.
    */
    public void takeDamage(int damage){
        if(damage-getDefense()>0)
            setHealth(getHealth()-(damage-getDefense()));
        else 
            setHealth(getHealth()-1);
    }
    /**Turns the Enemy into a boss.
     * Post-condition: The Enemy becomes a boss by changing it's image and stats;
     * It becomes harder to defeat but has a higher drop rate and drops better Items.
     */
    void makeKing(){
        if((int)(Math.random() * 100) + 1 > 30)
            return;
        System.out.print("A King " + getImage() + " has spawned!!!");
        setImage("king_" + getImage());
        setAttack(getAttack()*3);
        setMaxHealth(getHealth()*3);
        setHealth(getMaxHealth());
        setDefense(getDefense()*3);
        setScore(getScore()*10);
        setDropRate(0.65);
    }
}