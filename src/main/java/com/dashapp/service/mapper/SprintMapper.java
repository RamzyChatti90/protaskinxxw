package com.dashapp.service.mapper;

import com.dashapp.domain.AppUser;
import com.dashapp.domain.Sprint;
import com.dashapp.service.dto.AppUserDTO;
import com.dashapp.service.dto.SprintDTO;
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
