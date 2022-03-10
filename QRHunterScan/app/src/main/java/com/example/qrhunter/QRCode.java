package com.example.qrhunter;

public class QRCode {
    private int score;
    private String  QRId;
    private Boolean shared;
    private String comment;
    private String location;


    QRCode(int score, String QRId, Boolean shared, String comment) {
        this.score = score;
        this.QRId = QRId;
        this.shared = shared;
        this.comment = comment;
    }

    public QRCode(int score, String QRId, Boolean shared, String comment, String location) {
        this.score = score;
        this.QRId = QRId;
        this.shared = shared;
        this.comment = comment;
        this.location = location;
    }

    public QRCode(String QRId, int score) {
        this.score = score;
        this.QRId = QRId;
    }

    public int getScore() {
        return score;
    }

    public String getQRId() {
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
