/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Round;
import model.Tournament;
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

        ArrayList<Round> result = instance.getRoundList((new TournamentDAO()).getTournamentLatest());

        assertNotEquals(0, result.size());
    }

    @Test
    public void testGetRoundList_NoData() {
        System.out.println("getRoundList");

        RoundDAO instance = new RoundDAO();

        Tournament tournament = new Tournament();
        tournament.setID(0);

        ArrayList<Round> result = instance.getRoundList(tournament);

        assertEquals(0, result.size());
    }
}
