package pe.edu.cibertec.spring_mvc_playbox.response;

import pe.edu.cibertec.spring_mvc_playbox.dto.OrderDto;

public record FindOrdersResponse(String code,
                                 String error,
                                 Iterable<OrderDto> orders) {
}
