package model;

import java.util.ArrayList;

public class Match {
    private int ID;
    private int matchNum;
    private String name;

    public Match() {
    }

    public Match(int ID, int matchNum, String name) {
        this.ID = ID;
        this.matchNum = matchNum;
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public void setMatchNum(int matchNum) {
        this.matchNum = matchNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
