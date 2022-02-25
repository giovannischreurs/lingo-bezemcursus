package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.application.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

/**
 * This is a unit test.
 *
 * It tests the behaviors of our system under test,
 * GameService, in complete isolation:
 * - its methods are called by the test framework instead of a controller
 * - the GameService calls a test double instead of an actual repository
 */
@DisplayName("GameService")
class TrainerServiceTest {

    private GameRepository gameRepository;
    private WordRepository wordRepository;
    private TrainerService service;
    private Game game;

    @BeforeEach
    @DisplayName("initiates mocks and service for tests")
    void beforeEach() {
        gameRepository = mock(GameRepository.class);
        wordRepository = mock(WordRepository.class);
        this.game = new Game();

        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(game));
        when(wordRepository.findRandomWordByLength(anyInt()))
                .thenReturn(Optional.of(new Word("BLOED")));

        service = new TrainerService(gameRepository,wordRepository);
    }

    @Test
    @DisplayName("Starting a game returns progress of the game")
    void startGameReturnsNewGame() {
        game.startNewRound("BLOED");
        ProgressDTO result = service.startGame();
        ProgressDTO expected = convertGameToProgressDTO(game);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Get progress throws exception if game not found")
    void getProgressReturnsExceptionIfGameNotFound() {
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.getProgress(0L));
    }

    @Test
    @DisplayName("Get progress returns game as progress DTO")
    void getProgressReturnsProgressDTO() {
        game.startNewRound("BLOED");
        ProgressDTO expected = convertGameToProgressDTO(game);

        ProgressDTO result = service.getProgress(anyLong());

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("New round throws exception if game does not exists")
    void newRoundThrowsErrorByNonExistingGame() {
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.startNewRound(0L));
    }

    @Test
    @DisplayName("New round returns the new progress with the created round")
    void newRoundReturnsGameProgress() {
        game.startNewRound("BLOED");
        game.guess("BLOED");

        ProgressDTO result = service.startNewRound(anyLong());

        ProgressDTO expected = convertGameToProgressDTO(game);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Guess throws exception if game does not exists")
    void guessThrowsExceptionByNonExistingGame() {
        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(GameNotFoundException.class, () -> service.guess(0L,"TAKJE"));
    }

    @Test
    @DisplayName("Guess returns the new progress with the processed guess")
    void guessIsReturnedInGameProgress() {
        game.startNewRound("PAARD");
        ProgressDTO result = service.guess(anyLong(),"PAARS");

        game.guess("PAARS");
        ProgressDTO expected = convertGameToProgressDTO(game);

        assertEquals(expected, result);
    }

    @Test
    @DisplayName("throw exception by no games found")
    void allGamesEmptyException() {
        List<Game> gameList = new ArrayList<>();
        when(gameRepository.findAll())
                .thenReturn(gameList);

        assertThrows(ChangeSetPersister.NotFoundException.class, () -> service.getAllGames());
    }

    @Test
    @DisplayName("return a list of games")
    void allGamesReturnsListOfGames() throws ChangeSetPersister.NotFoundException {
        game.startNewRound("GROEI");

        List<Game> gameList = List.of(game);

        when(gameRepository.findAll())
                .thenReturn(gameList);

        assertEquals(1, service.getAllGames().size());
    }

    private static ProgressDTO convertGameToProgressDTO(Game game) {
        return new ProgressDTO.Builder(game.getId())
                .gameStatus(game.getGameStatus().getStatus())
                .score(game.getScore())
                .currentHint(game.getLatestRound().giveHint())
                .feedbackHistory(game.getLatestRound().getFeedbackHistory())
                .build();
    }
}