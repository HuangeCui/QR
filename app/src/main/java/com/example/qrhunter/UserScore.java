package com.example.qrhunter;

public class UserScore {
    String user;
    int score;

    public UserScore() {}

    public UserScore(String user, int score) {
        this.user = user;
        this.score =score;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
