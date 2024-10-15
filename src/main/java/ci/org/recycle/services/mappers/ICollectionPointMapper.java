package ci.org.recycle.services.mappers;

import ci.org.recycle.models.CollectionPoint;
import ci.org.recycle.services.dtos.responses.AllCollectionPointResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ICollectionPointMapper {

    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.description", target = "description"),
            @Mapping(source = "entity.latitude", target = "latitude"),
            @Mapping(source = "entity.longitude", target = "longitude"),

    })
    AllCollectionPointResponseDTO toDto(CollectionPoint entity);
}
