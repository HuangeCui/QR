package com.example.qrhunter;

import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User  {
    private String userID;
    private String userName;
    private String userPasscode;
    private int highest;
    private int sum;
    private int unique;
    private int total;
    String comment;
    String email;
    List<CodeScore> code;
    private ArrayList<QRCode> codes = new ArrayList<>();
    private String userEmail;
    ArrayList<String> scanned;



    public User(String name, String password) {
        this.userName = name;
        this.userPasscode = password;
        this.sum = 0;
        this.total = 0;
        this.email = "";
        this.comment = "";
        this.code = new ArrayList<CodeScore>();
       // this.codes = new ArrayList<>();
        //this.comment = "";
       // this.scanned = new ArrayList<String>();
    }
    public User(String userName, String userID, ArrayList<QRCode> codes) {
        this.userID = userID;
        this.userName = userName;
        this.codes = codes;
    }

    public User(String userID, String userName, String userPasscode) {
        this.userID = userID;
        this.userName = userName;
        this.userPasscode = userPasscode;
        this.codes = new ArrayList<>();


    }

    public User(String userName,int amount) {
       // this.userID = userID;
        this.userName = userName;
        this.codes = new ArrayList<>();
        this.total=amount;
        this.highest = amount;
        this.unique=amount;
        this.sum = amount;

    }

    public User() {
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
        //sum=0;
        //for(int i=0;i<codes.size();i++){
       //    sum+=codes.get(i).getScore();
       // }
        return sum;
    }

    public int getTotal(){
        //total = codes.size();
        return total;
    }

    public int getHighest(){
       // highest=0;
       // for(int i =0; i< codes.size();i++){
        //    if(highest<=codes.get(i).getScore()){
         //       highest=codes.get(i).getScore();
          //  }
      //  }
        return highest;
    }

    public int getUnique(){
        //unique =0;
      //  for(int i =0; i<codes.size();i++){
        //    if(codes.get(i).getQRId()== userID){
          //      unique=codes.get(i).getScore();
         //       break;
         //   }
        //123412341234}
        return unique;
    }
    public void removeCode(String code, int score) {
//        Pair<String, Integer> pair = new Pair<>(code, score);
        CodeScore codeScore = new CodeScore(code, score);
        this.codes.remove(codeScore);
    }

    public void initCodeList() {
        this.code = new ArrayList<CodeScore>();
    }







}
