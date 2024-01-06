package fr.pentagone.akcess.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class CorsOriginFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = Logger.getLogger("CorsOriginFilter");
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getServletPath().contains("editor")) LOGGER.info("TEST FILTER");
        LOGGER.info("servlet path : " + request.getServletPath());
        LOGGER.info("path : " + request.getContextPath());
        LOGGER.info("URL : " + request.getRequestURL());
        LOGGER.info("URI : " + request.getRequestURI());
        var origin = request.getHeaders("Origin").asIterator().next();
        if(origin.contains("editor.swagger")) {
            response.addHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "content-type, x-gwt-module-base, x-gwt-permutation, clientid, longpush");
            LOGGER.info("ACCEPT ORIGIN");
        }


        filterChain.doFilter(request, response);
    }
}
