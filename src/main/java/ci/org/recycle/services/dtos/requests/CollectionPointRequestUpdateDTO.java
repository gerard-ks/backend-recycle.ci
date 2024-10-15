package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CollectionPointRequestUpdateDTO(
         @NotNull(message = "L'id du point de collecte ne peut être null")
         UUID collectionPointId,
         Double latitude,
         Double longitude
) {
}
