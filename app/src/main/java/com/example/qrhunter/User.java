package com.example.qrhunter;

import android.os.Parcelable;

import java.util.ArrayList;

public class User  {
    private String userID;
    private String userName;
    private String userPasscode;
    private int highest;
    private int sum;
    private int unique;
    private int total;
    private ArrayList<QRCode> codes = new ArrayList<>();

    User(String userID, String userName, String userPasscode) {
        this.userID = userID;
        this.userName = userName;
        this.userPasscode = userPasscode;
    }

    public User(String userName, String userID, ArrayList<QRCode> codes) {
        this.userID = userID;
        this.userName = userName;
        this.codes = codes;
    }

    public String getUserID() {
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

    public void addCode(QRCode code){
        codes.add(code);
    }


    public int getSum(){
        sum=0;
        for(int i=0;i<codes.size();i++){
            sum+=codes.get(i).getScore();
        }
        return sum;
    }

    public int getTotal(){
        total = codes.size();
        return total;
    }

    public int getHighest(){
        highest=0;
        for(int i =0; i< codes.size();i++){
            if(highest<=codes.get(i).getScore()){
                highest=codes.get(i).getScore();
            }
        }
        return highest;
    }

    public int getUnique(){
        unique =0;
        for(int i =0; i<codes.size();i++){
            if(codes.get(i).getQRId()== userID){
                unique=codes.get(i).getScore();
                break;
            }
        }
        return unique;
    }




}
