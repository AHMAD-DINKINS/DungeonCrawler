import java.util.ArrayList;

public abstract class Enemy extends Entity
{ 
    /**
     * Constructor for an Enemy object
     * Enemy extends Entity
     * @param a - Attack damage
     * @param h - Health of enemy
     * @param d - Defense of enemy
     * @param p - The point at which the enemy spawns
     * @param i - The image name of the enemy
     */
    public Enemy(int a, int h, int d, Point p, String i, int score){
        super(a, h, d, p, i, score);
    }
    /**
     * Constructor for an Enemy object with items
     * Enemy extends Entity
     * @param a - Attack damage
     * @param h - Health of enemy
     * @param d - Defense of enemy
     * @param p - The point at which the enemy spawns
     * @param i - The image name of the enemy
     * @param e - The ArrayList of items the enemy has
     */
    public Enemy(int a, int h, int d, Point p, String i,ArrayList<Item> e, int score){
        super(a, h, d, p, i, e, score);
    }
    /**
     * Constructor for an Enemy object with items and status'
     * Enemy extends Entity
     * @param a - Attack damage
     * @param h - Health of enemy
     * @param d - Defense of enemy
     * @param p - The point at which the enemy spawns
     * @param i - The image name of the enemy
     * @param e - The ArrayList of items the enemy has
     * @param statuses - The status' the enemy has in an ArrayList
     */
    public Enemy(int a, int h, int d, Point p, String i, ArrayList<Item> e, ArrayList<Status> statuses, int score){
        super(a, h, d, p, i, e, statuses, score);
    }
    
    /** Randomly changes the x or y value of the Enemy.
     * @param map the Map containing values that are either true or false
     * depending on if there is a wall there or not.
     * @param eMap the EnemyMap that contains all of the existing enemies.
     * @param point the Point of the player.
     * Postcondition: The Enemy's Point is radomly incremented or decremented.
     */
    public void move(Map map, EnemyMap eMap, ChestMap cMap, Player pl){
        if(getHealth() <= 0)
            return;
        Point p = pl.getPoint();
        
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
            // decides to wait
            else if(num == 5){}
        }
        // otherwise if standing next to the player the Enemy will attack.
        else if(isWithinView(p) && (getPoint().equals(p.x() + 1, p.y()) || getPoint().equals(p.x() - 1, p.y())||
                                      getPoint().equals(p.x(), p.y() + 1) || getPoint().equals(p.x(), p.y() - 1))){
            attackPlayer(pl);
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
            // Random attack so that player is not constanly being followed and can sometimes outrun Enemy.
            else if((num >= 9 && num <= 10)){
                //System.out.println("Randomly attempted to attack the player.\n");
            }
        }
    }
    
    /**
    * Returns the item to be dropped
    * @param Item i - The specified Item.
    * @return Item i - The Item to be dropped.
    */
    public Item Drop(Item i){
        if(getDropRate() > Math.random()) // checks to see if the Item will be dropped.
            return super.Drop(i);
        return null;
    }
    
    /**
    * Method that will be called whenever the Enemy will attack.
    * @return int - an integer representing how much the Enemy will be attacking for.
    */
    public int attack(){
        // other multipliers added later
        return getAttack();
    }
    
    /**
    * Method that will be called whenever the Enemy will attack the player.
    * @return int - an integer representing how much the Enemy will be attacking for.
    */
    public void attackPlayer(Player p){
        // other multipliers added later
        //System.out.println("Attempted to attack the player.\n");
        p.takeDamage(attack());
    }
    
    /**
    * Method that will be called whenever the Enemy has the posiblity of losing health.
    * @param int - an integer representing how much the Enemy will be attacking for.
    */
    public void takeDamage(int damage){
        if(damage-getDefense()>0)
            setHealth(getHealth()-(damage-getDefense()));
        else 
            setHealth(getHealth()-1);
    }
        
    /**
    * Method that will be called whenever the Enemy wants to use an Item.
    */
    public  void use(){
        //May do something in the future.
    }
    
    /**Turns the Enemy into a boss.
     * Postcondition: The Enemy becomes a boss by changing it's image and stats;
     * It becomes harder to defeat but has a higher drop rate and drops better Items.
     */
    public void makeKing(){
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