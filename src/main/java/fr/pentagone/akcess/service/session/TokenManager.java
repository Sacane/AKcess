package fr.pentagone.akcess.service.session;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

@Component
public class TokenManager {
    private static final String SECRET_KEY = "efzuihgrekjvdnuigzeildksfrgzvdsfzdijfdiojiofajezvdiosj"; //TODO replace it
    public static final String TOKEN_CLAIM_KEY = "token";
    public static final String ENTITY_ID_CLAIM_KEY = "entityId";

    public JwtAccessToken generateJwtToken(int userId){
        Date expirationDate = new Date(System.currentTimeMillis() + 3600000); // 1 hour expiration time
        HashMap<String, Object> claims = new HashMap<>();
        var accessToken = UUID.randomUUID().toString();
        claims.put(ENTITY_ID_CLAIM_KEY, userId);
        claims.put(TOKEN_CLAIM_KEY, accessToken);
        var jwt = Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(userId))
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
        return new JwtAccessToken(jwt, accessToken);
    }
    public Optional<Claims> claims(String token){
        try {
            var claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Optional.of(claims);
        }catch(JwtException | IllegalArgumentException exc){
            return Optional.empty();
        }
    }
    private SecretKey getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
