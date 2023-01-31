package app.foot.service;

import app.foot.exception.BadRequestException;
import app.foot.repository.TeamRepository;
import app.foot.repository.entity.TeamEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository repository;

public TeamEntity getTeamEntityByName(String name){
    return repository.findAllByName(name).orElseThrow(()->new BadRequestException("Team with name: "+name+" does not exist"));
}
}
