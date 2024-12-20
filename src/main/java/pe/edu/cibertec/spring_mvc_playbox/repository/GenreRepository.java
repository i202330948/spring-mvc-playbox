package pe.edu.cibertec.spring_mvc_playbox.repository;

import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_mvc_playbox.entity.Genre;


public interface GenreRepository extends CrudRepository<Genre, Integer> {

}
