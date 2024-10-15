package ci.org.recycle.services;

import ci.org.recycle.models.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

public interface IJwtService {
    String generateToken(Collection<? extends GrantedAuthority> authorities, User user, boolean rememberMe);
    String createRefreshToken(String email);
    String extractUsername(String token);
    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenExpired(String token);

    Instant extractExpiresAt(String token);

    UUID getUserId(String token);
}
