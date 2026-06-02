package model;

import java.util.Date;
import java.util.ArrayList;
// import model.Round;

public class Tournament {
    private int id;
    private String name;
    private Date year;
    private int organizationTimes;
    private String address;
    private String description;
    private ArrayList<Integer> rounds;

    public Tournament(int id, String name, Date year, int organizationTimes, String address, String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.organizationTimes = organizationTimes;
        this.address = address;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getYear() {
        return year;
    }

    public int getOrganizationTimes() {
        return organizationTimes;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Integer> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<Integer> rounds) {
        this.rounds = rounds;
    }

}
