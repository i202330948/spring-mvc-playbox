package pe.edu.cibertec.spring_mvc_playbox.response;

import pe.edu.cibertec.spring_mvc_playbox.dto.GameDetailDto;

public record FindGameResponse(String code,
                               String error,
                               GameDetailDto game) {
}
