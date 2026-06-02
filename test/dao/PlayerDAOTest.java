/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import model.Player;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.PlayerDAO;

import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class PlayerDAOTest {
    
    public PlayerDAOTest() {
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

    /**
     * Test of updateElo method, of class PlayerDAO.
     */
    @Test
    public void testUpdateElo() {
        System.out.println("updateElo");
        Player player = null;
        float elo = 0.0F;
        PlayerDAO instance = new PlayerDAO();
        boolean expResult = false;
        boolean result = instance.updateElo(player, elo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
