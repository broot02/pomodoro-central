package com.pomodoro.service.mapper;

import com.pomodoro.domain.Action;
import com.pomodoro.domain.Task;
import com.pomodoro.service.dto.ActionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-21T07:07:23-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class ActionMapperImpl implements ActionMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Action> toEntity(List<ActionDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Action> list = new ArrayList<Action>( dtoList.size() );
        for ( ActionDTO actionDTO : dtoList ) {
            list.add( toEntity( actionDTO ) );
        }

        return list;
    }

    @Override
    public List<ActionDTO> toDto(List<Action> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ActionDTO> list = new ArrayList<ActionDTO>( entityList.size() );
        for ( Action action : entityList ) {
            list.add( toDto( action ) );
        }

        return list;
    }

    @Override
    public ActionDTO toDto(Action action) {
        if ( action == null ) {
            return null;
        }

        ActionDTO actionDTO = new ActionDTO();

        Long id = actionTaskId( action );
        if ( id != null ) {
            actionDTO.setTaskId( id );
        }
        actionDTO.setId( action.getId() );
        actionDTO.setStatus( action.getStatus() );
        actionDTO.setDuration( action.getDuration() );
        actionDTO.setStartTime( action.getStartTime() );
        actionDTO.setEndTime( action.getEndTime() );

        return actionDTO;
    }

    @Override
    public Action toEntity(ActionDTO actionDTO) {
        if ( actionDTO == null ) {
            return null;
        }

        Action action = new Action();

        action.setTask( taskMapper.fromId( actionDTO.getTaskId() ) );
        action.setId( actionDTO.getId() );
        action.setStatus( actionDTO.getStatus() );
        action.setDuration( actionDTO.getDuration() );
        action.setStartTime( actionDTO.getStartTime() );
        action.setEndTime( actionDTO.getEndTime() );

        return action;
    }

    private Long actionTaskId(Action action) {
        if ( action == null ) {
            return null;
        }
        Task task = action.getTask();
        if ( task == null ) {
            return null;
        }
        Long id = task.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
