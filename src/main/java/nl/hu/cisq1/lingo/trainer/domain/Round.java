package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "round_id")
    private Long id;

    @Column
    private int roundNumber;

    @Column
    private String wordToGuess;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Feedback> feedBackList = new ArrayList<>();

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.status = Status.PLAYING;

    }
    public Round() {

    }

    public Status getStatus() {
        return status;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<Feedback> getFeedbackList() {
        return feedBackList;
    }

    public Feedback getLastFeedback() {
        if (feedBackList.isEmpty()) {
            return new Feedback();
        } else {
            return feedBackList.get(feedBackList.size() - 1);
        }
    }

    public Feedback guess(String attempt) {
        List<Mark> marks = new ArrayList<>();
        Feedback feedback;

        if (attempt.length() == 0 || attempt.length() != wordToGuess.length()) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                marks.add(Mark.INVALID);
            }
            return new Feedback(attempt, marks);
        }

        for (int i = 0; i < attempt.length(); i++) {
            String presentLetters = attempt.charAt(i) + "";
            if (attempt.charAt(i) == wordToGuess.charAt(i)) {
                marks.add(Mark.CORRECT);
                continue;
            }

            if (wordToGuess.contains(presentLetters)) {
                marks.add(Mark.PRESENT);
                continue;
            }
            marks.add(Mark.ABSENT);
        }
        feedback = new Feedback(attempt, marks);
        feedBackList.add(feedback);

        if (getLastFeedback().isWordGuessed()) {
            status = Status.WON;
        } else {
            status = Status.LOST;
        }

        return feedback;
    }

}
