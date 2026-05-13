package com.example.service.mapper;

import com.example.domain.AppUser;
import com.example.domain.Category;
import com.example.domain.Task;
import com.example.service.dto.AppUserDTO;
import com.example.service.dto.CategoryDTO;
import com.example.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryId")
    @Mapping(target = "owner", source = "owner", qualifiedByName = "appUserId")
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "appUserId")
    TaskDTO toDto(Task s);

    @Named("categoryId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CategoryDTO toDtoCategoryId(Category category);

    @Named("appUserId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AppUserDTO toDtoAppUserId(AppUser appUser);
}
