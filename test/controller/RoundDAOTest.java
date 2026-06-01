/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Round;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class RoundDAOTest {

    public RoundDAOTest() {
    }

    /**
     * Test of getRoundList method, of class RoundDAO.
     */
    @Test
    public void testGetRoundList() {
        System.out.println("getRoundList");

        RoundDAO instance = new RoundDAO();

        ArrayList<Round> result = instance.getRoundList();

        assertNotNull(result);
    }

}
