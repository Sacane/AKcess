package fr.pentagone.akcess;

import fr.pentagone.akcess.service.PasswordEncoder;
import fr.pentagone.akcess.service.session.SessionManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class BearerTokenFilter extends OncePerRequestFilter {
    private final SessionManager sessionManager;
    private final PasswordEncoder passwordEncoder;

    public BearerTokenFilter(SessionManager sessionManager, PasswordEncoder passwordEncoder){
        this.sessionManager = sessionManager;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var authorization = request.getHeader("Authorization");
        if(authorization == null || !authorization.startsWith("Bearer ")) throw new IllegalStateException("Invalid auth header");
        var token = authorization.replaceFirst("Bearer ", "");

        filterChain.doFilter(request, response);
    }
}
