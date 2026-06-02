/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import model.User;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class UserDAOTest {

    public UserDAOTest() {
    }

    /**
     * Test of checkLogin method, of class UserDAO.
     */
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

}
