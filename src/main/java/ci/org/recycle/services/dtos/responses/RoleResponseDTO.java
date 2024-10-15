package ci.org.recycle.services.dtos.responses;

import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String description
) {
}
