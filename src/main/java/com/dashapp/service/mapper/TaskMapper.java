package com.dashapp.service.mapper;

import com.dashapp.domain.AppUser;
import com.dashapp.domain.Task;
import com.dashapp.domain.Team;
import com.dashapp.service.dto.AppUserDTO;
import com.dashapp.service.dto.TaskDTO;
import com.dashapp.service.dto.TeamDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "owner", source = "owner", qualifiedByName = "appUserId")
    @Mapping(target = "team", source = "team", qualifiedByName = "teamId")
    TaskDTO toDto(Task s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);

    @Named("teamId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TeamDTO toDtoTeamId(Team team);
}
