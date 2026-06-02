
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import model.Match;
import model.Player;
import model.Result;
import model.Round;
import model.Standing;


public class ResultDAO extends DAO {

    public ResultDAO() {
    }

    public ArrayList<Result> getResultMatch(Match match) {
        ArrayList<Result> results = new ArrayList<Result>();
        try {

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblResult WHERE tblMatchID = ? ORDER BY ID");
            ps.setInt(1, match.getID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Result result = new Result();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblPlayer WHERE ID = ?");
                ps1.setInt(1, rs.getInt("tblPlayerID"));
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    Player player = new Player(rs1.getInt("ID"), rs1.getString("name"), rs1.getInt("bornYear"), rs1.getString("nation"), rs1.getFloat("eloRating"), rs1.getString("note"));
                    result.setPlayer(player);
                }
                result.setID(rs.getInt("ID"));
                result.setMatch(match);
                result.setEloChange(rs.getFloat("eloChange"));
                result.setScore(rs.getFloat("score"));
                results.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public boolean updateResult(Result result, float score, float elo) {
        try {
            result.setScore(score);
            result.setEloChange(elo-result.getPlayer().getEloRating());
            PreparedStatement ps = con.prepareStatement("UPDATE tblResult SET score = ?, eloChange = ? WHERE ID = ?");
            ps.setFloat(1, result.getScore());
            ps.setFloat(2, result.getEloChange());
            ps.setInt(3, result.getID());
            ps.executeUpdate();
            
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean insertResult(Result result) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tblResult(tblMatchID, tblPlayerID, score, eloChange) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, result.getMatch().getID());
            ps.setInt(2, result.getPlayer().getID());
            ps.setFloat(3, result.getScore());
            ps.setFloat(4, result.getEloChange());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                result.setID(rs.getInt(1));
                return true;
            }
            return fallbackInsertResult(result);
        } catch (SQLException ex) {
            String message = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (message.contains("cannot insert the value null into column 'id'") || message.contains("cannot insert the value null into column \"id\"")) {
                return fallbackInsertResult(result);
            }
            ex.printStackTrace();
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean fallbackInsertResult(Result result) {
        try {
            int nextId = getNextResultId();
            result.setID(nextId);
            PreparedStatement ps = con.prepareStatement("INSERT INTO tblResult(ID, tblMatchID, tblPlayerID, score, eloChange) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, result.getID());
            ps.setInt(2, result.getMatch().getID());
            ps.setInt(3, result.getPlayer().getID());
            ps.setFloat(4, result.getScore());
            ps.setFloat(5, result.getEloChange());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private int getNextResultId() {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ISNULL(MAX(ID), 0) + 1 AS nextId FROM tblResult");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

public ArrayList<Standing> getRoundStandings(Round round) {
        ArrayList<Standing> standings = new ArrayList<>();
        Map<Integer, Standing> map = new HashMap<>();
        try {
            if (round == null) {
                return standings;
            }

            
            ArrayList<Player> allPlayers = new PlayerDAO().getAllPlayers();
            for (Player p : allPlayers) {
                Standing s = new Standing();
                s.setPlayer(p);
                s.setCurrentElo(p.getEloRating());
                s.setTotalScore(0f);
                s.setTotalOpponentScore(0f);
                map.put(p.getID(), s);
            }

            
            
            String sql = "SELECT m.ID FROM tblMatch m JOIN tblRound r ON m.tblRoundID = r.ID WHERE r.roundNum <= ?";
            PreparedStatement psMatch = con.prepareStatement(sql);
            psMatch.setInt(1, round.getRoundNum());
            ResultSet rsMatch = psMatch.executeQuery();
            
            while (rsMatch.next()) {
                Match match = new Match();
                match.setID(rsMatch.getInt("ID"));
                
                
                ArrayList<Result> matchResults = getResultMatch(match);
                
                if (matchResults.size() == 2) {
                    Result first = matchResults.get(0);
                    Result second = matchResults.get(1);
                    
                    addStanding(map, first, second.getScore());
                    addStanding(map, second, first.getScore());
                } else if (matchResults.size() == 1) { 
                    
                    Result first = matchResults.get(0);
                    addStanding(map, first, 0f); 
                }
            }
            
            standings.addAll(map.values());
            
            
            Collections.sort(standings, Comparator.comparing(Standing::getTotalScore).reversed()
                    .thenComparing(Standing::getTotalOpponentScore).reversed()
                    .thenComparing(Standing::getCurrentElo).reversed());
            
            for (int i = 0; i < standings.size(); i++) {
                standings.get(i).setRank(i + 1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return standings;
    }

    private void addStanding(Map<Integer, Standing> map, Result result, float opponentScore) {
        int playerId = result.getPlayer().getID();
        Standing standing = map.get(playerId);
        if (standing == null) {
            standing = new Standing();
            standing.setPlayer(result.getPlayer());
            standing.setCurrentElo(result.getPlayer().getEloRating());
            standing.setTotalScore(0f);
            standing.setTotalOpponentScore(0f);
            map.put(playerId, standing);
        }
        standing.setTotalScore(standing.getTotalScore() + result.getScore());
        standing.setTotalOpponentScore(standing.getTotalOpponentScore() + opponentScore);
    }

    public java.util.HashSet<String> getPlayedPairs() {
        java.util.HashSet<String> playedPairs = new java.util.HashSet<>();
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT a.tblPlayerID AS p1, b.tblPlayerID AS p2 " +
                "FROM tblResult a " +
                "JOIN tblResult b ON a.tblMatchID = b.tblMatchID " +
                "WHERE a.tblPlayerID < b.tblPlayerID"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int p1 = rs.getInt("p1");
                int p2 = rs.getInt("p2");
                playedPairs.add(p1 + "-" + p2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return playedPairs;
    }

    public java.util.HashSet<Integer> getPlayersWithBye() {
        java.util.HashSet<Integer> playersWithBye = new java.util.HashSet<>();
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT tblPlayerID FROM tblResult WHERE tblMatchID IN " +
                "(SELECT tblMatchID FROM tblResult GROUP BY tblMatchID HAVING COUNT(ID) = 1)"
            );
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                playersWithBye.add(rs.getInt("tblPlayerID"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return playersWithBye;
    }
}
