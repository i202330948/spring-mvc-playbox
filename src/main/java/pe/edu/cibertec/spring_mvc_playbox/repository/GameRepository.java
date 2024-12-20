package pe.edu.cibertec.spring_mvc_playbox.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_mvc_playbox.entity.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {

    @Cacheable(value = "games")
    Iterable<Game> findAll();

    @CacheEvict(value = "games", allEntries = true)
    Game save(Game game);

    @CacheEvict(value = "games", allEntries = true)
    void delete(Game game);

}
