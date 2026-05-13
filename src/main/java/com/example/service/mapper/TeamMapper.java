package com.example.service.mapper;

import com.example.domain.AppUser;
import com.example.domain.Team;
import com.example.service.dto.AppUserDTO;
import com.example.service.dto.TeamDTO;
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
