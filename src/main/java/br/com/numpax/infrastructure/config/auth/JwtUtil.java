package br.com.numpax.infrastructure.config.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "SuaChaveSecretaMuitoLongaESeguraParaProducao";
    private static final String REFRESH_SECRET_KEY = "ChaveSecretaDiferenteParaRefreshTokenMuitoLongaESegura";
    private static final long ACCESS_TOKEN_EXPIRATION = 900_000; // 15 minutos
    private static final long REFRESH_TOKEN_EXPIRATION = 2_592_000_000L; // 30 dias

    public static String generateAccessToken(String userId) {
        return generateToken(userId, SECRET_KEY, ACCESS_TOKEN_EXPIRATION);
    }

    public static String generateRefreshToken(String userId) {
        return generateToken(userId, REFRESH_SECRET_KEY, REFRESH_TOKEN_EXPIRATION);
    }

    private static String generateToken(String userId, String secretKey, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
            .setSubject(userId)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .compact();
    }

    public static String getUserIdFromToken(String token, boolean isRefreshToken) {
        String secretKey = isRefreshToken ? REFRESH_SECRET_KEY : SECRET_KEY;
        Claims claims = Jwts.parser()
            .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
            .build()
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject();
    }

    public static boolean validateToken(String token, boolean isRefreshToken) {
        try {
            String secretKey = isRefreshToken ? REFRESH_SECRET_KEY : SECRET_KEY;
            Jwts.parser()
                .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }
}
