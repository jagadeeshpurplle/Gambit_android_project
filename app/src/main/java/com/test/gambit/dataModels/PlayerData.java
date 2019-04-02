package com.test.gambit.dataModels;

import com.google.gson.annotations.SerializedName;

public class PlayerData {

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("position")
    private String position;

    @SerializedName("team")
    private Team team;

    @SerializedName("home_team")
    private HomeTeam homeTeam;

    @SerializedName("visitor_team")
    private VisitorTeam visitorTeam;

    public String getFirstName() {
        return firstName == null ? "n/a" : firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName == null ? "n/a" : lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return (position==null || position.isEmpty())? "n/a":position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public HomeTeam getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(HomeTeam homeTeam) {
        this.homeTeam = homeTeam;
    }

    public VisitorTeam getVisitorTeam() {
        return visitorTeam;
    }

    public void setVisitorTeam(VisitorTeam visitorTeam) {
        this.visitorTeam = visitorTeam;
    }

    public class Team {

        @SerializedName("conference")
        String conference;
        @SerializedName("division")
        String division;
        @SerializedName("full_name")
        String fullName;
        @SerializedName("name")
        String name;
        @SerializedName("id")
        String id;

        public String getConference() {
            return conference ==null? "n/a":conference;
        }

        public void setConference(String conference) {
            this.conference = conference;
        }


        public String getDivision() {
            return division == null ? "n/a" : division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getFullName() {
            return fullName == null ? "n/a" : fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getName() {
            return name == null ? "n/a": name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id == null ? "n/a" : id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class HomeTeam{
        @SerializedName("abbreviation")
        String abbreviation;
        @SerializedName("full_name")
        String fullName;

        public String getAbbreviation() {
            return abbreviation == null ? "" : abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getFullName() {
            return fullName == null ? "" : fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

    public class VisitorTeam{
        @SerializedName("abbreviation")
        String abbreviation;
        @SerializedName("full_name")
        String fullName;

        public String getAbbreviation() {
            return abbreviation == null ? "" : abbreviation;
        }

        public void setAbbreviation(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getFullName() {
            return fullName == null ? "" : fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }

}
