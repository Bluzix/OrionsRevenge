/*
 * This is the class that creates the panel that has buttons used to control the
 * player ship and displays scores and lives.
 */
package project7_lincoln;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class ControlPanel extends JPanel{
    //protected GamePanel to store reference because it is needed for ActionListener
    protected GamePanel game;
    
    //private JLabels to be updated
    private JLabel score;
    private JLabel lives;
    
    //private JPanels to change color when hit
    private JPanel buttonPanel;
    private JPanel display;
    private JPanel options;
    
    //private checkbox so I can change its color
    private JCheckBox muteBox;
    
    //I need to put my buttons up here as private for them to work]
    private JButton JBFire = new JButton(new ImageIcon("images/ui/fireButton.PNG"));
    private JButton JBUp = new JButton(new ImageIcon("images/ui/upButton.PNG"));
    private JButton JBDown = new JButton(new ImageIcon("images/ui/downButton.PNG"));
    private JButton JBRight = new JButton(new ImageIcon("images/ui/rightButton.PNG"));
    private JButton JBLeft = new JButton(new ImageIcon("images/ui/leftButton.PNG"));
    private JButton JBScore = new JButton("High Scores");
    
    /**
     * This is the constructor for ControlPanel, for normal operation give it the
     * GamePanel that has the ships and everything.
     * 
     * PreCondition: ControlPanel is to be created and GamePanel exists.
     * PostCondition: ControlPanel is created that can handle the stuff in GamePanel.
     * 
     * @param game This is the GamePanel you want to pair ControlPanel with.
     */
    public ControlPanel(GamePanel game){
        super(new GridLayout(1,2,5,5));
        this.game = game;
        
        //set up our buttons and a panel to hold them
        buttonPanel = new JPanel(new GridLayout(2,3,5,5));
        //JButton to fire with change if clicked
        JBFire.setPressedIcon(new ImageIcon("images/ui/fireButtonClicked.PNG"));
        //JButton to move up
        JBUp.setPressedIcon(new ImageIcon("images/ui/upButtonClicked.PNG"));
        //JButton to move down
        JBDown.setPressedIcon(new ImageIcon("images/ui/downButtonClicked.PNG"));
        //JButton to move right
        JBRight.setPressedIcon(new ImageIcon("images/ui/rightButtonClicked.PNG"));
        //JButton to move left
        JBLeft.setPressedIcon(new ImageIcon("images/ui/leftButtonClicked.PNG"));
        
        //add the buttons to buttonPanel
        buttonPanel.add(JBFire);
        buttonPanel.add(JBUp);
        //here is some wizardary I learned with my time of Android App programming
        buttonPanel.add(new JLabel());
        //second row
        buttonPanel.add(JBLeft);
        buttonPanel.add(JBDown);
        buttonPanel.add(JBRight);
        //add a title to Panel
        buttonPanel.setBorder(new TitledBorder("Controls"));
        //now to add it to ControlPanel
        this.add(buttonPanel);
        
        //create ActionListener for the buttons
        ControlListener listener = new ControlListener();
        //add it to the buttons
        JBFire.addActionListener(listener);
        JBUp.addActionListener(listener);
        JBLeft.addActionListener(listener);
        JBDown.addActionListener(listener);
        JBRight.addActionListener(listener);
        
        //Another JPanel for displaying Scores and Lives
        display = new JPanel(new GridLayout(3,1,5,5));
        score = new JLabel("Score: " + game.getScore());
        display.add(score);
        lives = new JLabel("Lives: " + game.getLives());
        display.add(lives);
        options = new JPanel (new GridLayout(1,2,5,5));
        options.add(JBScore);
        JBScore.addActionListener(listener);
        muteBox = new JCheckBox("Mute Music");
        options.add(muteBox);
        muteBox.addActionListener(listener);
        muteBox.setSelected(false);
        display.add(options);
        display.setBorder(new TitledBorder("Display"));
        //now add it to ControlPanel
        this.add(display);
        
        //set everything so that is cannot be focused, should stop it from stealing control from Keyboard
        score.setFocusable(false);
        lives.setFocusable(false);
        buttonPanel.setFocusable(false);
        display.setFocusable(false);
        options.setFocusable(false);
        muteBox.setFocusable(false);
        JBFire.setFocusable(false);
        JBUp.setFocusable(false);
        JBDown.setFocusable(false);
        JBRight.setFocusable(false);
        JBLeft.setFocusable(false);
        JBScore.setFocusable(false);
    }
    
    //I created this class to handle events from the buttons
    class ControlListener implements ActionListener {
        /**
         * This method handles the ActionEvents from the buttons in ControlPanel.
         * 
         * PreCondition: ControlPanel and GamePanel exist.
         * PostCondition: Depending on the button clicked, the player ship or 
         * PlayerObject should have been moved.
         * 
         * @param e The ActionEvent that is triggered by one of the buttons in
         * ControlPanel.
         */
        @Override
        public void actionPerformed(ActionEvent e){
            if (e.getSource() == JBFire){
                game.shoot();
            }
            else if (e.getSource() == JBUp){
                game.moveByButton(0);
            }
            else if (e.getSource() == JBLeft){
                game.moveByButton(2);
            }
            else if (e.getSource() == JBDown){
                game.moveByButton(1);
            }
            else if (e.getSource() == JBRight){
                game.moveByButton(3);
            }
            else if (e.getSource() == muteBox){
                game.setMute(muteBox.isSelected());
            }
            else if (e.getSource() == JBScore){
                game.showScores();
            }
        }//end of actionPerformed method
    }//end of ControlListener class
    
    /**
     * This method is used when we repaint ControlPanel.
     * 
     * PreCondition: ControlPanel exists g is valid Graphics object.
     * PostCondition: the Graphics are painted onto ControlPanel.
     * 
     * @param g The Graphics object that will be painted onto ControlPanel.
     */
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        
        score.setText("Score: " + game.getScore());
        lives.setText("Lives: " + game.getLives());
        
        //I want to change the background color of these if the player is shielded
        if (game.getShielded()){
            setBackground(Color.red);
            buttonPanel.setBackground(Color.red);
            display.setBackground(Color.red);
            score.setForeground(Color.white);
            lives.setForeground(Color.white);
            options.setBackground(Color.red);
            muteBox.setBackground(Color.red);
            muteBox.setForeground(Color.white);
        }
        //I want to set it back too.
        else {
            setBackground(null);
            buttonPanel.setBackground(null);
            display.setBackground(null);
            score.setForeground(null);
            lives.setForeground(null);
            options.setBackground(null);
            muteBox.setBackground(null);
            muteBox.setForeground(null);
        }
    }
    
}//end of ControlPanel class
