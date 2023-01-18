package app.foot.controller;

import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.service.PlayerScorerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class PlayerScorerController {
    private final PlayerScorerService service;

    @PostMapping("/matches/{id}/goals")
    public Match addGoals(@PathVariable int id, @RequestBody List<PlayerScorer> playerScorers) {
        return service.addGoals(id,playerScorers);
    }
}
