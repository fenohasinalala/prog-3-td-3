package app.foot.service.validation;

import app.foot.controller.exception.BadRequestException;
import app.foot.model.PlayerScorer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PlayerScorerValidator {
    public void accept(List<PlayerScorer> playerScorers) {
        playerScorers.forEach(this::accept);
    }

    public void accept(PlayerScorer playerScorers) {
        List<String> errorMessage = new ArrayList<>();
        if (playerScorers.getPlayer().getIsGuardian()){
            errorMessage.add("Player "+playerScorers.getPlayer().getName() +" can't score a goal, because he is a Guardian");
        }
        if (playerScorers.getMinute()<0 || playerScorers.getMinute()>90){
            errorMessage.add("Goal minute "+playerScorers.getMinute() +" is not between 0 and 90");
        }
        if (errorMessage.size()>0){
            String constraintMessages = errorMessage
                    .stream()
                    .map(n -> String.valueOf(n))
                    .collect(Collectors.joining(". "));
            throw new BadRequestException(constraintMessages);
        }

    }
}
