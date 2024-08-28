package staj.sigorta_uygulama_staj.Security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import staj.sigorta_uygulama_staj.Entities.Role;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String APP_SECRET;

    @Value("${jwt.token.validity}")
    private Long EXPIRES_IN;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(APP_SECRET.getBytes());
    }

    public Long extractUserId(String token) {

        Claims claims = extractAllClaims(token);
        String userIdString = claims.get("userId", String.class);

        if (userIdString == null || userIdString.equals("undefined")) {
            throw new IllegalArgumentException("User ID claim is missing");
        }
        try {
            return Long.parseLong(userIdString);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("User ID claim is not a valid number", e);
        }
    }

    // Token'dan Claims (payload) kısmını alır
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    public String generateToken(String username, Long userId,  Set<Role> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId.toString()) // userId'yi claim olarak ekler
                .claim("roles", roles.stream().map(Role::name).collect(Collectors.toSet())) // Roller bilgisini claim olarak ekler
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

    }

    // Token'ın geçerliliğini doğrular
    public boolean validateToken(String token) {
        final String username = extractUsername(token);


        return (!isTokenExpired(token));
    }

    // Token'dan kullanıcı adını çıkarır
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    // Token'ın süresinin dolup dolmadığını kontrol eder
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token'dan süresini çıkarır
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'dan Claims (payload) kısmını alır
    private <T> T extractClaim(String token, ClaimsResolver<T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.resolve(claims);
    }

    @FunctionalInterface
    private interface ClaimsResolver<T> {
        T resolve(Claims claims);
    }
}
