package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.application.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.data.WordRepository;
import nl.hu.cisq1.lingo.words.domain.exception.WordLengthNotSupportedException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TrainerService {
    private final GameRepository gameRepository;
    private final WordService wordService;

    public TrainerService(GameRepository gameRepository, WordService wordService) {
        this.gameRepository = gameRepository;
        this.wordService = wordService;
    }

    public ProgressDTO startGame() {
        Game game = new Game();
        String wordToGuess = wordService.provideRandomWord(5);
        game.startNewRound(wordToGuess);

        this.gameRepository.save(game);

        return convertGameToProgressDTO(game);
    }

    public ProgressDTO getProgress(Long id) {
        Game game = getGameById(id);
        return convertGameToProgressDTO(game);
    }

    public ProgressDTO startNewRound(Long id) {
        Game game = getGameById(id);
        int wordLength = game.provideNextWordLength();

        String wordToGuess = wordService.provideRandomWord(wordLength);
        game.startNewRound(wordToGuess);

        this.gameRepository.save(game);

        return convertGameToProgressDTO(game);
    }

    public ProgressDTO guess(Long id, String attempt) {
        Game game = getGameById(id);
        game.guess(attempt);
        this.gameRepository.save(game);

        return convertGameToProgressDTO(game);
    }

    public List<ProgressDTO> getAllGames() {
        List<ProgressDTO> gamePresentationDTOS = new ArrayList<>();
        List<Game> games = this.gameRepository.findAll();

        for (Game game : games) {
            gamePresentationDTOS.add(convertGameToProgressDTO(game));
        }

        return gamePresentationDTOS;
    }

    private Game getGameById(Long id) {
        return this.gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
    }

    private ProgressDTO convertGameToProgressDTO(Game game) {
        return new ProgressDTO.Builder(game.getId())
                .gameStatus(game.getGameStatus().getStatus())
                .score(game.getScore())
                .currentHint(game.getLatestRound().giveHint())
                .feedbackHistory(game.getLatestRound().getFeedbackHistory())
                .build();
    }

}
