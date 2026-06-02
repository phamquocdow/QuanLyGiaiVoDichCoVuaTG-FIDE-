package model;

import java.util.ArrayList;
import java.util.Date;

public class Tournament {
    private int id;
    private String name;
    private Date year;
    private int organizationTimes;
    private String address;
    private String description;
    private ArrayList<Round> rounds = new ArrayList<Round>();

    public Tournament(int id, String name, Date year, int organizationTimes, String address, String description) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.organizationTimes = organizationTimes;
        this.address = address;
        this.description = description;
    }

    public int getID() {
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

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public void setRounds(ArrayList<Round> rounds) {
        this.rounds = rounds;
    }

}
