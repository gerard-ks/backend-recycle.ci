package ci.org.recycle.services.dtos.responses;

import java.util.UUID;

public record TypeWasteResponseDTO(
        UUID id,
        String description
) {
}
