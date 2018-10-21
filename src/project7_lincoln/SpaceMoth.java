/*
 * This is the class that contains the object SpaceMoth, the enemy that goes left 
 * and right of GamePanel.
 */
package project7_lincoln;

import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class SpaceMoth extends GameObjects {
    //our sprite of SpaceMoth
    private Image sprite = new ImageIcon("images/enemyMoth.PNG").getImage();
    
    /**
     * This method creates an object SpaceMoth, an enemy to the player that moves
     * left and right across the screen.
     * 
     * PreCondition: SpaceMoth is to be made and starts at integer parameter.
     * PostCondition: SpaceMoth is created at (-45,y).
     * 
     * @param y The y position you want the SpaceMoth to start at.
     */
    public SpaceMoth(int y){
        super(-45, y, 50, 90, 0, 5);
        setFaction(1);
    }//end constructor
    
    /**
     * This is a constructor for creating SpaceMoths on the left or right of GamePanel.
     * 
     * PreCondition: a new SpaceMoth is called and given a valid GamePanel.
     * PostCondition: a new SpaceMoth is created that is to the left or right of 
     * GamePanel according to the boolean parameter.
     * 
     * @param y This is the y position you want the moth to start at.
     * @param fromRight This is a boolean, give it true and the moth will start at 
     * the right side of GamePanel.
     * @param GPanel This is a valid GamePanel you wish to pair SpaceMoth with.
     */
    public SpaceMoth(int y, boolean fromRight, GamePanel GPanel){
        //use super first
        super(-45, y, 50, 90, 0 , 5);
        setFaction(1);
        //run a check to see if we spawn these on the right
        if (fromRight){
            this.setX(GPanel.getWidth() - 45);
            this.setRun(-5);
        }
    }
    
    /**
     * This method is used to check if SpaceMoth is outside of GamePanel.
     * 
     * PreCondition: SpaceMoth exists and the GamePanel is valid
     * PostCondition: SpaceMoth is moved and its run is set the other way.
     */
    @Override
    public void outOfBounds(GamePanel GPanel){
        //if to set SpaceMoth half-way in GPanel if it is too far left.
        if (getX() < (-1 * getWidth()/2)){
            setX(-1*getWidth()/2);
            setRun(getRun() * -1);
        }
        //else if too far right
        else if (getX() > GPanel.getWidth() - getWidth()/2){
            setX(GPanel.getWidth() - getWidth()/2);
            setRun(getRun() * -1);
        }
        
        //check if too high up
        if (getY() < (-1 * getHeight()/2)){
            setY(-1 * getHeight()/2);
        }
        else if (getY() > GPanel.getHeight() - 100){
            setY(GPanel.getHeight() - 100);
        }
    }
    
    /**
     * This method returns the image that represents SpaceMoth.
     * 
     * PreCondition: SpaceMoth exists.
     * PostCondition: SpaceMoth's image is returned.
     * 
     * @return The Image that represents SpaceMoth; how it is shown in the panel.
     */
    @Override
    public Image getSprite(){
        return sprite;
    }
    
    /**
     * This is what happens when SpaceMoth is shot.
     * 
     * PreCondition: SpaceMoth exists.
     * PostCondition: SpaceMoth set to be destroyed and 1 point given.
     */
    @Override
    public void isShot(GamePanel GPanel){
        destroy();
        GPanel.gainPoints(1);
        GPanel.addObject(new Explosions(getX() + 20, getY()));
    }
    
    /**
     * This method is used to get SpaceMoth ready for removal.
     * 
     * PreCondition: SpaceMoth exists.
     * PostCondition: SpaceMoth marked for removal.
     */
    @Override
    public void kill(){
        destroy();
    }
    
}//end SpaceMoth class
