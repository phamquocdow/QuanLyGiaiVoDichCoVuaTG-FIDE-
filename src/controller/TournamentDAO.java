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
                        rs.getInt("id"),
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

}
