/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.sql.PreparedStatement;
import model.Match;
import model.Result;
import controller.DAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Assert;

/**
 *
 * @author MSI PC
 */
public class ResultDAOTest {

    private ResultDAO resultDAO;

    public ResultDAOTest() {
    }

    @Before
    public void setUp() {
        // Khởi tạo DAO (đồng thời khởi tạo kết nối CSDL trong constructor controller.DAO)
        resultDAO = new ResultDAO(); 
    }

    @Test
    public void testGetResultMatch_HaveData() {
        Match match = new Match(1, 0, "");

        ResultDAO dao = new ResultDAO();

        ArrayList<Result> results = dao.getResultMatch(match);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        Result firstResult = results.get(0);

        assertTrue(firstResult.getID() > 0);
        assertNotNull(firstResult.getPlayer());
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
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0, "");

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 0.5f, 2400f);

        assertTrue(updated);
    }

    @Test
    public void testUpdateResult_InvalidScore() {
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0, "");

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 2.0f, 2400f);

        assertFalse(updated);
    }

    @Test
    public void testUpdateResult_InvalidElo() {
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0, "");

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 0.5f, -100f);

        assertFalse(updated);
    }

    // =======================================================
    // TC7: Lấy danh sách các cặp đã thi đấu (CSDL có dữ liệu)
    // =======================================================
    @Test
    public void testGetPlayedPairs_WithData() {
        try {
            // 1. Tắt auto commit
            DAO.con.setAutoCommit(false);
            
            // Xóa dữ liệu cũ để đảm bảo kết quả chính xác
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            // 2. Insert dữ liệu giả
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (ID, matchNum, name, tblRoundID) VALUES (9999, 1, 'Fake Match', 1)");
            ps1.executeUpdate();
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (9901, 9999, 1, 0), (9902, 9999, 0, 0)");
            ps2.executeUpdate();

            // 3. Gọi hàm cần test và Assert kết quả
            HashSet<String> playedPairs = resultDAO.getPlayedPairs();
            
            Assert.assertNotNull(playedPairs);
            Assert.assertTrue("Phải chứa cặp 9901-9902", playedPairs.contains("9901-9902"));

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            // 4. Rollback và bật lại auto commit để dọn dẹp CSDL
            try {
                if (DAO.con != null) {
                    DAO.con.rollback(); 
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // =======================================================
    // TC8: Lấy danh sách các cặp đã thi đấu (CSDL rỗng)
    // =======================================================
    @Test
    public void testGetPlayedPairs_Empty() {
        try {
            DAO.con.setAutoCommit(false);
            
            // Xóa dữ liệu cũ để giả định CSDL hoàn toàn rỗng
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            // Gọi hàm getPlayedPairs khi CSDL không có lịch sử
            HashSet<String> playedPairs = resultDAO.getPlayedPairs();
            
            // Assert trả về HashSet không null và size = 0
            Assert.assertNotNull(playedPairs); 
            Assert.assertEquals(0, playedPairs.size());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback(); 
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // =======================================================
    // TC9: Lấy danh sách kỳ thủ đã nhận BYE
    // =======================================================
    @Test
    public void testGetPlayersWithBye() {
        try {
            DAO.con.setAutoCommit(false);
            
            // Xóa dữ liệu cũ
            PreparedStatement psDeleteResult = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDeleteResult.executeUpdate();
            PreparedStatement psDeleteMatch = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDeleteMatch.executeUpdate();

            // Insert 1 Match giả chỉ có 1 Result (tượng trưng cho 1 người nhận BYE)
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (ID, matchNum, name, tblRoundID) VALUES (8888, 2, 'Fake BYE Match', 1)");
            ps1.executeUpdate();
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (9999, 8888, 1, 0)");
            ps2.executeUpdate();

            // Gọi hàm getPlayersWithBye và Assert
            HashSet<Integer> playersWithBye = resultDAO.getPlayersWithBye();
            
            Assert.assertNotNull(playersWithBye);
            Assert.assertTrue("Phải chứa ID 9999", playersWithBye.contains(9999));

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback(); 
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}