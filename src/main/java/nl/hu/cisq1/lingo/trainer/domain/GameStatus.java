package nl.hu.cisq1.lingo.trainer.domain;

public enum GameStatus {
    WAITING("WAITING"),
    PLAYING("PLAYING"),
    FINISHED("FINISHED");

    private final String status;

    GameStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
