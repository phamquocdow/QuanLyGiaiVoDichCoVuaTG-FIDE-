
package model;

import java.time.LocalDate;
import java.util.Date;


public class User {
    private int ID;
    private String username;
    private String password;
    private String fullName;
    private Date birthDate;
    private String role;
    
    public User(){
        
    }

    public User(int ID, String username, String password, String fullName, Date birthDate, String role) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.role = role;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    
}
