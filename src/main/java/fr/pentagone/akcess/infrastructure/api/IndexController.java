package fr.pentagone.akcess.infrastructure.api;

import fr.pentagone.akcess.infrastructure.api.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/hello")
    public ResponseEntity<UserDTO> hello() {
        return ResponseEntity.ok(new UserDTO("foo", 25));
    }
}
