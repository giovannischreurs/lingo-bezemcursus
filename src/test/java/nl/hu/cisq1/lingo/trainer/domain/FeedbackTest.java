package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    @Test
    @DisplayName("Word is guessed if all letters are correct")
    public void wordIsGuessed() {
        List<Mark> marks = List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT);
        String attempt = "STEEN";
        Feedback feedback = new Feedback(attempt, marks);

        assertTrue(feedback.isWordGuessed());
    }

    @Test
    @DisplayName("Word is not guessed when all marks are not correct")
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

}