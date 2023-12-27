package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.LightApplicationDTO;
import fr.pentagone.akcess.dto.LightApplicationIdDTO;
import fr.pentagone.akcess.repository.sql.Application;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class.getName());

    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }
    public ResponseEntity<LightApplicationIdDTO> addApplication(LightApplicationDTO dto){
        LOGGER.info("Add a new application " + dto);
        var savedApp = applicationRepository.save(new Application(dto.name(), dto.url()));
        return ResponseEntity.ok(new LightApplicationIdDTO(savedApp.getId(), savedApp.getLabel(), savedApp.getUrl()));
    }
}
