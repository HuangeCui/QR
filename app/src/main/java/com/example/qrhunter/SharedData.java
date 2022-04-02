package com.example.qrhunter;

import android.app.Application;
//get data between activities
public class SharedData extends Application {
    private String username = "";
    private String searchname = "";
    private String qrcode;
    private String qrcodekey;
    private String imagepath;
    private int codescore;
    private String codedisplay;
    private String playerName;
    private Boolean comefromme;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSearchname(String searchname) {
        this.searchname = searchname;
    }

    public String getSearchname() {
        return searchname;
    }

    public void setQrcode(String s) {
        this.qrcode = s;
    }

    public String getQrcode() {
        return this.qrcode;
    }

    public void setImagepath(String s) {
        this.imagepath = s;
    }

    public String getImagepath() {
        return this.imagepath;
    }

    public void setCodescore(int codescore) {
        this.codescore = codescore;
    }

    public int getCodescore() {
        return codescore;
    }

    public void setCodedisplay(String codedisplay) {
        this.codedisplay = codedisplay;
    }

    public String getCodedisplay() {
        return codedisplay;
    }

    public void setQrcodekey(String qrcodekey) {
        this.qrcodekey = qrcodekey;
    }

    public String getQrcodekey() {
        return qrcodekey;
    }

    public void setComefromme(Boolean comefromme) {
        this.comefromme = comefromme;
    }

    public Boolean getComefromme() {
        return comefromme;
    }
}
