package com.example.qrhunter;

import android.app.Application;

public class SharedData extends Application {
    //    private User user;
    private String username = "";
    private String qrcode;
    private String imagepath;
    private int codescore;

    public void setUsername(String user) {
        this.username = user;
    }

    public String getUser() {
        return username;
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
