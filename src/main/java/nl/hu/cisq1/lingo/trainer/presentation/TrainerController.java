package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameData;
import nl.hu.cisq1.lingo.trainer.presentation.request.GuessRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping("/games")
    public GameData startGame() {
        return trainerService.startGame();
    }

//    @PostMapping("/games/{id}/guess")
//    public GameData guess(@PathVariable long id, @RequestBody GuessRequest request) {
//        return trainerService.guess(id, request.attempt);
//    }

//    @PostMapping("/games/{id}/round")
//    public GameData startRound(@PathVariable long id) {
//        return trainerService.startNewRound(id);
//    }

    // Foutafhandeling try/catch -> met juiste status en juiste exception (kan ook later)
}
