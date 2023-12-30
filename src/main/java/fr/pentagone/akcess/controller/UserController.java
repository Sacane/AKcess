package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.UserIdDTO;
import fr.pentagone.akcess.dto.UserInputDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    @PostMapping("/{applicationId}")
    public ResponseEntity<UserIdDTO> registerUser(
            @PathVariable("applicationId") int applicationId,
            @RequestBody UserInputDTO credentialsDTO
    ){
        LOGGER.info("You just entered the following applicationId : " + applicationId);
        return ResponseEntity.ok(new UserIdDTO(1, credentialsDTO.username())); // code 200
    }
}
