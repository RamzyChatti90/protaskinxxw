package com.example.protaskinxxw.service.mapper;

import com.example.protaskinxxw.domain.AppUser;
import com.example.protaskinxxw.domain.Sprint;
import com.example.protaskinxxw.service.dto.AppUserDTO;
import com.example.protaskinxxw.service.dto.SprintDTO;
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
