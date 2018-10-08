
/**
 * EquippableItemTester.java  
 *
 * @author:
 * Assignment #:
 * 
 * Brief Program Description:
 * 
 *
 */
public class EquippableItemTester
{
    public static void main(String[] args){
        EquippableItem e1 = new EquippableItem("sword", 5, 0, 0, 0);
        EquippableItem e2 = new EquippableItem("betterSword", 10, 0, 0,0);
        EquippableItem e3 = new EquippableItem("armor", 0, 5, 0, 1);
        ConsumableItem e4 = new ConsumableItem("healthPotion", new Status("health", 10, 1));
        Player p = new Player("Player", 0, 0 ,0, new Point(0,0), "boy");
        
        System.out.print("The player picks up a " + e1.getName());
        p.getInventory().add(e1);
        p.equip(e1);
        System.out.println(" and now has the " + e1.getName() + " equipped.");
        System.out.println("\n" + p + "\n\n" + e1 + "\n");
        
        System.out.println("The player picks up a " + e2.getName() + " and compares it to his " + e1.getName() + ".\n");
        p.getInventory().add(e2);
        System.out.println("\n" + p + "\n\n" + e1 + "\n\n" + e2 + "\n");
        
        if(e1.compareTo(e2) < 1)
            p.equip(e2);
            
        if(!e1.isM_equipped() && e2.isM_equipped())
            System.out.println("The player unequipped the " + e1.getName() + " and equipped the " + e2.getName() + ".\n");
        System.out.println("\n" + p + "\n\n" + e1 + "\n\n" + e2 + "\n");
    }
}
