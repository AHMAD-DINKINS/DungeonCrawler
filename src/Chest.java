import java.util.ArrayList;

public abstract class Chest
{
    private double spawnRate;
    private boolean opened = false;
    private ArrayList<EquippableItem> items;
    private EquippableItem[] normalEquipment = {new EquippableItem("sword_1", ((int)(Math.random() * 60) + 30), 0, 0, 0),
                                                             new EquippableItem("armor_1", 0, ((int)(Math.random() * 60) + 30), 0, 1),
                                                             new EquippableItem("sword_2", ((int)(Math.random() * 120) + 60), 0, 0, 0),
                                                             new EquippableItem("armor_2", 0, ((int)(Math.random() * 120) + 60), 0, 1),
                                                             new EquippableItem("sword_3", ((int)(Math.random() * 180) + 90), 0, 0, 0),
                                                             new EquippableItem("armor_3", 0, ((int)(Math.random() * 180) + 90), 0, 1),};
                                                           
    private static final EquippableItem[] specialEquipment = {new EquippableItem("sword_4", ((int)(Math.random() * 260) + 130), 20, 20, 0),
                                                              new EquippableItem("armor_4", 20, ((int)(Math.random() * 260) + 130), 20, 1),
                                                              new EquippableItem("sword_5", ((int)(Math.random() * 320) + 160), 40, 40, 0),
                                                              new EquippableItem("armor_5", 40, ((int)(Math.random() * 320) + 160), 40, 1),
                                                              new EquippableItem("sword_6", ((int)(Math.random() * 400) + 200), 80, 60, 0),
                                                              new EquippableItem("armor_6", 60, ((int)(Math.random() * 400) + 200), 80, 1)};
    private Point point;
    private String image;
    /**
     * The constuctor for a Chest
     * @param rate - Rate at which item spawns
     * @param p - Point at which chest spawns
     * @param i - Name of image that the chest will use
     */
    public Chest(Point p, String i){
        point = p;
        image = i;
    }
    
    /**
     * Opens the chest and gives a random EquippableItem
     */
    public EquippableItem open(){
        int i = generateEquipment();
        if(this instanceof NormalChest)
            return normalEquipment[i];
        return specialEquipment[i];
    }
    
    /**
     * Determines how good of an Item the chest will give favorng weakrer Items.
     */
    private int generateEquipment(){
        int x = ((int)(Math.random() * 100) + 1);
        
       if(x <= 5)
           return 5;
       else if(x <= 10)
           return 4;
       else if(x <= 25)
           return 3;
       else if(x <= 40)
           return 2;
       else if( x <= 70)
           return 1;
       return 0;
    }
    
    /**
     * Sets the position of the chest
     * @return Point point - The coordinates of the Chest
     */
    public void setPoint(int x, int y){
        point = new Point(x,y);
    }
    /**
     * Sets the position of the chest
     * @return Point point - The coordinates of the Chest
     */
    public void setPoint(Point p){
        point = p;
    }
    
    /**
     * Returns the position of the chest
     * @return Point point - The coordinates of the Chest
     */
    public Point getPoint(){
        return point;
    }
    
    public String getImage(){
        return image;
    }
}
