package fr.pentagone.akcess.security;

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
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Cors Origin filter");
        var origin = request.getHeader("Origin");
        if(origin == null)  {
            filterChain.doFilter(request, response);
            return;
        }
        if(origin.contains("editor.swagger")) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "content-type, x-gwt-module-base, x-gwt-permutation, clientid, longpush");
            LOGGER.info("The origin has been accepted by the cors origin policy");
        } else {
            LOGGER.severe("This origin is not accepted");
        }
        filterChain.doFilter(request, response);
    }
}
