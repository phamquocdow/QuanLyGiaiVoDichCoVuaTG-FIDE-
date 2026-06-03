package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.sql.PreparedStatement;
import model.Match;
import model.Result;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

public class ResultDAOTest {

    private ResultDAO resultDAO;

    public ResultDAOTest() {
    }

    @Before
    public void setUp() {
        resultDAO = new ResultDAO(); 
    }

    private int ensureRoundExists() throws Exception {
        try (PreparedStatement psFind = DAO.con.prepareStatement("SELECT TOP 1 ID FROM tblRound ORDER BY ID");
             java.sql.ResultSet rsFind = psFind.executeQuery()) {
            if (rsFind.next()) return rsFind.getInt(1);
        }
        
        int tournamentId;
        try (PreparedStatement psFindT = DAO.con.prepareStatement("SELECT TOP 1 ID FROM tblTournament ORDER BY ID");
             java.sql.ResultSet rsFindT = psFindT.executeQuery()) {
            if (rsFindT.next()) {
                tournamentId = rsFindT.getInt(1);
            } else {
                try (PreparedStatement psInsert = DAO.con.prepareStatement(
                        "INSERT INTO tblTournament (name, year, organizationTimes, address, description, tblUserID) VALUES (?, ?, ?, ?, ?, 1)",
                        java.sql.Statement.RETURN_GENERATED_KEYS)) {
                    psInsert.setString(1, "Test Tournament");
                    psInsert.setString(2, "2026");
                    psInsert.setInt(3, 1);
                    psInsert.setString(4, "Test Location");
                    psInsert.setString(5, "Test");
                    psInsert.executeUpdate();
                    try (java.sql.ResultSet rsInsert = psInsert.getGeneratedKeys()) {
                        rsInsert.next();
                        tournamentId = rsInsert.getInt(1);
                    }
                }
            }
        }
        
        try (PreparedStatement psRoundInsert = DAO.con.prepareStatement(
                "INSERT INTO tblRound (roundNum, tblTournamentID) VALUES (?, ?)", java.sql.Statement.RETURN_GENERATED_KEYS)) {
            psRoundInsert.setInt(1, 1);
            psRoundInsert.setInt(2, tournamentId);
            psRoundInsert.executeUpdate();
            try (java.sql.ResultSet rsRound = psRoundInsert.getGeneratedKeys()) {
                rsRound.next();
                return rsRound.getInt(1);
            }
        }
    }

    private int createPlayer() throws Exception {
        try (PreparedStatement psPlayer = DAO.con.prepareStatement(
                "INSERT INTO tblPlayer (name, fideID, bornYear, nation, eloRating, note) VALUES (?, ?, ?, ?, ?, ?)", 
                java.sql.Statement.RETURN_GENERATED_KEYS)) {
            psPlayer.setString(1, "Test Player");
            psPlayer.setString(2, "FIDE" + System.nanoTime());
            psPlayer.setInt(3, 1990);
            psPlayer.setString(4, "VN");
            psPlayer.setFloat(5, 1000f);
            psPlayer.setString(6, "Test");
            psPlayer.executeUpdate();
            try (java.sql.ResultSet rsPlayer = psPlayer.getGeneratedKeys()) {
                rsPlayer.next();
                return rsPlayer.getInt(1);
            }
        }
    }

    @Test
    public void testGetResultMatch_HaveData() {
        try {
            DAO.con.setAutoCommit(false);
            int roundId = ensureRoundExists();
            int p1Id = createPlayer();
            
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Fake Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.executeUpdate();

            Match match = new Match(matchId, 0, "");
            ResultDAO dao = new ResultDAO();
            ArrayList<Result> results = dao.getResultMatch(match);

            assertNotNull(results);
            assertFalse(results.isEmpty());
            Result firstResult = results.get(0);
            assertTrue(firstResult.getID() > 0);
            assertNotNull(firstResult.getPlayer());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testGetResultMatch_NoData() {
        Match match = new Match(9999, 0, "");
        ResultDAO dao = new ResultDAO();
        ArrayList<Result> results = dao.getResultMatch(match);
        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    public void testUpdateResult_ValidData() {
        try {
            DAO.con.setAutoCommit(false);
            int roundId = ensureRoundExists();
            int p1Id = createPlayer();
            
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Fake Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.executeUpdate();

            Match match = new Match(matchId, 0, "");
            ResultDAO dao = new ResultDAO();
            Result result = dao.getResultMatch(match).get(0);
            boolean updated = dao.updateResult(result, 0.5f, 2400f);
            assertTrue(updated);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testUpdateResult_InvalidScore() {
        try {
            DAO.con.setAutoCommit(false);
            int roundId = ensureRoundExists();
            int p1Id = createPlayer();
            
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Fake Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.executeUpdate();

            Match match = new Match(matchId, 0, "");
            ResultDAO dao = new ResultDAO();
            Result result = dao.getResultMatch(match).get(0);
            boolean updated = dao.updateResult(result, 2.0f, 2400f);
            assertFalse(updated);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testUpdateResult_InvalidElo() {
        try {
            DAO.con.setAutoCommit(false);
            int roundId = ensureRoundExists();
            int p1Id = createPlayer();
            
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Fake Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.executeUpdate();

            Match match = new Match(matchId, 0, "");
            ResultDAO dao = new ResultDAO();
            Result result = dao.getResultMatch(match).get(0);
            boolean updated = dao.updateResult(result, 0.5f, -100f);
            assertFalse(updated);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testGetPlayedPairs_WithData() {
        try {
            DAO.con.setAutoCommit(false);
            
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            int roundId = ensureRoundExists();
            int p1Id = createPlayer();
            int p2Id = createPlayer();

            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (1, 'Fake Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0), (?, ?, 0, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.setInt(3, p2Id);
            ps2.setInt(4, matchId);
            ps2.executeUpdate();

            HashSet<String> playedPairs = resultDAO.getPlayedPairs();
            
            Assert.assertNotNull(playedPairs);
            
            String pair1 = p1Id + "-" + p2Id;
            String pair2 = p2Id + "-" + p1Id;
            boolean containsPair = playedPairs.contains(pair1) || playedPairs.contains(pair2);
            Assert.assertTrue("Phải chứa cặp thi đấu", containsPair);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testGetPlayedPairs_Empty() {
        try {
            DAO.con.setAutoCommit(false);
            
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            HashSet<String> playedPairs = resultDAO.getPlayedPairs();
            
            Assert.assertNotNull(playedPairs); 
            Assert.assertEquals(0, playedPairs.size());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }

    @Test
    public void testGetPlayersWithBye() {
        try {
            DAO.con.setAutoCommit(false);
            
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            int roundId = ensureRoundExists();
            int p1Id = createPlayer();

            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (2, 'Fake BYE Match', ?)", java.sql.Statement.RETURN_GENERATED_KEYS);
            ps1.setInt(1, roundId);
            ps1.executeUpdate();
            int matchId = -1;
            try (java.sql.ResultSet rs = ps1.getGeneratedKeys()) {
                if (rs.next()) matchId = rs.getInt(1);
            }
            
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, 1, 0)");
            ps2.setInt(1, p1Id);
            ps2.setInt(2, matchId);
            ps2.executeUpdate();

            HashSet<Integer> playersWithBye = resultDAO.getPlayersWithBye();
            
            Assert.assertNotNull(playersWithBye);
            Assert.assertTrue("Phải chứa ID người chơi nhận BYE", playersWithBye.contains(p1Id));

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try { if (DAO.con != null) { DAO.con.rollback(); DAO.con.setAutoCommit(true); } } catch (Exception ex) {}
        }
    }
}