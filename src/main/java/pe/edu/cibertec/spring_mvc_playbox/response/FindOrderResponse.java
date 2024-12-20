package pe.edu.cibertec.spring_mvc_playbox.response;

import pe.edu.cibertec.spring_mvc_playbox.dto.OrderDto;

public record FindOrderResponse(String code,
                                String error,
                                OrderDto order) {
}
