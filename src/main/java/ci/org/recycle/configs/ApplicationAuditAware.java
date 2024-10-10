package ci.org.recycle.configs;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Optional;
import java.util.UUID;

public class ApplicationAuditAware implements AuditorAware<UUID> {
    @Override
    public Optional<UUID> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) principal;
            UUID userId = extractUserIdFromJwt(jwt);
            return Optional.ofNullable(userId);
        }

        return Optional.empty();
    }


    private UUID extractUserIdFromJwt(Jwt jwt) {
        Object userIdClaim = jwt.getClaim("user_id");
      if (userIdClaim instanceof UUID) {
            return (UUID) userIdClaim;
        } else {
            return null;
        }
    }
}
