package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record TypeWasteRequestDTO(
        @NotBlank(message = "La description ne doit pas être vide")
        String description
) {
}
