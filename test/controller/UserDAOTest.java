package controller;

import model.User;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {

    public UserDAOTest() {
    }

    @Test
    public void testCheckLogin_Success() {
        User user = new User("admin", "123456");
        UserDAO dao = new UserDAO();
        boolean result = dao.checkLogin(user);
        assertTrue(result);
    }

    @Test
    public void testCheckLogin_Wrong() {
        User user = new User("admin", "wrongpassword");
        UserDAO dao = new UserDAO();
        boolean result = dao.checkLogin(user);
        assertFalse(result);
    }

    @Test
    public void testCheckLoginSuccess() {
        System.out.println("checkLogin - Tên đăng nhập, mật khẩu đúng");
        User user = new User("hung_pq", "2495");
        UserDAO instance = new UserDAO();
        boolean result = instance.checkLogin(user);
        assertTrue("Mong đợi đăng nhập thành công với tài khoản đúng", result);
        assertNotNull("Họ tên người dùng phải được lấy lên từ DB", user.getFullname());
        assertNotNull("Quyền (role) người dùng phải được lấy lên", user.getRole());
    }

    @Test
    public void testCheckLoginWrongUsername() {
        System.out.println("checkLogin - Mật khẩu hoặc tên đăng nhập sai");
        User user = new User("hehe", "123456");
        UserDAO instance = new UserDAO();
        boolean result = instance.checkLogin(user);
        assertFalse("Mong đợi đăng nhập thất bại do tên đăng nhập không tồn tại", result);
    }
}
