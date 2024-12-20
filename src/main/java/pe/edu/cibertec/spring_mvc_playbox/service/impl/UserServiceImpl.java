package pe.edu.cibertec.spring_mvc_playbox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.cibertec.spring_mvc_playbox.dto.UserDto;
import pe.edu.cibertec.spring_mvc_playbox.entity.User;
import pe.edu.cibertec.spring_mvc_playbox.repository.UserRepository;
import pe.edu.cibertec.spring_mvc_playbox.service.UserService;

import java.util.Date;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean registerNewUser(UserDto userDto) {
        Optional<User> existingUser = userRepository.findByUsername(userDto.username());
        if (existingUser.isPresent()) {
            return false;
        }

        User user = new User();
        user.setUsername(userDto.username());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setEmail(userDto.email());
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setRole("USER");
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        userRepository.save(user);
        return true;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
