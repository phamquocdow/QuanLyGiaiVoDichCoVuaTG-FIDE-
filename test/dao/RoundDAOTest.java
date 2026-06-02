/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package dao;

import java.util.ArrayList;
import model.Round;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.RoundDAO;

import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class RoundDAOTest {
    
    public RoundDAOTest() {
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
     * Test of getRoundList method, of class RoundDAO.
     */
    @Test
    public void testGetRoundList() {
        System.out.println("getRoundList");
        RoundDAO instance = new RoundDAO();
        ArrayList<Round> expResult = null;
        ArrayList<Round> result = instance.getRoundList();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

