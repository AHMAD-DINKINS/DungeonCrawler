
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Game extends JPanel implements KeyListener {
    private Toolkit t = Toolkit.getDefaultToolkit();
    
    /**
     * Map Related Stuff
     */
    private static Map map = new Map(30, 20);
    private boolean[][] revealed = new boolean[map.height()][map.width()];
    private boolean lockLogText = false;

    private final int tileSize = 32;
    private int pressTime = 0;
    
    /*# temporary, for testing purposes: */
        public final boolean isWindows = false;
    /*# temporary, for testing purposes END */
    
    /**
     * Info Text
     */
    private Font font;
    private String logText = "Welcome to Dungeon Crawler 1.0! To view controls, press C.";
    private String controls = "Arrow keys to move.\n" +
                              "X to wait a turn\n" +
                              "Z, then an arrow key to attack in that direction.\n" +
                              "D to view inventory and current stats\n"+
                              "1-6 to use items when viewing inventory\n"+
                              "O to open chests.";
    /**
     * Window Size Constants
     */
    //MAC
    //private final int _xBuffer = 0, _yBuffer = 22;
    //WINDOWS
    private final int _xBuffer = 16, _yBuffer = 38;
    
    private final int windowWidth = _xBuffer + tileSize * map.width(),
                      windowHeight = _yBuffer + tileSize * map.height();
    
    private static final String TITLE = "Dungeon Crawler 1.0";
    
    
    /**
     * Gameplay Elements
     */
    private String phase = "move", difficulty;
    private int turns = 0, level = 1, killCount = 0,currentScore = 0;
    private static int highScore = 0;
    
    private Player player;
    private EquippableItem[] playerEquipment;
    private Point stairs = map.stairPos(), playerSpawn = map.startPos();
    
    private EnemyMap eMap;
    
    private ItemMap iMap = new ItemMap();
    private ChestMap cMap;
    
    public Game(String playerName, String difficulty, String character) {
        super();
        
        this.difficulty = difficulty;
        
        eMap = new EnemyMap(difficulty);
        eMap.spawn(level, map);
        
        iMap.spawn(level, map);
        cMap = new ChestMap(difficulty);
        
        player = new Player(playerName, 5, 40, 10, playerSpawn, character);
        playerEquipment = player.getEquipped();
        
        loadFont();
    }
    
    /**
     * Creates a new map:
     * - resets revealed tiles
     * - clears enemy list
     * - spawns new set of enemies and items
     */
    private void createNewMap() {
        map.newMap();
        revealed = new boolean[map.height()][map.width()];
        level++;
        
        eMap.spawn(level, map);
        iMap.spawn(level, map);
        cMap.spawn(level, map, eMap, iMap);
        
        stairs = map.stairPos();
        playerSpawn = map.startPos();
        
        player.setPoint(playerSpawn);
    }
    
    /**
     * Main paint method
     */
    public void paint(Graphics g) {
        clearScreen(g);
        drawMap(g);
        drawItems(g);
        drawChests(g);
        drawFog(g);
        drawPlayer(g);
        drawEnemies(g);
        drawLogText(g);
        drawScreens(g);
        drawStatuses(g);
        g.dispose();
    }
    
    /**
     * Draws a black rectangle that covers the whole window
     */
    public void clearScreen(Graphics g){ 
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, windowWidth, windowHeight);
        g.fillRect(0, 0, windowWidth, windowHeight);
    }
    
    /**
     * Draws out statuses.
     */
    private void drawStatuses(Graphics g) {
        int x = windowWidth - _xBuffer - tileSize, y = tileSize + 4;
        int buffer = 4, offset = 2, yalign = 20;

        for (Status s : player.getStatuses()) {
            if (s.duration() == -1) continue;

            String text = s.duration() + " +" + s.amount() + "%";

            g.drawImage(loadImage(s.getEffect() + "Buff"), x, y, this);
            drawStringWithShadow(g, text, x - g.getFontMetrics().stringWidth(text) - buffer, y + yalign);
            
            y += 28;
        }
    }
    
    /**
     * Draws out map tiles. Rules for drawing:
     * - Walls and stairs are drawn as they are revealed
     * - Items outside of player fieldOfViewView have a 'fog'.
     */
    private void drawMap(Graphics g) {
        for (int y = 0; y < map.height(); y++) {
            for (int x = 0; x < map.width(); x++) {
                if (player.isWithinView(x, y)) {
                    revealed[y][x] = true;
                }
                
                if (map.isWall(x, y) && revealed[y][x])
                    if(map.isNextToRoom(x, y))
                        g.drawImage(loadImage("wall"), getPos(x), getPos(y),this);
                    else
                        g.drawImage(loadImage("black"), getPos(x), getPos(y),this);
                else if(!map.isWall(x,y) && revealed[y][x])
                    g.drawImage(loadImage("floor"), getPos(x), getPos(y),this);
            }
        }
        
        if (revealed[stairs.y()][stairs.x()])
            g.drawImage(loadImage("stairs"), getPos(stairs.x()), getPos(stairs.y()),this);
    }

    /**
     * Method to draw enemies. Rules:
     * - Enemies are drawn only if they are within player fieldOfView
     */
    private void drawEnemies(Graphics g) {
        for (Enemy e : eMap.getEnemies())
            if (player.isWithinView(e.getPoint())) {
                g.drawImage(loadImage(e.getImage()), getPos(e.getPoint().x()), getPos(e.getPoint().y()),this);
                drawHPBar(g, e);
            }
    }

    /**
     * Method to draw fog.
     */
    private void drawFog(Graphics g) {
        for (int y = 0; y < map.height(); y++)
            for (int x = 0; x < map.width(); x++)
                if (!player.isWithinView(x, y))
                    g.drawImage(loadImage("fog"), getPos(x), getPos(y),this);
    }

    /**
     * Method to draw items. Rules:
     * - Items on the floor are drawn only if they have been revealed
     */
    private void drawItems(Graphics g) {
        for (Item i : iMap.items()){
            if (revealed[i.getPoint().y()][i.getPoint().x()])
                g.drawImage(loadImage(i.getName()), getPos(i.getPoint().x()), getPos(i.getPoint().y()), this);
        }
    }
    
    /**
     * Method to draw chests. Rules:
     * - Chests are drawn only if they have been revealed
     */
    private void drawChests(Graphics g) {
        for (Chest i : cMap.chests())
            if (revealed[i.getPoint().y()][i.getPoint().x()])
                g.drawImage(loadImage(i.getImage()), getPos(i.getPoint().x()), getPos(i.getPoint().y()), this);
    }
    
    /**
     * Method to draw the player. Rules:
     * - During attack phase an attack icon is drawn over the player.
     */
    private void drawPlayer(Graphics g) {
        g.drawImage(loadImage(player.getImage()), getPos(player.getPoint().x()), getPos(player.getPoint().y()),this);
        playerEquipment = player.getEquipped(); 
        if(playerEquipment[0] != null && !phase.equals("dead"))
            g.drawImage(loadImage(playerEquipment[0].getName()), getPos(player.getPoint().x()), getPos(player.getPoint().y()), this);
        if(playerEquipment[1] != null && !phase.equals("dead"))
            g.drawImage(loadImage(playerEquipment[1].getName()), getPos(player.getPoint().x()), getPos(player.getPoint().y()), this);
        drawHPBar(g, player);
    }
    
    /**
     * Draw the HP bar for any given entity
     */
    private void drawHPBar(Graphics g, Entity e) {
        int x = getPos(e.getPoint().x()) + 3;
        int y = getPos(e.getPoint().y()) - 3;
        int width = 26;
        
        double ratio= (double)e.getHealth()/e.getMaxHealth();
        
        g.setColor(Color.RED);
        g.fillRect(x, y, width, 2);
        g.setColor(Color.BLACK);
        g.drawRect(x - 1, y - 1, width + 1, 3);
        g.setColor(Color.GREEN);
        g.fillRect(x, y, (int)(width * ratio), 2);
    }
    
    /**
     * Loads the font.
     */
    private void loadFont() {
        String fontFile = "/font/PrintChar21.ttf";
        String fontName = "Print Char 21";
        int fontSize    = 16;
        
        try{ GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream(fontFile)));
        } catch (FontFormatException e) {
            log("Error- font not formatted correctly.");
        } catch (IOException e) {
            log("Error- font file not found.");
        }
        
        font = new Font(fontName, Font.PLAIN, fontSize);
    }
    
    /**
     * Draws the message window at the top.
     */
    private void drawLogText(Graphics g) {
        if (player.getHealth() < 0) player.setHealth(0);

        if(turns > 0 && !lockLogText)
            logText = "Floor " + level + 
                      " Turn " + turns +
                      " Kill Count " + killCount +
                      " Name " + player.getName() +
                      " Health " + player.getHealth() + "/" + player.getMaxHealth();
        
        //draw shade
        g.setColor(new Color(0,0,0,100));
        g.fillRect(0, 0, windowWidth, tileSize);
        
        int x = 5, y = 23;
        g.setFont(font);
        drawStringWithShadow(g, logText, x, y);
        lockLogText = false;
    }

    /**
     * Draws various screens.
     */
    private void drawScreens(Graphics g) {
        if (phase.equals("items")) drawInventoryScreen(g);
        else if (phase.equals("attack")) g.drawImage(loadImage("attackMode"), getPos(player.getPoint().x()), getPos(player.getPoint().y()),this);
        else if (phase.equals("dead"))drawGameOverScreen(g);
    }

    /**
     * Shortcut to drawing text
     */
    private void drawStringWithShadow(Graphics g, String text, int x, int y) {
        int shift = 2;
        g.setColor(Color.BLACK);
        g.drawString(text, x - shift, y + shift);
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
    }

    /**
     * Draws the inventory screen.
     */
    private void drawInventoryScreen(Graphics g) {
        String[] potions = {"HealthPotion", "AttackPotion", "DefensePotion"};

        //image dimensions: 350 x 200
        int x = (windowWidth - 350) / 2,
            y = (windowHeight - 200) / 2 - _yBuffer,
            buffer = 23;

        //draw shade
        g.setColor(new Color(0,0,0,100));
        g.fillRect(0, 0, windowWidth, windowHeight);

        g.drawImage(loadImage("inventory"), x, y,this);

        //draw player stats
        y += 60;
        drawStringWithShadow(g, "%HP " + player.getHealth() * 100 / player.getMaxHealth(), x + 360, y);
        drawStringWithShadow(g, "ATK " + player.attack(), x + 360, y + buffer);
        drawStringWithShadow(g, "DEF " + player.getBuffedDefense(), x + 360, y + buffer * 2);  
        drawStringWithShadow(g, "Score " + currentScore, x + 360, y + buffer * 3);
        y -= 60;

        x += 59;
        y += 52;

        for(String potion : potions) {
            g.drawImage(loadImage(potion), x, y, this);

            String text = "" + player.numInInventory(potion);
            g.setColor(new Color(0,0,0,100));
            g.fillRect(x, y, tileSize, tileSize);
            drawStringWithShadow(g, text, x + (tileSize - g.getFontMetrics().stringWidth(text)) / 2, y + buffer);

            x += 97;
        }

        y += 65;
        x -= 97 * 3;

        for (String potion : potions) {
            g.drawImage(loadImage("big" + potion), x, y, this);

            String text = "" + player.numInInventory("big" + potion);
            g.setColor(new Color(0,0,0,100));
            g.fillRect(x, y, tileSize, tileSize);
            drawStringWithShadow(g, text, x + (tileSize - g.getFontMetrics().stringWidth(text)) / 2, y + buffer);

            x += 97;
        }
    }
    
    /**
     * Draws the Game Over screen.
     */
    private void drawGameOverScreen(Graphics g) {
        //image dimensions: 350 x 200
        int x = (windowWidth - 350) / 2,
            y = (windowHeight - 200) / 2 - _yBuffer,
            buffer = 23;

        //draw shade
        g.setColor(new Color(0,0,0,100));
        g.fillRect(0, 0, windowWidth, windowHeight);

        g.drawImage(loadImage("gameover"), x, y,this);

    }

    public void keyPressed(KeyEvent e) {
        pressTime++;
        if (pressTime < 5) return;

        if (pressTime % 2 == 1)
            evaluateAction(e);
    }
    public void keyTyped(KeyEvent e) {}

    /**
     * Checks for player actions.
     * - Dead enemies are not drawn and are removed from EnemyMap
     */
    public void keyReleased(KeyEvent e) {
        pressTime = 0;
        evaluateAction(e);
    }

    public void evaluateAction(KeyEvent e) {
        if (phase.equals("dead")){
            return;
        }

        if (!isValidKey(e)) return;

        int keyCode = e.getKeyCode();

        if (isValidActionKey(e)) {
            if (!phase.equals("move")) phase = "move";
            else if (keyCode == KeyEvent.VK_X) phase = "wait";
            else if (keyCode == KeyEvent.VK_Z) {
                if (phase.equals("attack"))
                    phase = "move";
                else
                    phase = "attack";
            }
            else if (keyCode == KeyEvent.VK_D) phase = "items";
            else if (keyCode == KeyEvent.VK_O){
                if (cMap.chests().size() < 1) return;
                phase = "open";
            }
            else if (keyCode == KeyEvent.VK_C) JOptionPane.showMessageDialog(this, controls, getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }

        if (isValidInventoryKey(e) && phase.equals("items")) {
            turns++;

            useItem(e);

            //Call enemy update methods
            for(Enemy em: eMap.getEnemies())
                em.move(map, eMap, cMap, player);

            //Update kill count
            int numEnemies = eMap.getEnemies().size();
            eMap.clearDead();
            killCount+= numEnemies - eMap.getEnemies().size();

            player.updateStatuses();
            phase = "move";

        } else if (phase.equals("wait") || phase.equals("open") ||
                (isValidMovementKey(e) && (phase.equals("move") || phase.equals("attack")))) {
            Point p = intendedDestination(e);
            turns++; //advance the turn count

            if (phase.equals("move")) {
                if (!isBlocked(p.x(), p.y())) {
                    player.setPoint(p.x(), p.y());
                } else {
                    turns--; //prevent player from wasting turns walking into walls
                    return;
                }

            } else if (phase.equals("attack")) {
                if (eMap.exists(p.x(), p.y())) {
                    //Attack the enemy at point p
                    eMap.get(p.x(), p.y()).takeDamage(player.attack());
                } else {
                    phase = "move";
                }
            } else if (phase.equals("open")) {
                Point chestPos = null;
                p = player.getPoint();

                if(nextToChest(player.getPoint())){
                    if(cMap.exists(p.x() + 1, p.y()))
                        chestPos = new Point(p.x() + 1, p.y());
                    else if(cMap.exists(p.x() - 1, p.y()))
                        chestPos = new Point(p.x() - 1, p.y());
                    else if(cMap.exists(p.x(), p.y() + 1))
                        chestPos = new Point(p.x(), p.y() + 1);
                    else if (cMap.exists(p.x(), p.y() - 1))
                        chestPos = new Point(p.x(), p.y() - 1);
                } else {
                    turns--;
                    return;
                }
                EquippableItem i = cMap.get(chestPos).open();

                int old_hp = player.getMaxHealth();
                int old_atk = player.attack();

                player.addItem(i);
                cMap.chests().remove(cMap.get(chestPos));

                player.updateEquipment();
                player.updateStatuses();

                logText = "You found a new " + i.getName().substring(0, 5) + "! ";
                if (player.getMaxHealth() == old_hp && player.attack() == old_atk) logText += "Unfortunately, it was trash. You toss it out.";
                else {
                    logText += "You equip it immediately.";
                    if (i.getM_attackBonus() > 0) logText += " ATK +" + (player.attack() - old_atk) + "!";
                    if (i.getM_healthBonus() > 0) logText += " MHP +" + (player.getMaxHealth() - old_hp) + "!";
                }

                lockLogText = true;
            }

            //Call enemy update methods
            for(Enemy em: eMap.getEnemies())
                em.move(map, eMap, cMap, player);

            //Update the current score
            for(Enemy em: eMap.getEnemies())
                if(em.getHealth() <= 0 && em.getScore() > 0){
                    player.addToScore(em.getScore());
                }
            currentScore = player.getScore();

            //Update kill count
            int numEnemies = eMap.getEnemies().size();
            eMap.clearDead();
            killCount+= numEnemies - eMap.getEnemies().size();

            if (!phase.equals("open")) player.updateStatuses();
            if (!(phase.equals("attack") && nextToEnemy())) phase = "move";
        }

        if(iMap.exists(player.getPoint())) player.addItem(iMap.take(player.getPoint()));
        if (player.getPoint().equals(stairs)) createNewMap();

        repaint();

        if (player.getHealth() <= 0){
            player.setHealth(0);
            player.setImage("gravestone");
            phase = "dead";
            if(currentScore > highScore)
                highScore = currentScore;
            JOptionPane.showMessageDialog(this, "Your Score: " + currentScore, getTitle(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /**
     * Checks if a given keypress is valid. Prevents screen flickering
     */
    private boolean isValidKey(KeyEvent e) {
        return (
                    isValidMovementKey(e) ||
                    isValidActionKey(e) ||
                    isValidInventoryKey(e)
                );
    }

    /**
     * Checks if a given key is a valid action.
     */
    private boolean isValidActionKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        return (
                    keyCode == KeyEvent.VK_Z ||
                    keyCode == KeyEvent.VK_X ||
                    keyCode == KeyEvent.VK_D ||
                    keyCode == KeyEvent.VK_O ||
                    keyCode == KeyEvent.VK_C
                );
    }
    
    /**
     * Checks if a given key is an arrow key.
     */
    private boolean isValidMovementKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        return (
                    keyCode == KeyEvent.VK_UP ||
                    keyCode == KeyEvent.VK_DOWN ||
                    keyCode == KeyEvent.VK_LEFT ||
                    keyCode == KeyEvent.VK_RIGHT
                );
    }

    /**
     * Checks if a given key is an inventory key.
     */
    private boolean isValidInventoryKey(KeyEvent e) {
        int keyCode = e.getKeyCode();
        return (
                    keyCode == KeyEvent.VK_1 ||
                    keyCode == KeyEvent.VK_2 ||
                    keyCode == KeyEvent.VK_3 ||
                    keyCode == KeyEvent.VK_4 ||
                    keyCode == KeyEvent.VK_5 ||
                    keyCode == KeyEvent.VK_6
               );
    }
    
    /**
     * Checks if the player is next to a chest.
     */
    private boolean nextToChest(Point p){
        //checks to see if where the chest is
        return (cMap.exists(p.x() + 1, p.y()) ||
                cMap.exists(p.x() - 1, p.y()) ||
                cMap.exists(p.x(), p.y() + 1) ||
                cMap.exists(p.x(), p.y() - 1));
    }

    /**
     * Checks if the player is next to an enemy.
     */
    private boolean nextToEnemy(){
        Point p = player.getPoint();
        //checks to see if where the chest is
        return (eMap.exists(p.x() + 1, p.y()) ||
                eMap.exists(p.x() - 1, p.y()) ||
                eMap.exists(p.x(), p.y() + 1) ||
                eMap.exists(p.x(), p.y() - 1));
    }

    /**
     * Use items
     */
    private void useItem(KeyEvent e) {
        int itemId = e.getKeyCode();
        itemId -= KeyEvent.VK_1;
        
        String[] items = {"HealthPotion", "AttackPotion", "DefensePotion"};
        String name = items[itemId / 2];
        if (itemId % 2 == 1) name = "big" + name;

        ConsumableItem i = (ConsumableItem)(player.removeItem(name));
        if (i == null) return;

        for (Status s : i.getM_status()) {
            Status s0 = player.getStatus(s);
            if (s0 == null)
                player.addStatus(s);
            else
                s0.addDuration(s.duration());
        }
    }
    
    /**
     * Returns the intended destination of a movement key.
     */
    private Point intendedDestination(KeyEvent e) {
        if (phase.equals("wait")) return null;
        
        int keyCode = e.getKeyCode();
        Point target = new Point(player.getPoint());
        
        if (keyCode == KeyEvent.VK_UP)
            target.changeY(-1);
        else if (keyCode == KeyEvent.VK_DOWN)
            target.changeY(1);
        else if (keyCode == KeyEvent.VK_LEFT)
            target.changeX(-1);
        else if (keyCode == KeyEvent.VK_RIGHT)
            target.changeX(1);
            
        return target;
    }
    
    /**
     * Checks if a given block is passable.
     */
    private boolean isBlocked(int x, int y) {
        return (
                    map.isWall(x, y) ||
                    eMap.exists(x, y) ||
                    cMap.exists(x, y) ||
                    player.getPoint().equals(x,y)
               );
    }

    /**
     * Loads an image based on a filename.
     */
    private Image loadImage(String s){
        return loadImageForBuild(s);

//        if ((new File("img/" + s + ".png")).exists())
//            return t.getImage("img/" + s + ".png");
//
//        log("Error- image not found");
//        return t.getImage("img/error.png");
    }

    private Image loadImageForBuild(String s) {
        return (new javax.swing.ImageIcon(getClass().getResource("/img/" + s + ".png"))).getImage();
    }
    
    /**
     * Scales Point values to a x/y value
     */
    private int getPos(int index) {
        return index * tileSize;
    }
    
    /**
     * A shortcut for System.out.println() because I'm lazy
     */
    private void log(Object s) {
        System.out.println(s);
    }
    
    public static String getTitle(){
        return TITLE;
    }
    
    /**
     * Runs the game.
     */
    public static void main(String[] args) {  
        JFrame f = new JFrame();
        
        String[] difficulties = {"INSANE!!!!","Hard","Normal",};
        String[] characters = {"boy","girl","alien"};
        String[] chooseGender = {"male","female"};
        String name = "";
        int index = 2, cIndex = 2;
        
        /*# For testing purposes*/
        final boolean skipMessages = false;
        Game game;
        
        if (!skipMessages) {
            /*# For testing purposes END*/

            name = JOptionPane.showInputDialog(f, "What is your name?", Game.getTitle(),
                    JOptionPane.QUESTION_MESSAGE);
            if (name == null || name.length() == 0)
                name = "Player 1";

            cIndex = JOptionPane.showOptionDialog(f, "Gender?", Game.getTitle(), 0,
                    JOptionPane.QUESTION_MESSAGE, null, chooseGender, 2);
            if (cIndex < 0)
                cIndex = 2; // If they didn't choose a gender

            index = JOptionPane.showOptionDialog(f, "Difficulty?", Game.getTitle(), 0,
                    JOptionPane.QUESTION_MESSAGE, null, difficulties, 2);
            if (index < 0) {
                index = 2;
                JOptionPane.showMessageDialog(f, "You did not select a difficulty! "
                        + "Defaulted to Normal.", Game.getTitle(), JOptionPane.ERROR_MESSAGE);
            }

            
            game = new Game(name, difficulties[index], characters[cIndex]);
            JOptionPane.showMessageDialog(null, "To view controls press C", getTitle(), JOptionPane.INFORMATION_MESSAGE);
        } else {
            game = new Game("Player", difficulties[2], characters[0]);
        }

        int _xBuffer = 0, _yBuffer = 22;
        
        if (game.isWindows) {
            _xBuffer = 16;
            _yBuffer = 38;
        }
        
        f.add(game);
        f.addKeyListener(game);
        f.setSize(map.width() * 32 + _xBuffer, map.height() * 32 + _yBuffer);
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
    }
}  
