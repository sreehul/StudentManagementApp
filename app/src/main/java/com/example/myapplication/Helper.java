package com.example.myapplication;

public class Helper {
    String UserName, UserRoll, Workshop;

    public Helper() {
    }

    public Helper(String Name, String RollNumber) {
        UserName = Name;
        UserRoll = RollNumber;
    }


    public Helper(String userName, String userRoll, String workshop) {
        UserName = userName;
        UserRoll = userRoll;
        Workshop = workshop;
    }

    public String getUserName() {
        return UserName;
    }

    public String getUserRoll() {
        return UserRoll;
    }

    public String getWorkshop() {
        return Workshop;
    }
}
