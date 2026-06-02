package controller;

import static controller.DAO.con;
import java.util.ArrayList;
import model.Match;
import model.Round;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Player;
import model.Result;

public class MatchDAO extends DAO {

    public MatchDAO() {
    }

    public ArrayList<Match> getMatchRound(Round round) {
        ArrayList<Match> matches = round.getMatches();

        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblResult WHERE tblMatchID = ? ORDER BY ID");
            for (int i = 0; i < matches.size(); i++) {
                ArrayList<Player> players = new ArrayList<Player>();
                ps.setInt(1, matches.get(i).getID());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblPlayer WHERE ID = ?");
                    ps1.setInt(1, rs.getInt("tblPlayerID"));
                    ResultSet rs1 = ps1.executeQuery();
                    if (rs1.next()) {
                        Player player = new Player();
                        player.setID(rs1.getInt("ID"));
                        player.setName(rs1.getString("name"));
                        players.add(player);
                    }
                }
                if (players.size() >= 2) {
                    matches.get(i).setName(
                            players.get(0).getName()
                            + " - "
                            + players.get(1).getName()
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return matches;
    }
}
