package nl.hu.cisq1.lingo.trainer.application.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;

import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
public class ProgressDTO {

    @Getter
    private final Long id;
    @Getter
    private final String gameStatus;
    @Getter
    private final Integer score;
    @Getter
    private final List<Feedback> feedbackHistory;
    @Getter
    private final String currentHint;

    private ProgressDTO(Builder builder) {
        this.id = builder.id;
        this.gameStatus = builder.gameStatus;
        this.score = builder.score;
        this.feedbackHistory = builder.feedbackHistory;
        this.currentHint = builder.currentHint;
    }

    public static class Builder {
        private final Long id;
        private String gameStatus;
        private Integer score;
        private List<Feedback> feedbackHistory;
        private String currentHint;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder gameStatus(String gameStatus) {
            this.gameStatus = gameStatus;
            return this;
        }

        public Builder score(Integer score) {
            this.score = score;
            return this;
        }

        public Builder feedbackHistory(List<Feedback> feedbackHistory) {
            this.feedbackHistory = feedbackHistory;
            return this;
        }

        public Builder currentHint(String currentHint) {
            this.currentHint = currentHint;
            return this;
        }

        public ProgressDTO build() {
            return new ProgressDTO(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProgressDTO that = (ProgressDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(gameStatus, that.gameStatus) && Objects.equals(score, that.score) && Objects.equals(feedbackHistory, that.feedbackHistory) && Objects.equals(currentHint, that.currentHint);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gameStatus, score, feedbackHistory, currentHint);
    }
}
