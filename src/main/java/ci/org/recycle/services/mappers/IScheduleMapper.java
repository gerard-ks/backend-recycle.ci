package ci.org.recycle.services.mappers;

import ci.org.recycle.models.Schedule;
import ci.org.recycle.services.dtos.responses.ScheduleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IScheduleMapper {

    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.day", target = "day"),
            @Mapping(source = "entity.startTime", target = "startTime"),
            @Mapping(source = "entity.endTime", target = "endTime")
    })
    ScheduleResponseDTO toDto(Schedule entity);
}
