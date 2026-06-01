package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Match;
import model.Player;
import model.Result;

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
                    Player player = new Player(rs1.getInt("ID"), rs1.getString("name"), rs1.getInt("birthYear"), rs1.getString("nation"), rs1.getFloat("eloRating"), rs1.getString("note"));
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
        if (elo < 0) {
            System.err.println("Elo Không thể nhỏ hơn 0");
            return false;
        }
        try {
            result.setScore(score);
            result.setEloChange(elo - result.getPlayer().getEloRating());
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
}
