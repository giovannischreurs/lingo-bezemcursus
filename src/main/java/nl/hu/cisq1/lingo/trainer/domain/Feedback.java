package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Feedback {
    private String attempt;
    private List<Mark> markList;

    public Feedback(String attempt, List<Mark> markList) {
        this.attempt = attempt;
        this.markList = markList;
    }

    public String giveHint(String formerHint) {
        String hint = "";

        if (!formerhint.isBlank()) {
            hint = formerHint;
        }

        else {
            for (int wordsize = 0 ; wordsize<attempt.length() ; wordsize++) {
                hint+="-";
            }
        }

        char[] hintCharList = hint.toCharArray();
        int index = 0;
        for (Mark mark: markList) {

            if(mark==Mark.CORRECT) {
                hintCharList[index] = attempt.charAt(index);
                hint = String.valueOf(hintCharList);
            }
            index+=1;

        }
        return hint;
    }

    public boolean isWordGuessed() {
        return markList.stream().allMatch(result -> result == Mark.CORRECT);
    }

}
