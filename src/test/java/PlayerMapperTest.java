import app.foot.model.Player;
import app.foot.model.PlayerScorer;
import app.foot.repository.MatchRepository;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.entity.TeamEntity;
import app.foot.repository.mapper.PlayerMapper;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO-2: complete these tests
public class PlayerMapperTest {
    MatchRepository matchRepositoryMock = mock(MatchRepository.class);
    PlayerRepository playerRepositoryMock = mock(PlayerRepository.class);

    PlayerMapper subject = new PlayerMapper(matchRepositoryMock,playerRepositoryMock);
    @Test
    void player_to_domain_ok() {
        Player expected = Player.builder()
                .id(1)
                .isGuardian(false)
                .name("Bema")
                .teamName(teamBarea().getName())
                .build();
        Player actual = subject.toDomain(playerEntityBema());
        assertEquals(expected,actual);
    }

    @Test
    void player_scorer_to_domain_ok() {
        PlayerScoreEntity scorer = scorerEntityByPlayerEntity(playerEntityBema());
        PlayerScorer expected = PlayerScorer.builder()
                .player(playerModelBema())
                .isOwnGoal(false)
                .minute(10)
                .build();
        PlayerScorer actual = subject.toDomain(scorer);
        assertEquals(expected,actual);
    }

    @Test
    void player_scorer_to_entity_ok() {
        int matchId = 1;
        when(matchRepositoryMock.findById(matchId)).thenReturn(Optional.ofNullable(matchEntityWithId(matchId)));
        when(playerRepositoryMock.findById(matchId)).thenReturn(Optional.ofNullable(playerEntityBema()));
        PlayerScoreEntity expected = PlayerScoreEntity.builder()
                .player(playerEntityBema())
                .minute(10)
                .ownGoal(false)
                .match(matchEntityWithId(1))
                .build();
        PlayerScoreEntity actual = subject.toEntity(matchId,PlayerScorerToPost());
        assertEquals(expected,actual);

    }

    private static PlayerScoreEntity scorerEntityByPlayerEntity(PlayerEntity playerEntity) {
        return PlayerScoreEntity.builder()
                .id(1)
                .player(playerEntity)
                .minute(10)
                .build();
    }

    private static PlayerEntity playerEntityBema(){
        return PlayerEntity.builder()
                .id(1)
                .name("Bema")
                .guardian(false)
                .team(teamBarea())
                .build();
    }
    private static Player playerModelBema(){
        return Player.builder()
                .id(1)
                .name("Bema")
                .isGuardian(false)
                .teamName(teamBarea().getName())
                .build();
    }

    private static TeamEntity teamBarea() {
        return TeamEntity.builder()
                .id(1)
                .name("Barea")
                .build();
    }

    private static TeamEntity teamMaroc() {
        return TeamEntity.builder()
                .id(2)
                .name("Maroc")
                .build();
    }

    private static MatchEntity matchEntityWithId(int id){
        return MatchEntity.builder()
                .id(id)
                .scorers(List.of())
                .teamA(teamBarea())
                .teamB(teamMaroc())
                .datetime(Instant.parse("2023-01-24T06:03:09.215642426Z"))
                .stadium("Mahamasina")
                .build();
    }

    private static PlayerScorer PlayerScorerToPost(){
        return PlayerScorer.builder()
                .player(playerModelBema())
                .isOwnGoal(false)
                .minute(10)
                .build();
    }

}
