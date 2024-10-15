package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.models.enumerations.CitizenType;
import jakarta.validation.constraints.NotNull;

public record CitizenRequestDTO(
   @NotNull(message = "Le type de citoyen ne peut être null")
   CitizenType citizenType,
   @NotNull(message = "Le user ne peut être null")
   UserRequestDTO userRequestDTO
) {
}
