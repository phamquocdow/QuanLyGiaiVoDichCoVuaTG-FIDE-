package model;

public class Player {
    private int id;
    private String fideID;
    private String name;
    private int bornYear;
    private String nation;
    private float eloRating;
    private String note;

    public Player() {
    }

    public Player(int id, String fideID, String name, int bornYear, String nation, float eloRating, String note) {
        this.id = id;
        this.fideID = fideID;
        this.name = name;
        this.bornYear = bornYear;
        this.nation = nation;
        this.eloRating = eloRating;
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFideID() {
        return fideID;
    }

    public void setFideID(String fideID) {
        this.fideID = fideID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBornYear() {
        return bornYear;
    }

    public void setBornYear(int bornYear) {
        this.bornYear = bornYear;
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
        this.eloRating = eloRating;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
