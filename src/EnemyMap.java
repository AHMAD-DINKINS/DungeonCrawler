import java.util.*;
import javax.swing.JOptionPane;
public class EnemyMap
{
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private int maxNumSlime = 3, maxNumGhost = 3, maxNumFlame = 3, minNumSlime = 1, minNumGhost = 1, minNumFlame = 1;
    private int difficulty, level;
    private static final int EASY = 1, NORMAL = 2, HARD = 3;
    private static final double[] BUFF = {1, 1.5, 2, 2.5, 3, 3.5, 4};
    private boolean isMiniBoss = false;
    
    private Enemy e;
    public EnemyMap(String s){
        if(s.equals("Normal"))
            difficulty = EASY;
        else if(s.equals("Hard"))
            difficulty = NORMAL;
        else if(s.equals("INSANE!!!!"))
            difficulty = HARD;
        else    
            difficulty = EASY;
        this.difficulty = difficulty;
    }
    
    /**Adds new Enemy to the enemies ArrayList
     * @param Enemy e - The new Enemy
     */
    public void add(Enemy e){
        enemies.add(e);
    }
    
    /**Overloaded method that accepts either the Point object or x and y ints that represents
     * a coordinate point on the map and returns the Enemy at specified Point if there is one there.
     * @param int x - The x value of the Enemy's Point.
     * @param int y - The y value of the Enemy's Point.
     * @return Enemy e - The specified Enemy.
     */
    public Enemy get(int x, int y){
       for(Enemy e : enemies)
           if(e.getPoint().x()==x && e.getPoint().y()==y)
               return e;
       return null;
    }
    /**Overloaded method that accepts either the Point object or x and y ints that represents
     * a coordinate point on the map and returns the Enemy at specified Point if there is one there.
     * @param Point p - The Point of the Enemy.
     * @return Enemy e - The specified Enemy.
     */
    public Enemy get(Point p){
       int x = p.x(), y = p.y();
       for(Enemy e : enemies)
           if(e.getPoint().x()==x && e.getPoint().y()==y)
               return e;
       return null;
    }
    
    /**Overloaded method that accepts either the Point object or x and y ints that represents
     * a coordinate point on the map and returns a boolean that is true if the entered coordinate is a coordinate of an Enemy.
     * @param int x - The x value of the Enemy's Point.
     * @param int y - The y value of the Enemy's Point.
     * @return boolean b - The boolean representing if an Enemy alredy is at that coordinate.
     */
    public boolean exists(int x, int y){
        return get(x, y) != null;
    }
    /**Overloaded method that accepts either the Point object or x and y ints that represents
     * a coordinate point on the map and returns a boolean that is true if the entered coordinate is a coordinate of an Enemy.
     * @param Point p - The Point of the Enemy.
     *  @return boolean b - The boolean representing if an Enemy alredy is at that coordinate.
     */
    public boolean exists(Point p){
        return get(p.x(), p.y()) != null;
    }
    
    /**Spawns a certain amount of enemies depending on the level and difficulty
     * @param int level - the current level the player is on.
     * @pram Map m - The current Map the player is on.
     */
    public void spawn(int l, Map m){
        level = l;
        int count = 0;
        
        clearEnemies();
        
        /*#Slime Spawner*/
        /**Starts spawning depeding on the level and difficulty.
         * Makes sure that a random amount of additional slimes depending on the difficulty and level.
         * Minimum number spawned increased every fifth level after the first time they are spawned.
         */
        if(difficulty == EASY && level <  5 || difficulty == NORMAL && level < 3 || difficulty == HARD && level < 2)
            spawnSlime(m);

        /*#Ghost First Spawned*/
        /**Starts spawning depeding on the level and difficulty.
         * Makes sure that a random amount of additional ghosts depending on the difficulty and level.
         * Minimum number spawned increased every fourth level after the first time they are spawned.
         */
        else if(difficulty == EASY && level == 5 || difficulty == NORMAL && level == 3 || difficulty == HARD && level == 2){
            spawnSlime(m);
            e = new Ghost(getFreeNonSpawnPoint(m));
            setDifficulty();
            add(e);
        }
        else if (difficulty == EASY && level < 9 || difficulty == NORMAL && level < 6 || difficulty == HARD && level < 4){
            spawnSlime(m);
            spawnGhost(m);
        }
                
        /*#Flame First Spawned*/
        /**Starts spawning depeding on the level and difficulty.
         * Makes sure that a random amount of additional flames depending on the difficulty and level.
         * Minimum number spawned increased every fourth level after the first time they are spawned.
         */
        else if(difficulty == EASY && level == 9 || difficulty == NORMAL && level == 6 || difficulty == HARD && level == 4){
            spawnSlime(m);
            spawnGhost(m);
            e = new Flame(getFreeNonSpawnPoint(m));
            setDifficulty();
            add(e);
        }
        else if(difficulty == EASY && level < 20 || difficulty == NORMAL && level < 15 || difficulty == HARD && level < 10){
            spawnSlime(m);
            spawnGhost(m);
            spawnFlame(m);
        }
        
        /*#Boss First Spawned*/
        /**Starts spawning depeding on the level and difficulty.
         * Makes sure that a random amount of additional MrGs depending on the difficulty and level.
         * Minimum number spawned increased every third level after the first time they are spawned.
         */
        else if(difficulty == EASY && level == 20 || difficulty == NORMAL && level == 15 || difficulty == HARD && level == 10){
            spawnSlime(m);
            spawnGhost(m);
            spawnFlame(m);
            e = new MrG(getFreeNonSpawnPoint(m));
            setDifficulty();
            add(e);
            JOptionPane.showMessageDialog(null,"You have entered his lair...Good Luck...", "Dungeon Crawler 1.0", JOptionPane.INFORMATION_MESSAGE);
        }
        else if(difficulty == EASY && level > 20 || difficulty == NORMAL && level > 15 || difficulty == HARD && level > 10){ 
            spawnSlime(m);
            spawnGhost(m);
            spawnFlame(m);
            spawnMrG(m);
        }
        
    }
    
    /**Returns and open Point on the map
     * @param Map map - The containg info about wether or not points are open(doesn't factor in the player).
     */
    private Point getFreeNonSpawnPoint(Map m) {
        Point p = new Point(m.getFreeNonSpawnPoint());
        
        while (exists(p))
            p = new Point(m.getFreeNonSpawnPoint());
        return p;
    }
    
    /**
     * 
     */
    private void spawnSlime(Map m){
        int count = 0;
        if(difficulty == EASY && level % 4 == 0 ||difficulty == NORMAL && level % 3 == 0 ||difficulty == HARD && level % 2 == 0)
            maxNumSlime++; 
            
        int s = minNumSlime + (int)(Math.random() * maxNumSlime);
        while(count < minNumSlime + s) {
            e = new Slime(getFreeNonSpawnPoint(m));
            if(difficulty == EASY && level >= 10 || difficulty == NORMAL && level >= 8 || difficulty == HARD && level >= 3)  
                isMiniBoss = becomeMiniBoss();
            setDifficulty();
            add(e);
            count++;
        }
    }
    
    /**
     * 
     */
    private void spawnGhost(Map m){
        int count = 0;
        if(difficulty == EASY && level % 4 == 0 ||difficulty == NORMAL && level % 3 == 0 ||difficulty == HARD && level % 2 == 0){
           maxNumGhost++;
           minNumSlime++;
        }
        int g = minNumGhost + (int)(Math.random() * maxNumGhost);
        while(count < minNumGhost + g){
           e = new Ghost(getFreeNonSpawnPoint(m));
           if(difficulty == EASY && level >= 12 || difficulty == NORMAL && level >= 10 || difficulty == HARD && level >= 4)
                isMiniBoss = becomeMiniBoss();
           setDifficulty();
           add(e);
           count++;
        }
    }
    
    /**
     * 
     */
    private void spawnFlame(Map m){
        int count = 0;
        if(difficulty == EASY && level % 4 == 0 ||difficulty == NORMAL && level % 3 == 0 ||difficulty == HARD && level % 2 == 0){
            maxNumFlame++;
            minNumSlime++;
            minNumGhost++;
        }
        int f = minNumFlame + (int)(Math.random() * maxNumFlame);
        while(count < minNumFlame + f){
           e = new Flame(getFreeNonSpawnPoint(m));
          if(difficulty == EASY && level >= 14 || difficulty == NORMAL && level >= 12 || difficulty == HARD && level >= 5)
                isMiniBoss = becomeMiniBoss();
           setDifficulty();
           add(e);
           count++;
        }
    }
    
    /**
     * 
     */
    private void spawnMrG(Map m){
        int count = 0;
       if(level%2 == 0){
            minNumSlime+=2;
            minNumGhost++;
            minNumSlime+=2;
            minNumFlame++;
        }
        int x = 1 + (int)(Math.random() * difficulty/2);
        while(count < x){
           e = new MrG(getFreeNonSpawnPoint(m));
           setDifficulty();
           add(e);
           count++;
        }
    }
    
    /**Sets the stats of the enemies according to the difficulty of the game and what level thae player is on.
     * Postcondition: The enemies stats change based on the difficulty and level.
     */
    private void setDifficulty(){    
        /*# Setting the difficulty for the Slimes. There is stat progression.*/
        if(e instanceof Slime){
            if(difficulty == EASY && level < 5 || difficulty == NORMAL && level < 3 || difficulty == HARD && level < 2){
                if(difficulty == EASY)
                    setStats(EASY-1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL-1);
                else if(difficulty == HARD)
                    setStats(HARD-1);
            }
            else if(difficulty == EASY && level < 9 || difficulty == NORMAL && level < 6 || difficulty == HARD && level < 4){
                if(difficulty == EASY)
                    setStats(EASY);                
                else if(difficulty == NORMAL)
                    setStats(NORMAL);                
                else if(difficulty == HARD)
                    setStats(HARD);                
            }
            else if(difficulty == EASY && level < 16 || difficulty == NORMAL && level < 10 || difficulty == HARD && level < 6){
                if(difficulty == EASY)
                    setStats(EASY+1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+1);
                else if(difficulty == HARD)
                    setStats(HARD+1);
            }
            else if(difficulty == EASY && level < 20 || difficulty == NORMAL && level < 15 || difficulty == HARD && level < 8){
                if(difficulty == EASY)
                    setStats(EASY+2);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+2);
                else if(difficulty == HARD)
                    setStats(HARD+2);
            }
            else if(difficulty == EASY && level >= 20 || difficulty == NORMAL && level >= 15 || difficulty == HARD && level >= 10){
                if(difficulty == EASY)
                    setStats(EASY+3);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+3);
                else if(difficulty == HARD)
                    setStats(HARD+3);
            }
        }
        /*# Setting the difficulty for the Ghosts. There is stat progression.*/
        else if(e instanceof Ghost){
            if(difficulty == EASY && level < 9 || difficulty == NORMAL && level < 6 || difficulty == HARD && level < 4){
                if(difficulty == EASY)
                    setStats(EASY-1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL-1);
                else if(difficulty == HARD)
                    setStats(HARD-1);
            }
            else if(difficulty == EASY && level < 16 || difficulty == NORMAL && level < 10 || difficulty == HARD && level < 6){
                if(difficulty == EASY)
                    setStats(EASY);
                else if(difficulty == NORMAL)
                    setStats(NORMAL);
                else if(difficulty == HARD)
                    setStats(HARD);
            }
            else if(difficulty == EASY && level < 20 || difficulty == NORMAL && level < 15 || difficulty == HARD && level < 10){
                if(difficulty == EASY)
                    setStats(EASY+1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+1);
                else if(difficulty == HARD)
                    setStats(HARD+1);
            }
            else if(difficulty == EASY && level >= 20 || difficulty == NORMAL && level >= 15 || difficulty == HARD && level >= 10){
                if(difficulty == EASY)
                    setStats(EASY+2);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+2);
                else if(difficulty == HARD)
                    setStats(HARD+2);
            }
        }
        /*# Setting the difficulty for the Flames. There is stat progression.*/
        else if(e instanceof Flame){
            if(difficulty == EASY && level < 16 || difficulty == NORMAL && level < 10 || difficulty == HARD && level < 6){
                if(difficulty == EASY)
                    setStats(EASY);
                else if(difficulty == NORMAL)
                    setStats(NORMAL);
                else if(difficulty == HARD)
                    setStats(HARD);
            }
            else if(difficulty == EASY && level <  20 || difficulty == NORMAL && level < 15 || difficulty == HARD && level < 10){
                if(difficulty == EASY)
                    setStats(EASY+1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+1);
                else if(difficulty == HARD)
                    setStats(HARD+1);
            }
            else if(difficulty == EASY && level >= 20 || difficulty == NORMAL && level >= 15 || difficulty == HARD && level >= 10){
                if(difficulty == EASY)
                    setStats(EASY+2);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+2);
                else if(difficulty == HARD)
                    setStats(HARD+2);
            }
        }  
        /*# Setting the difficulty for MrGs. There is NO stat progression.*/
        else if(e instanceof MrG)
            if(difficulty == EASY && level >= 20 || difficulty == NORMAL && level >= 15 || difficulty == HARD && level >= 10)
                if(difficulty == EASY)
                    setStats(EASY+1);
                else if(difficulty == NORMAL)
                    setStats(NORMAL+2);
                else if(difficulty == HARD)
                    setStats(HARD+3);
    }
    
    /**Sets the stats for the Enemies
     * @param int buffIndex - integer representing the which buff the enemy should have
     */
    private void setStats(int buffIndex){
        e.setAttack((int)(e.getAttack() * BUFF[buffIndex]));
        e.setMaxHealth((int)(e.getMaxHealth() * BUFF[buffIndex]));
        e.setHealth(e.getMaxHealth());
        e.setDefense((int)(e.getDefense() * BUFF[buffIndex]));
        
        if(isMiniBoss){
            isMiniBoss = false;
            if(e instanceof Slime){
                ((Slime)(e)).makeMetal();
            }
            else if(e instanceof Ghost){
                ((Ghost)(e)).makeDemon();
            }
            else if(e instanceof Flame){
               ((Flame)(e)).makePyron();
            }
            e.makeKing();
        }
    }
    
    /**Determines randomly if a Enemy (besides MrG) becomes a mini boss. Has a 30% chance.
     * @return boolean - True if it becomes a mini boss;
     * Postcondition: If the result is true the Enemy will become a mini boss.
     */
    private boolean becomeMiniBoss(){
        return (1 + (int)(Math.random()*100) <= 10);
    }
    
    public ArrayList<Enemy> getEnemies() {return enemies;}
    public int numEnemies() {return enemies.size();}
    public void clearEnemies() {enemies.clear();}
    
    /**Removes all of the enemies that are dead
     * Postcondition: All enemies with health <= 0 are removed from the enemies ArrayList.
     */
    public void clearDead() {
        for (int i = enemies.size() - 1; i >= 0; i--)
            if (enemies.get(i).getHealth() <= 0)
                enemies.remove(i);
    }
}
