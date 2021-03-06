/*
 * This is the class used to encrypt the high scores file, it is a modified version 
 * of my Lab 7 program.
 */
package project7_lincoln;

import java.io.*;
import java.util.Scanner;

/**
 * Sam Lincoln Lab Section 19 Steve Sommer Mr. Ondrasek
 */
public class Encryption {

    /**
     * The getRandomString method generates a string of characters. Parameter
     * controls the number of characters.
     *
     * PreCondition: A string has been declared and that string is to be
     * assigned a value from this method. PostCondition: A string is created
     * with this method and returned to be stored in a variable or used in one.
     *
     * @param length Is the number of characters to be in this string.
     * @return Is the string generated by the method.
     */
    public static String getRandomString(int length) {
        //create a blank string to hold characters
        String newString = "";

        //for loop to fill string with characters
        for (int counter = 0; counter < length; counter++) {
            int blankSpace = (int) Math.random() * 100;
            if (blankSpace < 50) {
                char newChar = (char) ('!' + Math.random() * ('~' - '!' - 1));
                newString += newChar;
            }//end
            else {
                newString += " ";
            }//end else
        }//end for loop
        return newString;
    }//end of getRandomString method

    /**
     * The encryptString method is used to encrypt a string that is passed to
     * it, includes a parameter to control if you want to encrypt blank
     * characters.
     *
     * PreCondition: A string is declared is set to hold the String that this
     * method returns, a boolean is passed to method for blank character
     * encryption, a string is passed that needs encryption. PostCondition: A
     * string is created with this method and returned.
     *
     * @param doBlanks A boolean to state if you want blank characters
     * encrypted.
     * @param inputString A string that needs to be encrypted.
     * @return The encrypted string created by this method.
     */
    public static String encryptString(boolean doBlanks, String inputString) {

        //create a new string to hold encrypted characters
        String encryptedString = "";

        //if to check if we need to encrypt blank spaces
        if (doBlanks) {
            //for loop that checks each character of inputString and encrypts it
            for (int counter2 = 0; counter2 < inputString.length(); counter2++) {
                encryptedString += (char) ((int) inputString.charAt(counter2) * 5 + 23);
            }//end for that fills encryptedString
        }//end if for doBlank boolean
        else {
            //for loop that checks each character of inputString and encrypts it
            for (int counter3 = 0; counter3 < inputString.length(); counter3++) {
                //check that it is not a blank space
                if (inputString.charAt(counter3) != ' ') {
                    encryptedString += (char) ((int) inputString.charAt(counter3) * 5 + 23);
                }//end of if that checks for blank
            }//end for that fills encryptedString
        }//end else for if(doBlank)
        return encryptedString;
    }//end of encryptString method

    /**
     * The getFile method is used to get a valid file name.
     *
     * PreCondition: A File is declared and needs a value. PostCondition: The
     * file is valid and returned.
     *
     * @return The valid file that can have data written to it.
     */
    public static File getFile() {
        //initalize variables
        Scanner input = new Scanner(System.in);
        File selectedFile;

        //do while for asking the file name;
        do {
            System.out.print("Enter the file name to store the string: ");
            selectedFile = new File(input.nextLine());

            //print some warnings/exceptions
            if (selectedFile.exists()) {
                System.out.println("This file exists.  Method is set to reject existing files.");
            }//end if for existing file

        } while (selectedFile.exists());

        //return file
        return selectedFile;
    }//end of getAndSaveToFile method

    /**
     * saveToFile method is used to write the String to the valid file.
     *
     * PreCondition: A file that can be written to is passed to the method and a
     * String is passed to it to be written to the file. PostCondition: A file
     * with text data from the string has been created.
     *
     * @param writeTo Is the valid file that the String is to be written to.
     * @param content Is the String that is to be written to the file.
     */
    public static void saveToFile(File writeTo, String content) {
        //declare variables
        Scanner readString = new Scanner(content);

        //do a try catch for FileNotFoundException
        try {
            //declare PrintWriter
            PrintWriter output = new PrintWriter(writeTo);

            //while loop for long strings
            while (readString.hasNext()) {
                output.println(readString.nextLine());
            }//end while to write to file

            //close output
            output.close();

            //print that the file has been written to
            System.out.println("The encrypted String is written to file " + writeTo.getName());
        } //catch FileNotFound
        catch (FileNotFoundException e) {
            System.out.println("ERROR: File cannot be found to write to.");
        }
    }

    /**
     * The readFile method reads the string stored in the file parameter.
     *
     * PreCondition: A valid file with Strings written to it is passed to method
     * and a String is needed to be returned and used. PostCondition: The
     * Strings from the file is read and passed back.
     *
     * @param inputFile This is the valid file that contains Strings.
     * @return This is the String of the contents from the file. Returns empty String
     * if file is not found.
     */
    public static String readFile(File inputFile) {
        //try catch for FileNotFoundException
        try {
            //declare variables
            Scanner readFile = new Scanner(inputFile);
            String contents = "";

            //if to check if file can be read
            if (inputFile.canRead()) {
                //while loop to read File
                while (readFile.hasNext()) {
                    contents += readFile.nextLine();
                }//end while to read File
            }//end if for canRead
            else {
                System.out.println("This file cannot be read.");
            }

            //return String
            return contents;
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found to read from.");
            return "";
        }
    }//end of readFile method

    /**
     * The decryptString method can be used to decrypt the string passed to it,
     * has the boolean to check if blanks are part of the decryption.
     *
     * PreCondition: A String, encrypted by the encryptString method of this
     * class is passed to the String parameter. The doBlanks is passed to the
     * method. A decrypted string is needed to be passed back.
     *
     * @param doBlanks A boolean control; if false, it skips decrypting spaces.
     * @param inputString This is the String needed to be decrypted. Only works
     * if created by encryptString method.
     * @return The decrypted String.
     */
    public static String decryptString(boolean doBlanks, String inputString) {
        //create a new string to hold decrypted characters
        String decryptedString = "";

        //if to check if we need to encrypt blank spaces
        if (doBlanks) {
            //for loop that checks each character of inputString and encrypts it
            for (int counter2 = 0; counter2 < inputString.length(); counter2++) {
                decryptedString += (char) (((int) inputString.charAt(counter2) - 23) / 5);
            }//end for that fills decryptedString
        }//end if for doBlank boolean
        else {
            //for loop that checks each character of inputString and encrypts it
            for (int counter3 = 0; counter3 < inputString.length(); counter3++) {
                //check that it is not a blank space
                if (inputString.charAt(counter3) != ' ') {
                    decryptedString += (char) (((int) inputString.charAt(counter3) - 23) / 5);
                }//end of if that checks for blank
            }//end for that fills decryptedString
        }//end else for if(doBlank)
        return decryptedString;
    }//end of decryptString method
}//end of class
