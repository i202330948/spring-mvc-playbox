package pe.edu.cibertec.spring_mvc_playbox.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_mvc_playbox.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
