package app.foot.service;

import app.foot.exception.BadRequestException;
import app.foot.exception.NotFoundException;
import app.foot.model.Player;
import app.foot.repository.PlayerRepository;
import app.foot.repository.mapper.PlayerMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    public List<Player> getPlayers() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Player> createPlayers(List<Player> toCreate) {
        return repository.saveAll(toCreate.stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toUnmodifiableList())).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public List<Player> modifyPlayers(List<Player> players) {
        StringBuilder exceptionBuilder = new StringBuilder();
        for (Player p: players) {
            if (p.getId()!=null ){
                if (repository.findById(p.getId()).isEmpty()) {
                    exceptionBuilder.append("Player#").append(p.getId()).append(" not found");
                }
                if (p.getName().length()<1 || p.getName()==null){
                    exceptionBuilder.append("Name is mandatory");
                }
                if (p.getTeamName()!=null){
                    exceptionBuilder.append("teamName should not be specified");
                }
            }else exceptionBuilder.append("Valid Player Id must be specified");

        }
        if (exceptionBuilder.isEmpty()){
            throw new BadRequestException(exceptionBuilder.toString());
        }
        return repository.saveAll(players.stream()
                        .map(mapper::toEntity)
                        .collect(Collectors.toUnmodifiableList())).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }
}
