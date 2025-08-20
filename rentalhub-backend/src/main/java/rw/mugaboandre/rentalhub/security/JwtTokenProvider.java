package rw.mugaboandre.rentalhub.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import rw.mugaboandre.rentalhub.exception.InvalidTokenException;
import rw.mugaboandre.rentalhub.exception.RentalHubAPIException;
import rw.mugaboandre.rentalhub.exception.TokenExpiredException;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationMilliseconds;

    // Generate JWT token
    public String generateToken(Authentication authentication) {
        try {
            String username = authentication.getName();
            Date currentDate = new Date();
            Date expireDate = new Date(currentDate.getTime() + jwtExpirationMilliseconds);

            String authorities = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.joining(","));

            return Jwts.builder()
                    .setSubject(username)
                    .claim("roles", authorities)
                    .setIssuedAt(currentDate)
                    .setExpiration(expireDate)
                    .signWith(key())
                    .compact();
        } catch (Exception ex) {
            throw new RentalHubAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to generate JWT token: " + ex.getMessage());
        }
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // Get username from JWT token
    public String getUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT token is expired");
        } catch (JwtException e) {
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage());
        }
    }

    // Validate JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("JWT token is expired");
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidTokenException("Invalid JWT token: " + e.getMessage());
        }
    }
}