package ci.org.recycle.services.dtos.responses;



import java.util.List;
import java.util.UUID;

public record AllCollectionPointResponseDTO(
        UUID id,

        String description,
        Double latitude,
        Double longitude,

        List<PickerResponseDTO> pickerResponseDTOS,
        List<ScheduleResponseDTO> scheduleResponseDTOS
) {
}
