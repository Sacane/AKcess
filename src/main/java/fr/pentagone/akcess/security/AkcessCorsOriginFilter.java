package fr.pentagone.akcess.security;

import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.repository.sql.ApplicationRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class AkcessCorsOriginFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger("CorsOriginFilter");
    private final ApplicationRepository applicationRepository;

    public AkcessCorsOriginFilter(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Cors Origin filter");
        var origin = request.getHeader("Origin");
        if(origin == null || origin.equals("http://localhost:8080"))  {
            filterChain.doFilter(request, response);
            return;
        }
        var app = applicationRepository.findByLabelContainingIgnoreCase(origin);
        if(origin.contains("editor.swagger") || app.isPresent()) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PATCH, DELETE, PUT");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "content-type, x-gwt-module-base, x-gwt-permutation, clientid, longpush, Authorization");
            LOGGER.info("The origin has been accepted by the cors origin policy");
        } else {
            LOGGER.severe("This origin is not accepted ");
            throw HttpException.unauthorized("The origin is not accepted");
        }
        filterChain.doFilter(request, response);
    }
}
