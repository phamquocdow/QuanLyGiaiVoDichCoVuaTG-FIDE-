package model;

public class Result {
    private int ID;
    private float score;
    private float eloChange;
    private Player player;
    private Match match;

    public Result() {
    }

    public Result(int ID, float score, float eloChange, Player player, Match match) {
        this.ID = ID;
        this.score = score;
        this.eloChange = eloChange;
        this.player = player;
        this.match = match;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        if (score != 0.0f && score != 0.5f && score != 1.0f) {
        throw new IllegalArgumentException(
            "Score chỉ được phép là 0, 0.5 hoặc 1"
        );
    }
        this.score = score;
    }

    public float getEloChange() {
        return eloChange;
    }

    public void setEloChange(float eloChange) {
        this.eloChange = eloChange;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    
    
}
