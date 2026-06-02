
package model;

import java.util.ArrayList;


public class Round {
    private int ID;
    private int roundNum;
    private ArrayList<Match> matches;

    public Round() {
    }

    public Round(int ID, int roundNum, ArrayList<Match> matches) {
        this.ID = ID;
        this.roundNum = roundNum;
        this.matches = matches;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getRoundNum() {
        return roundNum;
    }

    public void setRoundNum(int roundNum) {
        this.roundNum = roundNum;
    }

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }
    
    
}
