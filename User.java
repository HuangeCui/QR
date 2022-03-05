package com.example.simpleparadox.listycity;

import java.util.ArrayList;

public class User {
    private int userID;
    private String userName;
    private String userPasscode;
    private ArrayList<QRCode> codes;

    public User(int userID, String userName, String userPasscode) {
        this.userID = userID;
        this.userName = userName;
        this.userPasscode = userPasscode;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPasscode() {
        return userPasscode;
    }

    public ArrayList<QRCode> getCodes() {
        return codes;
    }

}
