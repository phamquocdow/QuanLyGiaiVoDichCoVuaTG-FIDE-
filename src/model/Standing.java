package model;

public class Standing {
    private Player player;
    private float totalScore;
    private float totalOpponentScore;
    private float currentElo;
    private int rank;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public float getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(float totalScore) {
        this.totalScore = totalScore;
    }

    public float getTotalOpponentScore() {
        return totalOpponentScore;
    }

    public void setTotalOpponentScore(float totalOpponentScore) {
        this.totalOpponentScore = totalOpponentScore;
    }

    public float getCurrentElo() {
        return currentElo;
    }

    public void setCurrentElo(float currentElo) {
        this.currentElo = currentElo;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
