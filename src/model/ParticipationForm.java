package model;

import java.util.Date;

public class ParticipationForm {
    private int id;
    private Date registrationDate;
    private Player player;
    private Tournament tournament;

    public ParticipationForm(int id, Date registrationDate, Player player, Tournament tournament) {
        this.id = id;
        this.registrationDate = registrationDate;
        this.player = player;
        this.tournament = tournament;
    }

    public int getID() {
        return id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public Player getPlayer() {
        return player;
    }

    public Tournament getTournament() {
        return tournament;
    }

}
