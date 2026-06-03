package controller;

import model.Tournament;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentDAO extends DAO {

    public TournamentDAO() {
        super();
    }

    public ArrayList<Tournament> getAllFinishedTournaments() {
        ArrayList<Tournament> listTournament = new ArrayList<>();
        String sql = "SELECT * FROM tblTournament WHERE ID IN " +
                "(SELECT tblTournamentID FROM tblRound GROUP BY tblTournamentID HAVING COUNT(*) >= 11)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tournament tournament = new Tournament(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDate("year"),
                        rs.getInt("organizationTimes"),
                        rs.getString("address"),
                        rs.getString("description"));
                listTournament.add(tournament);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTournament;
    }

    public Tournament getTournamentLatest() {
        String sql = "SELECT TOP 1 * FROM tblTournament ORDER BY year DESC, ID DESC";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Tournament tournament = new Tournament(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDate("year"),
                        rs.getInt("organizationTimes"),
                        rs.getString("address"),
                        rs.getString("description"));

                rs.close();
                ps.close();
                return tournament;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLatestTournamentID() {
        try {
            PreparedStatement ps = con
                    .prepareStatement("SELECT TOP 1 ID FROM tblTournament ORDER BY year DESC, ID DESC");
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return -1;
            int latestTournamentID = rs.getInt("ID");
            rs.close();
            ps.close();
            return latestTournamentID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int countMatchesWithoutResult(int roundId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tblMatch m " +
                "WHERE m.tblRoundID = ? " +
                "AND NOT EXISTS (" +
                "    SELECT 1 FROM tblResult r WHERE r.tblMatchID = m.ID" +
                ")";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roundId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
