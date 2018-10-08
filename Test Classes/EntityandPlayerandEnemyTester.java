
/**
 * EntityandPlayerandEnemyTester.java  
 *
 * @author: Ahmad
 * Assignment: Final Project
 * 
 * Brief Program Description:
 * Tests the Entity, Player, and Enemy classes.
 *
 */
public class EntityandPlayerandEnemyTester
{
    private static Entity e;
    public static void main(String[] args){
        int x = 1 + (int)(Math.random() * 4);
        
        if(x==1)
            e = new Slime(new Point(0,0));
        else if(x==2)
            e = new Ghost(new Point(0,0));
        else if(x==3)
            e = new Flame(new Point(0,0));
        else if(x==4)
            e = new MrG(new Point(0,0));
        
        Entity p = new Player("Player",1,2,3,new Point(1,1),"girl");
        System.out.println(e);
        if(x==4)
            System.out.println("\n" + e.getImage() + " has spawned.");
        else
            System.out.println("\nA " + e.getImage() + " has spawned.");
        
        if(x==4)
            System.out.println("\nThe girl attacked " + e.getImage() + "!\n");
        else
            System.out.println("\nThe girl attacked the " + e.getImage() + "!\n");
        
        
        e.takeDamage(p.attack());
        System.out.println(e);
        
        if(x==4)
            System.out.println("\n" + e.getImage() + " attacked the girl!\n");
        else
            System.out.println("\nA " + e.getImage() + " attacked the girl!\n");
        
        
        p.takeDamage(e.attack());
        System.out.println(e + "\n");
        System.out.println(p + "\n");
        
        boolean firstPass = true;
        while(((Enemy)(e)).isWithinView(p.getPoint())){
            if(firstPass){
                if(x==4){
                    System.out.println(e.getImage() + " can see the player.\n");
                    firstPass = false;
                }
                else{
                    System.out.println("The " + e.getImage() + " can see the player.\n");
                    firstPass = false;
                }
            }
            else{
                if(x!=4)
                    System.out.println("The " + e.getImage() + " can still see the player.\n");
                else
                    System.out.println(e.getImage() + " can still see the player.\n");
            }
            System.out.println("The player ran down one space.\n");
            p.getPoint().changeY(1);
            System.out.println("The player is at point:" + p.getPoint() + "\n");
        }
        System.out.println("The player escaped!!!");
    }
}
