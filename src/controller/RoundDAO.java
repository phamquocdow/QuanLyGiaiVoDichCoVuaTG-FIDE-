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
        ArrayList<Round> rounds = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRound WHERE tblTournamentID = ?");
            ps.setInt(1, tournamentID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<Match> matches = new ArrayList<>();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblMatch WHERE tblRoundID = ?");
                ps1.setInt(1, rs.getInt("ID"));
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    Match match = new Match(
                            rs1.getInt("ID"),
                            rs1.getInt("matchNum"),
                            rs1.getString("name"));
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

    public Round getLatestRoundByNumber(int roundNum) {
        try {
             PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRound WHERE tblTournamentID = ? AND roundNum = ?");
            ps.setInt(1, (new TournamentDAO()).getLatestTournamentID());
            ps.setInt(2, roundNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ArrayList<Match> matches = new ArrayList<>();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblMatch WHERE tblRoundID = ?");
                ps1.setInt(1, rs.getInt("ID"));
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    Match match = new Match(
                            rs1.getInt("ID"),
                            rs1.getInt("matchNum"),
                            rs1.getString("name"));
                    matches.add(match);
                }
                Round round = new Round(
                        rs.getInt("ID"),
                        rs.getInt("roundNum"),
                        matches);
                return round;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}