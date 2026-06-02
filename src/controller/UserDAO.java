package controller;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends DAO {
    public UserDAO() {
        super();
    }

    public boolean checkLogin(User user) {
        boolean result = false;
        String sql = "SELECT * FROM tblUser WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setFullname(rs.getString("fullname"));
                user.setBirthDate(rs.getDate("birthDate"));
                user.setRole(rs.getString("role"));
                result = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
