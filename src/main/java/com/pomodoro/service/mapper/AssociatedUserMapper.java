package com.pomodoro.service.mapper;

import com.pomodoro.domain.*;
import com.pomodoro.service.dto.AssociatedUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AssociatedUser and its DTO AssociatedUserDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AssociatedUserMapper extends EntityMapper<AssociatedUserDTO, AssociatedUser> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "associatedUser.id", target = "associatedUserId")
    @Mapping(source = "associatedUser.login", target = "associatedUserLogin")
    AssociatedUserDTO toDto(AssociatedUser associatedUser);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "associatedUserId", target = "associatedUser")
    AssociatedUser toEntity(AssociatedUserDTO associatedUserDTO);

    default AssociatedUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssociatedUser associatedUser = new AssociatedUser();
        associatedUser.setId(id);
        return associatedUser;
    }
}
