package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.FullApplicationDTO;
import fr.pentagone.akcess.dto.LightApplicationDTO;
import fr.pentagone.akcess.dto.LightApplicationIdDTO;
import fr.pentagone.akcess.dto.UserIdDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.Application;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class.getName());

    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }
    public ResponseEntity<LightApplicationIdDTO> addApplication(LightApplicationDTO dto){
        LOGGER.info("Saving a new application " + dto);
        var duplicated = applicationRepository.findByLabel(dto.name());
        if(duplicated.isPresent()) throw HttpException.badRequest("There already are an application with the label " + dto.name());
        var savedApp = applicationRepository.save(new Application(dto.name(), dto.url()));
        return ResponseEntity.ok(new LightApplicationIdDTO(savedApp.getId(), savedApp.getLabel(), savedApp.getUrl()));
    }

    public ResponseEntity<Application> applicationById(int applicationId){
        var optApp = applicationRepository.findById(applicationId);
        if(optApp.isEmpty()) throw HttpException.badRequest("Application not found");
        return ResponseEntity.ok(optApp.get());
    }

    @Transactional
    public ResponseEntity<List<UserIdDTO>> retrieveUsers(int applicationId) {
        var appResult = applicationRepository.findByIdWithUsers(applicationId);
        if(appResult.isEmpty()){
            throw HttpException.notFound("The application has not been found");
        }
        return ResponseEntity.ok(
                appResult.get().getUsers().stream().map(u -> new UserIdDTO(u.getId(), u.getUsername())).toList()
        );
    }
}
