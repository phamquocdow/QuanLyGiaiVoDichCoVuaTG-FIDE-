package controller;

import model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserDAOTest {
    
    public UserDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCheckLoginSuccess() {
        System.out.println("checkLogin - Tên đăng nhập, mật khẩu đúng");
        
        User user = new User();
        user.setUsername("hung_pq");
        user.setPassword("2495");
        
        UserDAO instance = new UserDAO();
        boolean result = instance.checkLogin(user);
        
        assertTrue("Mong đợi đăng nhập thành công với tài khoản đúng", result);

        assertNotNull("Họ tên người dùng phải được lấy lên từ DB", user.getFullname());
        assertNotNull("Quyền (role) người dùng phải được lấy lên", user.getRole());
    }
    
    @Test
    public void testCheckLoginWrongUsername() {
        System.out.println("checkLogin - Mật khẩu hoặc tên đăng nhập sai");
        
        User user = new User();
        user.setUsername("hehe");
        user.setPassword("123456");
        
        UserDAO instance = new UserDAO();
        boolean result = instance.checkLogin(user);
        
        assertFalse("Mong đợi đăng nhập thất bại do tên đăng nhập không tồn tại", result);
    }
}
