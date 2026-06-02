package controller;

import model.EloStats;
import model.Tournament;
import model.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class EloStatsDAO extends DAO {
    public EloStatsDAO() {
        super();
    }

    public ArrayList<EloStats> getEloStats(Tournament tournament) {
        ArrayList<EloStats> listEloStats = new ArrayList<>();
        String sql = "SELECT ID, registrationDate, tblPlayerID FROM tblParticipationForm WHERE tblTournamentID = ?";
        PlayerDAO playerDAO = new PlayerDAO();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, tournament.getID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int playerID = rs.getInt("tblPlayerID");
                Player player = playerDAO.searchPlayer(playerID);

                float oldElo = player.getEloRating();
                float totalChange = getTotalEloChange(playerID, tournament.getID());
                float newElo = oldElo + totalChange;

                EloStats es = new EloStats(
                        rs.getInt("ID"),
                        rs.getDate("registrationDate"),
                        player,
                        tournament,
                        oldElo,
                        newElo);
                listEloStats.add(es);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listEloStats;
    }

    private float getTotalEloChange(int playerID, int tournamentID) {
        String sql = "SELECT SUM(r.eloChange) FROM tblResult r " +
                "INNER JOIN tblMatch m ON r.tblMatchID = m.ID " +
                "INNER JOIN tblRound rd ON m.tblRoundID = rd.ID " +
                "WHERE r.tblPlayerID = ? AND rd.tblTournamentID = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, playerID);
            ps.setInt(2, tournamentID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getFloat(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0f;
    }

}
