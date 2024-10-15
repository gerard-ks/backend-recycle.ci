package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PickerRequestUpdateDTO(
        @NotNull(message = "L'id de l'agent de collecte ne peut être null ")
        UUID pickerId,
        UserRequestUpdateDTO userRequestUpdateDTO
) {
}
