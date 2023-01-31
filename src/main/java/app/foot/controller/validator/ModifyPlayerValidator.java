package app.foot.controller.validator;

import app.foot.controller.rest.ModifyPlayer;
import app.foot.controller.rest.PlayerScorer;
import app.foot.exception.BadRequestException;
import app.foot.exception.NotFoundException;
import app.foot.model.Player;
import app.foot.repository.entity.PlayerEntity;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ModifyPlayerValidator implements Consumer<ModifyPlayer> {

    @Override
    public void accept(ModifyPlayer p) {
        StringBuilder exceptionBuilder = new StringBuilder();
            if (p.getId()!=null ){
                if (p.getName()==null){
                    exceptionBuilder.append("Name is mandatory");
                }else if(p.getName().length()<1) exceptionBuilder.append("Name is mandatory");
            }else exceptionBuilder.append("Valid Player Id must be specified");

        if (!exceptionBuilder.isEmpty()){
            throw new BadRequestException(exceptionBuilder.toString());
        }
    }
}
