package model;

import java.util.Date;

public class User {
    private int id;
    private String username;
    private String password;
    private String fullname;
    private Date birthDate;
    private String role;

    public User() {
    }

    public User(int id, String username, String password, String fullname, Date birthDate, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.birthDate = birthDate;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
