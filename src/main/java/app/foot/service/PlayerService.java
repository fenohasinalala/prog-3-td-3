package app.foot.service;

import app.foot.controller.exception.NotFoundException;
import app.foot.repository.PlayerRepository;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.mapper.PlayerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository repository;
    private final PlayerMapper mapper;


    public PlayerEntity getPlayerById(int id){
        return repository.findById(id).orElseThrow(()->new NotFoundException("Player with id "+id+" does not exists")) ;
    }
}
