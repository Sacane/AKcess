package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.ManagerTokenDTO;
import fr.pentagone.akcess.repository.sql.Manager;
import fr.pentagone.akcess.repository.sql.ManagerRepository;
import fr.pentagone.akcess.service.session.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.logging.Logger;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;

@Service
public class ManagerService {
    private static final Logger LOGGER = Logger.getLogger(ManagerService.class.getName());
    private static final String MANAGER_LOGIN = "admin";
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final SessionManager sessionManager;

    public ManagerService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder, SessionManager sessionManager){
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionManager = sessionManager;
    }

    public void insertBaseManagerIfNotExists(){
        var resultManager = managerRepository.findByName("MainAdmin");
        if(resultManager.isEmpty()) {
            LOGGER.info("No admin registered yet, insert the super admin...");
            managerRepository.save(new Manager("MainAdmin", MANAGER_LOGIN, passwordEncoder.encode(MANAGER_LOGIN)));
        } else {
            LOGGER.info("The admin is already registered");
        }
    }

    public ResponseEntity<ManagerTokenDTO> verify(String login, String password) {
        var managerResult = managerRepository.findByLogin(login);
        if(managerResult.isPresent()){
            var manager = managerResult.get();
            var isPasswordOk = passwordEncoder.verify(password, manager.getPassword());
            if(isPasswordOk) {
                var token = UUID.randomUUID();
                sessionManager.registerSession(manager.getId(), token);
                return ok(new ManagerTokenDTO(manager.getId(), token.toString()));
            }
            else return badRequest().build();
        }
        return badRequest().build();
    }
}
