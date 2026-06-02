package controller;

import model.Player;

public class PlayerDAO extends DAO {
    public PlayerDAO() {
        super();
    }

    public Player searchPlayer(int id) {
        Player player = null;
        String sql = "SELECT * FROM tblPlayer WHERE ID = ?";

        try {
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
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
