/*
 * This is the class of the enemy projectiles.
 */
package project7_lincoln;

import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class EnemyBullet extends GameObjects{
    //need a sprite
    private Image sprite = new ImageIcon("images/enemyShot.png").getImage();
    
    //play sound effect by a thread
    private Thread effect = new Thread(new PlaySound());
    
    /**
     * This is the constructor for EnemyBullet, is set to fly down constantly.
     * 
     * PreCondition: EnemyBullet is to be created at given x and y.
     * PostCondition: EnemyBullet created.
     * 
     * @param x An integer value of how far to the right it is to be created.
     * @param y An integer value of how far down it is to be created.
     */
    public EnemyBullet(int x, int y){
        super(x,y,5,5,10,0);
        setFaction(1);
        effect.start();
    }
    
    /**
     * This constructor is used when you want the bullet to move in a certain direction 
     * towards a target.
     * 
     * PreCondition: new EnemyBullet is called for.
     * PostCondition: new EnemyBullet created at (x,y), horizontal and vertical ate the 
     * distances to target and will be divided to get our rise and run of EnemyBullet.
     * 
     * @param x This is the x coordinate the bullet starts at.
     * @param y This is the y coordinate the bullet starts at.
     * @param horizontal This is the distance to the target horizontally.
     * @param vertical This is the distance to the target vertically.
     */
    public EnemyBullet(int x, int y, int horizontal, int vertical){
        super(x,y,5,5,horizontal/20, vertical/20);
        setFaction(1);
        effect.start();
    }
    /* An inner class to play a laser sound effect */
    class PlaySound implements Runnable {

        /**
         * This method plays the audio file on a different thread.
         * 
         * PreCondition: A new Thread(new PlaySound()) was called and started, and 
         * wav file has to be valid.
         * PostCondition: plays the sound effect.
         */
        @Override
        public void run() {
            try {
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/LASER1.WAV"));

            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            }
            catch (Exception e){
                System.out.println("Problem with EnemyBullet sound effect.");
                e.printStackTrace();
            }
        }
    }//end of inner class to play sound
    
    /**
     * This returns the image that represents EnemyBullet.
     * 
     * PreCondition: EnemyBullet exists.
     * PostCondition: Image of the object is returned.
     * 
     * @return An Image object that represents EnemyBullet.
     */
    @Override
    public Image getSprite(){
        return sprite;
    }
    
    /**
     * This method for PlayerBullet is created so that if the bullet hits something 
     * the bullet is marked for destruction.
     * 
     * PreCondition: EnemyBullet exists and GamePanel is valid.
     * PostCondition: EnemyBullet marked for removal.
     * 
     * @param GPanel This is the GamePanel you with to pair EnemyBullet with.
     */
    @Override
    public void isShot(GamePanel GPanel){
        destroy();
    }
    
    /**
     * This method is used to check if EnemyBullet is low and should be 
     * removed.
     * 
     * PreCondition: EnemyBullet exists and GamePanel is valid.
     * PostConition: EnemyBullet is marked for removal if too high up.
     * 
     * @param GPanel This is the GamePanel you wish to pair the object to.
     */
    @Override
    public void outOfBounds(GamePanel GPanel){
        
        //I don't want any EnemyBullets Outside of the screen.
        if (getY() > GPanel.getHeight()){
            destroy();
        }//end if bullet is too low to be displayed on GamePanel
        else if (getY() < 0){
            destroy();
        }
        
        if (getX() > GPanel.getWidth()){
            destroy();
        }
        else if (getX() < 0){
            destroy();
        }
    }
    
    /**
     * This method is used to safely remove EnemyBullet.
     * 
     * PreCondition: EnemyBullet exists.
     * PostCondition: EnemyBullet marked for removal.
     */
    @Override
    public void kill(){
        destroy();
    }
    
}//end of PlayerBullet class
