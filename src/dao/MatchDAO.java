
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Match;
import model.Result;
import model.Round;


public class MatchDAO extends DAO{
    public MatchDAO(){
    }
    
    public ArrayList<Match> getMatchRound(Round round){
        return round.getMatches();
    }

    public int insertMatch(Match match, Round round) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tblMatch(matchNum, name, tblRoundID) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, match.getMatchNum());
            ps.setString(2, match.getName());
            ps.setInt(3, round.getID());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                match.setID(generatedId);
                return generatedId;
            }

            
            
            return fallbackCreateMatch(match, round);
        } catch (SQLException ex) {
            String message = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (message.contains("cannot insert the value null into column 'id'") || message.contains("cannot insert the value null into column \"id\"")) {
                return fallbackCreateMatch(match, round);
            }
            ex.printStackTrace();
            return -1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int fallbackCreateMatch(Match match, Round round) {
        try {
            int nextId = getNextMatchId();
            match.setID(nextId);
            PreparedStatement ps = con.prepareStatement("INSERT INTO tblMatch(ID, matchNum, name, tblRoundID) VALUES (?, ?, ?, ?)");
            ps.setInt(1, match.getID());
            ps.setInt(2, match.getMatchNum());
            ps.setString(3, match.getName());
            ps.setInt(4, round.getID());
            ps.executeUpdate();
            return match.getID();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private int getNextMatchId() {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ISNULL(MAX(ID), 0) + 1 AS nextId FROM tblMatch");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    public boolean insertResult(Result result) {
        try {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tblResult(tblMatchID, tblPlayerID, score, eloChange) VALUES (?, ?, ?, ?)");
            ps.setInt(1, result.getMatch().getID());
            ps.setInt(2, result.getPlayer().getID());
            ps.setFloat(3, result.getScore());
            ps.setFloat(4, result.getEloChange());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
