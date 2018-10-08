public class SpecialChest extends Chest
{
   
    
    private static final int SPAWN_RATE = 5;
    
     /**
     * The constructor for SpecialChest
     * SpecialChest extends Chest
     * @parm p - The point at which the SpecialChest spawns at
     */
    public SpecialChest(Point p){
        super(p, "specialChest");
    }
    
    public static int getRate(){
        return SPAWN_RATE;
    }
}
