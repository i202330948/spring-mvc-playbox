package pe.edu.cibertec.spring_mvc_playbox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.spring_mvc_playbox.dto.OrderCreateDto;
import pe.edu.cibertec.spring_mvc_playbox.dto.OrderDto;
import pe.edu.cibertec.spring_mvc_playbox.entity.Game;
import pe.edu.cibertec.spring_mvc_playbox.entity.Order;
import pe.edu.cibertec.spring_mvc_playbox.entity.User;
import pe.edu.cibertec.spring_mvc_playbox.repository.OrderRepository;
import pe.edu.cibertec.spring_mvc_playbox.repository.GameRepository;
import pe.edu.cibertec.spring_mvc_playbox.repository.UserRepository;
import pe.edu.cibertec.spring_mvc_playbox.service.OrderService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderDto> getAllOrders() throws Exception {
        List<OrderDto> orders = new ArrayList<>();
        Iterable<Order> iterable = orderRepository.findAll();
        iterable.forEach(order -> {
            orders.add(new OrderDto(
                    order.getOrderId(),
                    order.getUser().getUsername(),
                    order.getGame().getTitle(),
                    order.getCreatedOrder(),
                    order.getQuantity(),
                    order.getAmount()
            ));
        });
        return orders;
    }

    @Override
    public Optional<OrderDto> getOrderById(int id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        return optional.map(order -> new OrderDto(
                order.getOrderId(),
                order.getUser().getUsername(),
                order.getGame().getTitle(),
                order.getCreatedOrder(),
                order.getQuantity(),
                order.getAmount()
        ));
    }

    @Override
    public boolean updateOrder(OrderCreateDto orderCreateDto) throws Exception {

        Optional<Order> optional = orderRepository.findById(orderCreateDto.orderId());
        Game game = gameRepository.findById(orderCreateDto.gameId()).orElseThrow();
        User user = userRepository.findById(orderCreateDto.userId()).orElseThrow();
        return optional.map( order -> {

            order.setQuantity(1);
            order.setAmount(game.getPrice());
            order.setUser(user);
            order.setGame(game);

            orderRepository.save(order);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean deleteOrderById(int id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        return optional.map(order -> {
            orderRepository.delete(order);
            return true;
        }).orElse(false);
    }

    @Override
    public boolean addOrder(OrderCreateDto orderCreateDto) throws Exception {

        Optional<Order> optional = orderRepository.findById(orderCreateDto.orderId());
        Game game = gameRepository.findById(orderCreateDto.gameId()).orElseThrow();
        User user = userRepository.findById(orderCreateDto.userId()).orElseThrow();

        if (optional.isEmpty()) {

            Order order = new Order();
            order.setQuantity(1);
            order.setAmount(game.getPrice());
            order.setUser(user);
            order.setGame(game);
            order.setCreatedOrder(new Date());

            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean createOrder(OrderCreateDto orderCreateDto) throws Exception {

        Game game = gameRepository.findById(orderCreateDto.gameId()).orElseThrow();
        User user = userRepository.findById(orderCreateDto.userId()).orElseThrow();
        if (game.getStock() <= 0) {
            throw new Exception("Not enough stock for this game");
        }
        Order order = new Order();
        order.setQuantity(1);
        order.setAmount(game.getPrice());
        order.setUser(user);
        order.setGame(game);
        order.setCreatedOrder(new Date());
        if (game.getStock() > 0) {
            game.setStock(game.getStock() - 1);
        }
        orderRepository.save(order);
        return true;

    }

}
