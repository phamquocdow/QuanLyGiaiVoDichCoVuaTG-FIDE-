package service;

import controller.PlayerDAO;
import controller.ResultDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import model.Player;
import model.Round;
import model.Standing;

public class StandingService {

    private final ResultDAO resultDAO = new ResultDAO();
    private final PlayerDAO playerDAO = new PlayerDAO();

    public ArrayList<Standing> loadRoundStandings(Round round) {
        
        if (round == null) {
            ArrayList<Standing> initialStandings = new ArrayList<>();
            ArrayList<Player> allPlayers = playerDAO.getAllPlayers();
            
            if (allPlayers != null) {
                
                for (Player p : allPlayers) {
                    Standing s = new Standing();
                    s.setPlayer(p);
                    s.setTotalScore(0f);
                    s.setTotalOpponentScore(0f);
                    s.setCurrentElo(p.getEloRating());
                    initialStandings.add(s);
                }
                
                
                Collections.sort(initialStandings, Comparator.comparing(Standing::getCurrentElo).reversed());
                for (int i = 0; i < initialStandings.size(); i++) {
                    initialStandings.get(i).setRank(i + 1);
                }
            }
            return initialStandings;
        }
        
        
        return resultDAO.getRoundStandings(round);
    }
}
