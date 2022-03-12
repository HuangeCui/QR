package com.example.qrhunter;

import android.app.Application;

public class SharedData extends Application {
    private String username;
    private String qrcode;
    private String imagepath;

    public void setUsername(String s) {
        this.username = s;
    }

    public String getUsername() {
        return this.username;
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
}
