package nl.hu.cisq1.lingo.trainer.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round implements Serializable {
    public static final int MAX_ATTEMPTS = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "round_id")
    private Long id;

    @Column
    private String wordToGuess;

    @OneToMany(cascade = CascadeType.ALL)
    private final List<Feedback> feedbackList = new ArrayList<>();

    @Column
    private String hint;

    public Round(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        this.hint = wordToGuess.charAt(0) + Strings.repeat(".", wordToGuess.length() - 1);
    }

    public Round() {
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void guess(String attempt) {
        List<Mark> marks = new ArrayList<>();

        if (isInvalidAttempt(attempt)) {
            for (int i = 0; i < wordToGuess.length(); i++) {
                marks.add(Mark.INVALID);
            }

            feedbackList.add(new Feedback(attempt, marks));
            return;
        }

        for (int i = 0; i < attempt.length(); i++) {
            String presentLetters = String.valueOf(attempt.charAt(i));
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

        Feedback feedback = new Feedback(attempt, marks);
        feedbackList.add(feedback);

        hint = feedback.giveHint(hint, wordToGuess);
    }

    public String getHint() {
        return hint;
    }

    public boolean isWordGuessed() {
        if (feedbackList.isEmpty()) {
            return false;
        }

        Feedback lastFeedback = feedbackList.get(feedbackList.size() - 1);

        return lastFeedback.isWordGuessed();
    }

    public int countAttempts() {
        return feedbackList.size();
    }

    public boolean wasLost() {
        return this.countAttempts() >= MAX_ATTEMPTS && !this.isWordGuessed();
    }

    private boolean isInvalidAttempt(String attempt) {
        return attempt.length() == 0 || attempt.length() != wordToGuess.length();
    }
}
