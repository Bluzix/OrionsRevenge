/*
 * This is the GameObjects Class which is the parent of all the other Objects
 * which will be the ships in our game.
 */
package project7_lincoln;

import java.awt.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public abstract class GameObjects {
    //define my fields/variables
    private int x, y;
    private int rise, run;
    
    //how do I make sure that enemies don't kill each other? Assign different factions.
    private int faction = 0;
    
    private int width, height;
    
    //a variable to mark if Object should be deleted
    private boolean trashThis = false;

    /**
     * This is the full constructor for GameObjects object.
     *
     * PreCondition: GameObject or subclass needs to be created. PostCondition:
     * GameObject or subclass has been created with these values.
     *
     * @param x integer for the x position for the object.
     * @param y integer for the y position for the object.
     * @param height integer for the height of the object.
     * @param width integer for the width of the object.
     * @param rise integer for the vertical movement for the object (positive
     * for down, negative for up).
     * @param run integer for the horizontal movement for the object (positive
     * for right, negative for left).
     */
    public GameObjects(int x, int y, int height, int width, int rise, int run) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.rise = rise;
        this.run = run;
    }

    /**
     * Is used to get the x position of the object.
     *
     * PreCondition: Object has its x defined. PostCondition: Object's x
     * position returned.
     *
     * @return Object's x position as an integer.
     */
    public int getX() {
        return x;
    }

    /**
     * Is used to get the y position of the object.
     *
     * PreCondition: Object has its y defined. PostCondition: Object's y
     * position returned.
     *
     * @return Object's y position as an integer.
     */
    public int getY() {
        return y;
    }

    /**
     * Is used to set the x position of an object.
     *
     * PreCondition: Object has been created. PostCondition: Object has a new x
     * position.
     *
     * @param x An integer use to define a new x position for the object.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Is used to set the y position of an object.
     *
     * PreCondition: Object has been created. PostCondition: Object has a new y
     * position.
     *
     * @param y An integer use to define a new y position for the object.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Used to set both the x and y position of an object in one line.
     *
     * PreCondition: Object has been created. PostCondition: Object has a new x
     * and y position.
     *
     * @param x this is the new x position for the object (integer).
     * @param y this is the new y position for the object (integer).
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method is used to return the width of the object.
     *
     * PreCondition: GameObject exists and has a width value. PostCondition: The
     * width value is returned as an integer.
     *
     * @return The width value of the GameObject as an integer.
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method is used to return the height of the object.
     *
     * PreCondition: GameObject exists and has a height value. PostCondition:
     * The height value is returned as an integer.
     *
     * @return The height value of the GameObject as an integer.
     */
    public int getHeight() {
        return height;
    }

    /**
     * This is used to set the vertical movement of an object.
     *
     * PreCondition: Object has been created. PostCondition: Object has a new
     * vertical movement.
     *
     * @param rise integer which is the new vertical movement for the object
     * (positive for down and negative for up).
     */
    public void setRise(int rise) {
        this.rise = rise;
    }

    /**
     * This is used to set the horizontal movement of an object.
     *
     * PreCondition: Object has been created. PostCondition: Object has new
     * horizontal movement.
     *
     * @param run integer which is the new horizontal movement for the object
     * (positive for right and negative for left).
     */
    public void setRun(int run) {
        this.run = run;
    }

    /**
     * This is used to get the rise value, vertical movement, of an object.
     *
     * PreCondition: Object is created and has vertical movement. PostCondition:
     * Value of vertical movement returned.
     *
     * @return integer which is the vertical movement of the object.
     */
    public int getRise() {
        return rise;
    }

    /**
     * This is used to get the run value, horizontal movement, of an object.
     *
     * PreCondition: Object is created and has horizontal movement.
     * PostCondition: Value of horizontal movement returned.
     *
     * @return integer which is the horizontal movement of the object.
     */
    public int getRun() {
        return run;
    }

    /**
     * This method is used to set the objects rise, vertical, and run,
     * horizontal, movement in one statement.
     *
     * PreCondition: Object has been created. PostCondition: Object has new
     * vertical and horizontal movement values.
     *
     * @param rise integer used to set the vertical movement (positive is down,
     * negative is up).
     * @param run integer used to set the horizontal movement (positive is to
     * the right and negative is to the left).
     */
    public void setMovement(int rise, int run) {
        this.rise = rise;
        this.run = run;
    }

    /**
     * This method is used to set GameObjects to different factions; player is faction 
     * 0, enemies are set to 1, explosions are set to 2.
     * 
     * PreCondition: GameObjects exists.
     * PostCondition: Faction for this object is set by parameter.
     * 
     * @param faction The integer used to set a GameObject on different factions, 
     * 1 for enemies and 0 for player.
     */
    public void setFaction(int faction){
        this.faction = faction;
    }
    
    /**
     * This method returns the faction of the GameObject as an integer.
     * 
     * PreCondition: GameObject exists.
     * PostCondition: faction returned as an integer.
     * 
     * @return The faction of this GameObject returned as an integer.
     */
    public int getFaction(){
        return faction;
    }
    
    /**
     * This method is used to move the object depending on the rise and run
     * values.
     *
     * PreCondition: GameObject exists and has x,y,rise and run values.
     * PostCondition: GameObject has been moved.
     */
    public void move() {
        x = x + run;
        y = y + rise;
    }

    /**
     * This method is used to check if this object is to be removed.
     *
     * PreCondition: GameObject exists. PostCondition: Returns true if object is
     * marked to be removed.
     *
     * @return a boolean that states the object is to be removed.
     */
    public boolean isTrash() {
        return trashThis;
    }

    /**
     * This method marks the object to be destroyed, method for destroying
     * object must be done in a class using GameObjects which is not a subclass.
     *
     * PreCondition: GameObject exists. PostCondition: GameObject marked for
     * removal.
     */
    public void destroy() {
        trashThis = true;
    }

    public void collision(GamePanel GPanel, GameObjects other) {
        if (other != this && other.getX() >= getX() && other.getFaction() != getFaction()
                && other.getFaction() != 2 && getFaction() != 2 && 
                other.getX() <= (getX() + getWidth()) && other.getY() >= getY()
                && other.getY() <= (getY() + getHeight())) {
            isShot(GPanel);
            other.isShot(GPanel);
        }//end of if
    }

    /* Made abstract so that different objects can have different images and have 
     * a hit image.
     */
    public abstract Image getSprite();

    /* Made abstract so when an object is shot, be it enemy or player, there is
     * a different action taken.
     */
    public abstract void isShot(GamePanel GPanel);

    /* I want to make an abstract method for checking if childs of GameObjects 
     * and perform the task that is unique to them.
     */
    public abstract void outOfBounds(GamePanel gPanel);
    
    /* I want each object to have a kill method to stop all the Timers that are 
     * with it.
     */
    public abstract void kill();
    
}//end of GameObject abstract class, parent to player and enemy objects
