package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private Long id;

    @Column
    private int score;

    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.FINISHED; // Set the standard state in END_GAME

    @OneToMany(cascade = CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    @Transient
    private List<Feedback> guesses = new ArrayList<>();

    public Game() {

    }

    public Long getId() {
        return id;
    }

    public Status getStatus() {
        return status;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public List<Feedback> getGuesses() {
        return guesses;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void score(int numberOfAttempts) {
        score += +5 * (5 - numberOfAttempts) + 5;
    }

    public void startNewRound(String wordToGuess) {
        if (status != Status.PLAYING) {
            Round round = new Round(wordToGuess);
            rounds.add(round);
            status = Status.PLAYING;
        } else {
            throw new IllegalStateException("The game is still active");
        }
    }

    public Round getLastRoundFromList() {
        if (rounds.isEmpty()) {
            return new Round();
        } else {
            return rounds.get(rounds.size() - 1);
        }
    }

    public void guess(String attempt) {
        Round round = getLastRoundFromList();
        round.guess(attempt);

        Feedback feedback = round.guess(attempt);
        this.guesses.add(feedback);

        if (getLastRoundFromList().getLastFeedback().isWordGuessed()) {
            score(getLastRoundFromList().getFeedbackList().size());
            status = Status.FINISHED;
        }

    }

}
