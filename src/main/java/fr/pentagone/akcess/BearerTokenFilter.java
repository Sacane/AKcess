package fr.pentagone.akcess;

import fr.pentagone.akcess.service.session.SessionManager;
import fr.pentagone.akcess.service.session.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public class BearerTokenFilter extends OncePerRequestFilter {
    private final SessionManager sessionManager;
    private final TokenManager tokenManager;
    private static final Logger LOGGER = Logger.getLogger(BearerTokenFilter.class.getName());

    public BearerTokenFilter(SessionManager sessionManager, TokenManager tokenManager){
        this.sessionManager = sessionManager;
        this.tokenManager = tokenManager;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        LOGGER.info("Start bearer filter...");
        var authorization = request.getHeader("Authorization"); // JWT Token
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            var requestPath = request.getServletPath();
            if(requestPath.contains("auth") || requestPath.contains("access")){
                filterChain.doFilter(request, response);
                LOGGER.info("Authorize to pass");
                return;
            }
            LOGGER.severe("Invalid auth header " + requestPath);
            throw new IllegalStateException("Invalid auth header");
        }
        var authorizedInput = authorization.replaceFirst("Bearer ", "");
        var claimsResponse = tokenManager.claims(authorizedInput);
        if(claimsResponse.isEmpty()){
            sendError(response, 400, "Invalid input");
            LOGGER.severe("Invalid token format : " + authorizedInput);
            return;
        }
        var claims = claimsResponse.get();
        var id = claims.get(TokenManager.ENTITY_ID_CLAIM_KEY, Integer.class);
        var token = claims.get(TokenManager.TOKEN_CLAIM_KEY, String.class);

        if(id == null || token == null) {
            sendError(response, 404, "Claims not found");
            LOGGER.severe("Claims not found");
            return;
        }

        if(!sessionManager.isRegistered(id, token)){
            sendError(response, 403, "Le token n'est pas reconnue");
            LOGGER.severe("Unrecognized token..." + token);
            return;
        }

        filterChain.doFilter(request, response);
        LOGGER.info("Bearer filter over");
    }
    private void sendError(HttpServletResponse response, int sc, String msg) throws IOException {
        SecurityContextHolder.clearContext();
        response.sendError(sc, msg);
    }
}
