package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.LightApplicationDTO;
import fr.pentagone.akcess.dto.LightApplicationIdDTO;
import fr.pentagone.akcess.dto.UserIdDTO;
import fr.pentagone.akcess.repository.sql.Application;
import fr.pentagone.akcess.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/applications")
public class ApplicationController {
    private static final Logger LOGGER = Logger.getLogger(ApplicationController.class.getName());
    private final ApplicationService applicationService;


    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }
    @PostMapping
    public ResponseEntity<LightApplicationIdDTO> addApplication(@RequestBody LightApplicationDTO applicationDTO){
        LOGGER.info("Trying to save an application..." + applicationDTO);
        return applicationService.addApplication(applicationDTO);
    }

    @GetMapping("/{applicationId}/users")
    public ResponseEntity<List<UserIdDTO>> retrieveAllAppUsers(@PathVariable("applicationId") int applicationId){
        return applicationService.retrieveUsers(applicationId);
    }

    @PostMapping("/{applicationId}/duplicate")
    public ResponseEntity<Application> duplicateApplication(@PathVariable int applicationId){
        return applicationService.applicationById(applicationId);
    }

}
