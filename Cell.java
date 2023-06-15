import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * A class called Cell that is generated by the game grid that acts as both
 * a storage for candies but also a visual representation of the game grid.
 * Cells have easy access to the candies that it contains.
 * 
 * @author Kelby To, Isaac Chan, Peterson Guo
 * @version June 15, 2023
 */
public class Cell extends Actor {
    private Candy candy;
    private int size;
    private GreenfootImage img;
    private final static Color selected = new Color(250,250,250), outline = Color.BLACK, normal = new Color(180,180,180);
    /**
     * A constructor that creates a cell and draws it.
     */
    public Cell(){
        size = FINAL.CELL_SIZE;
        img = new GreenfootImage(size, size);
        draw(normal);
    }
    
    /**
     * A method that draws a square based on the colour it takes.
     * 
     * @param c     The colour of the square
     */
    public void draw(Color c) {
        img.setColor(c);
        img.fill();
        img.setColor(outline);
        img.drawRect(0,0,size,size);
        setImage(img);
    }
    
     /**
     * Act - do whatever the Cell wants to do. This method is called whenever.
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if (MainWorld.getClicked() != null && MainWorld.getClicked().equals(candy))
            draw(selected);
        else draw(normal);
    }
    
    /**
     * A method that compares if a candy within this cell is the same colour.
     * as the candy within another cell.
     * 
     * @param c         The cell that contains another candy
     * @return boolean  If the colours are the same, return true otherwise false
     */
    public void setCandy(Candy c){
        candy = c;
        //candy.setLocation(getX(), getY());
        candy.setOrigin(new Pair(getX(), getY()));
    }
    
    /**
     * A setter method that sets the candy within this cell to another candy.
     * 
     * @param c     The candy being put into this cell
	 * @return Candy   The candy that this cell contains
     */
    public Candy getCandy(){
        return candy;
    }
    
    /**
     * A getter method that returns the candy within this cell.
     * 
     * @return Candy    The candy that this cell contains
     */
    public boolean comp(Cell c) {
        return candy.comp(c.getCandy());
    }
}
