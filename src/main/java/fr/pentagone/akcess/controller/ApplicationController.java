package fr.pentagone.akcess.controller;

import fr.pentagone.akcess.dto.LightApplicationDTO;
import fr.pentagone.akcess.dto.LightApplicationIdDTO;
import fr.pentagone.akcess.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
