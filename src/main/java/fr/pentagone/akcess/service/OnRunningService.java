package fr.pentagone.akcess.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class OnRunningService implements CommandLineRunner {
    private static final Logger LOGGER = Logger.getLogger(OnRunningService.class.getName());
    private final ManagerService managerService;

    public OnRunningService(ManagerService managerService){
        this.managerService = managerService;
    }
    @Override
    public void run(String... args) {
        managerService.insertBaseManagerIfNotExists();
    }
}
