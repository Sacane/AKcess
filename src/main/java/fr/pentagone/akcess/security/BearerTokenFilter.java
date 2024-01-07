package fr.pentagone.akcess.security;

import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.service.session.SessionManager;
import fr.pentagone.akcess.service.session.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UncheckedIOException;
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

        var spc = new SecurityPathConfigurer()
                .exclude("auth")
                .exclude("access")
                .exclude("h2-console")
                .exclude("ping")
                .exclude("info");
        if(authorization == null) {
            var requestPath = request.getServletPath();
            if(spc.isExcluded(requestPath)) {
                try {
                    filterChain.doFilter(request, response);
                } catch (IOException | ServletException e) {
                    throw HttpException.badRequest(e.getMessage());
                }
                LOGGER.info("Authorize to pass");
                return;
            }
            ErrorHandlers.respondError(response, 403, "Invalid request, Authorization content missing");
            LOGGER.severe("Invalid request, Authorization content missing");
            return;
        }
        var authorizedInput = authorization.replaceFirst("Bearer ", "");
        var claimsResponse = tokenManager.claims(authorizedInput);
        if(claimsResponse.isEmpty()){
            LOGGER.severe("Invalid token format : " + authorizedInput);
            ErrorHandlers.respondError(response, HttpStatus.UNAUTHORIZED.value(), "Invalid token format, missing value");
            throw HttpException.badRequest("Invalid token format : " + authorizedInput);
        }
        var claims = claimsResponse.get();
        var id = claims.get(TokenManager.ENTITY_ID_CLAIM_KEY, Integer.class);
        var token = claims.get(TokenManager.TOKEN_CLAIM_KEY, String.class);

        if(id == null || token == null) {
            LOGGER.severe("Claims not found");
            throw HttpException.notFound("Claims not found");
        }

        if(!sessionManager.isRegistered(id, token)){
            LOGGER.severe("Unrecognized token..." + token);
            ErrorHandlers.respondError(response, 401, "Invalid request, Authorization content missing");
            return;
        }

        filterChain.doFilter(request, response);
        LOGGER.info("Bearer filter over");
    }
}
