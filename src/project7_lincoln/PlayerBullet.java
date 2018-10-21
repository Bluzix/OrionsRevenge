/*
 * This is the class that is the projectile for PlayerObject.
 */
package project7_lincoln;

import java.awt.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Sction 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class PlayerBullet extends GameObjects{
    //need a sprite
    private Image sprite = new ImageIcon("images/playerBullet.PNG").getImage();
    
    //play sound effect by a thread
    private Thread effect = new Thread(new PlaySound());
    
    /**
     * This is the constructor for PlayerBullet, is set to fly upwards constantly.
     * 
     * PreCondition: PlayerBullet is to be created at given x and y.
     * PostCondition: PlayerBullet created.
     * 
     * @param x An integer value of how far to the right it is to be created.
     * @param y An integer value of how far down it is to be created.
     */
    public PlayerBullet(int x, int y){
        super(x,y,10,5,-10,0);
        setFaction(0);
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
                System.out.println("Problem with PlayerBullet sound effect.");
                e.printStackTrace();
            }
        }
    }//end of inner class to play sound
    
    /**
     * This returns the image that represents PlayerBullet.
     * 
     * PreCondition: PlayerBullet exists.
     * PostCondition: Image of the object is returned.
     * 
     * @return An Image object that represents PlayerBullet.
     */
    @Override
    public Image getSprite(){
        return sprite;
    }
    
    /**
     * This method for PlayerBullet is created so that if the bullet hits something 
     * the bullet is marked for destruction.
     * 
     * PreCondition: PlayerBullet exists and GamePanel is valid.
     * PostCondition: PlayerBullet marked for removal.
     * 
     * @param GPanel This is the GamePanel you with to pair PlayerBullet with.
     */
    @Override
    public void isShot(GamePanel GPanel){
        destroy();
    }
    
    /**
     * This method is used to check if PlayerBullet is too high up and should be 
     * removed.
     * 
     * PreCondition: PlayerBullet exists and GamePanel is valid.
     * PostConition: PlayerBullet is marked for removal if too high up.
     * 
     * @param GPanel This is the GamePanel you wish to pair the object too.
     */
    @Override
    public void outOfBounds(GamePanel GPanel){
        if (getY() + getWidth() < 0){
            destroy();
        }//end if bullet is too high to be displayed on GamePanel
    }
    
    /**
     * This is used to get PlayerBullet to be safely removed.
     * 
     * PreCondition: PlayerBullet exists.
     * PostConition: PlayerBullet marked for removal.
     */
    @Override
    public void kill(){
        destroy();
    }
}//end of PlayerBullet class
