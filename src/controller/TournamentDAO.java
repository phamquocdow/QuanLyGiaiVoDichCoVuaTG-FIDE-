package controller;

import model.Tournament;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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

}
