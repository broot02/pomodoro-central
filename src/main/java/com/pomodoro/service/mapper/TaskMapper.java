package com.pomodoro.service.mapper;

import com.pomodoro.domain.*;
import com.pomodoro.service.dto.TaskDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Task and its DTO TaskDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, })
public interface TaskMapper extends EntityMapper <TaskDTO, Task> {

    @Mapping(source = "username.id", target = "usernameId")
    @Mapping(source = "username.login", target = "usernameLogin")
    TaskDTO toDto(Task task); 

    @Mapping(source = "usernameId", target = "username")
    @Mapping(target = "actions", ignore = true)
    Task toEntity(TaskDTO taskDTO); 
    default Task fromId(Long id) {
        if (id == null) {
            return null;
        }
        Task task = new Task();
        task.setId(id);
        return task;
    }
}
