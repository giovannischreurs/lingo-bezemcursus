package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class GameData {
    private long id;
    private Status status;
    private int score;
    private List<Feedback> feedback;
    private String hint;

    public GameData(long id, Status status, int score, List<Feedback> feedback, String hint) {
        this.id = id;
        this.status = status;
        this.score = score;
        this.feedback = feedback;
        this.hint = hint;
    }

    public long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public int getScore() {
        return score;
    }

    public List<Feedback> getFeedback() {
        return feedback;
    }

    public String getHint() {
        return hint;
    }
}
