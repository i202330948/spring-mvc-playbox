package pe.edu.cibertec.spring_mvc_playbox.dto;

public record GameDetailDto(Integer gameId,
                            String title,
                            String description,
                            Integer releaseYear,
                            String platform,
                            String genre,
                            String mode,
                            Integer stock,
                            Double price,
                            String url) {
}
