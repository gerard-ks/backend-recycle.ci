package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record RoleRequestDTO(
        @NotBlank(message = "Le nom du rôle ne peut être vide")
        String roleName
) {
}
