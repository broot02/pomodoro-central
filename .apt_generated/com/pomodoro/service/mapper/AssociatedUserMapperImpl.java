package com.pomodoro.service.mapper;

import com.pomodoro.domain.AssociatedUser;
import com.pomodoro.domain.User;
import com.pomodoro.service.dto.AssociatedUserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-10T21:33:18-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class AssociatedUserMapperImpl implements AssociatedUserMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<AssociatedUser> toEntity(List<AssociatedUserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<AssociatedUser> list = new ArrayList<AssociatedUser>( dtoList.size() );
        for ( AssociatedUserDTO associatedUserDTO : dtoList ) {
            list.add( toEntity( associatedUserDTO ) );
        }

        return list;
    }

    @Override
    public List<AssociatedUserDTO> toDto(List<AssociatedUser> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AssociatedUserDTO> list = new ArrayList<AssociatedUserDTO>( entityList.size() );
        for ( AssociatedUser associatedUser : entityList ) {
            list.add( toDto( associatedUser ) );
        }

        return list;
    }

    @Override
    public AssociatedUserDTO toDto(AssociatedUser associatedUser) {
        if ( associatedUser == null ) {
            return null;
        }

        AssociatedUserDTO associatedUserDTO = new AssociatedUserDTO();

        String login = associatedUserUserLogin( associatedUser );
        if ( login != null ) {
            associatedUserDTO.setUserLogin( login );
        }
        Long id = associatedUserUserId( associatedUser );
        if ( id != null ) {
            associatedUserDTO.setUserId( id );
        }
        Long id1 = associatedUserAssociatedUserId( associatedUser );
        if ( id1 != null ) {
            associatedUserDTO.setAssociatedUserId( id1 );
        }
        String login1 = associatedUserAssociatedUserLogin( associatedUser );
        if ( login1 != null ) {
            associatedUserDTO.setAssociatedUserLogin( login1 );
        }
        associatedUserDTO.setId( associatedUser.getId() );

        return associatedUserDTO;
    }

    @Override
    public AssociatedUser toEntity(AssociatedUserDTO associatedUserDTO) {
        if ( associatedUserDTO == null ) {
            return null;
        }

        AssociatedUser associatedUser = new AssociatedUser();

        associatedUser.setUser( userMapper.userFromId( associatedUserDTO.getUserId() ) );
        associatedUser.setAssociatedUser( userMapper.userFromId( associatedUserDTO.getAssociatedUserId() ) );
        associatedUser.setId( associatedUserDTO.getId() );

        return associatedUser;
    }

    private String associatedUserUserLogin(AssociatedUser associatedUser) {
        if ( associatedUser == null ) {
            return null;
        }
        User user = associatedUser.getUser();
        if ( user == null ) {
            return null;
        }
        String login = user.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }

    private Long associatedUserUserId(AssociatedUser associatedUser) {
        if ( associatedUser == null ) {
            return null;
        }
        User user = associatedUser.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long associatedUserAssociatedUserId(AssociatedUser associatedUser) {
        if ( associatedUser == null ) {
            return null;
        }
        User associatedUser1 = associatedUser.getAssociatedUser();
        if ( associatedUser1 == null ) {
            return null;
        }
        Long id = associatedUser1.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String associatedUserAssociatedUserLogin(AssociatedUser associatedUser) {
        if ( associatedUser == null ) {
            return null;
        }
        User associatedUser1 = associatedUser.getAssociatedUser();
        if ( associatedUser1 == null ) {
            return null;
        }
        String login = associatedUser1.getLogin();
        if ( login == null ) {
            return null;
        }
        return login;
    }
}
