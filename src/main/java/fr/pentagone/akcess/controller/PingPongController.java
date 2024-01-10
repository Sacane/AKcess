package fr.pentagone.akcess.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/ping")
@RestController
public class PingPongController {
    @GetMapping
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @GetMapping("{ping}")
    public ResponseEntity<String> ping(@PathVariable("ping") String ping){
        return ResponseEntity.ok(ping);
    }
}
