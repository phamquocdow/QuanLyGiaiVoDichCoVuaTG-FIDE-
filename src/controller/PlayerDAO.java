package controller;

import controller.DAO;
import model.Player;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.ResultSet;

public class PlayerDAO extends DAO {
    public PlayerDAO() {
        super();
    }

    public boolean addPlayer(Player player) {
        String sql = "INSERT INTO tblPlayer (fideID, name, bornYear, nation, eloRating, note) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, player.getFideID());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getBornYear());
            ps.setString(4, player.getNation());
            ps.setFloat(5, player.getEloRating());
            ps.setString(6, player.getNote());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean editPlayerInformation(Player player) {
        String sql = "UPDATE tblPlayer SET fideID=?, name=?, bornYear=?, nation=?, eloRating=?, note=? WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, player.getFideID());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getBornYear());
            ps.setString(4, player.getNation());
            ps.setFloat(5, player.getEloRating());
            ps.setString(6, player.getNote());
            ps.setInt(7, player.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletePlayer(Player player) {
        String sql = "DELETE FROM tblPlayer WHERE id=?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, player.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
     public java.util.ArrayList<Player> searchPlayer(String name) {
        java.util.ArrayList<Player> listPlayer = new java.util.ArrayList<>();
        String sql = "SELECT * FROM tblPlayer WHERE name LIKE ?";
        
        try {
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name + "%");
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player player = new Player();
                player.setId(rs.getInt("id")); 
                player.setFideID(rs.getString("fideID"));
                player.setName(rs.getString("name"));
                player.setBornYear(rs.getInt("bornYear"));
                player.setNation(rs.getString("nation"));
                player.setEloRating(rs.getFloat("eloRating"));
                player.setNote(rs.getString("note"));
                listPlayer.add(player);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listPlayer; 
    }
}
