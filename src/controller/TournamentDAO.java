/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Tournament;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author MSI PC
 */
public class TournamentDAO extends DAO {

    public TournamentDAO() {
        super();
    }

    public Tournament getTournamentLatest() {
        String sql = "SELECT * FROM tblTournament ORDER BY year DESC";

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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
