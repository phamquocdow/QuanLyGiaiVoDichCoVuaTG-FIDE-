package service;

import controller.DAO;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import model.Player;
import model.Round;
import model.Standing;
import org.junit.Assert;
import org.junit.Test;

public class PairingServiceTest {

    // Helper tạo dữ liệu giả trên RAM cho Standings
    private Standing createMockStanding(int id, float score) {
        Player p = new Player();
        p.setID(id);
        p.setName("Player " + id);
        
        Standing s = new Standing();
        s.setPlayer(p);
        s.setTotalScore(score);
        return s;
    }

    // =======================================================
    // TC1: 4 người (số lượng chẵn), chưa từng thi đấu
    // =======================================================
    @Test
    public void testCreatePairings_EvenPlayers() {
        PairingService service = new PairingService();
        ArrayList<Standing> standings = new ArrayList<>();
        standings.add(createMockStanding(1, 4.0f)); // Hạng 1
        standings.add(createMockStanding(2, 3.0f)); // Hạng 2
        standings.add(createMockStanding(3, 2.0f)); // Hạng 3
        standings.add(createMockStanding(4, 1.0f)); // Hạng 4

        ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

        Assert.assertNotNull(pairings);
        Assert.assertEquals(2, pairings.size());
        
        // Hạng 1 đấu Hạng 2
        Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
        Assert.assertEquals(2, pairings.get(0).getPlayer2().getID());

        // Hạng 3 đấu Hạng 4
        Assert.assertEquals(3, pairings.get(1).getPlayer1().getID());
        Assert.assertEquals(4, pairings.get(1).getPlayer2().getID());
    }

    // =======================================================
    // TC2: 3 người (số lượng lẻ), người cuối bảng nhận BYE
    // =======================================================
    @Test
    public void testCreatePairings_OddPlayers() {
        PairingService service = new PairingService();
        ArrayList<Standing> standings = new ArrayList<>();
        standings.add(createMockStanding(1, 3.0f)); // Hạng 1
        standings.add(createMockStanding(2, 2.0f)); // Hạng 2
        standings.add(createMockStanding(3, 1.0f)); // Hạng 3

        ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

        Assert.assertNotNull(pairings);
        Assert.assertEquals(2, pairings.size());
        
        // Bàn 1: Hạng 1 đấu Hạng 2
        Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
        Assert.assertEquals(2, pairings.get(0).getPlayer2().getID());

        // Bàn 2: Hạng 3 nhận BYE (player2 là null)
        Assert.assertEquals(3, pairings.get(1).getPlayer1().getID());
        Assert.assertNull(pairings.get(1).getPlayer2());
    }

    // =======================================================
    // TC3: 4 người, Hạng 1 và Hạng 2 ĐÃ TỪNG thi đấu
    // =======================================================
    @Test
    public void testCreatePairings_AvoidDuplicate() {
        try {
            // Đảm bảo Connection đã khởi tạo
            new controller.ResultDAO();
            DAO.con.setAutoCommit(false);
            
            // Xóa dữ liệu cũ để đảm bảo kết quả chính xác
            PreparedStatement psDel1 = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDel1.executeUpdate();
            PreparedStatement psDel2 = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDel2.executeUpdate();

            // Insert giả lập Hạng 1 và Hạng 2 đã từng thi đấu ở vòng trước
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (ID, matchNum, name, tblRoundID) VALUES (999, 1, 'Mock Match', 1)");
            ps1.executeUpdate();
            // ID phải tương ứng với ID của mock Standing (1 và 2)
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (1, 999, 0, 0), (2, 999, 1, 0)");
            ps2.executeUpdate();

            PairingService service = new PairingService();
            ArrayList<Standing> standings = new ArrayList<>();
            standings.add(createMockStanding(1, 4.0f)); // Hạng 1
            standings.add(createMockStanding(2, 3.0f)); // Hạng 2
            standings.add(createMockStanding(3, 2.0f)); // Hạng 3
            standings.add(createMockStanding(4, 1.0f)); // Hạng 4

            ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

            Assert.assertNotNull(pairings);
            Assert.assertEquals(2, pairings.size());
            
            // Hạng 1 không thể đấu Hạng 2 nữa, phải chuyển xuống đấu Hạng 3
            Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
            Assert.assertEquals(3, pairings.get(0).getPlayer2().getID());

            // Hạng 2 sẽ đấu với Hạng 4
            Assert.assertEquals(2, pairings.get(1).getPlayer1().getID());
            Assert.assertEquals(4, pairings.get(1).getPlayer2().getID());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception e) {}
        }
    }

    // =======================================================
    // TC4: 3 người, người chót bảng (Hạng 3) ĐÃ TỪNG nhận BYE
    // =======================================================
    @Test
    public void testCreatePairings_AvoidDoubleBye() {
        try {
            new controller.ResultDAO();
            DAO.con.setAutoCommit(false);
            
            // Xóa dữ liệu cũ
            PreparedStatement psDel1 = DAO.con.prepareStatement("DELETE FROM tblResult");
            psDel1.executeUpdate();
            PreparedStatement psDel2 = DAO.con.prepareStatement("DELETE FROM tblMatch");
            psDel2.executeUpdate();

            // Insert giả lập Hạng 3 đã từng nhận BYE ở vòng trước (Match chỉ có 1 Result)
            PreparedStatement ps1 = DAO.con.prepareStatement("INSERT INTO tblMatch (ID, matchNum, name, tblRoundID) VALUES (998, 1, 'Mock BYE', 1)");
            ps1.executeUpdate();
            // ID tương ứng với Hạng 3 là 3
            PreparedStatement ps2 = DAO.con.prepareStatement("INSERT INTO tblResult (tblPlayerID, tblMatchID, score, eloChange) VALUES (3, 998, 1, 0)");
            ps2.executeUpdate();

            PairingService service = new PairingService();
            ArrayList<Standing> standings = new ArrayList<>();
            standings.add(createMockStanding(1, 3.0f)); // Hạng 1
            standings.add(createMockStanding(2, 2.0f)); // Hạng 2
            standings.add(createMockStanding(3, 1.0f)); // Hạng 3

            ArrayList<PairingService.PairingRow> pairings = service.createPairings(standings);

            Assert.assertNotNull(pairings);
            Assert.assertEquals(2, pairings.size());
            
            // Hạng 3 đã nhận BYE rồi, nên người nhận BYE vòng này phải là Hạng 2
            // Vậy Bàn 1: Hạng 1 sẽ đấu với Hạng 3
            Assert.assertEquals(1, pairings.get(0).getPlayer1().getID());
            Assert.assertEquals(3, pairings.get(0).getPlayer2().getID());

            // Bàn 2: Hạng 2 nhận BYE (player2 là null)
            Assert.assertEquals(2, pairings.get(1).getPlayer1().getID());
            Assert.assertNull(pairings.get(1).getPlayer2());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Ngoại lệ: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception e) {}
        }
    }

    // =======================================================
    // TC5: Lưu danh sách PairingRow chuẩn xuống CSDL
    // =======================================================
    @Test
    public void testSavePairings_Standard() {
        PairingService service = new PairingService();
        
        // 1. Tạo dữ liệu giả: 1 cặp đấu hợp lệ
        Player p1 = new Player(); p1.setID(101); p1.setName("P1");
        Player p2 = new Player(); p2.setID(102); p2.setName("P2");
        PairingService.PairingRow row = new PairingService.PairingRow(1, p1, p2);
        
        ArrayList<PairingService.PairingRow> rows = new ArrayList<>();
        rows.add(row);
        
        // Tạo Round giả
        Round round = new Round();
        round.setID(1);
        round.setRoundNum(1);
        
        // 2. Gọi hàm cần test (Luồng chuẩn)
        boolean result = service.savePairings(rows, round);
        
        // 3. Assert kết quả trả về là true
        Assert.assertTrue("Lưu kết quả thất bại", result);
        
        // Lưu ý: Hàm savePairings sẽ tự động gọi DAO.con.commit() nếu thành công.
        // Đề bài không yêu cầu dùng Rollback Pattern cho hàm này vì đây là luồng Save chuẩn.
    }

    // =======================================================
    // TC6: Ngoại lệ (Danh sách null hoặc rỗng)
    // =======================================================
    @Test
    public void testSavePairings_Exception() {
        PairingService service = new PairingService();
        Round round = new Round();
        round.setID(1);
        
        // Lần 1: parameter null
        boolean result1 = service.savePairings(null, round);
        Assert.assertFalse("Truyền null phải trả về false", result1);
        
        // Lần 2: ArrayList rỗng
        ArrayList<PairingService.PairingRow> emptyList = new ArrayList<>();
        boolean result2 = service.savePairings(emptyList, round);
        Assert.assertFalse("Truyền list rỗng phải trả về false", result2);
    }
}

