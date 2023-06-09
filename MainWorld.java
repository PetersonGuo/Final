import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The main world runs the main portion of the game. This game is an iteration
 * of the game Candy Crush where the goal is to get the highest score while 
 * completing the main objective, determined by the user. The game requires the
 * player to match candies of the same colours into groups of 3 or greater either
 * vertically or horizontally. There are special candies that can be generated 
 * if the match is greater than 3 or creates a cross-section between 2 matches.
 * Score will be calculated breaking candies and completing the objective. A
 * player has a certain number of moves when playing so players must be wise with
 * their decision-making. 
 * <p>
 * This game was created by the following individuals: 
 * Isaac Chan, Peterson Guo, Kelby To, Kevin Luo, Jett Miysaki, William Tsao. <br>
 * Credit goes to Mr.Cohen for providing the following classes: SuperStatBar <br>
 * Credit goes to Greenfoot for providing the following classes: Text, Counter
 * <p>
 * Sources: <br>
 * Candy Visuals: King (developers of Candy Crush) <br>
 * Game Sounds: King (developers of Candy Crush) <br>
 * 
 * Current Bugs: <br>
 * - Random occurances of striped candies returning null and randomly using ability <br>
 * - Simultaneous unexpected generations of special candies <br>
 * - Small glitches with animation <br>
 * - Randomly generated candy is sometimes not very random <br>
 * - Candy selection can randomly stop working (rare) <br>
 * Unfinished Code: <br>
 * - Candy Count Objective <br>
 * - Instruction World Images <br>
 * 
 * @author Peterson Guo, Kevin Luo, Kelby To, Isaac Chan 
 * @version June 15, 2023
 */
public class MainWorld extends Worlds {
    private static GameGrid grid;
    private static Candy clicked;
    //Candy Count variables
    private static Counter score, moves;
    private static int counter, totalCandy, colour;
    private static boolean objComplete;
    private Objectives obj;
    private Sound ingredient, background;
    
    //Ingredients variables
    /**
     * Constructor for objects of class MyWorld.
     */
    public MainWorld(boolean choice) {
        score = new Counter("Score: ");
        moves = new Counter("Moves: ");
        background = new Sound("background.mp3");
        background.play(20);
        moves.setValue(20);

        addObject(score, 100, 50);
        addObject(moves, 300, 50);
        
        grid = new GameGrid(10,10);
        addObject(grid, 400, 400);
        grid.addCandies();
        
        obj = StartWorld.getObj();
        if (getObjects(Ingredient.class).size() == 0 && obj instanceof DropIngredients)
            grid.addIngredient();
        
        clicked = null;
        objComplete = false;
    }
    
    /**
     * Act - do whatever the MainWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if(moves.getValue() == 0){
            background.stop();
            Greenfoot.setWorld(new EndWorld(false, score.getValue()));
        }
        if (obj instanceof CandyCount){ //if the objective is candy count
            //something
        }
        if (obj instanceof DropIngredients){ //if the objective is getting ingredients
            for(Candy c : grid.getRow(9)){
                if(c instanceof Ingredient){
                    grid.removeFromWorld(grid.getGridCoor(c));
                    ((DropIngredients)obj).decreaseIngredients();
                    grid.drop();
                    ingredient = new Sound("ingredient.mp3");
                    ingredient.play();
                }
            }
        }
        if (DropIngredients.getTotalIngredients() != 0 && getObjects(Ingredient.class).size() == 0){
            grid.addIngredient();
        }
        if (DropIngredients.getTotalIngredients() == 0){
            objComplete = true;
            Greenfoot.setWorld(new EndWorld(true, score.getValue()));
        }
        background.loop();
    }

    /**
     * A method that runs when the world is stopped.
     */
    public void stopped(){
        background.stop();
    }
    
    /**
     * A method that returns a boolean depending on whether the selected candy
     * can swap with another selected candy.
     * 
     * @param c         The first selected candy
     * @return boolean  A boolean that determines if a swap can be made 
     */
    public static boolean setClicked(Candy c) {
        boolean valid = false;
        if (clicked == null || c == null || clicked.equals(c)) clicked = c; //first click
        else if(clicked.getWorld() != null && c.getWorld() != null){
            //checks if the second click can be swapped with the first click
            if ((Math.abs(c.getX() - clicked.getX()) <= FINAL.CELL_SIZE && c.getX() != clicked.getX() && c.getY() == clicked.getY()) || 
                (Math.abs(c.getY() - clicked.getY()) <= FINAL.CELL_SIZE && c.getY() != clicked.getY() && c.getX() == clicked.getX())) {
                valid = grid.validSwap(grid.getGridCoor(c), grid.getGridCoor(clicked));
                clicked = null;
            } else clicked = c;
        }
        if(valid) decreaseMoves();
        return valid;
    }
    
    /**
     * A method that adds points to the score
     * 
     * @param pts   The number of points the score increases by
     */
    public static void addPoints(int pts){
        score.changeValue(pts);
    }

    /**
     * A method that decreases the number of moves the player has left
     */
    public static void decreaseMoves(){
        moves.changeValue(-1);
    }
    
    /**
     * A method that determines if the objective is completed.
     */
    public static void objectiveCompleted(){
        objComplete = true;
    }
    
    public static boolean isObjectiveCompleted(){
        return objComplete;
    }
    
    /**
     * A method that returns the world's GameGrid
     * 
     * @return GameGrid     The game grid in the world
     */
    public static GameGrid getGrid() {
        return grid;
    }
    
    /**
     * A method that returns the candy thats currently being clicked
     * 
     * @return Candy    The candy thats being clicked
     */
    public static Candy getClicked() {
        return clicked;
    }
    
    public static int getPoints() {
        return score.getValue();
    }
}
