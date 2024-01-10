package fr.pentagone.akcess.exception;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = Controller.class)
public class ExceptionMvcAdvisor {
    @ExceptionHandler(value = HttpException.class)
    public String handleHttpException(HttpException httpException){
        return "error.html";
    }
}
