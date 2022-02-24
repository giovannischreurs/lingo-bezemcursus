package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Feedback {
    private String attempt;

    @ElementCollection
    private List<Mark> marks;

    private List<String> hints = new ArrayList<>();

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == Mark.CORRECT);
    }

    public boolean isAttemptValid() {
        return marks.stream().noneMatch(mark -> mark == Mark.INVALID);
    }

    public String giveHint(String previousHint, String wordToGuess) {
        String[] splitWordToGuess = wordToGuess.split("");
        String[] splitPreviousHint  = previousHint.split("");

        for (int i = 0; i < splitWordToGuess.length; i++) {
            if (marks.get(i) == Mark.CORRECT) {
                hints.add(splitWordToGuess[i]);
            }
            else {
                hints.add(splitPreviousHint[i]);
            }
        }
        return String.join("", hints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }
}
