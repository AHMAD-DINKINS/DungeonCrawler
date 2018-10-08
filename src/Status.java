import java.util.ArrayList;
public class Status
{
    private String effect;
    private int amount;
    private int duration;
    /**
     * Constructor for Status
     * @param e - The string that holds info for a Status object
     */
    public Status(String e, int a, int d){
        effect = e;
        amount = a;
        duration = d;
    }
    public Status(String e){
        Status s = Status.parseStatuses(e).get(0);
        effect = s.getEffect();
        amount = s.amount();
        duration = s.duration();
    }
    
    /**
     * @return effect - The effect the Status object has
     */
    public String getEffect(){
        return effect;
    }
    
    /**
     * Sets a new effect for the Status Object
     * @param effect - The new effect for the item
     */
    public void setEffect(String effect){
        this.effect = effect;
    }
    
    public int amount() {
        return amount;
    }
    
    /**
     * @return The duration that the Status object lasts in turn lengths
     */
    public int duration(){
        return duration;
    }
    /**
     * Sets the new duration length for the item
     * @param duration - The new duration length of the Status object
     */
    public void setDuration(int duration){
        this.duration = duration;
    }
    
    public static ArrayList<Status> parseStatuses(String e){
        
        ArrayList<Status> arr = new ArrayList<Status>();
        String[] statuses = e.split("/");
        
        if (statuses.length % 3 != 0) return null;
        
        for(int i = 0; i < statuses.length - 2; i += 3) {
            arr.add(new Status(statuses[i], Integer.parseInt(statuses[i+1]), Integer.parseInt(statuses[i+2])));
        }
        
        return arr;
    }
    
    public String toString(){
        return effect + "|" + amount + "|" + duration;
    }
    
    public void update(Entity e) {
        
        if (effect.equals("health")){
            e.heal((int)Math.ceil(e.getMaxHealth() * (amount/100.0)));
            //Prevent healing past 100%
            if (e.getHealth() > e.getMaxHealth()) e.setHealth(e.getMaxHealth());
        }
        
        if (duration >= 0)
            duration --;
    }
    
    public static void main(String[] args) {
        System.out.println(Status.parseStatuses("health/10/39/defense/20/23/attack/24/23"));
    }
    
    public void addDuration(int duration){
        this.duration += duration;
    }
    
    public boolean equals(Status s) {
        return (getEffect().equals(s.getEffect()) && amount() == s.amount());
    }
}


