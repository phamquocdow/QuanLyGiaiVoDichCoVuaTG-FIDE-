package controller;

import java.util.ArrayList;
import model.Match;
import model.Round;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Player;

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
}
