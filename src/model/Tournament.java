/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author MSI PC
 */
public class Tournament {
    private int ID;
    private String name;
    private Date year;
    private int organizationTimes;
    private String address;
    private String description;
    private ArrayList<Round> rounds = new ArrayList<Round>();

    public Tournament() {
    }

    public Tournament(int ID, String name, Date year, int organizationTimes, String address, String description) {
        this.ID = ID;
        this.name = name;
        this.year = year;
        this.organizationTimes = organizationTimes;
        this.address = address;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public int getOrganizationTimes() {
        return organizationTimes;
    }

    public void setOrganizationTimes(int organizationTimes) {
        this.organizationTimes = organizationTimes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }
    
    
}
