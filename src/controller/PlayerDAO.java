
package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Player;


public class PlayerDAO extends DAO {

    public PlayerDAO() {
    }

    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblPlayer ORDER BY name");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Player player = new Player(
                    rs.getInt("ID"),
                    rs.getString("name"),
                    rs.getInt("bornYear"),
                    rs.getString("nation"),
                    rs.getFloat("eloRating"),
                    rs.getString("note")
                );
                players.add(player);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return players;
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

    public boolean insertPlayer(Player player) {
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO tblPlayer(name, bornYear, nation, eloRating, note) VALUES (?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            ps.setString(1, player.getName());
            ps.setInt(2, player.getBornYear());
            ps.setString(3, player.getNation());
            ps.setFloat(4, player.getEloRating());
            ps.setString(5, player.getNote());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                player.setID(rs.getInt(1));
                return true;
            }
            return fallbackInsertPlayer(player);
        } catch (SQLException ex) {
            String message = ex.getMessage() == null ? "" : ex.getMessage().toLowerCase();
            if (message.contains("cannot insert the value null into column 'id'") || message.contains("cannot insert the value null into column \"id\"")) {
                return fallbackInsertPlayer(player);
            }
            ex.printStackTrace();
            return false;
        }
    }

    private boolean fallbackInsertPlayer(Player player) {
        try {
            int nextId = getNextPlayerId();
            player.setID(nextId);
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO tblPlayer(ID, name, bornYear, nation, eloRating, note) VALUES (?, ?, ?, ?, ?, ?)"
            );
            ps.setInt(1, player.getID());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getBornYear());
            ps.setString(4, player.getNation());
            ps.setFloat(5, player.getEloRating());
            ps.setString(6, player.getNote());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private int getNextPlayerId() {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT ISNULL(MAX(ID), 0) + 1 AS nextId FROM tblPlayer");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("nextId");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }
}


