package pe.edu.cibertec.spring_mvc_playbox.dto;

import java.util.Date;

public record OrderDto(Integer orderId,
                       String user,
                       String game,
                       Date createdOrder,
                       Integer quantity,
                       Double amount) {
}
