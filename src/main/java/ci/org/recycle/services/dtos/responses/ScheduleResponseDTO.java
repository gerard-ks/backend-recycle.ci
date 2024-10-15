package ci.org.recycle.services.dtos.responses;



import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.UUID;

public record ScheduleResponseDTO(
        UUID id,
        DayOfWeek day,
        LocalTime startTime,
        LocalTime endTime
) {
}
