package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RepairerRequestUpdateDTO(
        @NotNull(message = "L'id du reparateur ne peut être null")
        UUID repairerId,
        UserRequestUpdateDTO userRequestUpdateDTO
) {
}
