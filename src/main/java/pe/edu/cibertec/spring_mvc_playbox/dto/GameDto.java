package pe.edu.cibertec.spring_mvc_playbox.dto;

public record GameDto(Integer gameId,
                      String title,
                      String platform,
                      String genre,
                      Integer stock,
                      Double price,
                      String url) {
}
