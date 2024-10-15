package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PickerRequestDTO(
        @NotNull(message = "Le user ne peut être vide")
        UserRequestDTO userRequestDTO,
        @NotNull(message = "Le point de collecte ne peut être null")
        UUID collectionPoint_id
) {
}
