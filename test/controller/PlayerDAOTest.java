/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import model.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class PlayerDAOTest {

    public PlayerDAOTest() {
    }

    /**
     * Test of updateElo method, of class PlayerDAO.
     */
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
}
