/*
 * This is the PlayerObject class which defines everything about the player and
 * the ship the player controls on screen.
 */
package project7_lincoln;

import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class PlayerObject extends GameObjects {
    //let's create some lives for our PlayerObject, three to start (can be hit 4 times)

    private int lives = 3;
    //create value for Image
    private Image sprite = new ImageIcon("images/playerShip.PNG").getImage();
    private Image shieldedSprite = new ImageIcon("images/playerShipShield.PNG").getImage();
    //create a boolean to make the player ship invulnerable for a while
    private boolean shield = false;
    //create a Timer to lower shield when time is up
    private Timer shieldTime = new Timer(5000, new ShieldDrop());

    /**
     * This is the full constructor for PlayerObject.
     *
     * PreCondition: A PlayerObject is to be created. PostCondition: A
     * PlayerObject is created with these values
     *
     * @param x integer for the x position for the object.
     * @param y integer for the y position for the object.
     */
    public PlayerObject(int x, int y, int height, int width) {
        super(x, y, height, width, 0, 0);
    }

    /**
     * This method returns the number of lives the player has.
     *
     * PreCondition: PlayObject exists. PostCondition: Number of lives returned
     * as an integer.
     *
     * @return the number of lives the player has, integer.
     */
    public int getLives() {
        return lives;
    }

    /**
     * This method is used when you lose a life, lives goes down by one.
     *
     * PreCondition: PlayerObject exists. PostCondition: Number of lives is
     * decreased by one.
     */
    public void loseALife() {
        lives--;
    }

    /**
     * This method is used when a player gains a life, by getting a certain
     * score.
     *
     * PreCondition: PlayerObject exists.
     * PostConiditon: lives increased by one.
     */
    public void gainALife() {
        lives++;
    }

    /**
     * This method is used to reset the amount of lives PlayObject has.
     * 
     * PreConition: PlayerObject exists.
     * PostConition: lives set to 3.
     */
    public void resetLives(){
        lives = 3;
    }
    
    /**
     * This method is used when you want to move the ship to the left.
     *
     * PreCondition: PlayObject exists. PostCondition: A negative run is set,
     * object moving left.
     */
    public void moveLeft() {
        super.setRun(-5);
    }

    /**
     * This method is used when you want to move the ship to the right.
     *
     * PreCondition: PlayerObject exists. PostCondition: A positive run is set,
     * object moving right.
     */
    public void moveRight() {
        super.setRun(5);
    }

    /**
     * This method is used when you want to move the ship up.
     *
     * PreCondition: PlayerObject exists. PostCondition: A negative rise is set,
     * object moving up.
     */
    public void moveUp() {
        super.setRise(-5);
    }

    /**
     * This method is used when you want to move the ship down.
     *
     * PreCondition: PlayerObject exists. PostCondition: A positive rise is set,
     * object moving down.
     */
    public void moveDown() {
        super.setRise(5);
    }

    /**
     * This method is used when you want to stop the vertical movement of the
     * ship; such as when you release a button.
     *
     * PreCondition: PlayerObject exists. PostCondition: A 0 rise is set, object
     * has no vertical movement.
     */
    public void stopVertical() {
        super.setRise(0);
    }

    /**
     * This method is used when you want to stop the horizontal movement of the
     * ship; such as when you release a button.
     *
     * PreCondition: PlayerObject exists. PostCondition: A 0 run is set, object
     * has no horizontal movement.
     */
    public void stopHorizontal() {
        super.setRun(0);
    }

    /**
     * This method creates a PlayerBullet object in a relative position of
     * PlayerObject.
     *
     * PreCondition: PlayerObject exists with X and Y positions. PostCondition:
     * A new PlayerBullet is created.
     *
     * @return A new PlayerBullet object.
     */
    public PlayerBullet shoot() {
        return new PlayerBullet(getX() + 23, getY() - 5);
    }

    /**
     * This method returns true if player ship is shielded, no lives should be
     * lost during this time.
     *
     * PreCondition: PlayerObject exists. PostCondition: Returns true if ship is
     * invulnerable, false if not.
     *
     * @return a boolean of invulnerable status
     */
    public boolean isShielded() {
        return shield;
    }

    /**
     * This method can be used to cancel the shielded status of PlayerObject;
     * they can't set it to true but can set it to false.
     *
     * PreCondition: PlayerObject exists. PostCondition: PlayerObject's shields
     * set to false.
     */
    public void dropShields() {
        shield = false;
    }

    /**
     * This method returns the sprite (Image) that defines the object.
     *
     * PreCondition: PlayerObject exists. PostCondition: The Image of
     * PlayerObject is returned, is different if PlayObject is shielded.
     *
     * @return The Image that is of the object.
     */
    @Override
    public Image getSprite() {
        if (shield) {
            return shieldedSprite;
        }
        return sprite;
    }

    /**
     * This method is used whenever the PlayerObject is shot down by an enemy.
     *
     * PreCondition: PlayerObject exists and GamePanel is valid. PostCondition:
     * Depending on lives it could be a lost life or a game over.
     *
     * @param GPanel the GamePanel that is paired with PlayerObject.
     */
    @Override
    public void isShot(GamePanel GPanel) {
        if (lives > 0 && !shield) {
            loseALife();
            shield = true;
            shieldTime.start();
        }//end if lives are more than zero.
        
        //game over time
        else if (lives == 0 && !shield){
            GPanel.gameOver();
        }
    }

    /**
     * This method is used to move the PlayerObject if it tries to move outside
     * of the GamePanel.
     *
     * PreCondition: PlayObject exists and the parameter GPanel is a valid
     * GamePanel PostCondition: PlayerObject is moved so that it stays in the
     * window.
     *
     * @param GPanel This is the valid GamePanel you wish to pair the
     * PlayerObject with.
     */
    @Override
    public void outOfBounds(GamePanel GPanel) {
        //if else for checking if PlayerObject is out of the screen
        if (getX() < 0) {
            setX(0);
        }//end if PlayerObject too far left
        else if (getX() > GPanel.getWidth() - getWidth()) {
            setX(GPanel.getWidth() - getWidth());
        }//end if too far right

        if (getY() < 0) {
            setY(0);
        }//end if too far up
        else if (getY() > GPanel.getHeight() - getHeight()) {
            setY(GPanel.getHeight() - getHeight());
        }//end if too far down
    }

    /**
     * This method is used to get PlayerObject set for removal.
     * 
     * PreCondition: PlayerObject exists.
     * PostCondition: PlayerObject marked for removal.
     */
    @Override
    public void kill(){
        destroy();
        shieldTime.stop();
    }
    
    /*A class to handel the timer that we set for shields */
    class ShieldDrop implements ActionListener {

        /**
         * This is the method that will cause the player's shields to drop after
         * it receives the ActionEvent from the PlayerObjects Timer.
         *
         * PreCondition: new ShieldDrop is called for. PostCondition: new
         * ShieldDrop object exists.
         *
         * @param e This is the ActionEvent triggered by an object.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            dropShields();
            shieldTime.stop();
        }
    }//end of inner class to handle dropping shields
    
}//end of PlayerObject class
