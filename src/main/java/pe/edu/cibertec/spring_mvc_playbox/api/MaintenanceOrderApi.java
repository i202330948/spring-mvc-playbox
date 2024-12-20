package pe.edu.cibertec.spring_mvc_playbox.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.cibertec.spring_mvc_playbox.dto.OrderCreateDto;
import pe.edu.cibertec.spring_mvc_playbox.dto.OrderDto;
import pe.edu.cibertec.spring_mvc_playbox.response.*;
import pe.edu.cibertec.spring_mvc_playbox.service.OrderService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/maintenance-order")
public class MaintenanceOrderApi {

    @Autowired
    OrderService orderService;

    @GetMapping("/all")
    public FindOrdersResponse findOrders() {
        try {
            List<OrderDto> orders = orderService.getAllOrders();
            if (!orders.isEmpty())
                return new FindOrdersResponse("01", null, orders);
            else
                return new FindOrdersResponse("02", "Orders not found", null);

        } catch (Exception e) {
            e.printStackTrace();
            return new FindOrdersResponse("99", "An error occurred, please try again", null);
        }
    }

    @GetMapping("/detail")
    public FindOrderResponse findOrder(@RequestParam(value = "id", defaultValue = "0") String id) {
        try {
            Optional<OrderDto> optional = orderService.getOrderById(Integer.parseInt(id));
            return optional.map(order ->
                    new FindOrderResponse("01", null, order)
            ).orElse(
                    new FindOrderResponse("02", "Order not found", null)
            );

        } catch (Exception e) {
            e.printStackTrace();
            return new FindOrderResponse("99", "An error occurred, please try again", null);
        }
    }

    @PutMapping("/update")
    public UpdateOrderResponse updateOrder(@RequestBody OrderCreateDto orderCreateDto) {
        try {
            if (orderService.updateOrder(orderCreateDto))
                return new UpdateOrderResponse("01", null);
            else
                return new UpdateOrderResponse("02", "Order not found");

        } catch (Exception e) {
            e.printStackTrace();
            return new UpdateOrderResponse("99", "An error occurred, please try again");
        }
    }

    @DeleteMapping("/delete/{id}")
    public DeleteOrderResponse deleteOrder(@PathVariable String id) {
        try {
            if (orderService.deleteOrderById(Integer.parseInt(id)))
                return new DeleteOrderResponse("01", null);
            else
                return new DeleteOrderResponse("02", "Order not found");

        } catch (Exception e) {
            e.printStackTrace();
            return new DeleteOrderResponse("99", "An error occurred, please try again");
        }
    }

    @PostMapping("/create")
    public CreateOrderResponse createOrder(@RequestBody OrderCreateDto orderCreateDto) {
        try {
            if (orderService.addOrder(orderCreateDto))
                return new CreateOrderResponse("01", null);
            else
                return new CreateOrderResponse("02", "Order already exists");

        } catch (Exception e) {
            e.printStackTrace();
            return new CreateOrderResponse("99", "An error occurred, please try again");
        }
    }

}
