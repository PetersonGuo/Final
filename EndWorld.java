import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.*;
import java.util.*;

/**
 * This is the world that appears when the game has ended. There are 2 different screens
 * depending on whether the player has succeeded in completing the objective or if they
 * failed to complete it. There is also a scoreboard that keeps track of the players' 
 * scores and allows the player to play again.
 * 
 * @author Isaac Chan, Jett Miyasaki 
 * @version June 15, 2023
 * 
 * Background: Same creator as previosly mentioned in StartWorld, edited by Jett M.
 */
public class EndWorld extends Worlds {
    private GreenfootImage background;
    private Text endResult, scoreText, hScore1, hScore2, hScore3;
    private Sound sound;
    
    //used to display score
    private FileWriter userScore;
    private PrintWriter output;
    private Scanner scan;
    private int lines;
    private boolean linesLeft, win;
    private ArrayList<Integer> scoreboard;
    /**
     * Constructor for objects of class EndWorld.
     */
    
    public EndWorld(boolean win, int score) {
        background = new GreenfootImage("CandyEndScreen.png");
        background.scale(FINAL.WORLD_WIDTH, FINAL.WORLD_HEIGHT);
        setBackground(background);
        
        if(win) sound = new Sound("victory.mp3");
        else sound = new Sound("lose.mp3");
        
        //text visuals
        if(MainWorld.isObjectiveCompleted()) endResult = new Text("Mission Completed!", Color.WHITE, 50);
        else endResult = new Text("Mission Failed...", Color.WHITE, 50);
        addObject(endResult, FINAL.WORLD_WIDTH /2, FINAL.WORLD_HEIGHT/ 8);    
        
        scoreText = new Text("Score: " + score, Color.BLUE, 30);
        addObject(scoreText, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT/ 4);
        
        scoreboard = new ArrayList<Integer>();
        Text topScores = new Text("Top Scores:", Color.BLUE, 30);
        addObject(topScores, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 3 + 30);
        updateScore();
        displayScore();
        
        Text play = new Text("Press Space to Play Again", Color.RED, 30);
        addObject(play, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT - 100);
    }
    
    /**
     * Act - do whatever the EndWorld wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() {
        if(Greenfoot.isKeyDown("space")) {
            sound.stop();
            Greenfoot.setWorld(new StartWorld());
        }
    }
    
    /**
     * A method that runs when the world is started.
     */
    public void started(){
        sound.play();
    }
    
    /**
     * A method that runs when the world is stopped.
     */
    public void stopped(){
        sound.stop();
    }
    
    /**
     * A method that saves the score to a file.
     */
    public void updateScore() {
        try{
            userScore = new FileWriter("score.txt", true);
            output = new PrintWriter(userScore);
            output.println(MainWorld.getPoints());
            output.close();
        }
        catch(IOException e){
            System.out.println("No File Found...Cannot update scoreboard");
        }
    }
    
    /**
     * A method that displays the score to the player.
     */
    public void displayScore() {
        try {
            scan = new Scanner(new File("score.txt"));
        } catch(FileNotFoundException e){
        }
        while(linesLeft){
            try {
                scoreboard.add(Integer.parseInt(scan.nextLine()));
            }
            catch(NoSuchElementException e){
                linesLeft = false;
            }
        }
        //sorting method called from Collections
        Collections.sort(scoreboard);
        Collections.reverse(scoreboard);
        
        //print score to screen
        try{
            hScore1 = new Text("Score: " + Integer.toString(scoreboard.get(0)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore1 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        try{
            hScore2 = new Text("Score: " + Integer.toString(scoreboard.get(1)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore2 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        try{
            hScore3 = new Text("Score: " + Integer.toString(scoreboard.get(2)), Color.BLUE, 30);
        }
        catch(IndexOutOfBoundsException e){
            hScore3 = new Text("Score: " + Integer.toString(0), Color.BLUE, 30);
        }
        
        addObject(hScore1, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2);
        addObject(hScore2, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2 + 40);
        addObject(hScore3, FINAL.WORLD_WIDTH / 2, FINAL.WORLD_HEIGHT / 2 + 80);
    }
}
