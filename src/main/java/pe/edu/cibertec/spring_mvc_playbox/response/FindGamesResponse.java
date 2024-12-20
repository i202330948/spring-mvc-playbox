package pe.edu.cibertec.spring_mvc_playbox.response;

import pe.edu.cibertec.spring_mvc_playbox.dto.GameDto;

public record FindGamesResponse(String code,
                                String error,
                                Iterable<GameDto> games) {
}
