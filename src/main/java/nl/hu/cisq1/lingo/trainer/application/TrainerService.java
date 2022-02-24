package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameData;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.presentation.TrainerController;
import nl.hu.cisq1.lingo.words.application.WordService;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.transaction.Transactional;

@Service
@Transactional
public class TrainerService {
    private final WordService wordService;
    private final GameRepository gameRepository;

    public TrainerService(WordService wordService, GameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public GameData startGame() {
        Game game = new Game();

        String word = wordService.provideRandomWord(5);
        game.startNewRound(word);

        gameRepository.save(game);

        return game.showData();
    }

//    public GameData guess() {
//        Game game = game.showData();
//    }

//    public GameData startNewRound() {
//        Game game = gameRepository.getById(id);
//    }

    public void showGame() {

    }
}
