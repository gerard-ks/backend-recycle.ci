package ci.org.recycle.services.impl;

import ci.org.recycle.models.User;
import ci.org.recycle.services.IJwtService;
import ci.org.recycle.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService implements IJwtService {

    @Value("${security.authentification.jwt.token-validity-in-seconds:0}")
    private long tokenValidityInSeconds;

    @Value("${security.authentification.jwt.token-validity-in-seconds-for-remember-me:0}")
    private long tokenValidityInSecondsForRememberMe;

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    @Override
    public String generateToken(Collection<? extends GrantedAuthority> authorities, User user, boolean rememberMe) {
        String scope = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        Instant now = Instant.now();
        Instant validity = now.plus(
                rememberMe ? this.tokenValidityInSecondsForRememberMe : this.tokenValidityInSeconds,
                ChronoUnit.SECONDS
        );

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiresAt(validity)
                .subject(user.getEmail())
                .claim(SecurityUtils.AUTHORITIES_KEY, scope)
                .claim(SecurityUtils.AUTHORITIES_USER_ID_KEY, user.getId());


        JwtClaimsSet claimsSet = claimsBuilder.build();

        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claimsSet)).getTokenValue();
    }

    @Override
    public String createRefreshToken(String email) {
        Instant instantNow = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .id(UUID.randomUUID().toString())
                .issuer("jwt-service")
                .issuedAt(instantNow)
                .expiresAt(instantNow.plus(8, ChronoUnit.HOURS))
                .subject(email)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(SecurityUtils.JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }

    @Override
    public String extractUsername(String token) {
        Jwt jwt = this.jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        Jwt jwt = this.jwtDecoder.decode(token);
        return Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now());
    }

    @Override
    public Instant extractExpiresAt(String token) {
        return jwtDecoder.decode(token).getExpiresAt();
    }

    @Override
    public UUID getUserId(String token) {
        return jwtDecoder.decode(token).getClaim("userId");
    }
}
