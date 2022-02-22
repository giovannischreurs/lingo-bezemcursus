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

}