package edsh.blps.security;

import edsh.blps.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class UserAuthProvider {
    private static final long expiration = 1000 * 60 * 60 * 6; // 6 час
    private final SecretKey secretKey;
    private final UserService userService;

    public UserAuthProvider(UserService userService,
                            @Value("${jwt.secret}") String secret) {
        this.userService = userService;
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Authentication validateToken(String token) {
        var parser = Jwts.parser().verifyWith(secretKey).build();
        var decoded = parser.parseSignedClaims(token).getPayload();
        UserDetails user = userService.loadUserByUsername(decoded.getSubject());
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}