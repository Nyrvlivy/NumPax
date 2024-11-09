package br.com.numpax.infrastructure.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Base64;

public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("JWT_SECRET_KEY");
    private static final long EXPIRATION_TIME = Long.parseLong(
        System.getenv().getOrDefault("JWT_EXPIRATION_TIME", "86400000")
    );
    
    private static final Key SIGNING_KEY = Keys.hmacShaKeyFor(
        Base64.getDecoder().decode(SECRET_KEY)
    );

    public static String generateToken(String userId) {
        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(SIGNING_KEY, SignatureAlgorithm.HS256)
            .compact();
    }

    public static String validateTokenAndGetUserId(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(SIGNING_KEY)
            .build()
            .parseClaimsJws(token)
            .getBody();
        
        return claims.getSubject();
    }

    public static Key getKeys() {
        return SIGNING_KEY;
    }
}
