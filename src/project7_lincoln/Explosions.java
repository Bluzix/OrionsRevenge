/*
 * This is the class for the Explosions object.
 */
package project7_lincoln;

import java.awt.Image;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * SamLincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class Explosions extends GameObjects {
    //private image and Timer

    private Image sprite = new ImageIcon("images/explosion.png").getImage();
    private Timer timeToLive = new Timer(500, new Die());
    private Thread effect = new Thread(new PlaySound());

    /**
     * This is the constructor used to create Explosions objects.
     *
     * PreCondition: new Explosions Object called for, x and y are valid.
     * PostCondition: new Explosion Object created at (x,y).
     *
     * @param x This is the x position you want the explosion at.
     * @param y This is the y position you want the explosion at.
     */
    public Explosions(int x, int y) {
        super(x, y, 50, 50, 0, 0);
        setFaction(2);
        timeToLive.start();
        effect.start();
    }

    /**
     * This method returns the Image that represents Explosions Object.
     *
     * PreCondition: Explosions Object exists. PostCondition: Image of the
     * Object is returned.
     *
     * @return The Image that represents the Object.
     */
    @Override
    public Image getSprite() {
        return sprite;
    }

    /**
     * This method is what happens when Explosions is shot; which should be
     * nothing.
     *
     * PreCondition: Explosions object exists and GamePanel is valid.
     * PostCondition: Nothing should happen.
     *
     * @param GPanel A valid GamePanel to pair this object with.
     */
    @Override
    public void isShot(GamePanel GPanel) {
        //nothing should happen
    }

    /**
     * This method is what happens when Explosions go out of bounds of the
     * GamePanel.
     *
     * PreCondition: Explosions object exists and GamePanel is valid.
     * PostCondition: Nothing should happen.
     *
     * @param GPanel A valid GamePanel to pair this object with.
     */
    @Override
    public void outOfBounds(GamePanel GPanel) {
        //nothing should happen
    }

    /**
     * This method is used to safely remove the object.
     * 
     * PreCondition: Explosions exists.
     * PostCondition: Explosions marked for removal and timers stopped.
     */
    @Override
    public void kill(){
        destroy();
        timeToLive.stop();
    }
    
    /* An inner class that is used with the timeToLive Timer to set the object to be removed. */
    class Die implements ActionListener {

        /**
         * This method is used to set Explosions object to be removed.
         *
         * PreCondition: The timer in Explosions class has this as an
         * ActionListener. PostCondition: The timer is stopped and Explosions
         * Object set to be removed.
         *
         * @param e This is the ActionEvent the timer sends.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            destroy();
            timeToLive.stop();
        }
    } //end of inner class Die

    /* An inner class to play an explostion sound effect */
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
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/bomb-02.wav"));

            Clip clip = AudioSystem.getClip();
            clip.open(stream);
            clip.start();
            }
            catch (Exception e){
                System.out.println("Problem with explosion sound effect.");
                e.printStackTrace();
            }
        }
    }//end of inner class to play sound
    
}//end of Explosions class
