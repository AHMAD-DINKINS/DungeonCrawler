public abstract class Item{
    private String m_name;
    private Point point;
    /**
     * The constructor for Item which takes a string for the name as a parameter
     * @param name - The name of the item
     */
    public Item(String name){
        m_name = name;
        point = null;
    }
    
    /**
     * The second constructor for Item which takes a name and Point variable as parameters
     * @param name - The name of the object
     * @param p - The point of the item 
     */
    public Item(String name, Point p){
        m_name = name;
        point = p;
    }
    
    /**
     * Returns the name of the item
     * @return name - The name of the item
     */
    public String getName(){
        return m_name;
    }
    
    /**
     * Sets the position of the item
     * @return Point point - The coordinates of the Item
     */
    public void setPoint(int x, int y){
        point = new Point(x,y);
    }
    /**
     * Sets the position of the item
     * @return Point point - The coordinates of the Item
     */
    public void setPoint(Point p){
        point = p;
    }
    
    /**
     * Returns the position of the item
     * @return Point point - The coordinates of the Item
     */
    public Point getPoint(){
        return point;
    }
    
    public String toString(){
        return "Name: " + m_name;
    }
}