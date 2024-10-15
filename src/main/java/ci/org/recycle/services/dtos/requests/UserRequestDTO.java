package ci.org.recycle.services.dtos.requests;

import ci.org.recycle.utils.Constants;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record UserRequestDTO(
        @NotBlank(message = "Le nom ne peut être vide")
        String firstName,
        @NotBlank(message = "Le prenom ne peut être vide")
        String lastName,
        @PastOrPresent(message = "La date de naissance doit se situer dans le passé ou le présent")
        LocalDate birthDate,
        @NotBlank(message = "L'adresse email ne peut être vide")
        @Email(message = "L'adresse email doit être valide")
        String email,
        @NotBlank(message = "Le mot de passe ne peut être vide")
        @Size(min = 8, message = "Le mot de passe doit être constitué au minimum de 8 caractères")
        String password,
        @NotBlank(message = "Le téléphone ne peut être vide")
        @Pattern(regexp = "^(00225|\\+225)\\d{" + Constants.PHONE_NUMBER_DIGITS + "}$",
                message = "Le téléphone doit commencer par '00225' ou '+225' et être suivi de " + Constants.PHONE_NUMBER_DIGITS + " chiffres")
        String telephone,
        @NotNull(message = "L'adresse ne peut être null")
        AddressRequestDTO addressRequestDTO,
        @NotNull(message = "Le role ne peut être null")
        UUID roleId
) {
}
