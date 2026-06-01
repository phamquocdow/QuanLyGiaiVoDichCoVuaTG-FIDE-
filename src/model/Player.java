package model;

import java.util.Date;

public class Player {
    private int ID;
    private String name;
    private int birthYear;
    private String nation;
    private float eloRating;
    private String note;

    public Player() {
    }

    public Player(int ID, String name, int birthYear, String nation, float eloRating, String note) {
        this.ID = ID;
        this.name = name;
        this.birthYear = birthYear;
        this.nation = nation;
        this.eloRating = eloRating;
        this.note = note;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public float getEloRating() {
        return eloRating;
    }

    public void setEloRating(float eloRating) {
        if (eloRating < 0) {
            System.err.println("Elo không thể nhỏ hơn 0");
            return;
        }
        this.eloRating = eloRating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    
}
