package controller;

import model.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDAO extends DAO {

    public PlayerDAO() {
        super();
    }

    public boolean updateElo(Player player, float elo) {
        player.setEloRating(elo);
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE tblPlayer SET eloRating = ? WHERE ID = ?");
            ps.setFloat(1, player.getEloRating());
            ps.setInt(2, player.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Player searchPlayer(int ID) {
        Player player = null;
        String sql = "SELECT * FROM tblPlayer WHERE ID = ?";

        try {
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ID);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                player = new Player(
                        rs.getInt("ID"),
                        rs.getString("fideID"),
                        rs.getString("name"),
                        rs.getInt("bornYear"),
                        rs.getString("nation"),
                        rs.getFloat("eloRating"),
                        rs.getString("note"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return player;
    }
}
