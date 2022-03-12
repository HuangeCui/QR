package com.example.qrhunter;

import android.app.Application;

public class SharedData extends Application {
    private User user;
    private String qrcode;
    private String imagepath;

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
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
