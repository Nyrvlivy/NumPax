package br.com.numpax.infrastructure.config.auth;

import br.com.numpax.infrastructure.entities.User;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {

    private final Key key;

    public JwtUtil() {
        String secret = "u#84E$lpMv&tJ8uZ2vDp^9Q3&*GnP0w^zRtVkC8Y!sH%j5bX*B";
        key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {

        long jwtExpirationMs = 86400000;
        return Jwts.builder()
            .setSubject(user.getUserId())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public String getUserIdFromJwtToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken);
            return true;
        } catch (JwtException e) {

            return false;
        }
    }
}
