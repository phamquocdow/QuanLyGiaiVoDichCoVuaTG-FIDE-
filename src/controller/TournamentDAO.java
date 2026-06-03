package controller;

import model.Tournament;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TournamentDAO extends DAO {
    private RoundDAO roundDAO = new RoundDAO();
    private Map<Integer, Integer> roundIds = new HashMap<>();

    public TournamentDAO() {
        super();
        loadRoundIds();
    }

    public ArrayList<Tournament> getAllFinishedTournaments() {
        ArrayList<Tournament> listTournament = new ArrayList<>();
        String sql = "SELECT * FROM tblTournament WHERE ID IN " +
                "(SELECT tblTournamentID FROM tblRound GROUP BY tblTournamentID HAVING COUNT(*) >= 11)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Tournament tournament = new Tournament(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDate("year"),
                        rs.getInt("organizationTimes"),
                        rs.getString("address"),
                        rs.getString("description"));
                listTournament.add(tournament);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listTournament;
    }

    public Tournament getTournamentLatest() {
        String sql = "SELECT TOP 1 * FROM tblTournament ORDER BY year DESC, ID DESC";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Tournament tournament = new Tournament(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getDate("year"),
                        rs.getInt("organizationTimes"),
                        rs.getString("address"),
                        rs.getString("description"));

                rs.close();
                ps.close();
                return tournament;
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLatestTournamentID() {
        try {
            PreparedStatement ps = con
                    .prepareStatement("SELECT TOP 1 ID FROM tblTournament ORDER BY year DESC, ID DESC");
            ResultSet rs = ps.executeQuery();
            if (!rs.next())
                return -1;
            int latestTournamentID = rs.getInt("ID");
            rs.close();
            ps.close();
            return latestTournamentID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void loadRoundIds() {
        int latestTournamentID = getLatestTournamentID();
        if (latestTournamentID < 0)
            return;
        List<model.Round> rounds = roundDAO.getRoundList(latestTournamentID);
        for (model.Round r : rounds) {
            roundIds.put(r.getRoundNum(), r.getID());
        }
    }

    public int countTotalMatches(int roundId) {
        int count = 0;
        try (PreparedStatement ps = con.prepareStatement(
                "SELECT COUNT(*) FROM tblMatch WHERE tblRoundID = ?")) {
            ps.setInt(1, roundId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public int countMatchesWithoutResult(int roundId) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM tblMatch m " +
                "WHERE m.tblRoundID = ? " +
                "AND NOT EXISTS (" +
                "    SELECT 1 FROM tblResult r WHERE r.tblMatchID = m.ID" +
                ")";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, roundId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                count = rs.getInt(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<RoundInfo> getRounds() {
        List<RoundInfo> rounds = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            if (!roundIds.containsKey(i)) {
                rounds.add(new RoundInfo(i, "Chưa diễn ra"));
                continue;
            }
            int id = roundIds.get(i);
            int total = countTotalMatches(id);
            int missing = countMatchesWithoutResult(id);

            String status;
            if (missing == 0 && total > 0) {
                status = "Đã diễn ra";
            } else {
                status = "Đang diễn ra";
            }
            rounds.add(new RoundInfo(i, status));
        }
        return rounds;
    }

    public List<PlayerRecord> getRankingForRound(int roundNum) {
        int latestTournamentID = getLatestTournamentID();
        Map<Integer, PlayerRecord> recordMap = new HashMap<>();

        String sqlPoints = "SELECT p.ID, p.name, p.bornYear, p.nation, p.eloRating, " +
                "       COALESCE(SUM(res.score), 0)     AS totalPoints, " +
                "       COALESCE(SUM(res.eloChange), 0) AS totalEloChange " +
                "FROM tblPlayer p " +
                "JOIN tblParticipationForm pf ON pf.tblPlayerID = p.ID " +
                "     AND pf.tblTournamentID = ? " +
                "LEFT JOIN tblResult res ON res.tblPlayerID = p.ID " +
                "LEFT JOIN tblMatch m    ON m.ID = res.tblMatchID " +
                "LEFT JOIN tblRound rnd  ON rnd.ID = m.tblRoundID " +
                "     AND rnd.tblTournamentID = ? " +
                "     AND rnd.roundNum <= ? " +
                "GROUP BY p.ID, p.name, p.bornYear, p.nation, p.eloRating";

        try (PreparedStatement ps = con.prepareStatement(sqlPoints)) {
            ps.setInt(1, latestTournamentID);
            ps.setInt(2, latestTournamentID);
            ps.setInt(3, roundNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double baseElo = rs.getDouble("eloRating");
                double eloChange = rs.getDouble("totalEloChange");
                int currentElo = (int) Math.round(baseElo + eloChange);

                PlayerRecord pr = new PlayerRecord(
                        rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getInt("bornYear"),
                        rs.getString("nation"),
                        rs.getDouble("totalPoints"),
                        0.0,
                        currentElo);
                recordMap.put(pr.id, pr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String sqlMatchPlayers = "SELECT res.tblMatchID, res.tblPlayerID " +
                "FROM tblResult res " +
                "JOIN tblMatch m   ON m.ID = res.tblMatchID " +
                "JOIN tblRound rnd ON rnd.ID = m.tblRoundID " +
                "WHERE rnd.tblTournamentID = ? " +
                "  AND rnd.roundNum <= ?";

        Map<Integer, List<Integer>> matchPlayers = new HashMap<>();
        try (PreparedStatement ps = con.prepareStatement(sqlMatchPlayers)) {
            ps.setInt(1, latestTournamentID);
            ps.setInt(2, roundNum);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int matchId = rs.getInt("tblMatchID");
                int playerId = rs.getInt("tblPlayerID");
                matchPlayers.computeIfAbsent(matchId, k -> new ArrayList<>()).add(playerId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (List<Integer> players : matchPlayers.values()) {
            for (int pid : players) {
                if (!recordMap.containsKey(pid))
                    continue;
                for (int oppId : players) {
                    if (oppId == pid || !recordMap.containsKey(oppId))
                        continue;
                    recordMap.get(pid).oppPoints += recordMap.get(oppId).points;
                }
            }
        }

        List<PlayerRecord> list = new ArrayList<>(recordMap.values());
        list.sort(Comparator
                .comparingDouble((PlayerRecord p) -> p.points).reversed()
                .thenComparingDouble((PlayerRecord p) -> p.oppPoints).reversed()
                .thenComparingInt((PlayerRecord p) -> p.elo).reversed());

        int rank = 1;
        for (PlayerRecord p : list) {
            p.rank = rank++;
        }

        return list;
    }

    public static class RoundInfo {
        public int round;
        public String status;

        public RoundInfo(int round, String status) {
            this.round = round;
            this.status = status;
        }
    }

    public static class PlayerRecord {
        public int rank;
        public int id;
        public String name;
        public int year;
        public String nation;
        public double points;
        public double oppPoints;
        public int elo;

        public PlayerRecord(int id, String name, int year, String nation,
                double points, double oppPoints, int elo) {
            this.id = id;
            this.name = name;
            this.year = year;
            this.nation = nation;
            this.points = points;
            this.oppPoints = oppPoints;
            this.elo = elo;
        }
    }
}
