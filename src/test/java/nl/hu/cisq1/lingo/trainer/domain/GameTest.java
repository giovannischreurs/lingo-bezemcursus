package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = new Game();
    Round round = new Round("PAARD");

    @Test
    @DisplayName("Check if a new round is started and added to the rounds")
    void startNewRound() {
        this.game.startNewRound(this.round.getWordToGuess());
        this.game.setStatus(Status.FINISHED);

        this.game.startNewRound("LEVEN");
        this.game.setStatus(Status.FINISHED);

        this.game.startNewRound("STAAL");
        this.game.setStatus(Status.FINISHED);

        assertEquals(3, this.game.getRounds().size());
    }

    @Test
    @DisplayName("Check if a new round is started while the status is still on PLAYING")
    void startNewRoundWithActiveRound() {
        this.game.startNewRound(this.round.getWordToGuess());

        assertThrows(IllegalStateException.class, () -> this.game.startNewRound("LEVEN"));
    }

    @Test
    @DisplayName("Check if the last round is being selected")
    void getLastRoundFromList() {
        this.game.getRounds().add(this.round);
        assertEquals(this.game.getLastRoundFromList(), this.round);
    }

    @Test
    @DisplayName("Check if new guesses are added to the list of guesses")
    void guess() {
        this.game.startNewRound(this.round.getWordToGuess());
        assertEquals(0, this.game.getGuesses().size());

        this.game.guess("LUCHT");
        assertEquals(1, this.game.getGuesses().size());

        this.game.guess("STAAR");
        assertEquals(2, this.game.getGuesses().size());
    }
}