package com.pomodoro.service.mapper;

import com.pomodoro.domain.Task;
import com.pomodoro.domain.User;
import com.pomodoro.service.dto.TaskDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-08T16:19:47-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<TaskDTO> toDto(List<Task> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<TaskDTO> list = new ArrayList<TaskDTO>( arg0.size() );
        for ( Task task : arg0 ) {
            list.add( toDto( task ) );
        }

        return list;
    }

    @Override
    public List<Task> toEntity(List<TaskDTO> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Task> list = new ArrayList<Task>( arg0.size() );
        for ( TaskDTO taskDTO : arg0 ) {
            list.add( toEntity( taskDTO ) );
        }

        return list;
    }

    @Override
    public TaskDTO toDto(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        String login = taskUsernameLogin( task );
        if ( login != null ) {
            taskDTO.setUsernameLogin( login );
        }
        Long id = taskUsernameId( task );
        if ( id != null ) {
            taskDTO.setUsernameId( id );
        }
        taskDTO.setEstimate( task.getEstimate() );
        taskDTO.setId( task.getId() );
        taskDTO.setName( task.getName() );
        taskDTO.setStatus( task.getStatus() );
        taskDTO.setStatusDate( task.getStatusDate() );

        return taskDTO;
    }

    @Override
    public Task toEntity(TaskDTO taskDTO) {
        if ( taskDTO == null ) {
            return null;
        }

        Task task = new Task();

        task.setUsername( userMapper.userFromId( taskDTO.getUsernameId() ) );
        task.setEstimate( taskDTO.getEstimate() );
        task.setId( taskDTO.getId() );
        task.setName( taskDTO.getName() );
        task.setStatus( taskDTO.getStatus() );
        task.setStatusDate( taskDTO.getStatusDate() );

        return task;
    }

    private String taskUsernameLogin(Task task) {
        if ( task == null ) {
            return null;
        }
        User username = task.getUsername();
        if ( username == null ) {
            return null;
        }
        String login = username.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long taskUsernameId(Task task) {
        if ( task == null ) {
            return null;
        }
        User username = task.getUsername();
        if ( username == null ) {
            return null;
        }
        Long id = username.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
