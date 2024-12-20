package pe.edu.cibertec.spring_mvc_playbox.service;

import pe.edu.cibertec.spring_mvc_playbox.dto.OrderCreateDto;
import pe.edu.cibertec.spring_mvc_playbox.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<OrderDto> getAllOrders() throws Exception;

    Optional<OrderDto> getOrderById(int id) throws Exception;

    boolean updateOrder(OrderCreateDto orderCreateDto) throws Exception;

    boolean deleteOrderById(int id) throws Exception;

    boolean addOrder(OrderCreateDto orderCreateDto) throws Exception;

    boolean createOrder(OrderCreateDto orderCreateDto) throws Exception;

}
