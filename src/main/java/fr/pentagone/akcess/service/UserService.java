package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.UserDTO;
import fr.pentagone.akcess.dto.UserIdDTO;
import fr.pentagone.akcess.dto.UserInputDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import fr.pentagone.akcess.repository.sql.User;
import fr.pentagone.akcess.repository.sql.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService{
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationRepository applicationRepository;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, ApplicationRepository applicationRepository){
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.applicationRepository = applicationRepository;
    }

    public ResponseEntity<String> deleteUser(int userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            LOGGER.info("User " + userId + " deleted successfully");
            return ResponseEntity.ok("User deleted successfully.");
        }
        LOGGER.info("User " + userId + " was not found");
        throw HttpException.badRequest("User not found");
    }

    @Transactional
    public ResponseEntity<UserIdDTO> getUserInfo(int userId){
        var userResult = userRepository.findById(userId);
        if(userResult.isPresent()){
            var userGet = userResult.get();
            return ResponseEntity.ok(new UserIdDTO(userGet.getId(), userGet.getUsername()));
        }
        return ResponseEntity.badRequest().build();
    }
    @Transactional
    public ResponseEntity<UserIdDTO> save(int applicationId, UserInputDTO userDTO) {
        var encodedPassword = passwordEncoder.encode(userDTO.credentials().password());
        var savedUser = new User(userDTO.username(), userDTO.credentials().login(), encodedPassword);
        var appResult = applicationRepository.findById(applicationId);
        if(appResult.isEmpty()) {
            throw HttpException.notFound("Application has not been found");
        }
        var app = appResult.get();
        userRepository.save(savedUser);
        app.addUser(savedUser);
        return ResponseEntity.ok(new UserIdDTO(savedUser.getId(), savedUser.getUsername()));
    }
}
