package ci.org.recycle.services.mappers;

import ci.org.recycle.models.Repairer;
import ci.org.recycle.services.dtos.responses.RepairerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IRepairerMapper {

    @Mappings({
            @Mapping(source = "dto.id", target = "id"),
            @Mapping(source = "dto.speciality", target = "speciality"),
            @Mapping(source = "dto.yearOfExperience", target = "yearOfExperience"),
            @Mapping(source = "dto.certificate", target = "certificate"),
            @Mapping(source = "dto.userResponseDTO", target = "user"),
            @Mapping(source = "dto.userResponseDTO.id", target = "user.id"),
            @Mapping(source = "dto.userResponseDTO.firstName", target = "user.firstName"),
            @Mapping(source = "dto.userResponseDTO.lastName", target = "user.lastName"),
            @Mapping(source = "dto.userResponseDTO.birthDate", target = "user.birthDate"),
            @Mapping(source = "dto.userResponseDTO.telephone", target = "user.telephone"),
            @Mapping(source = "dto.userResponseDTO.email", target = "user.email"),
            @Mapping(source = "dto.userResponseDTO.password", target = "user.password"),
            @Mapping(source = "dto.userResponseDTO.accountNonLocked", target = "user.accountNonLocked"),
            @Mapping(source = "dto.userResponseDTO.enabled", target = "user.enabled"),
            @Mapping(source = "dto.userResponseDTO.roleResponseDTO", target = "user.role"),
            @Mapping(source = "dto.userResponseDTO.roleResponseDTO.id", target = "user.role.id"),
            @Mapping(source = "dto.userResponseDTO.roleResponseDTO.description", target = "user.role.roleName"),
            @Mapping(source = "dto.userResponseDTO.addressResponseDTO", target = "user.address"),
            @Mapping(source = "dto.userResponseDTO.addressResponseDTO.id", target = "user.address.id"),
            @Mapping(source = "dto.userResponseDTO.addressResponseDTO.town", target = "user.address.town"),
            @Mapping(source = "dto.userResponseDTO.addressResponseDTO.district", target = "user.address.district"),
            @Mapping(source = "dto.userResponseDTO.addressResponseDTO.city", target = "user.address.city"),
    })
    Repairer toEntity(RepairerResponseDTO dto);


    @Mappings({
            @Mapping(source = "entity.id", target = "id"),
            @Mapping(source = "entity.speciality", target = "speciality"),
            @Mapping(source = "entity.yearOfExperience", target = "yearOfExperience"),
            @Mapping(source = "entity.certificate", target = "certificate"),
            @Mapping(source = "entity.user", target = "userResponseDTO"),
            @Mapping(source = "entity.user.id", target = "userResponseDTO.id"),
            @Mapping(source = "entity.user.firstName", target = "userResponseDTO.firstName"),
            @Mapping(source = "entity.user.lastName", target = "userResponseDTO.lastName"),
            @Mapping(source = "entity.user.birthDate", target = "userResponseDTO.birthDate"),
            @Mapping(source = "entity.user.telephone", target = "userResponseDTO.telephone"),
            @Mapping(source = "entity.user.email", target = "userResponseDTO.email"),
            @Mapping(source = "entity.user.password", target = "userResponseDTO.password", ignore = true),
            @Mapping(source = "entity.user.accountNonLocked", target = "userResponseDTO.accountNonLocked"),
            @Mapping(source = "entity.user.enabled", target = "userResponseDTO.enabled"),
            @Mapping(source = "entity.user.role", target = "userResponseDTO.roleResponseDTO"),
            @Mapping(source = "entity.user.role.id", target = "userResponseDTO.roleResponseDTO.id"),
            @Mapping(source = "entity.user.role.roleName", target = "userResponseDTO.roleResponseDTO.description"),
            @Mapping(source = "entity.user.address", target = "userResponseDTO.addressResponseDTO"),
            @Mapping(source = "entity.user.address.id", target = "userResponseDTO.addressResponseDTO.id"),
            @Mapping(source = "entity.user.address.town", target = "userResponseDTO.addressResponseDTO.town"),
            @Mapping(source = "entity.user.address.district", target = "userResponseDTO.addressResponseDTO.district"),
            @Mapping(source = "entity.user.address.city", target = "userResponseDTO.addressResponseDTO.city"),
    })
    RepairerResponseDTO toDto(Repairer entity);
}
