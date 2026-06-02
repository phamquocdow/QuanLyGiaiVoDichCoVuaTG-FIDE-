/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Match;
import model.Result;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.ResultDAO;

import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class ResultDAOTest {
    
    public ResultDAOTest() {
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
     * Test of getResultMatch method, of class ResultDAO.
     */
    @Test
    public void testGetResultMatch() {
        System.out.println("getResultMatch");
        Match match = null;
        ResultDAO instance = new ResultDAO();
        ArrayList<Result> expResult = null;
        ArrayList<Result> result = instance.getResultMatch(match);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateResult method, of class ResultDAO.
     */
    @Test
    public void testUpdateResult() {
        System.out.println("updateResult");
        Result result_2 = null;
        float score = 0.0F;
        float elo = 0.0F;
        ResultDAO instance = new ResultDAO();
        boolean expResult = false;
        boolean result = instance.updateResult(result_2, score, elo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
