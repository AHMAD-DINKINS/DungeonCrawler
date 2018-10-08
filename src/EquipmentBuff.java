import java.util.ArrayList;

public class EquipmentBuff
{
    private ArrayList<Status> buffs = new ArrayList<Status>();
    private int duration = -1;

    public EquipmentBuff(EquippableItem e){
        buffs.add(new Status(e.getName() + "'s attackBuff", e.getM_attackBonus(), duration));
        buffs.add(new Status(e.getName() + "'s healthBuff", e.getM_healthBonus(), duration));
        buffs.add(new Status(e.getName() + "'s defenseBuff", e.getM_defenseBonus(), duration));
    }
    
    public ArrayList<Status> buffs(){
        return buffs;
    }
}
