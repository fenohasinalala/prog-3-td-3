package app.foot.service;

import app.foot.controller.validator.PlayerValidator;
import app.foot.model.Player;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;

    private final PlayerValidator validator;

    public List<Player> getPlayers() {
        return repository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<Player> postPlayers(List<Player> players) {
        for (Player p:players) {
            validator.accept(p);
        }
        List<PlayerEntity> PlayerEntityToAdd = players.stream().map(mapper::toEntity).collect(Collectors.toUnmodifiableList());
        return repository.saveAll(PlayerEntityToAdd).stream().map(mapper::toDomain).toList();
    }
}
