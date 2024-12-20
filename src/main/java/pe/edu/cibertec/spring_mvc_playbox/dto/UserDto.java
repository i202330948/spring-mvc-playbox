package pe.edu.cibertec.spring_mvc_playbox.dto;

import java.util.Date;

public record UserDto(Integer userId,
                      String username,
                      String password,
                      String email,
                      String firstName,
                      String lastName,
                      String role,
                      Date createdAt,
                      Date updatedAt) {
}
