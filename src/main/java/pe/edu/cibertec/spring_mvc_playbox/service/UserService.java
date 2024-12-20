package pe.edu.cibertec.spring_mvc_playbox.service;

import pe.edu.cibertec.spring_mvc_playbox.dto.UserDto;
import pe.edu.cibertec.spring_mvc_playbox.entity.User;

import java.util.Optional;

public interface UserService {

    boolean registerNewUser(UserDto userDto);

    Optional<User> findByUsername(String username);

}
