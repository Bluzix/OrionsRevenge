/*
 * This class is for the window that shows the high score list, since it is encrypted 
 * in the text file.
 */
package project7_lincoln;

import java.io.*;
import java.util.*;
import javax.swing.*;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class ScoreFrame extends JFrame {
    //I made an Array to hold Scores Objects

    private Scores[] list = new Scores[11];
    //private JTextArea with a JScrollPane
    private JTextArea scoreList;
    private JScrollPane scrollable;
    //neeed to use the file name
    private File file = new File("highscores.txt");

    /**
     * This is the constructor for ScoreFrame, it is used to read high scores.
     *
     * PreCondition: a new ScoreFrame is called for. PostCondition: a new
     * ScoreFrame created but not shown or set visible.
     */
    public ScoreFrame() {
        super();
        //my JTextArea for listing scores
        scoreList = new JTextArea("This is for the scores.", 10, 2);
        scoreList.setLineWrap(false);

        //I place it in scrollable JScrollPane in case it needs to be scrolled
        scrollable = new JScrollPane(scoreList);
        add(scrollable);
        setTitle("High Scores Table");
        setLocationRelativeTo(null);
        

        //run a method to load the high score list
        loadScores();
        
        //should set size to fit everthing
        pack();
    }

    /**
     * This method is used when you want to add a score to the high scores list.
     *
     * PreCondition: ScoreFrame exists and parameter is a valid Scores object.
     * PostCondition: Scores object added to the array in ScoreFrame, is added
     * to the last spot (index 10) and then array is sorted.
     *
     * @param object This is a valid Scores object you wish to add to the list.
     */
    public void addScore(Scores object) {
        list[10] = object;

        //sort the array, I have compareTo method in Scores set backward for this
        Arrays.sort(list);

        //save the new Scores
        saveList();
        
        //send new Scores to JTextArea
        updateList();
    }

    /**
     * This method is used when ScoreFrame is created and is used to populate
     * the array of scores from the file or create a file if it doesn't exist.
     *
     * PreCondition: ScoreFrame exists. PostCondition: A file may be created if
     * there is no highscores.txt or the array of Scores may just be filled from
     * that file.
     */
    private void loadScores() {
        //if file doesn't exist
        if (!file.exists()) {
            createNewList();
        } //if there is a file
        else {
            readFile();
        }

        //put Scores onto the JTextArea
        updateList();
    }

    /**
     * The method fills the Scores[] with dummy Scores and then calls another
     * method to create the new file.
     *
     * PreCondition: ScoreFrame exists. PostConition: Scores[] of ScoreFrame is
     * filled with dummy Scores and saveList() is called to create the file.
     */
    private void createNewList() {
        //fill Scores[] with dummy Scores
        for (int count = 0; count < 10; count++) {
            list[count] = new Scores("CPU", 0);
        }//end of for loop

        //call a method to create the file
        saveList();
    }

    /**
     * This method is used to save the Scores[] into a file on the computer.
     *
     * PreCondition: file is a valid place and 10 Score objects are in Scores[].
     * PostCondition: The 10 Scores are written to highscores.txt and are
     * Encrypted thanks to Encryption.java.
     */
    private void saveList() {

        //try catch in case it doesn't work right, it shouldn't
        try {
            //declare a PrintWriter
            PrintWriter output = new PrintWriter(file);

            //write 10 scores to the file
            for (int count = 0; count < 10; count++) {
                output.println(Encryption.encryptString(true, list[count].toString()));
            }

            //close output to save file
            output.close();
        } catch (Exception e) {
            System.out.println("Error in saving highscores.txt");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to get the Scores objects from the encrypted text file 
     * highscores.txt thanks to Encryption class.
     * 
     * PreCondition: ScoreFrame exists and file highscores.txt is valid.
     * PostCondition: Scores[] is filled with values stored on highscores.txt.
     */
    private void readFile() {

        //try catch in case it doesn't work, it shouldn't
        try {
            //declare my Scanner
            Scanner input = new Scanner(file);
            
            //for loop to continuosly read the line 10 times
            for (int count = 0; count < 10; count++){
                String currentLine = input.nextLine();
                
                //I have to scan currentLine and decrypt it too
                Scanner line = new Scanner(Encryption.decryptString(true, currentLine));
                
                //add it to the arrays
                list[count] = new Scores(line.next(), line.nextInt());
            }
            
            //close input
            input.close();
            
        } catch (Exception e) {
            System.out.println("Error in reading highscores.txt");
        }
    }
    
    /**
     * This method is used to put Scores[] in to the JTextArea to be displayed.
     * 
     * PreCondition: ScoreFrame exists and Scores[] has 10 Scores and Scores have 
     * toString method.
     * PostCondition: The JTextArea has setText to the values in Scores[].
     */
    public void updateList(){
        
        //have a String to hold the values of Scores[]
        String values = "";
        
        //have a for loop to add Scores to String values from Scores[]
        for (int count = 0; count < 10; count++){
            values += list[count] + "\n";
        }
        
        //now add that to JTextArea
        scoreList.setText(values);
    }
    
}//end of ScoreFrame class
