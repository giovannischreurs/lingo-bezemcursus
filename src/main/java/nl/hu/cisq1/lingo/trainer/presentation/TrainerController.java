package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.DTO.ProgressDTO;
import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameNotFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.GameStateException;
import nl.hu.cisq1.lingo.trainer.presentation.request.GuessRequest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/games")
    public ProgressDTO startGame() {
        return trainerService.startGame();
    }

    @PostMapping("/games/{id}/round")
    public ProgressDTO startRound(@PathVariable long id) {
        try {
            return trainerService.startNewRound(id);
        } catch (GameNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (GameStateException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @PostMapping("/games/{id}/guess")
    public ProgressDTO guess(@PathVariable long id, @RequestBody GuessRequest request) {
        try {
            return trainerService.guess(id, request.attempt);
        } catch (GameNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        } catch (GameStateException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }

    @GetMapping("/games/{id}")
    public ProgressDTO showGame(@PathVariable long id) {
        try {
            return trainerService.getProgress(id);
        } catch (GameNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/games")
    public List<ProgressDTO> getAllGames() {
        return trainerService.getAllGames();
    }

}
