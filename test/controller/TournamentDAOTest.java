package controller;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import model.Tournament;
import org.junit.Assert;
import org.junit.Test;

public class TournamentDAOTest {

    TournamentDAO td = new TournamentDAO();

    @Test
    public void testGetAllFinishedTournaments() {
        try {
            DAO.con.setAutoCommit(false);

            int tourId = 0;
            try (PreparedStatement psTour = DAO.con.prepareStatement(
                    "INSERT INTO tblTournament (name, year, organizationTimes, address, description, tblUserID) VALUES ('Test Finished Tour', '2026-01-01', 1, 'HN', 'Test', 1)", 
                    Statement.RETURN_GENERATED_KEYS)) {
                psTour.executeUpdate();
                java.sql.ResultSet rsTour = psTour.getGeneratedKeys();
                if (rsTour.next()) tourId = rsTour.getInt(1);
            }

            try (PreparedStatement psRound = DAO.con.prepareStatement("INSERT INTO tblRound (roundNum, tblTournamentID) VALUES (?, ?)")) {
                for (int i = 1; i <= 11; i++) {
                    psRound.setInt(1, i);
                    psRound.setInt(2, tourId);
                    psRound.executeUpdate();
                }
            }

            ArrayList<Tournament> finishedTours = td.getAllFinishedTournaments();

            Assert.assertNotNull(finishedTours);
            boolean isFound = false;
            for (Tournament t : finishedTours) {
                if (t.getID() == tourId) isFound = true;
            }
            Assert.assertTrue("Phải tìm thấy giải đấu vừa được bơm 11 vòng", isFound);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            try {
                DAO.con.rollback();
                DAO.con.setAutoCommit(true);
            } catch (Exception ex) { }
        }
    }
}