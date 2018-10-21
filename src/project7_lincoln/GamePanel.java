/*
 * This is the class for the JPanel which controls our game.  It draws the objects,
 * creates enemies and keeps our score.
 */
package project7_lincoln;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Sction 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class GamePanel extends JPanel {
    //let's set up our private variables for score, scoreToLife and an array for GameObjects

    private int score = 0;
    //a timer used to spawn enemies.
    private Timer spawnEnemies;
    private int scoreToLife = 10000;
    private ArrayList<GameObjects> list = new ArrayList<>();
    //make my Thread for playing music private so I can use it in paint objects
    private Thread musicPlayer = new Thread(new PlayBGM());
    //a boolean to mute sounds used in my thread
    protected boolean mute = false;

    //JFrame that shows highscores
    private JFrame scoreFrame = new ScoreFrame();
    
    /**
     * This is the constructor for GamePanel.
     *
     * PreCondition: A new GamePanel is called for. PostCondition: A GamePanel
     * is created with the PlayerObject added
     */
    public GamePanel() {
        super();
        list.add(new PlayerObject(200, 200, 50, 50));
        spawnEnemies = new Timer(2000, new SpawnEnemies(this));
        spawnEnemies.start();
        addKeyListener(new KeyBoardControl());
        //start thread to play background music
        musicPlayer.start();
    }//end constructor for GamePanel

    /**
     * This method returns the current score.
     *
     * PreCondition: GamePanel exists. PostCondition: The current score is
     * returned as an integer.
     *
     * @return the current score as an integer
     */
    public int getScore() {
        return score;
    }//end getScore method

    /**
     * This method is used when an enemy is destroyed and Player scores points.
     *
     * PreCondition: GamePanel exists. PostConition: score has been increased
     * and a counter for the number of points needed to get a life is decreased
     * unless a new life has been given.
     *
     * @param points This is the number of points the player just scored as an
     * integer.
     */
    public void gainPoints(int points) {
        score += points;
        scoreToLife -= points;

        //if scoreToLife is less than zero, we gain a life and reset scoreToLife
        if (scoreToLife < 0) {
            ((PlayerObject) list.get(0)).gainALife();
            scoreToLife = 10000;
        }//end if scoreToLife is less than zero
    }

    /**
     * This method returns the lives of the PlayerObject in the GamePanel.
     *
     * PreCondition: GamePanel exists and has a PlayerObject in list index 0.
     * PostCondition: The lives of PlayerObject is returned as an integer.
     *
     * @return the amount of lives the player has as an integer.
     */
    public int getLives() {
        return ((PlayerObject) list.get(0)).getLives();
    }//end getLives method

    /**
     * This is the method used when the play ship or PlayerObject is to shoot a
     * bullet.
     *
     * PreCondition: GamePanel exists and PlayerObject is first in it's list.
     * PostCondition: A PlayerBullet object is added to the ArrayList.
     */
    public void shoot() {
        list.add(((PlayerObject) list.get(0)).shoot());
    }//end shoot method

    /**
     * This method is used to act as an external ActionListener for buttons on a
     * different JPanel and moves the PlayerObject in the ArrayList; integer 0
     * is up, 1 is down, 2 is left and 3 is right.
     *
     * PreCondition: GamePanel exists and a PlayerObject is the first in its
     * list. PostCondition: Depending on the integer parameter, PlayerObject
     * gets a new X or Y coordinate.
     *
     * @param button The integer that will cause PlayerObject to move.
     */
    public void moveByButton(int button) {
        switch (button) {
            case 0:
                list.get(0).setY(list.get(0).getY() - 25);
                break;
            case 1:
                list.get(0).setY(list.get(0).getY() + 25);
                break;
            case 2:
                list.get(0).setX(list.get(0).getX() - 25);
                break;
            case 3:
                list.get(0).setX(list.get(0).getX() + 25);
                break;
        }//end switch-case
    }//end moveByButton

    /**
     * This method is used to move the GameObjects in GamePanel.
     *
     * PreCondition: GamePanel exists and has GameObjects in one of its list.
     * PostCondition: Objects in GamePanel's list may have been moved.
     *
     * @param GPanel This is the GamePanel that has the object in its ArrayList.
     */
    public void move(GamePanel GPanel) {
        //for loop to move everything and check for out of bounds
        for (int scan = 0; scan < list.size(); scan++) {
            list.get(scan).move();
            list.get(scan).outOfBounds(GPanel);
        }//end for loop to move

        //two for loops to run collision checking
        for (int scan = 0; scan < list.size(); scan++) {
            for (int check = 0; check < list.size(); check++) {
                list.get(scan).collision(GPanel, list.get(check));
            }
        }//end of two for loops that check for collisions

        //run the trashCollector method which removes all GameObjects marked for removal
        trashCollector();

        //repaint all the GameObjects
        repaint();
    }

    /**
     * This method is used to check if GameObjects need to be removed, as in
     * they have been marked for removal.
     *
     * PreCondition: GamePanel exists and has GameObjects in the ArrayList.
     * PostCondition: all marked GameObjects have been removed from the list.
     */
    public void trashCollector() {
        //boolean to stop scanning if all objects mark for deletion have been removed
        boolean finished = false;

        //do-while to remove all objects mark for deletion
        do {
            //run scan to remove a marked object, one at a time to not get errors
            for (int scan = 0; scan < list.size(); scan++) {
                if (list.get(scan).isTrash()) {
                    list.remove(scan);
                    break;
                }//end if GameObject is marked
            }//end for loop that runs a scan and removes objects

            //run another scan to see if all marked objects have been removed
            for (int scan = 0; scan < list.size(); scan++) {
                if (list.get(scan).isTrash()) {
                    finished = false;
                    break;
                } else {
                    finished = true;
                }
            }//end of for loop to check if all marked objects were removed
        } while (!finished);
    }

    /**
     * This method is used to return true if PlayerObject is shielded.
     *
     * PreCondition: GamePanel exists and PlayerObject is first in GamePanel's
     * list. PostCondition: Returns true if PlayerObject is shield
     *
     * @return
     */
    public boolean getShielded() {
        return ((PlayerObject) list.get(0)).isShielded();
    }

    /* An inner class designed to be used with a Timer object to spawn enemies */
    class SpawnEnemies implements ActionListener {
        //private variable to GamePanel
        private GamePanel game;

        /**
         * This is the constructor used to tie SpawnEnemies to our GamePanel.
         *
         * PreCondition: new SpawnEnemies is called with GamePanel in parameter.
         * PostCondition: new SpawnEnemies created that is linked to GamePanel.
         *
         * @param game The GamePanel you want to link to, done so that we can
         * get height and width of current GamePanel.
         */
        public SpawnEnemies(GamePanel game) {
            this.game = game;
        }

        /**
         * This method is used to create enemies for the player to shoot.
         * 
         * PreCondition: This object has a valid GamePanel tied to it and there is 
         * a timer or something else using this ActionListener.
         * PostCondition: random enemies have been create onto the GamePanel.
         * 
         * @param e This is the ActionEvent that is thrown, should be from a Timer.
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            Random random = new Random();
            //start off easy, gets harder with score
            if (score < 10) {
                //spawn SpaceMoth at a random height but still fits current GamePanel
                list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50));
            }//end if score less than 10
            
            //at score >10 and <15 SpaceMoths spawn to the left or right and EnemyShips first appear
            else if (score < 15) {
                list.add(new EnemyShip((int) (Math.random() * game.getWidth())));
                if (random.nextInt(100) < 50) {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, false, game));
                } else {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, true, game));
                }
            }
            
            //now EnemyShips have armor
            else if (score < 30) {
                list.add(new EnemyShip((int) (Math.random() * game.getWidth()), list.get(0).getX() + list.get(0).getWidth()/2,
                        list.get(0).getY() + list.get(0).getHeight()/2, game, false, false, false));
                if (random.nextInt(100) < 50) {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, false, game));
                } else {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, true, game));
                }
            }
            
            
            
            //now EnemyShips can fire
            else if (score < 30) {
                list.add(new EnemyShip((int) (Math.random() * game.getWidth()), list.get(0).getX() + list.get(0).getWidth()/2,
                        list.get(0).getY() + list.get(0).getHeight()/2, game, false, true, false));
                if (random.nextInt(100) < 50) {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, false, game));
                } else {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, true, game));
                }
            }
            
            //now EnemyShips can fire and armor is now random
            else if (score < 60) {
                list.add(new EnemyShip((int) (Math.random() * game.getWidth()), list.get(0).getX() + list.get(0).getWidth()/2,
                        list.get(0).getY() + list.get(0).getHeight()/2, game, true, true, false));
                if (random.nextInt(100) < 50) {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, false, game));
                } else {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, true, game));
                }
            }
           
            
            //EnemyShips now fire randomly
            else {
                list.add(new EnemyShip((int) (Math.random() * game.getWidth()), list.get(0).getX() + list.get(0).getWidth()/2,
                        list.get(0).getY() + list.get(0).getHeight()/2, game, true, true, true));
                if (random.nextInt(100) < 50) {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, false, game));
                } else {
                    list.add(new SpaceMoth((int) (Math.random() * game.getHeight()) - list.get(0).getHeight() - 50, true, game));
                }
            }
            
        }//end actionPerformed method
    }//end of SpawnEnemies class

    /* Let's create another inner class to handle keyboard control of PlayerObject */
    class KeyBoardControl extends KeyAdapter {

        /**
         * This method monitors if a key is PRESSED and modifies the
         * PlayerObject in the ArrayList with a new Rise and/or Run value in
         * accordance to different keys; also causes the ship to fire when space
         * is pressed.
         *
         * PreCondition: GamePanel exists and PlayerObject is first in
         * GamePanel's list. PostCondition: The PlayerObject performs an action:
         * moves or fires.
         *
         * @param e This is the KeyEvent triggered by the KeyBoard.
         */
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    ((PlayerObject) list.get(0)).moveUp();
                    break;
                case KeyEvent.VK_W:
                    ((PlayerObject) list.get(0)).moveUp();
                    break;
                case KeyEvent.VK_DOWN:
                    ((PlayerObject) list.get(0)).moveDown();
                    break;
                case KeyEvent.VK_S:
                    ((PlayerObject) list.get(0)).moveDown();
                    break;
                case KeyEvent.VK_LEFT:
                    ((PlayerObject) list.get(0)).moveLeft();
                    break;
                case KeyEvent.VK_A:
                    ((PlayerObject) list.get(0)).moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((PlayerObject) list.get(0)).moveRight();
                    break;
                case KeyEvent.VK_D:
                    ((PlayerObject) list.get(0)).moveRight();
                    break;
                case KeyEvent.VK_SPACE:
                    list.add(((PlayerObject) list.get(0)).shoot());
                    break;
            }//end switch case for finding key pressed
        }//end keyPressed method

        /**
         * This method monitors if a key is RELEASED and causes the PlayerObject
         * in the ArrayList to stop moving left and right and/or up and down
         * depending on the key released.
         *
         * PreCondition: GamePanel exists and a PlayerObject is first in
         * GamePanel's list. PostCondition: The rise and/or run values for
         * PlayerObject have been set to 0 depending on the keys released.
         *
         * @param e This is the KeyEvent triggered by the KeyBoard.
         */
        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    ((PlayerObject) list.get(0)).stopVertical();
                    break;
                case KeyEvent.VK_W:
                    ((PlayerObject) list.get(0)).stopVertical();
                    break;
                case KeyEvent.VK_DOWN:
                    ((PlayerObject) list.get(0)).stopVertical();
                    break;
                case KeyEvent.VK_S:
                    ((PlayerObject) list.get(0)).stopVertical();
                    break;
                case KeyEvent.VK_LEFT:
                    ((PlayerObject) list.get(0)).stopHorizontal();
                    break;
                case KeyEvent.VK_A:
                    ((PlayerObject) list.get(0)).stopHorizontal();
                    break;
                case KeyEvent.VK_RIGHT:
                    ((PlayerObject) list.get(0)).stopHorizontal();
                    break;
                case KeyEvent.VK_D:
                    ((PlayerObject) list.get(0)).stopHorizontal();
                    break;
            }//end case for finding key released
        }//end of keyReleased method
    }//end of KeyBoardCondtrol inner class

    /* An inner class for music to be played on a separate thread */
    class PlayBGM implements Runnable {

        //I want to be able to tell if it has stopped
        private boolean stopped = false;

        /**
         * This method is used to play the Background music in the game.
         *
         * PreCondition: GamePanel exists and the background music file exists.
         * PostCondition: Background music is played and looped if not muted.
         */
        @Override
        public void run() {
            try {
                while (true) {
                    if (!mute) {
                        stopped = false;

                        AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/Bluzix - Orion's Revenge.wav"));

                        Clip clip = AudioSystem.getClip();
                        clip.open(stream);
                        clip.start();

                        Thread.sleep(13714);
                    }//if to check if mute is false
                }//forever true to always run
            } catch (Exception e) {
                System.out.println("Error with BGM wav file.");
                e.printStackTrace();
            }
        }//end of run method
    }//end of PlayBGM inner class

    /**
     * This method is used to set if the BGM is muted or not, true being mute.
     * 
     * PreCondition: GamePanel exists.
     * PostCondition: mute is set to true or false; is used to stop music from looping.
     * 
     * @param mute A boolean: true and music stops looping, false and it will loop or 
     * start looping again.
     */
    public void setMute(boolean mute){
        this.mute = mute;
    }
    
    /**
     * This method is used to add GameObjects, like Explosions, to GamePanel
     * from outside of the GamePanel class.
     *
     * PreCondition: GamePanel exists and parameter is a valid GameObjects.
     * PostCondition: The GameObjects Object is added to GamePanel's ArrayList.
     *
     * @param object This is a valid GameObject to add to GamePanel's ArrayList.
     */
    public void addObject(GameObjects object) {
        list.add(object);
    }

    /**
     * This method is used to paint our objects in the GamePanel.
     *
     * PreCondition: GamePanel exists. PostCondition: Everything has been
     * painted to the GamePanel part of the GUI.
     *
     * @param g This is the Graphics that will be painted.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //paint black background
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        //paint the GameObjects
        for (int scan = 0; scan < list.size(); scan++) {
            g.drawImage(list.get(scan).getSprite(), list.get(scan).getX(), list.get(scan).getY(), list.get(scan).getWidth(), list.get(scan).getHeight(), this);
        }//end of for loop to paint objects

        if (((PlayerObject) list.get(0)).isShielded()) {
            g.setColor(Color.red);
            g.drawRect(0, 0, getWidth(), getHeight());
        }//end if for PlayerObject Sheilded

    }//end of paintComponent method
    
    /**
     * This method is used to call that the game has reached a game over point.
     * 
     * PreCondition: GamePanel exists.
     * PostConition: Everything in GamePanel is reset and score is passed to ScoreFrame 
     * to be saved on a file.
     */
    public void gameOver(){
        String name;
        
        spawnEnemies.stop();
        
        //run for loop to kill all objects
        for (int scan = 1; scan < list.size(); scan++){
            list.get(scan).kill();
        }//end killing all objects
        
        //run trashcollector
        trashCollector();
        
        //ask for name
        name = JOptionPane.showInputDialog("What is your name?");
        
        //add score
        ((ScoreFrame)scoreFrame).addScore(new Scores(name, score));
        
        //automatically show list
        showScores();
        
        //then reInitalize
        reInitalize();
    }
    
    /**
     * This method is used to show the scores, it makes the JFrame visible.
     * 
     * PreConition: GamePanel exists and scoreFrame exists.
     * PostCondition: scoreFrame is set to visible.
     */
    public void showScores(){
        scoreFrame.setVisible(true);
    }
    
    /**
     * This method is used to reset everything as if you started a new game.
     * 
     * PreCondition: GamePanel exists.
     * PostCondition: Most of the values are set to when you started the game and 
     * timer to spawn enemies is restarted.
     */
    public void reInitalize(){
        //set values back on everything
        score = 0;
        scoreToLife = 10000;
        list.get(0).setX(200);
        list.get(0).setY(200);
        ((PlayerObject)list.get(0)).resetLives();
        spawnEnemies = new Timer(2000, new SpawnEnemies(this));
        spawnEnemies.start();
    }
    
}//end of GamePanel class
