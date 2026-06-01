package controller;

import model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO extends DAO{
    public UserDAO(){
    }
    
    public boolean checkLogin(User user){
        try {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM tblUser WHERE username = ? AND password = ?");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                user.setFullName(rs.getString("fullName"));
                user.setID(rs.getInt("ID"));
                user.setBirthDate(rs.getDate("birthDate"));
                user.setRole(rs.getString("role"));
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
}
