package controller;

import model.Player;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerDAOTest {

    public PlayerDAOTest() {
    }

    @Test
    public void testUpdateElo_Valid() {
        PlayerDAO dao = new PlayerDAO();
        Player player = dao.searchPlayer(1);
        boolean result = dao.updateElo(player, 2400f);
        assertTrue(result);
    }

    @Test
    public void testUpdateElo_Invalid() {
        PlayerDAO dao = new PlayerDAO();
        Player player = dao.searchPlayer(1);
        boolean result = dao.updateElo(player, -200f);
        assertFalse(result);
    }

    @Test
    public void testAddPlayerDuplicateFideID() {
        System.out.println("addPlayer - Trùng FIDE ID");
        Player player = new Player(14, "10001", "Kỳ thủ mới", 2000, "VIE", 1500.0f, "Thêm mới trùng ID");
        PlayerDAO instance = new PlayerDAO();
        boolean result = instance.addPlayer(player);
        assertFalse("Mong đợi thêm thất bại do trùng FIDE ID (UNIQUE CONSTRAINT)", result);
    }

    @Test
    public void testAddPlayerInvalidData() {
        System.out.println("addPlayer - Dữ liệu không hợp lệ");
        Player player = new Player(0, "FIDE_INVALID", null, 2000, "Chuỗi quốc gia rất dài vượt quá giới hạn của varchar trong cơ sở dữ liệu............", 1500.0f, "Lỗi");
        PlayerDAO instance = new PlayerDAO();
        boolean result = instance.addPlayer(player);
        assertFalse("Mong đợi thêm thất bại do dữ liệu không hợp lệ với cấu trúc CSDL", result);
    }

    @Test
    public void testAddPlayerSuccess() {
        System.out.println("addPlayer - Thêm mới hợp lệ");
        Player player = new Player(0, "FIDE_NEW_" + System.currentTimeMillis(), "Kỳ thủ Hợp Lệ", 2005, "VIE", 1600.0f, "Thêm thành công");
        PlayerDAO instance = new PlayerDAO();
        try {
            if (DAO.con != null) DAO.con.setAutoCommit(false);
            boolean result = instance.addPlayer(player);
            assertTrue("Mong đợi thêm kỳ thủ thành công", result);
        } catch (Exception e) {
            fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {}
        }
    }

    @Test
    public void testDeletePlayerSuccess() {
        System.out.println("deletePlayer - Xóa kỳ thủ tồn tại");
        Player player = new Player(0, "FIDE_NEW_" + System.currentTimeMillis(), "Kỳ thủ Hợp Lệ", 2005, "VIE", 1600.0f, "Thêm thành công");
        PlayerDAO instance = new PlayerDAO();
        instance.addPlayer(player);
        
        try {
            if (DAO.con != null) DAO.con.setAutoCommit(false);
            boolean result = instance.deletePlayer(player.getID());
            assertTrue("Mong đợi xóa thành công (có dòng bị xóa)" + player.getID(), result);
        } catch (Exception e) {
            fail("Ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {}
        }
    }

    @Test
    public void testDeletePlayerNotFound() {
        System.out.println("deletePlayer - Xóa kỳ thủ không tồn tại");
        PlayerDAO instance = new PlayerDAO();
        boolean result = instance.deletePlayer(999999);
        assertFalse("Mong đợi xóa thất bại (trả về false do không có dòng nào bị ảnh hưởng)", result);
    }

    @Test
    public void testSearchPlayerFound() {
        System.out.println("searchPlayer - Tìm kỳ thủ tồn tại");
        String name = "A";
        PlayerDAO instance = new PlayerDAO();
        ArrayList<Player> result = instance.searchPlayer(name);
        assertNotNull("Danh sách trả về không được null", result);
        assertTrue("Mong đợi danh sách có chứa ít nhất 1 kỳ thủ (kích thước > 0)", result.size() > 0);
    }

    @Test
    public void testSearchPlayerNotFound() {
        System.out.println("searchPlayer - Tìm kỳ thủ chưa tồn tại");
        String name = "TenNaoDoKhongTheCoThat123456";
        PlayerDAO instance = new PlayerDAO();
        ArrayList<Player> result = instance.searchPlayer(name);
        assertNotNull("Danh sách trả về không được null", result);
        assertEquals("Mong đợi danh sách trả về rỗng vì không tìm thấy ai", 0, result.size());
    }

    @Test
    public void testEditPlayerDuplicateFideID() {
        System.out.println("editPlayerInformation - Trùng FIDE ID");
        Player player = new Player(3, "10001", "Nguyen Van A", 1990, "VIE", 2500.0f, "Sửa bị lỗi do trùng Fide ID");
        PlayerDAO instance = new PlayerDAO();
        boolean result = instance.editPlayerInformation(player);
        assertFalse("Mong đợi cập nhật thất bại do vi phạm khóa duy nhất (UNIQUE FIDE ID)", result);
    }

    @Test
    public void testEditPlayerInvalidData() {
        System.out.println("editPlayerInformation - Dữ liệu không hợp lệ");
        Player player = new Player(1, "FIDE_NEW", null, 1990, "Chuỗi quốc gia rất dài vượt quá giới hạn của cơ sở dữ liệu......................................", 2500.0f, "Dữ liệu sai");
        PlayerDAO instance = new PlayerDAO();
        boolean result = instance.editPlayerInformation(player);
        assertFalse("Mong đợi cập nhật thất bại do dữ liệu đầu vào không hợp lệ với cấu trúc CSDL", result);
    }

    @Test
    public void testEditPlayerSuccess() {
        System.out.println("editPlayerInformation - Cập nhật hợp lệ thành công");
        Player player = new Player(1, "FIDE_VALID", "Nguyen Van Updated", 1995, "VIE", 2650.0f, "Sửa thành công");
        PlayerDAO instance = new PlayerDAO();
        try {
            if (DAO.con != null) DAO.con.setAutoCommit(false);
            boolean result = instance.editPlayerInformation(player);
            assertTrue("Mong đợi cập nhật thành công (trả về true)", result);
        } catch (Exception e) {
            fail("Có lỗi ngoại lệ xảy ra: " + e.getMessage());
        } finally {
            try {
                if (DAO.con != null) {
                    DAO.con.rollback();
                    DAO.con.setAutoCommit(true);
                }
            } catch (Exception ex) {}
        }
    }
}