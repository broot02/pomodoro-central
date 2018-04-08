package com.pomodoro.service.mapper;

import com.pomodoro.domain.*;
import com.pomodoro.service.dto.ActionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Action and its DTO ActionDTO.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface ActionMapper extends EntityMapper<ActionDTO, Action> {

    @Mapping(source = "task.id", target = "taskId")
    ActionDTO toDto(Action action);

    @Mapping(source = "taskId", target = "task")
    Action toEntity(ActionDTO actionDTO);

    default Action fromId(Long id) {
        if (id == null) {
            return null;
        }
        Action action = new Action();
        action.setId(id);
        return action;
    }
}
