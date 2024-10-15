package ci.org.recycle.services.dtos.requests;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RepairerRequestDTO(
         @NotBlank(message = "La specialité ne peut être vide")
         String speciality,
         @NotNull(message = "L'année d'expérience ne peut être null")
         Integer yearOfExperience,
         String certificate,
         @NotNull(message = "Le user ne peut être null")
         UserRequestDTO userRequestDTO
) {
}
