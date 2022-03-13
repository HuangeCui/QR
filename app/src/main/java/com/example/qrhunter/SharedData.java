package com.example.qrhunter;

import android.app.Application;

public class SharedData extends Application {
    private String username = "";
    private String searchname = "";
    private String qrcode;
    private String imagepath;
    private int codescore;

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
}
