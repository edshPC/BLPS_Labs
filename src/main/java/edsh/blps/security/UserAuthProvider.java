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
    private final XmlUserDetailsService userDetailsService;

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
        System.out.println(token);
        var parser = Jwts.parser().verifyWith(secretKey).build();
        System.out.println(parser);
        var decoded = parser.parseSignedClaims(token).getPayload();
        System.out.println(decoded);
        System.out.println(decoded.getIssuer());
        UserDetails user = userDetailsService.loadUserByUsername(decoded.getIssuer());
        System.out.println(1);
        System.out.println(user);
        System.out.println(2);
        return new UsernamePasswordAuthenticationToken(user, null, null);
    }
}