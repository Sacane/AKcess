package fr.pentagone.akcess.service;

import fr.pentagone.akcess.dto.ManagerInputDTO;
import fr.pentagone.akcess.dto.ManagerOutputDTO;
import fr.pentagone.akcess.dto.TokenDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.Manager;
import fr.pentagone.akcess.repository.sql.ManagerRepository;
import fr.pentagone.akcess.service.session.SessionManager;
import fr.pentagone.akcess.service.session.TokenManager;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final TokenManager tokenManager;

    public ManagerService(ManagerRepository managerRepository, PasswordEncoder passwordEncoder, SessionManager sessionManager, TokenManager tokenManager){
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.sessionManager = sessionManager;
        this.tokenManager = tokenManager;
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

    public ResponseEntity<TokenDTO> verify(String login, String password) {
        var managerResult = managerRepository.findByLogin(login);
        if(managerResult.isPresent()){
            var manager = managerResult.get();
            var isPasswordOk = passwordEncoder.verify(password, manager.getPassword());
            if(isPasswordOk) {
                var jwtToken = tokenManager.generateJwtToken(manager.getId());
                sessionManager.registerSession(manager.getId(), jwtToken.accessToken());
                return ok(new TokenDTO(jwtToken.jwt()));
            }
            else return badRequest().build();
        }
        return badRequest().build();
    }

    @Transactional
    public ResponseEntity<ManagerOutputDTO> create(ManagerInputDTO managerInputDTO) {
        var password = passwordEncoder.encode(managerInputDTO.credentials().password());
        var manager = new Manager(managerInputDTO.username(), managerInputDTO.credentials().login(), password);
        var managerSaved = managerRepository.save(manager);
        return ResponseEntity.ok(new ManagerOutputDTO(managerSaved.getId(), managerSaved.getName()));
    }

    @Transactional
    public ResponseEntity<Void> delete(Integer managerId) {
        var superManagerOpt = managerRepository.findByName("MainAdmin");
        if(superManagerOpt.isEmpty()) {
            throw HttpException.badRequest("The main admin doesn't exists");
        }
        var superManager = superManagerOpt.get();
        if(superManager.getId().equals(managerId)) {
            throw HttpException.badRequest("You cannot delete the superAdmin");
        }
        if(managerRepository.existsById(managerId)) {
            managerRepository.deleteById(managerId);
            ResponseEntity.ok().build();
        }
        throw HttpException.notFound("manager not found");
    }
}
