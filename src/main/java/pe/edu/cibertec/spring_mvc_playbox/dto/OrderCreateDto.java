package pe.edu.cibertec.spring_mvc_playbox.dto;

import java.util.Date;

public record OrderCreateDto(Integer orderId,
                             Integer userId,
                             Integer gameId,
                             Date createdOrder,
                             Integer quantity,
                             Double amount) {
}
