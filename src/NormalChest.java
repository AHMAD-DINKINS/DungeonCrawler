public class NormalChest extends Chest
{
    /**
     * Constructor for a NormalChest object
     * NormalChest extends Chest
     * @param p - The point at which the chest spawns at
     */
    
    private static final int SPAWN_RATE = 65;
    
    public NormalChest(Point p){
        super(p, "normalChest");
    }
    
    public static int getRate(){
        return SPAWN_RATE;
    }
}
