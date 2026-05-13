package com.example.protaskinxxw.service.mapper;

import com.example.protaskinxxw.domain.AppUser;
import com.example.protaskinxxw.domain.Notification;
import com.example.protaskinxxw.service.dto.AppUserDTO;
import com.example.protaskinxxw.service.dto.NotificationDTO;
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
