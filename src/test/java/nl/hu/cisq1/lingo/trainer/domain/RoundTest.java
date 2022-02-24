package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static nl.hu.cisq1.lingo.trainer.domain.Mark.*;
import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    @Test
    @DisplayName("If all letters are right, the word is guessed")
    void guess() {
        Round round = new Round("PAARD");

        assertEquals(List.of(CORRECT, CORRECT, ABSENT, ABSENT, ABSENT), round.guess("PASEN").getMarks());
        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, ABSENT), round.guess("PAARS").getMarks());
        assertEquals(List.of(CORRECT, CORRECT, CORRECT, CORRECT, CORRECT), round.guess("PAARD").getMarks());
    }

    @Test
    @DisplayName("Check if feedback is the same feedback object")
    void checkFeedback() {
        Round round = new Round("PAARD");


        assertEquals(round.guess("VEREN"), round.getLastFeedback());
    }

    @Test
    @DisplayName("Test if feedback is created")
    void getFeedbackList() {
        Round round = new Round("PAARD");
        round.guess("VEREN");

        assertEquals(round.getFeedbackList().size(), 1);
    }

}