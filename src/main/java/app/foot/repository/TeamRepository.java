package app.foot.repository;

import app.foot.repository.entity.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findAllByName(String name);
}
