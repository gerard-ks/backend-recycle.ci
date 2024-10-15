package ci.org.recycle.services.dtos.responses;

import java.util.UUID;

public record CurrentUserResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
