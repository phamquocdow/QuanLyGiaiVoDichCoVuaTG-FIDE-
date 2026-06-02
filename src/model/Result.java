package model;

public class Result {
    private int ID;
    private float score;
    private float eloChange;
    private Player player;
    private Match match;

    public Result(int ID, float score, float eloChange, Player player, Match match) {
        this.ID = ID;
        setScore(score);
        this.eloChange = eloChange;
        this.player = player;
        this.match = match;
    }

    public int getID() {
        return ID;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        if (score != 0.0f && score != 0.5f && score != 1.0f) {
            throw new IllegalArgumentException(
                    "Score chỉ được phép là 0, 0.5 hoặc 1");
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

    public Match getMatch() {
        return match;
    }

}
