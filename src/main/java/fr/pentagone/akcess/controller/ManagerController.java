package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.*;
import fr.pentagone.akcess.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/managers")
public class ManagerController {
    private final ManagerService managerService;
    private static final Logger LOGGER = Logger.getLogger(ManagerController.class.getName());
    public ManagerController(ManagerService managerService){
        this.managerService = managerService;
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> akcessManager(@RequestBody CredentialsDTO credentialsDTO){
        LOGGER.info("check access to a manager");
        return managerService.verify(credentialsDTO.login(), credentialsDTO.password());
    }


    @PostMapping
    public ResponseEntity<ManagerOutputDTO> createManager(@RequestBody ManagerInputDTO managerInputDTO) {
        LOGGER.info("Create a manager : " + managerInputDTO.username());
        return managerService.create(managerInputDTO);
    }

    @DeleteMapping("{managerId}")
    public ResponseEntity<Void> deleteManager(@PathVariable("managerId") int managerId) {
        LOGGER.info("Delete a manager");
        return managerService.delete(managerId);
    }
}
