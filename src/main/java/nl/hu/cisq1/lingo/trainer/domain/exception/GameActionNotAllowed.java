package nl.hu.cisq1.lingo.trainer.domain.exception;

public class GameActionNotAllowed extends IllegalStateException {
    public GameActionNotAllowed(String message) {
        super(message);
    }
}
