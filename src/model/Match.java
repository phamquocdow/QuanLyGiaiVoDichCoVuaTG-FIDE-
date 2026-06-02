package model;

public class Match {
    private int ID;
    private int matchNum;
    private String name;

    public Match(int ID, int matchNum) {
        this.ID = ID;
        this.matchNum = matchNum;
    }

    public int getID() {
        return ID;
    }

    public int getMatchNum() {
        return matchNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
