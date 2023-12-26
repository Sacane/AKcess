package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.CredentialsDTO;
import fr.pentagone.akcess.dto.TokenDTO;
import fr.pentagone.akcess.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.logging.Logger;

@RestController
@RequestMapping("/managers")
@CrossOrigin(origins = "https://editor.swagger.io/", maxAge = 3600)
public class ManagerController {
    private final ManagerService managerService;
    private static final Logger LOGGER = Logger.getLogger(ManagerController.class.getName());
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> akcessManager(@RequestBody CredentialsDTO credentialsDTO){
        LOGGER.info("check...");
        return managerService.verify(credentialsDTO.login(), credentialsDTO.password());
    }
}
