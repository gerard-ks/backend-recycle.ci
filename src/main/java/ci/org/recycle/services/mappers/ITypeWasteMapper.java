package ci.org.recycle.services.mappers;

import ci.org.recycle.models.TypeWaste;
import ci.org.recycle.services.dtos.responses.TypeWasteResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITypeWasteMapper {
    TypeWasteResponseDTO toDto(TypeWaste entity);
}
