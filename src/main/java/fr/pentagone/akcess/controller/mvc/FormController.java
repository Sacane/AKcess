package fr.pentagone.akcess.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {
    @GetMapping("auth")
    public String form() {
        return "form";
    }
}
