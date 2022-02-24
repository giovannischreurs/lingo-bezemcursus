package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feedback_id")
    private Long id;

    @Column
    private String attempt;

    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Mark.class)
    private List<Mark> marks;

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public Feedback() {
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

        List<String> hint = new ArrayList<>();

        for (int i = 0; i < splitWordToGuess.length; i++) {
            if (marks.get(i) == Mark.CORRECT) {
                hint.add(splitWordToGuess[i]);
            } else {
                hint.add(splitPreviousHint[i]);
            }
        }

        return String.join("", hint);
    }

    public String getAttempt() {
        return attempt;
    }

    public List<Mark> getMarks() {
        return marks;
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
