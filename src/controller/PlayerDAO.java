package controller;

import model.Player;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDAO extends DAO {

    public PlayerDAO() {
    }

    public boolean updateElo(Player player, float elo) {
        player.setEloRating(elo);
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE tblPlayer SET eloRating = ? WHERE ID = ?");
            ps.setFloat(1, player.getEloRating());
            ps.setInt(2, player.getID());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
