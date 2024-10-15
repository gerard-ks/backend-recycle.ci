package ci.org.recycle.services.dtos.responses;

import java.util.UUID;

public record CollectionPointResponseDTO(
        UUID id,
        String description,
        Double latitude,
        Double longitude
) {
}
