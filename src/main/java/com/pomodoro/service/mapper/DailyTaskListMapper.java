package com.pomodoro.service.mapper;

import com.pomodoro.domain.*;
import com.pomodoro.service.dto.DailyTaskListDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity DailyTaskList and its DTO DailyTaskListDTO.
 */
@Mapper(componentModel = "spring", uses = {TaskMapper.class})
public interface DailyTaskListMapper extends EntityMapper<DailyTaskListDTO, DailyTaskList> {



    default DailyTaskList fromId(Long id) {
        if (id == null) {
            return null;
        }
        DailyTaskList dailyTaskList = new DailyTaskList();
        dailyTaskList.setId(id);
        return dailyTaskList;
    }
}
