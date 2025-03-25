package edsh.blps.security;

import edsh.blps.entity.User;
import edsh.blps.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class UserAuthProvider {
    private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long expiration = 1000 * 60 * 60 * 6; // 6 час
    private final UserService userService;

    public String createToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .issuer(username)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    public Authentication validateToken(String token) {
        var parser = Jwts.parser().verifyWith(secretKey).build();
        var decoded = parser.parseSignedClaims(token).getPayload();
        UserDetails user = userService.loadUserByUsername(decoded.getIssuer());
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}