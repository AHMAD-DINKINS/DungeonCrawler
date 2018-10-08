import java.util.*;

public class ItemMap
{
     /**
      * Available Image Files:
     * AttackPotion, bigAttackPotion
     * HealthPotion, bigHealthPotion
     * DefensePotion, bigDefensePotion
     */
    private ArrayList<Item> e = new ArrayList<Item>();
    int potpow=20;
    int potpot=1;
    int potdur=5;
    int bigpotpot=1;
    int bigpotdur=10;
    int potatk=20;
    int potdef=40;
    int bigpotdef=65;
    Status defense;
    Status attack;
    Status health;
    
    int maxitems=3;
    int minitems=1;
    /**
     * Constructor for the ItemMap
     */
    public ItemMap(){}
    /**
     * Adds an item to the ArrayList
     * @param Item y
     * @return none
     */
    public void add(Item y){
        e.add(y);
    }
    public ArrayList<Item> items(){
        return e;
    }
   
    /**
     * Finds and returns the first item found in the ArrayList that has specified x & y coordinates
     * @param int x, int y
     * @return Item or null
     */
    public Item get(int x, int y){
        for(Item m : e)
            if(m.getPoint().x()==x && m.getPoint().y()==y)
                return m;
        return null;
    }
    public Item get(Point p){
        return get(p.x(), p.y());
    }
    
    /**
     * Checks whether or not an item exists in given point position
     * @param int x, int y
     * @return boolean
     */
    public boolean exists(int x, int y){
        return get(x, y) != null;
    }
    /**
     * Overloaded version of exists that accepts a Point instead
     * @param Point
     * @return boolean
     */
    public boolean exists(Point p){
        return get(p.x(), p.y()) != null;
    }
    /**
     * Removes the item at specified Point from ItemMap and returns it
     * 
     * @param Point p
     * @return Item or null
     * 
     */
    public Item take(Point p){
        for(int x=0; x<e.size();x++){
            if(e.get(x).equals(get(p))){
                return e.remove(x);
            }
        }
        return null;
    }
    
    /**
     * Generates a ConsumableItem, randomly selecting one of 6 possible preset potions
     * @param none
     * @return ConsumableItem
     */
    public ConsumableItem generateCon(){
         /**
         * Available Image Files-- CASE SENSITIVE!
         * AttackPotion, bigAttackPotion
         * HealthPotion, bigHealthPotion
         * DefensePotion, bigDefensePotion
         */
         int rando=(int)(Math.random()*100)+1;
            if(rando<40){
                return new ConsumableItem("HealthPotion", "health/"+potpow+"/1/health/" +potpot+ "/"+potdur);
            }
            else if(rando<60){
                return new ConsumableItem("AttackPotion", "attack/"+potatk+"/40");
            }
            else if(rando<70){
                return new ConsumableItem("DefensePotion", "defense/"+potdef+"/40");
            }
            else if(rando<85){
                return new ConsumableItem("bigHealthPotion", "health/"+potpow*2+"/1/health/"+bigpotpot+"/"+bigpotdur);
            }
            else if(rando<95){
                return new ConsumableItem("bigAttackPotion", "attack/"+potatk*2+"/40");
            }
            else{
                return new ConsumableItem("bigDefensePotion", "defense/"+bigpotdef+"/40");
            }
    }
     /**Spawns a certain amount of items depending on the level and difficulty
     * @param int level - the current level the player is on.
     * @pram Map m - The current Map the player is on.
     */
    public void spawn(int level, Map m){
        e.clear();

        if(level%3==0){
            maxitems++;
            bigpotpot++;
            
            if(level%6==0){
                minitems++;
                
            }
        }
        if(level%2==0){
            potatk++;
            potdef=potdef+2;
            bigpotdef=bigpotdef+2;
            potdur++;
            bigpotdur++;
            potpow=potpow+2; 
        }
        
        int num = (int)(Math.random()*maxitems)+minitems;
        for(int x=0; x< num;x++){
                e.add(generateCon());
                e.get(e.size()-1).setPoint(m.getFreeNonSpawnPoint());
        }
    }
}