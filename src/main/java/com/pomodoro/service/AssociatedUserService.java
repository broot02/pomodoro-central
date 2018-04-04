package com.pomodoro.service;

import com.pomodoro.domain.AssociatedUser;
import com.pomodoro.repository.AssociatedUserRepository;
import com.pomodoro.service.dto.AssociatedUserDTO;
import com.pomodoro.service.mapper.AssociatedUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing AssociatedUser.
 */
@Service
@Transactional
public class AssociatedUserService {

    private final Logger log = LoggerFactory.getLogger(AssociatedUserService.class);

    private final AssociatedUserRepository associatedUserRepository;

    private final AssociatedUserMapper associatedUserMapper;

    public AssociatedUserService(AssociatedUserRepository associatedUserRepository, AssociatedUserMapper associatedUserMapper) {
        this.associatedUserRepository = associatedUserRepository;
        this.associatedUserMapper = associatedUserMapper;
    }

    /**
     * Save a associatedUser.
     *
     * @param associatedUserDTO the entity to save
     * @return the persisted entity
     */
    public AssociatedUserDTO save(AssociatedUserDTO associatedUserDTO) {
        log.debug("Request to save AssociatedUser : {}", associatedUserDTO);
        AssociatedUser associatedUser = associatedUserMapper.toEntity(associatedUserDTO);
        associatedUser = associatedUserRepository.save(associatedUser);
        return associatedUserMapper.toDto(associatedUser);
    }

    /**
     * Get all the associatedUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<AssociatedUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssociatedUsers");
        return associatedUserRepository.findAll(pageable)
            .map(associatedUserMapper::toDto);
    }

    /**
     * Get one associatedUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public AssociatedUserDTO findOne(Long id) {
        log.debug("Request to get AssociatedUser : {}", id);
        AssociatedUser associatedUser = associatedUserRepository.findOne(id);
        return associatedUserMapper.toDto(associatedUser);
    }

    /**
     * Delete the associatedUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AssociatedUser : {}", id);
        associatedUserRepository.delete(id);
    }
}
