package fr.pentagone.akcess.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> handleHttpException(HttpException httpException){
        return ResponseEntity.status(httpException.getStatusCode()).body(httpException.getMessage());
    }
}
