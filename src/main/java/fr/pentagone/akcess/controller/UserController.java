package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.*;
import fr.pentagone.akcess.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/users")
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

    @PostMapping("/application/{applicationId}/auth")
    public ResponseEntity<UserIdTokenDTO> checkAcces(@PathVariable int applicationId, @RequestBody CredentialsDTO credentialsDTO){
        return userService.checkAccess(applicationId, credentialsDTO);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserIdDTO> patchUser(@PathVariable("userId") int userId, @RequestBody NewUserInputDTO newUserInputDTO){
        return userService.updateUser(userId, newUserInputDTO);
    }
}

// admin / admin
