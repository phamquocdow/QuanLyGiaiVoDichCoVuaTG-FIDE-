package model;

import java.util.Date;

public class EloStats extends ParticipationForm {
    private float eloRatingBefore;
    private float eloRatingAfter;

    public EloStats(int id, Date registrationDate, Player player, Tournament tournament,
            float eloRatingBefore, float eloRatingAfter) {
        super(id, registrationDate, player, tournament);
        this.eloRatingBefore = eloRatingBefore;
        this.eloRatingAfter = eloRatingAfter;
    }

    public float getEloRatingBefore() {
        return eloRatingBefore;
    }

    public float getEloRatingAfter() {
        return eloRatingAfter;
    }

}
