package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Match;
import model.Player;
import model.Result;

public class ResultDAO extends DAO {

    public ResultDAO() {
        super();
    }

    public ArrayList<Result> getResultMatch(Match match) {
        ArrayList<Result> results = new ArrayList<Result>();
        try {
            PlayerDAO playerDAO = new PlayerDAO();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblResult WHERE tblMatchID = ? ORDER BY ID");
            ps.setInt(1, match.getID());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player player = playerDAO.searchPlayer(rs.getInt("tblPlayerID"));
                Result result = new Result(
                        rs.getInt("ID"),
                        rs.getFloat("score"),
                        rs.getFloat("eloChange"),
                        player,
                        match);
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
