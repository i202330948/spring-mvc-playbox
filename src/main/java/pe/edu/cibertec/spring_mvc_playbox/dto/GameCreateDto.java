package pe.edu.cibertec.spring_mvc_playbox.dto;

public record GameCreateDto(Integer gameId,
                            String title,
                            String description,
                            Integer releaseYear,
                            Integer platformId,
                            Integer genreId,
                            Integer modeId,
                            Integer stock,
                            Double price,
                            String url) {
}
