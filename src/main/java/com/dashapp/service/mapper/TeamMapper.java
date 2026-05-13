package com.dashapp.service.mapper;

import com.dashapp.domain.AppUser;
import com.dashapp.domain.Team;
import com.dashapp.service.dto.AppUserDTO;
import com.dashapp.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "appUserId")
    TeamDTO toDto(Team s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
