/*
 * This is the class that contains another enemy for the player to destroy, this 
 * object with fly downwards, have armor and can shoot at the player.
 */
package project7_lincoln;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Sction 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class EnemyShip extends GameObjects {
    //private variables for image and threads/timers

    private Image sprite = new ImageIcon("images/enemyShip.png").getImage();
    private Image spriteHit = new ImageIcon("images/enemyShipHit.png").getImage();
    private boolean isHit = false;
    private boolean canFire = false;
    private int armor = 0;
    private Random random = new Random();
    private Thread hitSound = new Thread(new PlayHit());
    private Timer hitTime = new Timer(100, new HitDrop());
    private Timer fire = new Timer(0, new AttackTarget());
    //coordinates for target
    private int targetX = 0;
    private int targetY = 0;
    private GamePanel game;

    /**
     * This is the constructor used when you want to spawn an EnemyShip at an x
     * coordinate with the default armor (destroy after it is hit).
     *
     * PreCondition: new EnemyShip is called for. PostCondition: new EnemyShip
     * is created at the x coordinate.
     *
     * @param x This is the x coordinate you want the ship at.
     */
    public EnemyShip(int x) {
        super(x, -25, 50, 50, 5, 0);
        setFaction(1);
    }

    /**
     * This is the constructor is used if you want to specify if the enemy ship
     * can fire and give it a target to shoot at, it even has a parameter to
     * specify if you want random armor values and random times to fire at
     * player.
     *
     * PreCondition: a new EnemyShip is called for. PostCondition: a new
     * EnemyShip is created at x, its target is at (targetX, targetY),
     * randomArmor is true for random values, canFire is true if you want
     * EnemyShip to fire and randomTimer is true if you want the EnemyShip to
     * fire at random times.
     *
     * @param x This is the x coordinate you want the ship at.
     * @param targetX This is the target's x coordinate.
     * @param targetY This is the target's y coordinate.
     * @param randomArmor This is true if you want to create an EnemyShip that
     * can take 1 to 5 hits from PlayerBullet, false and it takes two hits.
     * @param canFire This is true if the EnemyShip can fire at target.
     * @param randomTimer This is true if you want the ship to fire at target
     * between 0 and 1 seconds, false and it fires each second.
     */
    public EnemyShip(int x, int targetX, int targetY, GamePanel game, boolean randomArmor, boolean canFire, boolean randomTimer) {
        super(x, -25, 50, 50, 5, 0);
        setFaction(1);
        this.canFire = canFire;
        this.targetX = targetX;
        this.targetY = targetY;
        this.game = game;

        //if to set a random armor value
        if (randomArmor) {
            armor = random.nextInt(5);
        } //else set armor so you have to hit it twice
        else {
            armor = 1;
        }

        //set timer if it can fire and if user says randomTimer
        if (canFire) {
            if (randomTimer) {
                fire = new Timer(random.nextInt(1000), new AttackTarget());
                fire.start();
            } else {
                fire = new Timer(1000, new AttackTarget());
                fire.start();
            }
        }//end if for canFire
    }//end of EnemyShip's full constructor

    /**
     * This method is used to check if the EnemyShip is outside of GamePanel and 
     * move or destroy it depending on where it currently is.
     *
     * PreCondition: EnemyShip exists and GamePanel parameter is valid.
     * PostConition: EnemyShip is moved or destroyed depending on current position.
     * 
     * @param GPanel A valid GamePanel you want to tie the object to.
     */
    @Override
    public void outOfBounds(GamePanel GPanel) {

        //mark for destruction if too far down
        if (getY() > GPanel.getHeight()) {
            destroy();
            if (canFire) {
                fire.stop();
            }
        }//end if too far down
        //else if too high up
        else if (getY() < -1 * getHeight() / 2) {
            setY(-1 * getHeight() / 2);
        }

        //if too far left
        if (getX() < -1 * getWidth() / 3) {
            setX(-1 * getWidth() / 3);
        } //else if too far right
        else if (getX() > GPanel.getWidth() - getWidth() / 3) {
            setX(GPanel.getWidth() - getWidth() / 3);
        }

    }//end of outOfBounds method

    /**
     * This method is used when the object is shot or a collision happens.
     *
     * PreCondition: EnemyShip exists and GamePanel is valid. PostCondition:
     * depending on armor levels, it is destroyed or shown to be hit
     *
     * @param GPanel This is the GamePanel you with to pair the object to.
     */
    @Override
    public void isShot(GamePanel GPanel) {
        armor--;

        if (armor < 0) {
            GPanel.addObject(new Explosions(getX(), getY()));
            GPanel.gainPoints(2);
            destroy();
        } else {
            isHit = true;
            hitTime.start();
            //there was this huge bug that would cause this game to crash, this was the fix
            try {
                hitSound.start();
            } catch (IllegalThreadStateException e) {
            }
        }

        if (canFire && armor < 0) {
            fire.stop();
        }
    }

    /**
     * This method is used to get the image that represents EnemyShip.
     *
     * PreCondition: EnemyShip exists PostCondition: returns a sprite depending
     * on if it has been hit or not.
     *
     * @return This is the sprite that represents the object.
     */
    @Override
    public Image getSprite() {
        //check if hit
        if (isHit) {
            return spriteHit;
        }

        //else return normal sprite
        return sprite;
    }

    /**
     * This method is used to get EnemyShip ready for removal and stops any running 
     * timers.
     * 
     * PreCondition: EnemyShip exists.
     * PostConition: EnemyShip marked for removal and if it can fire, fire timer 
     * is stopped.
     */
    @Override
    public void kill(){
        destroy();
        if (canFire){
            fire.stop();
        }
    }
    
    /* Inner class to handle the Thread for playing the hit sound effect */
    class PlayHit implements Runnable {

        /**
         * This method is used in a new thread to play the sound effect when
         * enemy ship is hit.
         *
         * PreCondition: There is a thread that has PlayHit as a Runnable and
         * file at audio/hit.wav exists. PostCondition: audio/hit.wav is played.
         */
        @Override
        public void run() {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/hit.wav"));

                Clip clip = AudioSystem.getClip();
                clip.open(stream);
                clip.start();

            } catch (Exception e) {
                System.out.println("Problem with EnemyShip hit sound effect.");
                e.printStackTrace();
            }
        }
    }//end of inner clas PlayHit which play the sound effect when EnemyShip is hit

    /* Inner class to handle the timer for stopping the hit effect on EnemyShip's Image */
    class HitDrop implements ActionListener {

        /**
         * This method is used when it is time to drop the hit effect on
         * EnemyShip.
         *
         * PreCondition: There is a timer or something that has new HitDrop as
         * an ActionListener. PostCondition: isHit is set to false, effect
         * stopped, and timer is stopped.
         *
         * @param e This is an ActionEvent thrown, should be thrown by hitTime
         * Timer.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            isHit = false;
            hitTime.stop();
        }
    }//end of inner class for HitDrop, stops hit effect

    /* Innner class to handle the timer for attacking the target */
    class AttackTarget implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            game.addObject(new EnemyBullet(getX() + getWidth() / 2, getY() + getHeight(),
                    targetY - (getY() + getHeight()), targetX - (getX() + getWidth() / 2)));
        }
    }//end of innner class to handle creating a bullet to run to player
}//end of EnemyShip class
