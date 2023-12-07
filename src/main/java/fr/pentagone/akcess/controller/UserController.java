package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.UserDTO;
import fr.pentagone.akcess.dto.UserIdDTO;
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
            @RequestBody UserDTO userDTO
    ){
        LOGGER.info("You just entered the following applicationId : " + applicationId);
        return ResponseEntity.ok(new UserIdDTO(1, userDTO.username())); // code 200
    }
}
