package fr.pentagone.akcess.controller.mvc;

import fr.pentagone.akcess.controller.mvc.dto.CredentialsOutputFormDTO;
import fr.pentagone.akcess.service.AkcessRedirectionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    private final AkcessRedirectionService akcessRedirectionService;
    public FormController(AkcessRedirectionService akcessRedirectionService) {
        this.akcessRedirectionService = akcessRedirectionService;
    }

    @GetMapping("auth")
    public String form(Model model) {
        model.addAttribute("form", new CredentialsOutputFormDTO());
        return "form";
    }

    @PostMapping("login")
    public String redirect(CredentialsOutputFormDTO credentials) {
        return akcessRedirectionService.loginAndRedirect(credentials);
    }
}
