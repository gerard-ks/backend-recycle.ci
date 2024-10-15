package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AddressRequestDTO(
         @NotBlank(message = "La commune ne peut être vide")
         String town,
         @NotBlank(message = "Le quartier ne peut être vide")
         String district,
         @NotBlank(message = "La ville ne peut être vide")
         String city
) {
}
