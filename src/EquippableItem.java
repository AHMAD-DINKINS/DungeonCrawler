public class EquippableItem extends Item implements Comparable 
{
    //These are all of the possible bonuses items can provide
    private int m_attackBonus;
    private int m_healthBonus;
    //private int m_strengthBonus;
    //private int m_constitutionBonus;
    //private int m_dexterityBonus;
    private int m_defenseBonus;
    private boolean m_equipped;
    private int m_inventoryNum;
    private EquipmentBuff buff;
    
    /**
     * The constructor for EquippableItem
     * @param name- A string for the name of the item
     * @param attackB - The attack bonus the item gives
     * @param healthB - The health bonus the item gives
     * @param defB - The defense bonus the item gives
     * @param equipped - A boolean for whether the item is equipped
     * @param invNum - The inventory number the item has
     */
    public EquippableItem(String name, int attackB, int healthB, int defB, int invNum){
        super(name);
        m_attackBonus = attackB;
        m_healthBonus = healthB;
        //m_strengthBonus = stengthB;
        //m_constitutionBonus = constitutionB;
        //m_dexterityBonus = dexterityB;
        m_defenseBonus = defB;
        m_equipped = false;
        m_inventoryNum = invNum;
        
        buff = new EquipmentBuff(this);
    }
    
    /**
     * Compares this EquippableItem with the specified Equippable Item.
     * Returns a one, zero, or negative one as this object is greater than, equal to, or less than the specified object.
     * Also returns zero in the case that you are not comparing the same type of weapon(sword, armor, boots)
     * or a non-EquippableItem.
     * @param EquippableItem e The item to be compared to.
     * @return int A value representing how the EquippableItems compare.
     */
    public int compareTo(Object o){
        if(o instanceof EquippableItem){
            EquippableItem e = (EquippableItem)(o);
            if(e.getM_inventoryNum() == m_inventoryNum){
                int statTotal = m_attackBonus + m_healthBonus + m_defenseBonus;
                int newStatTotal = e.getM_attackBonus() + e.getM_healthBonus() + e.getM_defenseBonus();
                if(statTotal < newStatTotal)
                   return -1;
                else if(statTotal == newStatTotal)
                   return 0;
                else
                    return 1;
            }
        }
        return 0;
    }
    
    public String toString(){
        return super.toString() + "\n" + 
               "Attack Bonus: " + m_attackBonus + "\n" +
               "Health Bonus: " + m_healthBonus + "\n" +
               //"Strength Bonus: " + m_strengthBonus + "\n" +
               //"Constitution Bonus: " + m_constitutionBonus + "\n" +
               //"Dexterity Bonus: " + m_dexterityBonus + "\n" + 
               "Defense Bonus: " + m_defenseBonus + "\n" +
               "Equipped: " + m_equipped + "\n" +
               "Inventory Number: " + m_inventoryNum;
    }
    
    /**
     * @return m_attackBonus - The attack bonus the item gives
     */
    public int getM_attackBonus() {
        return m_attackBonus;
    }
    
    /**
     * @param m_healthBonus - The new health bonus the item gives
     */
    public void setM_attackBonus(int m_healthBonus) {
        this.m_attackBonus = m_attackBonus;
    }
    
    /**
     * @return m_healthBonus - The current health bonus of the item
     */
    public int getM_healthBonus() {
        return m_healthBonus;
    }
    
    /**
     * @param m_healthBonus - The new health bonus for the item
     */
    public void setM_healthBonus(int m_healthBonus) {
        this.m_healthBonus = m_healthBonus;
    }

//     public int getM_strengthBonus() {
//         return m_strengthBonus;
//     }
// 
//     public void setM_strengthBonus(int m_strengthBonus) {
//         this.m_strengthBonus = m_strengthBonus;
//     }
// 
//     public int getM_constitutionBonus() {
//         return m_constitutionBonus;
//     }
// 
//     public void setM_constitutionBonus(int m_constitutionBonus) {
//         this.m_constitutionBonus = m_constitutionBonus;
//     }
// 
//     public int getM_dexterityBonus() {
//         return m_dexterityBonus;
//     }
// 
//     public void setM_dexterityBonus(int m_dexterityBonus) {
//         this.m_dexterityBonus = m_dexterityBonus;
//     }
    
    /**
    * @return m_defenseBonus - The current defense bonus of the item
    */
    public int getM_defenseBonus() {
        return m_defenseBonus;
    }

    /**
     * @param m_healthBonus - The new health bonus for the item
     */
    public void setM_defenseBonus(int m_defenseBonus) {
        this.m_defenseBonus = m_defenseBonus;
    }
    
    /**
     * @return m_equipped - The state of the item (Equipped or Unequipped)
     */
    public boolean isM_equipped() {
        return m_equipped;
    }

    /**
     * @param m_equipped - New equipped state of the item
     */
    public void setM_equipped(boolean m_equipped) {
        this.m_equipped = m_equipped;
    }

    /**
     * @return m_inventoryNum - The inventory number of the item
     */
    public int getM_inventoryNum() {
        return m_inventoryNum;
    }

    /**
     * @param m_inventoryNum - The new inventory number of the item
     */
    public void setM_inventoryNum(int m_inventoryNum) {
        this.m_inventoryNum = m_inventoryNum;
    }
    
    public EquipmentBuff getBuff(){
        return buff;
    }
    
    public void removeBuff(){
        for(Status buff: getBuff().buffs())
            buff.setDuration(0);
    }
}