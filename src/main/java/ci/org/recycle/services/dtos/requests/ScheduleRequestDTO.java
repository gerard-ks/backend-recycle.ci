package ci.org.recycle.services.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.time.DayOfWeek;
import java.time.LocalTime;

public record ScheduleRequestDTO(
        @NotBlank(message = "Le jour ne peut être vide")
        DayOfWeek day,
        @NotBlank(message = "L'heure de début ne peut être vide")
        LocalTime startTime,
        @NotBlank(message = "L'heure de fin ne peut être vide")
        LocalTime endTime
) {
}
