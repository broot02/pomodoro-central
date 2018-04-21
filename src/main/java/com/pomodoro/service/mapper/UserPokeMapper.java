package com.pomodoro.service.mapper;

import com.pomodoro.domain.*;
import com.pomodoro.service.dto.UserPokeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserPoke and its DTO UserPokeDTO.
 */
@Mapper(componentModel = "spring", uses = {AssociatedUserMapper.class})
public interface UserPokeMapper extends EntityMapper<UserPokeDTO, UserPoke> {

    @Mapping(source = "associatedUser.id", target = "associatedUserId")
    UserPokeDTO toDto(UserPoke userPoke);

    @Mapping(source = "associatedUserId", target = "associatedUser")
    UserPoke toEntity(UserPokeDTO userPokeDTO);

    default UserPoke fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserPoke userPoke = new UserPoke();
        userPoke.setId(id);
        return userPoke;
    }
}
