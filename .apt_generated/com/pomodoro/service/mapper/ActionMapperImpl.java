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
    date = "2018-04-21T08:07:42-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class ActionMapperImpl implements ActionMapper {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<ActionDTO> toDto(List<Action> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ActionDTO> list = new ArrayList<ActionDTO>( arg0.size() );
        for ( Action action : arg0 ) {
            list.add( toDto( action ) );
        }

        return list;
    }

    @Override
    public List<Action> toEntity(List<ActionDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Action> list = new ArrayList<Action>( arg0.size() );
        for ( ActionDTO actionDTO : arg0 ) {
            list.add( toEntity( actionDTO ) );
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
        actionDTO.setDuration( action.getDuration() );
        actionDTO.setEndTime( action.getEndTime() );
        actionDTO.setId( action.getId() );
        actionDTO.setStartTime( action.getStartTime() );
        actionDTO.setStatus( action.getStatus() );

        return actionDTO;
    }

    @Override
    public Action toEntity(ActionDTO actionDTO) {
        if ( actionDTO == null ) {
            return null;
        }

        Action action = new Action();

        action.setTask( taskMapper.fromId( actionDTO.getTaskId() ) );
        action.setDuration( actionDTO.getDuration() );
        action.setEndTime( actionDTO.getEndTime() );
        action.setId( actionDTO.getId() );
        action.setStartTime( actionDTO.getStartTime() );
        action.setStatus( actionDTO.getStatus() );

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
