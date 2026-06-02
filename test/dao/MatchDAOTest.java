/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import java.util.ArrayList;
import model.Match;
import model.Round;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.MatchDAO;

import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class MatchDAOTest {
    
    public MatchDAOTest() {
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
     * Test of getMatchRound method, of class MatchDAO.
     */
    @Test
    public void testGetMatchRound() {
        System.out.println("getMatchRound");
        Round round = null;
        MatchDAO instance = new MatchDAO();
        ArrayList<Match> expResult = null;
        ArrayList<Match> result = instance.getMatchRound(round);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
