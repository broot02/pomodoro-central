package com.pomodoro.service;

import com.pomodoro.domain.UserPoke;
import com.pomodoro.repository.UserPokeRepository;
import com.pomodoro.service.dto.UserPokeDTO;
import com.pomodoro.service.mapper.UserPokeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserPoke.
 */
@Service
@Transactional
public class UserPokeService {

    private final Logger log = LoggerFactory.getLogger(UserPokeService.class);

    private final UserPokeRepository userPokeRepository;

    private final UserPokeMapper userPokeMapper;

    public UserPokeService(UserPokeRepository userPokeRepository, UserPokeMapper userPokeMapper) {
        this.userPokeRepository = userPokeRepository;
        this.userPokeMapper = userPokeMapper;
    }

    /**
     * Save a userPoke.
     *
     * @param userPokeDTO the entity to save
     * @return the persisted entity
     */
    public UserPokeDTO save(UserPokeDTO userPokeDTO) {
        log.debug("Request to save UserPoke : {}", userPokeDTO);
        UserPoke userPoke = userPokeMapper.toEntity(userPokeDTO);
        userPoke = userPokeRepository.save(userPoke);
        return userPokeMapper.toDto(userPoke);
    }

    /**
     * Get all the userPokes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<UserPokeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserPokes");
        return userPokeRepository.findAll(pageable)
            .map(userPokeMapper::toDto);
    }

    /**
     * Get one userPoke by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public UserPokeDTO findOne(Long id) {
        log.debug("Request to get UserPoke : {}", id);
        UserPoke userPoke = userPokeRepository.findOne(id);
        return userPokeMapper.toDto(userPoke);
    }

    /**
     * Delete the userPoke by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete UserPoke : {}", id);
        userPokeRepository.delete(id);
    }
}
