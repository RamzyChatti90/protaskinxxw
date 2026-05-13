package com.example.service.mapper;

import com.example.domain.AppUser;
import com.example.domain.Sprint;
import com.example.service.dto.AppUserDTO;
import com.example.service.dto.SprintDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Sprint} and its DTO {@link SprintDTO}.
 */
@Mapper(componentModel = "spring")
public interface SprintMapper extends EntityMapper<SprintDTO, Sprint> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "appUserId")
    SprintDTO toDto(Sprint s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
