import java.util.ArrayList;
/**
 * ConsumableItemTester.java  
 *
 * @author:
 * Assignment #:
 * 
 * Brief Program Description:
 * 
 *
 */
public class ConsumableItemTester
{
    public static void main(String[] Args){
        
        ConsumableItem i=new ConsumableItem("Potion", new Status("health", 3, 2));
        i=new ConsumableItem("Potion", "health/3/3");
        ArrayList<Status> m = new ArrayList<Status>();
        m.add(new Status("health/3/3"));
        m.add(new Status("health/3/2"));
        
        i= new ConsumableItem("Potion",m);
        System.out.println(i.isUsed());
        System.out.println(i.toString());
        m.add(new Status("attack/3/3/"));
        i.setM_status(m);
        i.setM_effectLasts(10);
        i.consume();
        System.out.println(i.toString());
        System.out.println(i.isUsed());
    }
}
