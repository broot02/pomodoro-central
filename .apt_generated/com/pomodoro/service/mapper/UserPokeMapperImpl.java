package com.pomodoro.service.mapper;

import com.pomodoro.domain.AssociatedUser;
import com.pomodoro.domain.UserPoke;
import com.pomodoro.service.dto.UserPokeDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2018-04-21T12:38:27-0400",
    comments = "version: 1.2.0.Final, compiler: Eclipse JDT (IDE) 3.12.3.v20170228-1205, environment: Java 1.8.0_92 (Oracle Corporation)"
)
@Component
public class UserPokeMapperImpl implements UserPokeMapper {

    @Autowired
    private AssociatedUserMapper associatedUserMapper;

    @Override
    public List<UserPoke> toEntity(List<UserPokeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<UserPoke> list = new ArrayList<UserPoke>( dtoList.size() );
        for ( UserPokeDTO userPokeDTO : dtoList ) {
            list.add( toEntity( userPokeDTO ) );
        }

        return list;
    }

    @Override
    public List<UserPokeDTO> toDto(List<UserPoke> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserPokeDTO> list = new ArrayList<UserPokeDTO>( entityList.size() );
        for ( UserPoke userPoke : entityList ) {
            list.add( toDto( userPoke ) );
        }

        return list;
    }

    @Override
    public UserPokeDTO toDto(UserPoke userPoke) {
        if ( userPoke == null ) {
            return null;
        }

        UserPokeDTO userPokeDTO = new UserPokeDTO();

        Long id = userPokeAssociatedUserId( userPoke );
        if ( id != null ) {
            userPokeDTO.setAssociatedUserId( id );
        }
        userPokeDTO.setId( userPoke.getId() );
        userPokeDTO.setTitle( userPoke.getTitle() );
        userPokeDTO.setDescription( userPoke.getDescription() );

        return userPokeDTO;
    }

    @Override
    public UserPoke toEntity(UserPokeDTO userPokeDTO) {
        if ( userPokeDTO == null ) {
            return null;
        }

        UserPoke userPoke = new UserPoke();

        userPoke.setAssociatedUser( associatedUserMapper.fromId( userPokeDTO.getAssociatedUserId() ) );
        userPoke.setId( userPokeDTO.getId() );
        userPoke.setTitle( userPokeDTO.getTitle() );
        userPoke.setDescription( userPokeDTO.getDescription() );

        return userPoke;
    }

    private Long userPokeAssociatedUserId(UserPoke userPoke) {
        if ( userPoke == null ) {
            return null;
        }
        AssociatedUser associatedUser = userPoke.getAssociatedUser();
        if ( associatedUser == null ) {
            return null;
        }
        Long id = associatedUser.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
