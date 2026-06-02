package controller;

import java.util.ArrayList;
import model.Round;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Match;

public class RoundDAO extends DAO {
    public RoundDAO() {
        super();
    }

    public ArrayList<Round> getRoundList(int tournamentID) {
        ArrayList<Round> rounds = new ArrayList<Round>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRound WHERE tblTournamentID = ?");
            ps.setInt(1, tournamentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                ArrayList<Match> matches = new ArrayList<Match>();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblMatch WHERE tblRoundID = ?");
                ps1.setInt(1, rs.getInt("ID"));
                ResultSet rs1 = ps1.executeQuery();

                while (rs1.next()) {
                    Match match = new Match(
                            rs1.getInt("ID"),
                            rs1.getInt("matchNum"));
                    matches.add(match);
                }

                Round round = new Round(
                        rs.getInt("ID"),
                        rs.getInt("roundNum"),
                        matches);
                rounds.add(round);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rounds;
    }
}
