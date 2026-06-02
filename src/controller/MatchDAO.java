package controller;

import java.util.ArrayList;
import model.Match;
import model.Round;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Player;
import model.Result;

public class MatchDAO extends DAO {

    public MatchDAO() {
        super();
    }

    public ArrayList<Match> getMatchRound(Round round) {
        ArrayList<Match> matches = round.getMatches();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblResult WHERE tblMatchID = ? ORDER BY ID");
            PlayerDAO playerDAO = new PlayerDAO();

            for (int i = 0; i < matches.size(); i++) {
                ArrayList<Player> players = new ArrayList<Player>();
                ps.setInt(1, matches.get(i).getID());
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Player player = playerDAO.searchPlayer(rs.getInt("tblPlayerID"));
                    players.add(player);

                }
                if (players.size() >= 2) {
                    matches.get(i).setName(
                            players.get(0).getName()
                                    + " - "
                                    + players.get(1).getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
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
