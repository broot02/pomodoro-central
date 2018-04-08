package com.pomodoro.service;

import com.pomodoro.domain.DailyTaskList;
import com.pomodoro.repository.DailyTaskListRepository;
import com.pomodoro.service.dto.DailyTaskListDTO;
import com.pomodoro.service.mapper.DailyTaskListMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DailyTaskList.
 */
@Service
@Transactional
public class DailyTaskListService {

    private final Logger log = LoggerFactory.getLogger(DailyTaskListService.class);

    private final DailyTaskListRepository dailyTaskListRepository;

    private final DailyTaskListMapper dailyTaskListMapper;

    public DailyTaskListService(DailyTaskListRepository dailyTaskListRepository, DailyTaskListMapper dailyTaskListMapper) {
        this.dailyTaskListRepository = dailyTaskListRepository;
        this.dailyTaskListMapper = dailyTaskListMapper;
    }

    /**
     * Save a dailyTaskList.
     *
     * @param dailyTaskListDTO the entity to save
     * @return the persisted entity
     */
    public DailyTaskListDTO save(DailyTaskListDTO dailyTaskListDTO) {
        log.debug("Request to save DailyTaskList : {}", dailyTaskListDTO);
        DailyTaskList dailyTaskList = dailyTaskListMapper.toEntity(dailyTaskListDTO);
        dailyTaskList = dailyTaskListRepository.save(dailyTaskList);
        return dailyTaskListMapper.toDto(dailyTaskList);
    }

    /**
     * Get all the dailyTaskLists.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DailyTaskListDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DailyTaskLists");
        return dailyTaskListRepository.findAll(pageable)
            .map(dailyTaskListMapper::toDto);
    }

    /**
     * Get one dailyTaskList by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public DailyTaskListDTO findOne(Long id) {
        log.debug("Request to get DailyTaskList : {}", id);
        DailyTaskList dailyTaskList = dailyTaskListRepository.findOneWithEagerRelationships(id);
        return dailyTaskListMapper.toDto(dailyTaskList);
    }

    /**
     * Delete the dailyTaskList by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete DailyTaskList : {}", id);
        dailyTaskListRepository.delete(id);
    }
}
