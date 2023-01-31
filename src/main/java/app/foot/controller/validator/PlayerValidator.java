package app.foot.controller.validator;

import app.foot.exception.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class PlayerValidator implements Consumer<app.foot.model.Player> {
    @Override
    public void accept(app.foot.model.Player player) {
        StringBuilder exceptionBuilder = new StringBuilder();
        if (player.getId()!=null) {
            exceptionBuilder.append("Player#")
                    .append(player.getName())
                    .append(" has id.").append(" While posting player, Id should not be specified");
        }
        if (player.getName()==null || player.getName().length()==0) {
            exceptionBuilder.append("Player name is mandatory");
        }


        if (!exceptionBuilder.isEmpty()) {
            throw new BadRequestException(exceptionBuilder.toString());
        }
    }
}
