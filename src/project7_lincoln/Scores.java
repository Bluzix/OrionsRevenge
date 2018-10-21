/*
 * This is the class for objects that hold names and points of people who played 
 * this game.
 */
package project7_lincoln;

/*
 * Sam Lincoln
 * Lab Section 19
 * Steve Sommer
 * Mr. Ondrasek
 */
public class Scores implements Comparable<Scores> {
    //holds names and points

    private String name;
    private int points;

    /**
     * This is the constructor for a Scores Object.
     *
     * PreCondition: a new Scores object is called for with valid parameters.
     * PostCondition: a new Scores object created that holds these values.
     *
     * @param name This is the String that represents the name of the person who
     * got the score.
     * @param points This is the amount of points the person scored.
     */
    public Scores(String name, int points) {
        this.name = name;
        this.points = points;
    }

    /**
     * This is used to get the points that is in the object.
     *
     * PreCondition: Scores object exists and has point value. PostCondition:
     * points returned as an integer.
     *
     * @return This is the amount of points that the object holds.
     */
    public int getPoints() {
        return points;
    }

    /**
     * This is used to return the name that is in the object.
     *
     * PreCondition: Scores object exists and has name value. PostCondition:
     * name returned as a String.
     *
     * @return A String that is the name of the person who got the points.
     */
    public String getName() {
        return name;
    }

    /**
     * This method is the compareTo method, it can now use the sort method of
     * ArrayList to get highest points set first in an array; this returns 1 if 
     * Scores has less points than the other one in the parameter and -1 if 
     * Scores has more than the parameter.
     *
     * PreCondition: Scores object exists and the parameter is a valid Scores
     * object. PostCondition: an integer is returned that shows if it is greater
     * than or less than the one in the parameter.
     *
     * @param other This is a valid Scores object you wish to compare with.
     * @return This is the integer that tells the rank of the object.
     */
    @Override
    public int compareTo(Scores other) {
        //I have the -1 and 1 returns backwards so that when sorted, highest is first.
        if (getPoints() > other.getPoints()) {
            return -1;
        } else if (getPoints() < other.getPoints()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * This method is used to return the value of Scores object as a String; 
     * both name and points.
     * 
     * PreCondition: Scores object exists and has name and points values.
     * PostCondition: Scores object value is returned in a nice String.
     * 
     * @return The String that holds the values of Scores object.
     */
    @Override
    public String toString() {
        return getName() + " " + getPoints();
    }
}//end of Scores class
