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
import static org.junit.Assert.*;

/**
 *
 * @author MSI PC
 */
public class ResultDAOTest {

    public ResultDAOTest() {
    }

    @Test
    public void testGetResultMatch_HaveData() {
        Match match = new Match(1, 0);

        ResultDAO dao = new ResultDAO();

        ArrayList<Result> results = dao.getResultMatch(match);

        assertNotNull(results);
        assertFalse(results.isEmpty());

        Result firstResult = results.get(0);

        assertTrue(firstResult.getID() > 0);
        assertNotNull(firstResult.getPlayer());
    }

    @Test
    public void testGetResultMatch_NoData() {
        Match match = new Match(9999, 0);

        ResultDAO dao = new ResultDAO();

        ArrayList<Result> results = dao.getResultMatch(match);

        assertNotNull(results);
        assertEquals(0, results.size());
    }

    @Test
    public void testUpdateResult_ValidData() {
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0);

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 0.5f, 2400f);

        assertTrue(updated);
    }

    @Test
    public void testUpdateResult_InvalidScore() {
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0);

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 2.0f, 2400f);

        assertFalse(updated);
    }

    @Test
    public void testUpdateResult_InvalidElo() {
        ResultDAO dao = new ResultDAO();

        Match match = new Match(1, 0);

        Result result = dao.getResultMatch(match).get(0);

        boolean updated = dao.updateResult(result, 0.5f, -100f);

        assertFalse(updated);
    }
}
