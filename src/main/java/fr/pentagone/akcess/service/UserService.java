package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.*;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import fr.pentagone.akcess.repository.sql.User;
import fr.pentagone.akcess.repository.sql.UserRepository;
import fr.pentagone.akcess.service.session.TokenManager;
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
    private final TokenManager tokenManager;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, ApplicationRepository applicationRepository, TokenManager tokenManager){
        this.userRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.applicationRepository = applicationRepository;
        this.tokenManager = tokenManager;
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

    public ResponseEntity<UserIdTokenDTO> checkAccess(int applicationId, CredentialsDTO credentialsDTO){
        var optApp = applicationRepository.findByIdWithUsers(applicationId);
        if(optApp.isEmpty()) {
            LOGGER.severe("Application not found");
            throw HttpException.notFound("Application not found");
        }
        var appUsers = optApp.get().getUsers();
        var userOpt = appUsers.stream().filter(u -> u.getLogin().equals(credentialsDTO.login())).findFirst();
        if(userOpt.isEmpty()) {
            LOGGER.severe("User not found");
            throw HttpException.notFound("The user " + credentialsDTO.login() + " not found");
        }
        var user = userOpt.get();
        var token = tokenManager.generateJwtToken(user.getId());
        if(passwordEncoder.verify(credentialsDTO.password(), user.getPassword())) {
            return ResponseEntity.ok(new UserIdTokenDTO(user.getId(), user.getUsername(), token.accessToken()));
        }
        throw HttpException.forbidden("Bad credentials");
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
