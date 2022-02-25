package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.GameStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game game;

    @BeforeEach
    @DisplayName("initiates new game before each test")
    void beforeEachTest() {
        game = new Game();
    }

    @Test
    @DisplayName("cannot start new round when there already is an open round")
    void cannotStartRoundWhenCurrentRoundIsOpen() {
        game.startNewRound("PAARD");

        assertThrows(GameStateException.class, () ->
                game.startNewRound("PAARD")
        );
    }

    @Test
    @DisplayName("cannot start new round when the game is eliminated")
    void cannotStartRoundWhenGameEliminated() {
        game.startNewRound("PAARD");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");

        assertThrows(GameStateException.class, () ->
                game.startNewRound("BLOED")
        );
    }

    @Test
    @DisplayName("changes game status by starting a new round")
    void newRoundChangesGameStatus() {
        game.startNewRound("PAARD");

        assertEquals(GameStatus.PLAYING,game.getGameStatus());
    }

    @Test
    @DisplayName("returns the latest round")
    void returnsLatestRound() {
        game.startNewRound("PAARD");

        Round round = new Round("PAARD");
        assertEquals(round,game.getLatestRound());
    }

    @Test
    @DisplayName("returns the latest round when multiple rounds are played")
    void multipleRoundsReturnsLatest() {
        game.startNewRound("PAARD");
        game.guess("PAARD");
        game.startNewRound("PAARS");

        Round round = new Round("PAARS");
        assertEquals(round,game.getLatestRound());
    }

    @Test
    @DisplayName("throws exception if there is no last round")
    void noActiveRoundsException() {
        assertThrows(GameStateException.class, () ->
                game.getLatestRound()
        );
    }

    @Test
    @DisplayName("throws exception when trying to guess without a open round")
    void cannotGuessWhenNoRound() {
        assertThrows(GameStateException.class, () ->
                game.guess("PAREN")
        );
    }

    @Test
    @DisplayName("throws exception when trying to guess when game is eliminated")
    void cannotGuessWhenGameEliminated() {
        game.startNewRound("PAARD");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");

        assertThrows(GameStateException.class, () ->
                game.guess("PAREN")
        );
    }

    @Test
    @DisplayName("is eliminated when word is not guessed within attempt limit")
    void playerEliminatedWordNotGuessed() {
        game.startNewRound("PAARD");

        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");

        assertEquals(GameStatus.FINISHED, game.getGameStatus());
    }

    @Test
    @DisplayName("is not eliminated when word is guessed within attempt limit")
    void playerNotEliminatedWhenWordIsGuessed() {
        game.startNewRound("PAARD");

        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAARD");

        assertNotSame(GameStatus.FINISHED, game.getGameStatus());
    }

    @Test
    @DisplayName("Eliminated if guessed after the limit was reached")
    void playerEliminatedWordGuessedAfterLimitReached() {
        game.startNewRound("PAARD");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");
        game.guess("PAREN");

        assertThrows(GameStateException.class, () ->
                game.guess("PAARD")
        );
    }

    @Test
    @DisplayName("Score added after succesfully guessing word")
    void addScoreWhenWordIsGuessed() {
        game.startNewRound("PAARD");
        game.guess("PAARD");

        assertEquals(25,game.getScore());
    }

    @ParameterizedTest
    @DisplayName("correctly adds score when winning multiple rounds")
    @MethodSource("provideGameExamples")
    void nextScoreCorrectlyCounted(Game game, Integer expectedScore) {
        assertEquals(game.getScore(), expectedScore);
    }

    @Test
    @DisplayName("When word is guessed game status is changed")
    void correctGuessChangesStatus() {
        game.startNewRound("PAARD");
        game.guess("PAARD");

        assertEquals(GameStatus.WAITING, game.getGameStatus());
    }

    @Test
    @DisplayName("Playing when word is not guessed")
    void gameInProgress() {
        game.startNewRound("PAARD");
        game.guess("BAREN");

        assertTrue(game.isPlaying());
    }

    @Test
    @DisplayName("Not playing when word is guessed")
    void gameNotInProgress() {
        game.startNewRound("PAARD");
        game.guess("PAARD");

        assertFalse(game.isPlaying());
    }

    @ParameterizedTest
    @DisplayName("next word length is reset after 7 letter word")
    @MethodSource("provideWordExamples")
    void wordResetAfterSevenLetters(String wordToGuess, Integer expectedWordLength) {
        game.startNewRound(wordToGuess);

        assertEquals(expectedWordLength, game.provideNextWordLength());
    }

    @Test
    @DisplayName("Start with 5 letter word")
    void startingFiveLetters() {
        assertEquals(5,game.provideNextWordLength());
    }

    @Test
    @DisplayName("next word length is based on previous round")
    void increasingWordLength() {
        game.startNewRound("PAARD");
        game.guess("PAARD");

        assertEquals(6, game.provideNextWordLength());
    }

    static Stream<Arguments> provideWordExamples() {
        String fiveLetterWord = "PAARD";
        String sixLetterWord = "DAALDE";
        String sevenLetterWord = "AARDBEI";

        return Stream.of(
                Arguments.of(fiveLetterWord,  6),
                Arguments.of(sixLetterWord,  7),
                Arguments.of(sevenLetterWord,  5)
        );
    }

    static Stream<Arguments> provideGameExamples() {
        Game testGame1 = new Game();
        testGame1.startNewRound("PAARD");
        testGame1.guess("PAARD");

        Game testGame2 = new Game();
        testGame2.startNewRound("PAARD");
        testGame2.guess("PAARD");
        testGame2.startNewRound("DAAGDE");
        testGame2.guess("PAARD");
        testGame2.guess("DAAGDE");

        Game testGame3 = new Game();
        testGame3.startNewRound("PAARD");
        testGame3.guess("PAARD");
        testGame3.startNewRound("DAAGDE");
        testGame3.guess("DAAGDE");
        testGame3.startNewRound("AARDBEI");
        testGame3.guess("AARDBOL");
        testGame3.guess("AARDBOL");
        testGame3.guess("AARDBEI");

        return Stream.of(
                Arguments.of(testGame1,  25),
                Arguments.of(testGame2,  45),
                Arguments.of(testGame3,  65)
        );
    }

}