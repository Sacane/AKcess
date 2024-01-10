package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.*;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.Application;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@Service
public class ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ConcurrentHashMap<Integer, Application> idempotencyPost = new ConcurrentHashMap<>();
    private static final Logger LOGGER = Logger.getLogger(ApplicationService.class.getName());

    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }

    public ResponseEntity<List<LightApplicationDTO>> listApplication() {
        return ResponseEntity.ok(applicationRepository.findAll().stream().map(app -> new LightApplicationDTO(app.getId(), app.getLabel(), app.getUrl())).toList());
    }

    public ResponseEntity<FullApplicationDTO> getApplication(int applicationId) {
        var application = applicationRepository.findByIdWithUsers(applicationId);
        System.out.println(application);
        if (application.isEmpty()) {
            throw HttpException.badRequest("Application not found");
        }
        return ResponseEntity.ok(new FullApplicationDTO(application.get().getId(), application.get().getLabel(), application.get().getUrl(), application.get().getUsers().stream().map(user -> new UserDTO(user.getUsername(), user.getLogin())).toList(), List.of(new RoleDTO(1, "DEFAULT", 1))));
    }

    @Transactional
    public ResponseEntity<LightApplicationDTO> patchApplication(int applicationId, PatchApplicationDTO applicationDTO) {
        var optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            throw HttpException.badRequest("Application not found");
        }
        var application = optionalApplication.get();
        if (applicationDTO.name() != null) {
            var labelApplication = applicationRepository.findByLabel(applicationDTO.name());
            if (labelApplication.isEmpty()) {
                application.setLabel(applicationDTO.name());
            }
            else {
                throw HttpException.forbidden("Application with this label already exist");
            }
        }
        if (applicationDTO.url() != null) {
            application.setUrl(applicationDTO.url());
        }
        return ResponseEntity.ok(new LightApplicationDTO(application.getId(), application.getLabel(), application.getUrl()));
    }

    @Transactional
    public ResponseEntity<Void> deleteApplication(int applicationId) {
        System.out.println(applicationId);
        var optionalApplication = applicationRepository.findById(applicationId);
        if (optionalApplication.isEmpty()) {
            throw HttpException.badRequest("Application not found");
        }
        var application = optionalApplication.get();
        applicationRepository.delete(application);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<LightApplicationIdDTO> addApplication(PostApplicationDTO dto) {
        LOGGER.info("Saving a new application " + dto);
        if (idempotencyPost.containsKey(dto.idempotencyKey())) {
            var application = idempotencyPost.get(dto.idempotencyKey());
            return ResponseEntity.ok(new LightApplicationIdDTO(application.getId(), application.getLabel(), application.getUrl()));
        }
        var duplicated = applicationRepository.findByLabel(dto.name());
        if(duplicated.isPresent()) throw HttpException.badRequest("There already are an application with the label " + dto.name());
        var savedApp = applicationRepository.save(new Application(dto.name(), dto.url()));
        idempotencyPost.put(dto.idempotencyKey(), savedApp);
        return ResponseEntity.ok(new LightApplicationIdDTO(savedApp.getId(), savedApp.getLabel(), savedApp.getUrl()));
    }

    public ResponseEntity<LightApplicationDTO> duplicateApplication(int applicationId){
        var optApp = applicationRepository.findById(applicationId);
        if(optApp.isEmpty()) throw HttpException.badRequest("Application not found");
        var app = optApp.get();
        var newApp = new Application(app.getLabel(), app.getUrl());
        var saved = applicationRepository.save(newApp);
        return ResponseEntity.ok(new LightApplicationDTO(saved.getId(), saved.getLabel(), saved.getUrl()));
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
