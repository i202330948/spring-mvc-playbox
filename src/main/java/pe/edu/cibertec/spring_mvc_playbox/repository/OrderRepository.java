package pe.edu.cibertec.spring_mvc_playbox.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.repository.CrudRepository;
import pe.edu.cibertec.spring_mvc_playbox.entity.Order;

public interface OrderRepository extends CrudRepository<Order, Integer> {

    @CacheEvict(value = "games", allEntries = true)
    Order save(Order order);

}
