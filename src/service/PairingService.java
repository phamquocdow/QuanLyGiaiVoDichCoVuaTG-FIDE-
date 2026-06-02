package service;

import controller.DAO;
import controller.MatchDAO;
import controller.ResultDAO;
import java.util.ArrayList;
import java.util.HashSet;
import model.Match;
import model.Player;
import model.Result;
import model.Round;
import model.Standing;

public class PairingService {

    public static class PairingRow {
        private final int matchNum;
        private final Player player1;
        private final Player player2; 
        private Match match;

        public PairingRow(int matchNum, Player player1, Player player2) {
            this.matchNum = matchNum;
            this.player1 = player1;
            this.player2 = player2;
        }

        public int getMatchNum() { return matchNum; }
        public Player getPlayer1() { return player1; }
        public Player getPlayer2() { return player2; }
        public Match getMatch() { return match; }
        public void setMatch(Match match) { this.match = match; }
    }

    public ArrayList<PairingRow> createPairings(ArrayList<Standing> standings) {
        ArrayList<PairingRow> pairingRows = new ArrayList<>();
        if (standings == null || standings.isEmpty()) return pairingRows;

        ResultDAO resultDAO = new ResultDAO();
        HashSet<String> playedPairs = resultDAO.getPlayedPairs();
        HashSet<Integer> playersWithBye = resultDAO.getPlayersWithBye();

        
        Player byePlayer = null;
        if (standings.size() % 2 != 0) {
            
            for (int i = standings.size() - 1; i >= 0; i--) {
                Player p = standings.get(i).getPlayer();
                if (!playersWithBye.contains(p.getID())) {
                    byePlayer = p;
                    standings.remove(i); 
                    break;
                }
            }
            
            if (byePlayer == null) {
                byePlayer = standings.get(standings.size() - 1).getPlayer();
                standings.remove(standings.size() - 1);
            }
        }

        
        boolean[] isPaired = new boolean[standings.size()];
        ArrayList<PairingRow> tempPairings = new ArrayList<>();
        
        boolean success = backtrackPairing(standings, isPaired, playedPairs, tempPairings);

        if (!success) {
            System.out.println("Cảnh báo: Bế tắc toàn cục! Không thể tìm ra cách xếp cặp hoàn hảo không trùng lặp.");
            
            
            tempPairings.clear();
            for (int i = 0; i < standings.size(); i += 2) {
                Player p1 = standings.get(i).getPlayer();
                Player p2 = (i + 1 < standings.size()) ? standings.get(i + 1).getPlayer() : null;
                if (p2 != null) {
                    tempPairings.add(new PairingRow(0, p1, p2));
                }
            }
        }

        
        int matchNum = 1;
        for (PairingRow row : tempPairings) {
            pairingRows.add(new PairingRow(matchNum++, row.getPlayer1(), row.getPlayer2()));
        }
        
        
        if (byePlayer != null) {
            pairingRows.add(new PairingRow(matchNum++, byePlayer, null));
        }

        return pairingRows;
    }

    
    private boolean backtrackPairing(ArrayList<Standing> standings, boolean[] isPaired, HashSet<String> playedPairs, ArrayList<PairingRow> tempPairings) {
        int firstUnpaired = -1;
        
        for (int i = 0; i < standings.size(); i++) {
            if (!isPaired[i]) {
                firstUnpaired = i;
                break;
            }
        }

        
        if (firstUnpaired == -1) return true;

        Player p1 = standings.get(firstUnpaired).getPlayer();
        isPaired[firstUnpaired] = true;

        
        for (int j = firstUnpaired + 1; j < standings.size(); j++) {
            if (!isPaired[j]) {
                Player p2 = standings.get(j).getPlayer();
                
                int minId = Math.min(p1.getID(), p2.getID());
                int maxId = Math.max(p1.getID(), p2.getID());

                
                if (!playedPairs.contains(minId + "-" + maxId)) {
                    isPaired[j] = true;
                    tempPairings.add(new PairingRow(0, p1, p2)); 

                    
                    if (backtrackPairing(standings, isPaired, playedPairs, tempPairings)) {
                        return true; 
                    }

                    
                    tempPairings.remove(tempPairings.size() - 1);
                    isPaired[j] = false;
                }
            }
        }

        
        isPaired[firstUnpaired] = false;
        return false;
    }

    
    public boolean savePairings(ArrayList<PairingRow> pairingRows, Round round) {
        if (pairingRows == null || pairingRows.isEmpty() || round == null) {
            return false;
        }

        MatchDAO matchDAO = new MatchDAO();
        ResultDAO resultDAO = new ResultDAO();

        try {
            DAO.con.setAutoCommit(false);

            for (PairingRow pairing : pairingRows) {
                Match match = new Match();
                match.setMatchNum(pairing.getMatchNum());

                if (pairing.getPlayer2() != null) {
                    match.setName(pairing.getPlayer1().getName() + " vs " + pairing.getPlayer2().getName());
                    int matchId = matchDAO.insertMatch(match, round);
                    if (matchId <= 0) {
                        DAO.con.rollback();
                        return false;
                    }
                    pairing.setMatch(match);
                    match.setID(matchId); 

                    Result result1 = new Result(match, pairing.getPlayer1(), 0f, 0f);
                    Result result2 = new Result(match, pairing.getPlayer2(), 0f, 0f);

                    if (!resultDAO.insertResult(result1) || !resultDAO.insertResult(result2)) {
                        DAO.con.rollback();
                        return false;
                    }
                } else {
                    match.setName(pairing.getPlayer1().getName() + " (BYE)");
                    int matchId = matchDAO.insertMatch(match, round);
                    if (matchId <= 0) {
                        DAO.con.rollback();
                        return false;
                    }
                    pairing.setMatch(match);
                    match.setID(matchId); 

                    Result result1 = new Result(match, pairing.getPlayer1(), 1f, 0f);
                    if (!resultDAO.insertResult(result1)) {
                        DAO.con.rollback();
                        return false;
                    }
                }
            }

            DAO.con.commit();
            return true;
        } catch (Exception e) {
            try {
                DAO.con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                DAO.con.setAutoCommit(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
 