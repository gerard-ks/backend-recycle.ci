package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CitizenRequestUpdateDTO(
        @NotNull(message = "L'id du citoyen ne peut être null")
        UUID citizenId,
        Long pointDeRecompense,
        UserRequestUpdateDTO userRequestUpdateDTO
) {
}
