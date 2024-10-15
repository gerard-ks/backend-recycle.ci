package ci.org.recycle.services.mappers;

import ci.org.recycle.models.Diagnostic;
import ci.org.recycle.services.dtos.responses.DiagnosticResponseForRepairerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IDiagnosticMapper {

    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.statusDiagnostic", target = "statusDiagnostic"),
            @Mapping(source = "entity.waste", target = "wasteResponseDTO"),
            @Mapping(source = "entity.waste.id", target = "wasteResponseDTO.id"),
            @Mapping(source = "entity.waste.weight", target = "wasteResponseDTO.weight"),
            @Mapping(source = "entity.waste.condition", target = "wasteResponseDTO.conditionWaste"),
            @Mapping(source = "entity.waste.urlImage", target = "wasteResponseDTO.urlPhoto"),
            @Mapping(source = "entity.waste.details", target = "wasteResponseDTO.details"),
            @Mapping(source = "entity.waste.typeWaste", target = "wasteResponseDTO.typeWaste"),
            @Mapping(source = "entity.waste.typeWaste.id", target = "wasteResponseDTO.typeWaste.id"),
            @Mapping(source = "entity.waste.typeWaste.description", target = "wasteResponseDTO.typeWaste.description"),
    })
    DiagnosticResponseForRepairerDTO toDto(Diagnostic entity);
}
