package fr.pentagone.akcess.service;

import fr.pentagone.akcess.controller.mvc.dto.CredentialsOutputFormDTO;
import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import fr.pentagone.akcess.repository.sql.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AkcessRedirectionService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AkcessRedirectionService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String loginAndRedirect(CredentialsOutputFormDTO credentials) {
        var userOpt = userRepository.findByLogin(credentials.getLogin());
        if(userOpt.isEmpty()) {
            throw HttpException.notFound("The user with the login " + credentials.getLogin() + " does not exists");
        }
        var user = userOpt.get();
        if(!passwordEncoder.verify(credentials.getPassword(), user.getPassword())) {
            throw HttpException.unauthorized("Wrong password for the login " + credentials.getLogin());
        }
        var app = user.getApplication();
        return "redirect:" + app.getUrl();
    }
}
