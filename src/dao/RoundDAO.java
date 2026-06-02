
package dao;

import java.util.ArrayList;
import model.Round;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Match;


public class RoundDAO extends DAO{
    public RoundDAO(){
    }
    
    public ArrayList<Round> getRoundList(){
        ArrayList<Round> rounds = new ArrayList<Round>();
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRound");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Round round = new Round();
                round.setID(rs.getInt("ID"));
                round.setRoundNum(rs.getInt("roundNum"));
                ArrayList<Match> matches = new ArrayList<Match>();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblMatch WHERE tblRoundID = ?");
                ps1.setInt(1, rs.getInt("ID"));
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()){
                    Match match = new Match();
                    match.setID(rs1.getInt("ID"));
                    match.setMatchNum(rs1.getInt("matchNum"));
                    match.setName(rs1.getString("name"));
                    matches.add(match);
                }
                round.setMatches(matches);
                rounds.add(round);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return rounds;
    }

    public Round getRoundByNumber(int roundNum) {
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblRound WHERE roundNum = ?");
            ps.setInt(1, roundNum);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Round round = new Round();
                round.setID(rs.getInt("ID"));
                round.setRoundNum(rs.getInt("roundNum"));
                ArrayList<Match> matches = new ArrayList<Match>();
                PreparedStatement ps1 = con.prepareStatement("SELECT * FROM tblMatch WHERE tblRoundID = ?");
                ps1.setInt(1, round.getID());
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()){
                    Match match = new Match();
                    match.setID(rs1.getInt("ID"));
                    match.setMatchNum(rs1.getInt("matchNum"));
                    match.setName(rs1.getString("name"));
                    matches.add(match);
                }
                round.setMatches(matches);
                return round;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
