package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    static Stream<Arguments> provideHintExamples() {
        return Stream.of(
                Arguments.of("PAARD", "PASEN", "P....", List.of(CORRECT, CORRECT, ABSENT, ABSENT, ABSENT), "PA..."),
                Arguments.of("PAARD", "PEREN", "PA...", List.of(CORRECT, ABSENT, PRESENT, ABSENT, ABSENT), "PA..."),
                Arguments.of("PAARD", "PAARS", "PA...", List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), "PAAR."),
                Arguments.of("PAARD", "PAARD", "PAAR.", List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), "PAARD")
        );
    }

    @Test
    @DisplayName("Word is guessed if all letters are correct")
    public void wordIsGuessed() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        String attempt = "STEEN";
        Feedback feedback = new Feedback(attempt, marks);

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed when any marks are not correct")
    public void wordIsNotGuessed() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, INVALID);
        String attempt = "STEEK";
        Feedback feedback = new Feedback(attempt, marks);

        assertFalse(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Attempt is invalid when any mark is invalid")
    public void guessIsInvalid() {
        List<Mark> marks = List.of(INVALID, INVALID, INVALID, INVALID, INVALID, INVALID, INVALID);
        String attempt = "STEEGJE";
        Feedback feedback = new Feedback(attempt, marks);

        assertFalse(feedback.isAttemptValid());
    }

    @Test
    @DisplayName("Attempt is valid when none of the marks are invalid")
    public void guessIsNotInvalid() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT);
        String attempt = "STOEP";
        Feedback feedback = new Feedback(attempt, marks);

        assertTrue(feedback.isAttemptValid());
    }

    @ParameterizedTest
    @MethodSource("provideHintExamples")
    @DisplayName("Test method for hints")
    void giveHintTest(String wordToGuess, String attempt, String previousHint, List<Mark> marks, String nextHint) {
        Feedback feedback = new Feedback(attempt, marks);

        assertEquals(nextHint, feedback.giveHint(previousHint), wordToGuess);
    }
}