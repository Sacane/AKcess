package fr.pentagone.akcess;

import fr.pentagone.akcess.exception.HttpException;
import fr.pentagone.akcess.service.session.SessionManager;
import fr.pentagone.akcess.service.session.TokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
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
            if(requestPath.contains("auth") || requestPath.contains("access") || requestPath.contains("h2-console") || requestPath.contains("ping")){
                filterChain.doFilter(request, response);
                LOGGER.info("Authorize to pass");
                return;
            }
            LOGGER.severe("Invalid auth header " + requestPath);
            throw HttpException.badRequest("The given authorization is invalid");
        }
        var authorizedInput = authorization.replaceFirst("Bearer ", "");
        var claimsResponse = tokenManager.claims(authorizedInput);
        if(claimsResponse.isEmpty()){
            LOGGER.severe("Invalid token format : " + authorizedInput);
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
            throw HttpException.forbidden("Unrecognized token..." + token);
        }

        filterChain.doFilter(request, response);
        LOGGER.info("Bearer filter over");
    }
}
