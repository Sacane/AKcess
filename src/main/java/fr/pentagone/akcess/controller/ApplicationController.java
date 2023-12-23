package fr.pentagone.akcess.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applications")
@CrossOrigin(origins = "https://editor.swagger.io/", maxAge = 3600)
public class ApplicationController {
}
