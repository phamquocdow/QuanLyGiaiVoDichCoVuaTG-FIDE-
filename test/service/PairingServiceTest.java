package service;

import controller.DAO;
import controller.ResultDAO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import model.Player;
import model.Round;
import model.Standing;
import org.junit.Assert;
import org.junit.Test;

/**
 * JUnit4 tests for PairingService.
 *
 * These tests include pairing logic and savePairings persistence behavior.
 */
public class PairingServiceTest {

    private Standing createMockStanding(int id, float score) {
        Player p = new Player();
        p.setID(id);
        p.setName("Player " + id);

        Standing standing = new Standing();
        standing.setPlayer(p);
        standing.setTotalScore(score);
        return standing;
    }

    private void clearMatchAndResultTables() throws Exception {
        try (PreparedStatement psDelResult = DAO.con.prepareStatement("DELETE FROM tblResult");
             PreparedStatement psDelMatch = DAO.con.prepareStatement("DELETE FROM tblMatch")) {
            psDelResult.executeUpdate();
            psDelMatch.executeUpdate();
        }
    }

    private int findOrCreateTournament() throws Exception {
        try (PreparedStatement psFind = DAO.con.prepareStatement("SELECT TOP 1 ID FROM tblTournament ORDER BY ID");
             ResultSet rsFind = psFind.executeQuery()) {
            if (rsFind.next()) {
                return rsFind.getInt(1);
            }
        }

        try (PreparedStatement psInsert = DAO.con.prepareStatement(
                "INSERT INTO tblTournament (name, year, organizationTimes, address, description, tblUserID) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            psInsert.setString(1, "Test Tournament");
            psInsert.setString(2, "2026");
            psInsert.setInt(3, 1);
            psInsert.setString(4, "Test Location");
            psInsert.setString(5, "Auto-created tournament for tests");
            psInsert.setInt(6, 1);
            psInsert.executeUpdate();
            try (ResultSet rsInsert = psInsert.getGeneratedKeys()) {
                Assert.assertTrue("Tournament ID should be generated", rsInsert.next());
                return rsInsert.getInt(1);
            }
        }
    }

    private int createRound(int roundNum) throws Exception {
        int tournamentId = findOrCreateTournament();
        try (PreparedStatement psRoundDelete = DAO.con.prepareStatement("DELETE FROM tblRound WHERE roundNum = ? AND tblTournamentID = ?");
             PreparedStatement psRoundInsert = DAO.con.prepareStatement(
                     "INSERT INTO tblRound (roundNum, tblTournamentID) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            psRoundDelete.setInt(1, roundNum);
            psRoundDelete.setInt(2, tournamentId);
            psRoundDelete.executeUpdate();
            psRoundInsert.setInt(1, roundNum);
            psRoundInsert.setInt(2, tournamentId);
            psRoundInsert.executeUpdate();
            try (ResultSet rsRound = psRoundInsert.getGeneratedKeys()) {
                Assert.assertTrue("Round ID should be generated", rsRound.next());
                return rsRound.getInt(1);
            }
        }
    }

    @Test
    public void testCreatePairings_EvenPlayers() {
        PairingService service = new PairingService();
        ArrayList<Standing> standings = new ArrayList<>();
        standings.add(createMockStanding(1, 4.0f));
        standings.add(createMockStanding(2, 3.0f));
        standings.add(createMockStanding(3, 2.0f));
        standings.add(createMockStanding(4, 1.0f));

        ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

        Assert.assertNotNull(pairings);
        Assert.assertEquals(2, pairings.size());
        Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
        Assert.assertEquals(2, pairings.get(0).getPlayer2().getID());
        Assert.assertEquals(3, pairings.get(1).getPlayer1().getID());
        Assert.assertEquals(4, pairings.get(1).getPlayer2().getID());
    }

    @Test
    public void testCreatePairings_OddPlayers() {
        PairingService service = new PairingService();
        ArrayList<Standing> standings = new ArrayList<>();
        standings.add(createMockStanding(1, 3.0f));
        standings.add(createMockStanding(2, 2.0f));
        standings.add(createMockStanding(3, 1.0f));

        ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

        Assert.assertNotNull(pairings);
        Assert.assertEquals(2, pairings.size());
        Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
        Assert.assertEquals(2, pairings.get(0).getPlayer2().getID());
        Assert.assertEquals(3, pairings.get(1).getPlayer1().getID());
        Assert.assertNull(pairings.get(1).getPlayer2());
    }

    @Test
    public void testCreatePairings_AvoidDuplicate() {
        try {
            new ResultDAO();
            DAO.con.setAutoCommit(false);
            clearMatchAndResultTables();
            int roundId = createRound(1);

            try (PreparedStatement psMatch = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psResult = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, ?, ?)") ) {
                psMatch.setInt(1, 1);
                psMatch.setString(2, "Mock Match");
                psMatch.setInt(3, roundId);
                psMatch.executeUpdate();
                try (ResultSet generatedKeys = psMatch.getGeneratedKeys()) {
                    Assert.assertTrue("Match ID should be generated", generatedKeys.next());
                    int matchId = generatedKeys.getInt(1);
                    psResult.setInt(1, 1);
                    psResult.setInt(2, matchId);
                    psResult.setInt(3, 0);
                    psResult.setInt(4, 0);
                    psResult.executeUpdate();
                    psResult.setInt(1, 2);
                    psResult.setInt(2, matchId);
                    psResult.setInt(3, 1);
                    psResult.setInt(4, 0);
                    psResult.executeUpdate();
                }
            }

            PairingService service = new PairingService();
            ArrayList<Standing> standings = new ArrayList<>();
            standings.add(createMockStanding(1, 4.0f));
            standings.add(createMockStanding(2, 3.0f));
            standings.add(createMockStanding(3, 2.0f));
            standings.add(createMockStanding(4, 1.0f));

            ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);
            Assert.assertNotNull(pairings);
            Assert.assertEquals(2, pairings.size());
            Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
            Assert.assertEquals(3, pairings.get(0).getPlayer2().getID());
            Assert.assertEquals(2, pairings.get(1).getPlayer1().getID());
            Assert.assertEquals(4, pairings.get(1).getPlayer2().getID());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testCreatePairings_AvoidDoubleBye() {
        try {
            new ResultDAO();
            DAO.con.setAutoCommit(false);
            clearMatchAndResultTables();
            int roundId = createRound(1);

            try (PreparedStatement psMatch = DAO.con.prepareStatement("INSERT INTO tblMatch (matchNum, name, tblRoundID) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psResult = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (?, ?, ?, ?)") ) {
                psMatch.setInt(1, 1);
                psMatch.setString(2, "Mock BYE");
                psMatch.setInt(3, roundId);
                psMatch.executeUpdate();
                try (ResultSet rsMatch = psMatch.getGeneratedKeys()) {
                    Assert.assertTrue("Match ID should be generated", rsMatch.next());
                    int matchId = rsMatch.getInt(1);
                    psResult.setInt(1, 3);
                    psResult.setInt(2, matchId);
                    psResult.setInt(3, 1);
                    psResult.setInt(4, 0);
                    psResult.executeUpdate();
                }
            }

            PairingService service = new PairingService();
            ArrayList<Standing> standings = new ArrayList<>();
            standings.add(createMockStanding(1, 3.0f));
            standings.add(createMockStanding(2, 2.0f));
            standings.add(createMockStanding(3, 1.0f));

            ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);
            Assert.assertNotNull(pairings);
            Assert.assertEquals(2, pairings.size());
            Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
            Assert.assertEquals(3, pairings.get(0).getPlayer2().getID());
            Assert.assertEquals(2, pairings.get(1).getPlayer1().getID());
            Assert.assertNull(pairings.get(1).getPlayer2());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSavePairings_Standard() {
        try {
            new ResultDAO();
            DAO.con.setAutoCommit(false);
            clearMatchAndResultTables();
            int roundId = createRound(1);

            Round round = new Round(roundId, 1, new ArrayList<>());

            Player p1 = new Player();
            p1.setName("P1");
            Player p2 = new Player();
            p2.setName("P2");

            try (PreparedStatement psPlayer = DAO.con.prepareStatement("INSERT INTO tblPlayer (name, fideID, bornYear, nation, eloRating, note) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                psPlayer.setString(1, "P1");
                psPlayer.setString(2, "FIDE" + System.nanoTime());
                psPlayer.setInt(3, 1990);
                psPlayer.setString(4, "VN");
                psPlayer.setFloat(5, 1000f);
                psPlayer.setString(6, "Test");
                psPlayer.executeUpdate();
                try (ResultSet rsPlayer = psPlayer.getGeneratedKeys()) {
                    Assert.assertTrue("Player 1 ID should be generated", rsPlayer.next());
                    p1.setID(rsPlayer.getInt(1));
                }

                psPlayer.setString(1, "P2");
                psPlayer.setString(2, "FIDE" + System.nanoTime() + "2");
                psPlayer.setInt(3, 1990);
                psPlayer.setString(4, "VN");
                psPlayer.setFloat(5, 1000f);
                psPlayer.setString(6, "Test");
                psPlayer.executeUpdate();
                try (ResultSet rsPlayer = psPlayer.getGeneratedKeys()) {
                    Assert.assertTrue("Player 2 ID should be generated", rsPlayer.next());
                    p2.setID(rsPlayer.getInt(1));
                }
            }

            PairingService service = new PairingService();
            ArrayList<PairingService.PairingRow> pairingRows = new ArrayList<>();
            pairingRows.add(new PairingService.PairingRow(1, p1, p2));

            boolean ok = service.savePairings(pairingRows, round);
            Assert.assertTrue(ok);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ignored) {
            }
        }
    }

    @Test
    public void testSavePairings_Exception() {
        PairingService service = new PairingService();
        Round round = new Round(1, 1, new ArrayList<>());

        boolean result1 = service.savePairings(null, round);
        boolean result2 = service.savePairings(new ArrayList<>(), round);

        Assert.assertFalse(result1);
        Assert.assertFalse(result2);
    }
}
