package controller;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import model.EloStats;
import model.Tournament;
import org.junit.Assert;
import org.junit.Test;

public class EloStatsDAOTest {

    EloStatsDAO esd = new EloStatsDAO();

    @Test
    public void testGetEloStats_WithData() {
        try {
            DAO.con.setAutoCommit(false);

            int playerId = 0;
            try (PreparedStatement psPlayer = DAO.con.prepareStatement(
                    "INSERT INTO tblPlayer (fideID, name, bornYear, nation, eloRating, note) VALUES ('TEST-FIDE', 'Test Player', 2000, 'VN', 2000.0, 'Test')", Statement.RETURN_GENERATED_KEYS)) {
                psPlayer.executeUpdate();
                java.sql.ResultSet rsPlayer = psPlayer.getGeneratedKeys();
                if (rsPlayer.next()) playerId = rsPlayer.getInt(1);
            }

            int tourId = 0;
            try (PreparedStatement psTour = DAO.con.prepareStatement(
                    "INSERT INTO tblTournament (name, year, organizationTimes, address, description, tblUserID) VALUES ('Test Tour Elo', '2026-01-01', 1, 'HN', 'Test', 1)", Statement.RETURN_GENERATED_KEYS)) {
                psTour.executeUpdate();
                java.sql.ResultSet rsTour = psTour.getGeneratedKeys();
                if (rsTour.next()) tourId = rsTour.getInt(1);
            }

            try (PreparedStatement psForm = DAO.con.prepareStatement(
                    "INSERT INTO tblParticipationForm (registrationDate, tblPlayerID, tblTournamentID) VALUES ('2025-12-01', ?, ?)")) {
                psForm.setInt(1, playerId);
                psForm.setInt(2, tourId);
                psForm.executeUpdate();
            }

            int roundId = 0, matchId = 0;
            try (PreparedStatement psRound = DAO.con.prepareStatement("INSERT INTO tblRound (roundNum, tblTournamentID) VALUES (1, ?)", Statement.RETURN_GENERATED_KEYS)) {
                psRound.setInt(1, tourId);
                psRound.executeUpdate();
                java.sql.ResultSet rsRound = psRound.getGeneratedKeys();
                if (rsRound.next()) roundId = rsRound.getInt(1);
            }
            try (PreparedStatement psMatch = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Test Match', ?)", Statement.RETURN_GENERATED_KEYS)) {
                psMatch.setInt(1, roundId);
                psMatch.executeUpdate();
                java.sql.ResultSet rsMatch = psMatch.getGeneratedKeys();
                if (rsMatch.next()) matchId = rsMatch.getInt(1);
            }
            
            try (PreparedStatement psResult = DAO.con.prepareStatement("INSERT INTO tblResult (score, eloChange, tblPlayerID, tblMatchID) VALUES (?, ?, ?, ?)")) {
                psResult.setFloat(1, 1.0f); psResult.setFloat(2, 5.5f); psResult.setInt(3, playerId); psResult.setInt(4, matchId);
                psResult.executeUpdate();
                
                psResult.setFloat(1, 0.0f); psResult.setFloat(2, -2.0f); psResult.setInt(3, playerId); psResult.setInt(4, matchId);
                psResult.executeUpdate();
            }

            Tournament mockTour = new Tournament(tourId, "Test Tour Elo", new java.util.Date(), 1, "HN", "Test");
            ArrayList<EloStats> stats = esd.getEloStats(mockTour);

            Assert.assertNotNull(stats);
            Assert.assertEquals("Phai co 1 ky thu trong danh sach", 1, stats.size());
            
            EloStats resultStat = stats.get(0);
            Assert.assertEquals("Elo cu phai la 2000", 2000.0f, resultStat.getEloRatingBefore(), 0.001);
            Assert.assertEquals("Elo moi phai la 2003.5", 2003.5f, resultStat.getEloRatingAfter(), 0.001);

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
    
    @Test
    public void testGetEloStats_EmptyTournament() {
        Tournament mockTour = new Tournament(-999, "Empty Tour", new java.util.Date(), 1, "HN", "Test");
        
        ArrayList<EloStats> stats = esd.getEloStats(mockTour);
        
        Assert.assertNotNull(stats);
        Assert.assertEquals("Danh sach phai rong", 0, stats.size());
    }
}