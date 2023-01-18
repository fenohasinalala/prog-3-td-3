package app.foot.service;

import app.foot.controller.exception.BadRequestException;
import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.repository.PlayerScorerRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.mapper.MatchMapper;
import app.foot.repository.mapper.PlayerMapper;
import app.foot.repository.mapper.TeamMapper;
import app.foot.service.validation.PlayerScorerValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerScorerService {
    private final PlayerScorerRepository repository;
    private final MatchService matchService;
    private final PlayerService playerService;
    private final MatchMapper matchMapper;
    private final TeamMapper teamMapper;
    private final PlayerMapper playerMapper;
    private final PlayerScorerValidator validator;

    public Match addGoals(int id,List<PlayerScorer> playerScorers) {
        List<PlayerScoreEntity> toSave = new ArrayList<>();
        MatchEntity matchEntity = matchService.getMatchById(id);
        validator.accept(playerScorers);
        for (PlayerScorer p: playerScorers) {
            PlayerScoreEntity goal = new PlayerScoreEntity();
            PlayerEntity player = playerService.getPlayerById(p.getPlayer().getId());
            if (!player.getTeam().getId().equals(matchEntity.getTeamA().getId()) && !player.getTeam().getId().equals(matchEntity.getTeamB().getId())){
                throw new BadRequestException("Player with Id "+player.getId()+" is not in teamA or in teamB ");
            }
            if(!p.getPlayer().equals(playerMapper.toDomain(player))){
                throw new BadRequestException("Player with Id "+player.getId()+" is not the same as the player in DB");
            }
            goal.setPlayer(player);
            goal.setOwnGoal(p.getIsOwnGoal());
            goal.setMinute(p.getMinute());
            goal.setMatch(matchEntity);
            toSave.add(goal);
        }
        repository.saveAll(toSave);

        return matchMapper.toDomain(matchEntity);

    }
}
