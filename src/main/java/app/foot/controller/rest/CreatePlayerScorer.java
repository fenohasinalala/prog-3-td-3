package app.foot.controller.rest;

import app.foot.model.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreatePlayerScorer {
    private Player player;
    private Integer scoreTime;
    private Boolean isOG;
}
