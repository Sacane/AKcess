package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.UserDTO;
import fr.pentagone.akcess.repository.sql.User;
import fr.pentagone.akcess.repository.sql.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService{
    private static final Logger LOGGER = Logger.getLogger(User.class.getName());
    private final UserRepository userRepository;

    public UserService(UserRepository repository){
        this.userRepository = repository;
    }

    public ResponseEntity<String> deleteUser(int userId){
        if(userRepository.existsById(userId)){
            userRepository.deleteById(userId);
            LOGGER.info("User " + userId + " deleted successfully");
            return ResponseEntity.ok("User deleted successfully.");
        }
        LOGGER.info("User " + userId + " was not found");
        return new ResponseEntity<>("User is not present in our database.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<UserDTO> getUserInfo(int userId){
        var userResult = userRepository.findById(userId);
        if(userResult.isPresent()){
            var userGet = userResult.get();
            return ResponseEntity.ok(new UserDTO(userGet.getUsername(), null)); //TODO Encoder le password ou traitement dessus à faire
        }
        return ResponseEntity.badRequest().build();
    }
}
