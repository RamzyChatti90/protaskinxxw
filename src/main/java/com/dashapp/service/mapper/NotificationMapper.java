package com.dashapp.service.mapper;

import com.dashapp.domain.AppUser;
import com.dashapp.domain.Notification;
import com.dashapp.service.dto.AppUserDTO;
import com.dashapp.service.dto.NotificationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Notification} and its DTO {@link NotificationDTO}.
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "user", source = "user", qualifiedByName = "appUserId")
    NotificationDTO toDto(Notification s);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
