package relucky.code.technicaltask2.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import relucky.code.technicaltask2.common.enums.TokenType;
import relucky.code.technicaltask2.domain.repository.UserRepository;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private @Value("${jwt.issuer}") String issuer;
    private @Value("${jwt.secret}") String secret;

    public String generateToken(TokenType token, Map<String, Object> claims, String subject) {
        var expiration = Date.from(ZonedDateTime.now().plusMinutes(token.getExpirationMinute()).toInstant());
        return Jwts.builder()
                .setClaims(new HashMap<>(claims))
                .setIssuer(issuer)
                .setSubject(subject)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(signKey(), token.getAlgorithm())
                .compact();
    }


    public String extractUsername(String token, TokenType type) {
        var claims = extractAllClaims(token);
        var tokenClaim = claims.get("token-type");
        if (!tokenClaim.equals(type.name())) {
            System.out.println("not equals");
            return null;
        }
        return extractClaim(token, Claims::getSubject);

    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails, TokenType type) {
        final String username = extractUsername(token, type);
        return username.equals(userDetails.getUsername());
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(signKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    private Key signKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}

