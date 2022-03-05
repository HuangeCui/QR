package com.example.simpleparadox.listycity;

public class QRCode {
    private int score;
    private int QRId;
    private Boolean shared;
    private String comment;
    private String location;


    public QRCode(int score, int QRId, Boolean shared, String comment) {
        this.score = score;
        this.QRId = QRId;
        this.shared = shared;
        this.comment = comment;
    }

    public QRCode(int score, int QRId, Boolean shared, String comment, String location) {
        this.score = score;
        this.QRId = QRId;
        this.shared = shared;
        this.comment = comment;
        this.location = location;
    }

    public int getScore() {
        return score;
    }

    public int getQRId() {
        return QRId;
    }

    public Boolean getShared() {
        return shared;
    }

    public String getComment() {
        return comment;
    }

    public String getLocation() {
        return location;
    }
}
