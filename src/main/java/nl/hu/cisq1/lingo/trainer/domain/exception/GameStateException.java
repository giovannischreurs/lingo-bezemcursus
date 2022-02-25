package nl.hu.cisq1.lingo.trainer.domain.exception;

import nl.hu.cisq1.lingo.trainer.domain.GameStatus;

public class GameStateException extends RuntimeException {
    public GameStateException(GameStatus state ) {
        super("This action is not allowed in the current gamestate: " +  state.getStatus() + ".");
    }
}
