package com.example.service.mapper;

import com.example.domain.AppUser;
import com.example.domain.User;
import com.example.service.dto.AppUserDTO;
import com.example.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "internalUser", source = "internalUser", qualifiedByName = "userId")
    AppUserDTO toDto(AppUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
