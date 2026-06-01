/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Match;
import model.Round;
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
public class MatchDAOTest {

    public MatchDAOTest() {
    }

    /**
     * Test of getMatchRound method, of class MatchDAO.
     */
    @Test
    public void testGetMatchRound_HaveData() {
        RoundDAO roundDAO = new RoundDAO();
        MatchDAO matchDAO = new MatchDAO();

        Round round = roundDAO.getRoundList().get(0);

        ArrayList<Match> result = matchDAO.getMatchRound(round);

        assertNotNull(result);
    }

    @Test
    public void testGetMatchRound_NoData() {
        MatchDAO matchDAO = new MatchDAO();

        Round round = new Round();
        round.setMatches(new ArrayList<>());

        ArrayList<Match> result = matchDAO.getMatchRound(round);

        assertEquals(0, result.size());
    }
}
