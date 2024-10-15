package ci.org.recycle.services.dtos.requests;


import java.util.List;

public record CollectionPointRequestDTO(
        String description,
        Double latitude,
        Double longitude,
        List<ScheduleRequestDTO> scheduleRequestDTOList
) {
}
