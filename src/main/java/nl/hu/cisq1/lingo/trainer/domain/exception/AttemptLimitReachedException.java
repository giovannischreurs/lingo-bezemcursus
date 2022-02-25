package nl.hu.cisq1.lingo.trainer.domain.exception;

public class AttemptLimitReachedException extends RuntimeException {
    public AttemptLimitReachedException(Integer attempts) {
        super("The attempt limit has been reached. Current count: " + attempts);
    }
}