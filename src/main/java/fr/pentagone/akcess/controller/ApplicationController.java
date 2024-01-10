package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.*;
import fr.pentagone.akcess.repository.sql.Application;
import fr.pentagone.akcess.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("api/v1/applications")
public class ApplicationController {
    private static final Logger LOGGER = Logger.getLogger(ApplicationController.class.getName());
    private final ApplicationService applicationService;


    public ApplicationController(ApplicationService applicationService){
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<LightApplicationDTO>> listApplication() {
        return applicationService.listApplication();
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<FullApplicationDTO> getApplication(@PathVariable("applicationId") int applicationId) {
        return applicationService.getApplication(applicationId);
    }

    @PatchMapping("/{applicationId}")
    public ResponseEntity<LightApplicationDTO> patchApplication(@RequestBody PatchApplicationDTO applicationDTO, @PathVariable("applicationId") int applicationId) {
        return applicationService.patchApplication(applicationId, applicationDTO);
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<Void> deleteApplication(@PathVariable("applicationId") int applicationId) {
        return applicationService.deleteApplication(applicationId);
    }

    @PostMapping
    public ResponseEntity<LightApplicationIdDTO> addApplication(@RequestBody PostApplicationDTO applicationDTO) {
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
