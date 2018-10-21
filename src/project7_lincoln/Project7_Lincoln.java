/*
 * This contains the code for putting together the GUI and running the program to
 * bring the GUI on screen.
 */
package project7_lincoln;

import java.awt.BorderLayout;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class Project7_Lincoln extends JFrame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        GamePanel GPanel = new GamePanel();
        ControlPanel control = new ControlPanel(GPanel);
        frame.setTitle("Orion's Revenge");
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(GPanel, BorderLayout.CENTER);
        frame.add(control, BorderLayout.SOUTH);
        GPanel.setFocusable(true);
        frame.setVisible(true);
        //an infinite loop to drive the game, thanks for the pause code from our lab
        while (true) {
            pause();
            GPanel.move(GPanel);
            control.repaint();
        }//end infinite while loop
    }//end of main

    /**
     * Pause command used to control the speed of the game.  Yes, I did pull this 
     * from the ball labs, but it works great here too.
     * 
     * PreConition: it is called.
     * PostConition: Thread sleeps for 20 ms.
     */
    public static void pause() {
        try {
            Thread.sleep(20); // pause for 20 ms
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }//end of catch
    }//end of pause method 
    
}//end of Project7_Lincoln class
