package com.pomodoro.service.mapper;

import com.pomodoro.domain.DailyTaskList;
import com.pomodoro.domain.Task;
import com.pomodoro.service.dto.DailyTaskListDTO;
import com.pomodoro.service.dto.TaskDTO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-10T21:33:18-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class DailyTaskListMapperImpl implements DailyTaskListMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public DailyTaskList toEntity(DailyTaskListDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DailyTaskList dailyTaskList = new DailyTaskList();

        dailyTaskList.setId( dto.getId() );
        dailyTaskList.setTaskDate( dto.getTaskDate() );
        dailyTaskList.setTasks( taskDTOSetToTaskSet( dto.getTasks() ) );

        return dailyTaskList;
    }

    @Override
    public DailyTaskListDTO toDto(DailyTaskList entity) {
        if ( entity == null ) {
            return null;
        }

        DailyTaskListDTO dailyTaskListDTO = new DailyTaskListDTO();

        dailyTaskListDTO.setId( entity.getId() );
        dailyTaskListDTO.setTaskDate( entity.getTaskDate() );
        dailyTaskListDTO.setTasks( taskSetToTaskDTOSet( entity.getTasks() ) );

        return dailyTaskListDTO;
    }

    @Override
    public List<DailyTaskList> toEntity(List<DailyTaskListDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<DailyTaskList> list = new ArrayList<DailyTaskList>( dtoList.size() );
        for ( DailyTaskListDTO dailyTaskListDTO : dtoList ) {
            list.add( toEntity( dailyTaskListDTO ) );
        }

        return list;
    }

    @Override
    public List<DailyTaskListDTO> toDto(List<DailyTaskList> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<DailyTaskListDTO> list = new ArrayList<DailyTaskListDTO>( entityList.size() );
        for ( DailyTaskList dailyTaskList : entityList ) {
            list.add( toDto( dailyTaskList ) );
        }

        return list;
    }

    protected Set<Task> taskDTOSetToTaskSet(Set<TaskDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<Task> set1 = new HashSet<Task>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( TaskDTO taskDTO : set ) {
            set1.add( taskMapper.toEntity( taskDTO ) );
        }

        return set1;
    }

    protected Set<TaskDTO> taskSetToTaskDTOSet(Set<Task> set) {
        if ( set == null ) {
            return null;
        }

        Set<TaskDTO> set1 = new HashSet<TaskDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Task task : set ) {
            set1.add( taskMapper.toDto( task ) );
        }

        return set1;
    }
}
