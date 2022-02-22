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

    public Feedback(String attempt, List<Mark> marks) {
        this.attempt = attempt;
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        return marks.stream().allMatch(mark -> mark == Mark.CORRECT);
    }

}
