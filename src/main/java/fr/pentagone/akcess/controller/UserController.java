package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.UserIdDTO;
import fr.pentagone.akcess.dto.UserInputDTO;
import fr.pentagone.akcess.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/{applicationId}")
    public ResponseEntity<UserIdDTO> registerUser(
            @PathVariable("applicationId") int applicationId,
            @RequestBody UserInputDTO credentialsDTO
    ){
        LOGGER.info("You just entered the following applicationId : " + applicationId);
        return userService.save(applicationId, credentialsDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") int userId){
        return userService.deleteUser(userId);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserIdDTO> getUserInfo(@PathVariable("userId") int userId){
        LOGGER.info("Getting info of the user with the id : " + userId);
        return userService.getUserInfo(userId);
    }

    @PostMapping("/{applicationId}/acces")
    public ResponseEntity<String> checkAcces(@PathVariable int applicationId){
        return userService.checkAccess(applicationId);
    }

}
