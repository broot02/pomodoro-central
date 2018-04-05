package com.pomodoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pomodoro.service.DailyTaskListService;
import com.pomodoro.web.rest.errors.BadRequestAlertException;
import com.pomodoro.web.rest.util.HeaderUtil;
import com.pomodoro.web.rest.util.PaginationUtil;
import com.pomodoro.service.dto.DailyTaskListDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DailyTaskList.
 */
@RestController
@RequestMapping("/api")
public class DailyTaskListResource {

    private final Logger log = LoggerFactory.getLogger(DailyTaskListResource.class);

    private static final String ENTITY_NAME = "dailyTaskList";

    private final DailyTaskListService dailyTaskListService;

    public DailyTaskListResource(DailyTaskListService dailyTaskListService) {
        this.dailyTaskListService = dailyTaskListService;
    }

    /**
     * POST  /daily-task-lists : Create a new dailyTaskList.
     *
     * @param dailyTaskListDTO the dailyTaskListDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dailyTaskListDTO, or with status 400 (Bad Request) if the dailyTaskList has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/daily-task-lists")
    @Timed
    public ResponseEntity<DailyTaskListDTO> createDailyTaskList(@Valid @RequestBody DailyTaskListDTO dailyTaskListDTO) throws URISyntaxException {
        log.debug("REST request to save DailyTaskList : {}", dailyTaskListDTO);
        if (dailyTaskListDTO.getId() != null) {
            throw new BadRequestAlertException("A new dailyTaskList cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DailyTaskListDTO result = dailyTaskListService.save(dailyTaskListDTO);
        return ResponseEntity.created(new URI("/api/daily-task-lists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /daily-task-lists : Updates an existing dailyTaskList.
     *
     * @param dailyTaskListDTO the dailyTaskListDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dailyTaskListDTO,
     * or with status 400 (Bad Request) if the dailyTaskListDTO is not valid,
     * or with status 500 (Internal Server Error) if the dailyTaskListDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/daily-task-lists")
    @Timed
    public ResponseEntity<DailyTaskListDTO> updateDailyTaskList(@Valid @RequestBody DailyTaskListDTO dailyTaskListDTO) throws URISyntaxException {
        log.debug("REST request to update DailyTaskList : {}", dailyTaskListDTO);
        if (dailyTaskListDTO.getId() == null) {
            return createDailyTaskList(dailyTaskListDTO);
        }
        DailyTaskListDTO result = dailyTaskListService.save(dailyTaskListDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dailyTaskListDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /daily-task-lists : get all the dailyTaskLists.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dailyTaskLists in body
     */
    @GetMapping("/daily-task-lists")
    @Timed
    public ResponseEntity<List<DailyTaskListDTO>> getAllDailyTaskLists(Pageable pageable) {
        log.debug("REST request to get a page of DailyTaskLists");
        Page<DailyTaskListDTO> page = dailyTaskListService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/daily-task-lists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /daily-task-lists/:id : get the "id" dailyTaskList.
     *
     * @param id the id of the dailyTaskListDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dailyTaskListDTO, or with status 404 (Not Found)
     */
    @GetMapping("/daily-task-lists/{id}")
    @Timed
    public ResponseEntity<DailyTaskListDTO> getDailyTaskList(@PathVariable Long id) {
        log.debug("REST request to get DailyTaskList : {}", id);
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dailyTaskListDTO));
    }

    /**
     * DELETE  /daily-task-lists/:id : delete the "id" dailyTaskList.
     *
     * @param id the id of the dailyTaskListDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/daily-task-lists/{id}")
    @Timed
    public ResponseEntity<Void> deleteDailyTaskList(@PathVariable Long id) {
        log.debug("REST request to delete DailyTaskList : {}", id);
        dailyTaskListService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
