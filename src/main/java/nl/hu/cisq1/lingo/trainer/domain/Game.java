package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameActionNotAllowed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "game_id")
    private long id;

    @Column
    private int score;

    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.WAITING;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    public Game() {
    }

    public void guess(String attempt) {
        if (this.status != Status.PLAYING) {
            throw new GameActionNotAllowed("The game is not active");
        }

        Round round = rounds.get(rounds.size() - 1);
        round.guess(attempt);

        if (round.isWordGuessed()) {
            updateScore(round.countAttempts());
            status = Status.WAITING;
        } else if (round.wasLost()) {
            status = Status.FINISHED;
        }
    }

    public void startNewRound(String wordToGuess) {
        if (this.status != Status.WAITING) {
            throw new GameActionNotAllowed("The game is still active or has ended");
        }

        Round round = new Round(wordToGuess);
        rounds.add(round);
        status = Status.PLAYING;
    }

    private void updateScore(int numberOfAttempts) {
        score += 5 * (5 - numberOfAttempts) + 5;
    }

    public GameData showData() {
        Round currentRound = rounds.get(rounds.size() - 1);

        return new GameData(
                id,
                status,
                score,
                currentRound.getFeedbackList(),
                currentRound.getHint()
        );
    }
}
