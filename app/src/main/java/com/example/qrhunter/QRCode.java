package com.example.qrhunter;

import java.io.File;
import java.util.ArrayList;

public class QRCode {
    private int score;
    private String  QRId;
    private Boolean shared;
    private String comment;
    private String location;
    ArrayList<String> scanners;
    File image;

    public QRCode() {}

    public QRCode(String QRId, int score) {
        this.score = score;
        this.QRId = QRId;
        this.shared = false;
        this.comment = "";
        this.location = "";
        this.scanners = new ArrayList<String>();
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setQRId(String QRId) {
        this.QRId = QRId;
    }

    public String getQRId() {
        return QRId;
    }

    public void setShared(Boolean shared) {
        this.shared = shared;
    }

    public Boolean getShared() {
        return shared;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public void setScanners(ArrayList<String> scanners) {
        this.scanners = scanners;
    }

    public ArrayList<String> getScanners() {
        return scanners;
    }

    public void addScanner(String scanner) {
        this.scanners.add(scanner);
    }
}
