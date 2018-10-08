import java.util.ArrayList;

public abstract class Entity
{
    private int attack, health, defense; // constitution and dexterity will be added later upon discussion.
    private int maxHealth;
    private int score;
    private Point point;
    private String image;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private EquippableItem[] equipped = new EquippableItem[2];
    private ArrayList<Status> statusEffects = new ArrayList<Status>();
    private int fieldOfView = 5;
    private double dropRate = 0.25;
    private boolean sameSword = false, sameArmor = false;

    /**
     * Constructor used for player and enemies without any default items and or statuses
     * @param a - The amount of attack damage the Entity does
     * @param h - The amount of health the entity has
     * @param d - The amount of defense the entity has
     * @param p - The point at which the entity spawns at
     * @param i - The name of the image that the entity will have to represent itself
     */
    public Entity(int a, int h, int d, Point p, String i, int s){
        attack = a;
        health = h;
        maxHealth = health;
        defense = d;
        point = p;
        image = i;
        score = s;
    }

    /**
     * Constructor used for enemies with any default items
     * @param a - The amount of attack damage the Entity does
     * @param h - The amount of health the entity has
     * @param d - The amount of defense the entity has
     * @param p - The point at which the entity spawns at
     * @param i - The name of the image that the entity will have to represent itself
     * @param e - An ArrayList that contains the items that the entity will have
     */
    public Entity(int a, int h, int d, Point p, String i, ArrayList<Item> e, int s){
        attack = a;
        health = h;
        maxHealth = health;
        defense = d;
        point = p;
        image = i;
        inventory = e;
        score = s;
    }

    /**
     * Constructor used for enemies with any default items and statuses
     * @param a - The amount of attack damage the Entity does
     * @param h - The amount of health the entity has
     * @param d - The amount of defense the entity has
     * @param p - The point at which the entity spawns at
     * @param i - The name of the image that the entity will have to represent itself
     * @param e - An ArrayList that contains the items that the entity will have
     * @param statusEffects - An ArrayList that contains all the status' that the entity has
     */
    public Entity(int a, int h, int d, Point p, String i, ArrayList<Item> e, ArrayList<Status> statusEffects, int s){
        attack = a;
        health = h;
        maxHealth = health;
        defense = d;
        point = p;
        image = i;
        inventory = e;
        this.statusEffects = statusEffects;
        score = s;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int newScore){
        score = newScore;
    }

    public void addToScore(int pointsEarned){
        score+=pointsEarned;
    }

    /**
     * Sets the attack value of the entity
     * @param health - The new attack value of the entity.
     */
    public void setAttack(int attack){
        this.attack = attack;
    }

    /**
     * Returns the attack value of the entity
     * @return attack - The attack value of the entity.
     */
    public int getAttack(){
        return attack;
    }

    /**
     * Sets the health value of the entity
     * @param health - The new health value of the entity.
     */
    public void setHealth(int health){
        this.health = health;
    }

    /**
     * Heals the entity
     * @param health - The new health value of the entity.
     */
    public void heal(int health){
        this.health += health;
    }

    /**
     * Returns the health value of the entity
     * @return health - The attack value of the entity.
     */
    public int getHealth(){
        return health;
    }
    /**
     * Sets a new max health for the entity
     * @param newMax - The new max health of the entity
     */
    public void setMaxHealth(int newMax) {
        maxHealth = newMax;
    }
    /**
     * @return The max health that the entity has
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Sets the defense value of the entity
     * @param health - The new defense value of the entity.
     */
    public void setDefense(int defense){
        this.defense = defense;
    }

    /**
     * Returns the defense value of the entity
     * @return defense - The attack value of the entity.
     */
    public int getDefense(){
        return defense;
    }

    /**
     * Sets the position of the entity
     * @param Point p - The new point of the entity on the map.
     */
    public void setPoint(Point p){
        point = new Point(p);
    }
    /**
     * Sets the position of the entity
     * @param int x- The new x value of the entity on the map.
     * @param int y- The new y value of the entity on the map.
     */
    public void setPoint(int x, int y){
        point = new Point(x, y);
    }

    /**
     * Returns the position of the entity
     * @return Point point - The point of the entity on the map.
     */
    public Point getPoint(){
        return point;
    }

    /**
     * Sets the entity's field of view.
     * @param int fieldOfView - The new value of the entity's field of view.
     */
    public void setFieldOfView(int fieldOfView){
        this.fieldOfView = fieldOfView;
    }

    /**
     * Returns the fieldOfView value of the entity
     * @return int fieldOfView - The fieldOfView value of the entity.
     */
    public int getFieldOfView(){
        return fieldOfView;
    }

    /**
     * Adds item i to inventory
     */
    public void addItem(Item i) {
        inventory.add(i);
    }

    /**
     * Sets the entity's drop rate.
     * @param int dRate - The new value of the entity's drop rate.
     */
    public void setDropRate(double dRate){
        dropRate = dRate;
    }

    /**
     * Returns the entity's drop rate.
     * @return int dRate - The entity's drop rate.
     */
    public double getDropRate(){
        return dropRate;
    }

    /**
     * Overloaded method that returns a boolean representing wether or not the Entity can see the player.
     * @param Point p - the Point to be checked.
     * @return Boolean - The boolean representing wether or not the Entity can see the Point.
     */
    public boolean isWithinView(int x, int y) {
        return getPoint().distanceTo(x, y) <= fieldOfView;
    }
    /**
     * Checks to see if a specific point is in view, relative to the entity
     * @param p - The point to check vision for
     * @return Whether the point is in view of the entity
     */
    public boolean isWithinView(Point p) {
        return getPoint().distanceTo(p) <= fieldOfView;
    }

    /**
     * Returns the specified item if it is in th entity's inventory.
     * @param name - The specified item.
     * @return item - The item in the entity's inventory.
     */
    public Item getItem(String name){
        String item;
        for(Item i: inventory)
            if (i.getName().equals(name))
                return i;
        return null;
    }

    /**
     * Returns the inventory
     * @return ArrayList<Item>
     */
    public ArrayList<Item> getInventory () {
        return inventory;
    }

    /**
     * Change the x component of the point of entity
     * @param x - New x-value
     */
    public void changeX(int x){
        point.changeX(x);
    }
    /**
     * Change the y-component of the point in the entity
     * @param y - The new y-component
     */
    public void changeY(int y){
        point.changeY(y);
    }

    /**
     * Sets the String of the entity
     * @param String image - The new String of the entity.
     */
    public void setImage(String image){
        this.image = image;
    }

    /**
     * Returns the String of the entity
     * @return String image - The String of the entity.
     */
    public String getImage(){
        return image;
    }

    /**
     * @return The equipment that the player has equipped.
     */
    public EquippableItem[] getEquipped(){
        return equipped;
    }

    /**
     * return the number of items with a given name in the entity's inventory
     * @param item String
     * @return int num items in inventory
     */
    public int numInInventory(String item) {
        int count = 0;
        for(Item i : inventory)
            if (i.getName().equals(item))
                count++;
        return count;
    }

    /**
     * Method that will be called whenever the Player wants to use an Item.
     */
    public Item removeItem(String item){
        Item it;
        for(int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getName().equals(item)) {
                it = inventory.remove(i);
                return it;
            }
        }
        return null;
    }

    /**
     * Adds the specified Item to equipped
     * @param EquippableItem e - The specified Item
     */
    public void equip(EquippableItem e){
        if(equipped[e.getM_inventoryNum()] == null){
            equipped[e.getM_inventoryNum()] = e;
            equipped[e.getM_inventoryNum()].setM_equipped(true);
            if(e.getM_inventoryNum() == 0){
                sameSword = true;
                for (Status s : equipped[0].getBuff().buffs()){
                    if (s.getEffect().contains("healthBuff")){
                        if(equipped[1] != null)
                            setMaxHealth(getMaxHealth() + (int) Math.ceil(40 * (s.amount()/100.0)));
                        else
                            setMaxHealth(40 + (int) Math.ceil(40 * (s.amount()/100.0)));
                    }
                    statusEffects.add(s);
                }
            }
            else{
                sameArmor = true;
                for (Status s : equipped[1].getBuff().buffs()){
                    if (s.getEffect().contains("healthBuff")){
                        if(equipped[1] != null)
                            setMaxHealth(getMaxHealth() + (int) Math.ceil(40 * (s.amount()/100.0)));
                        else
                            setMaxHealth(40 + (int) Math.ceil(40 * (s.amount()/100.0)));
                    }
                    statusEffects.add(s);
                }
            }
        }
        else if(equipped[e.getM_inventoryNum()].compareTo(e) == -1){
            if(e.getM_inventoryNum() == 0)
                sameSword = false;
            else
                sameArmor = false;
            equipped[e.getM_inventoryNum()].setM_equipped(false);
            equipped[e.getM_inventoryNum()].removeBuff();
            equipped[e.getM_inventoryNum()] = e;
            equipped[e.getM_inventoryNum()].setM_equipped(true);
        }
    }

    // This method will be called when a monster drops an EquippableItem.
    /**
     * Removes the specified Item from equipped
     * @param EquippableItem e - The specified Item
     */
    public void unequip(int index){
        equipped[index] = null;
    }

    /**
     * Returns the item to be dropped
     * @param Item i - The specified Item.
     * @return Item i - The Item to be dropped.
     */
    public Item Drop(Item i){
        if(canDrop(i)) // checks to see if the Item is in the inventory
            return inventory.remove(inventory.indexOf(i));
        return null;
    }

    /**
     * Returns true if the specified item is in the entity's inventory
     * @param Item i - The specified Item
     * @return boolean - Wether or not the item is in the entity's inventory and can be dropped.
     */
    private boolean canDrop(Item i){
        return inventory.contains(i);
    }

    /**
     * Adds the specified status effect to the statusEffects List.
     * @param name - The specified status.
     * Postcondition: The entity will have a new Status.
     */
    public void addStatus(Status s){
        statusEffects.add(s);
    }

    /**
     * Returns the specified status effect if it is in the statusEffect List.
     * @param name - The specified status.
     * @return status - The status in the entity's statusEffcts List.
     */
    public ArrayList<Status> getStatuses(){
        return statusEffects;
    }

    public void updateEquipment(){
        for(Item i: inventory){
            if(!(i instanceof ConsumableItem))
                equip((EquippableItem)(i));
        }
        if(!sameSword)
            if(equipped[0] != null){
                sameSword = true;
                for (Status s : equipped[0].getBuff().buffs()){
                    if (s.getEffect().contains("healthBuff")){
                        if(equipped[1] != null)
                            setMaxHealth(getMaxHealth() + (int) Math.ceil(40 * (s.amount()/100.0)));
                        else
                            setMaxHealth(40 + (int) Math.ceil(40 * (s.amount()/100.0)));
                    }
                    statusEffects.add(s);
                }
            }
        if(!sameArmor)
            if(equipped[1] != null){
                sameArmor = true;
                for (Status s : equipped[1].getBuff().buffs()){
                    if (s.getEffect().contains("healthBuff")){
                        if(equipped[1] != null)
                            setMaxHealth(getMaxHealth() + (int) Math.ceil(40 * (s.amount()/100.0)));
                        else
                            setMaxHealth(40 + (int) Math.ceil(40 * (s.amount()/100.0)));
                    }
                    statusEffects.add(s);
                }
            }
    }

    public void updateStatuses() {
        for (Status s : statusEffects)
            s.update(this);
        for (int i = 0; i < statusEffects.size(); i++)
            if (statusEffects.get(i).duration() == 0)
                statusEffects.remove(i);
    }

    /**
     * Method that will be called whenever the Entity will attack.
     * @return int - an integer representing how much the Enttiy will be attacking for.
     */
    public abstract int attack();
    /**
     * Method that will be called whenever the Entity has the posiblity of losing health.
     * @param int - an integer representing how much the Enttiy will be attacking for.
     */
    public abstract void takeDamage(int damage);
    /**
     * Method that will be called whenever the Entity wants to use an Item.
     */
    //public abstract void use();

    public String toString(){
        int totalAttackBuff = 0, totalHealthBuff = 0, totalDefenseBuff = 0;

        for(Status s : statusEffects)
            if(s.getEffect().contains("attackBuff"))
                totalAttackBuff+= s.amount();
            else if(s.getEffect().contains("healthBuff"))
                totalHealthBuff+= s.amount();
            else
                totalDefenseBuff+= s.amount();
        String items = "", status = "";
        for (Item i: inventory)
            items+= i.getName() + " ";
        for (Status s: statusEffects)
            status+= s.getEffect() + " ";
        return "Image: "+ image +
                "\nAttack: "+ attack + " + " + totalAttackBuff +
                "\nMax Health: "+ 40 + " + " + totalHealthBuff +
                "\nHealth: "+ health +
                "\nDefense: "+ defense + " + " + totalDefenseBuff +
                "\nPoint: "+ point +
                "\nInventory: "+ items +
                "\nStatus Effects: "+ status;
    }
}