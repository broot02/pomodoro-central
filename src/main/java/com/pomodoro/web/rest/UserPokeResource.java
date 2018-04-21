package com.pomodoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pomodoro.service.UserPokeService;
import com.pomodoro.web.rest.errors.BadRequestAlertException;
import com.pomodoro.web.rest.util.HeaderUtil;
import com.pomodoro.web.rest.util.PaginationUtil;
import com.pomodoro.service.dto.UserPokeDTO;
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
 * REST controller for managing UserPoke.
 */
@RestController
@RequestMapping("/api")
public class UserPokeResource {

    private final Logger log = LoggerFactory.getLogger(UserPokeResource.class);

    private static final String ENTITY_NAME = "userPoke";

    private final UserPokeService userPokeService;

    public UserPokeResource(UserPokeService userPokeService) {
        this.userPokeService = userPokeService;
    }

    /**
     * POST  /user-pokes : Create a new userPoke.
     *
     * @param userPokeDTO the userPokeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userPokeDTO, or with status 400 (Bad Request) if the userPoke has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-pokes")
    @Timed
    public ResponseEntity<UserPokeDTO> createUserPoke(@Valid @RequestBody UserPokeDTO userPokeDTO) throws URISyntaxException {
        log.debug("REST request to save UserPoke : {}", userPokeDTO);
        if (userPokeDTO.getId() != null) {
            throw new BadRequestAlertException("A new userPoke cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserPokeDTO result = userPokeService.save(userPokeDTO);
        return ResponseEntity.created(new URI("/api/user-pokes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-pokes : Updates an existing userPoke.
     *
     * @param userPokeDTO the userPokeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userPokeDTO,
     * or with status 400 (Bad Request) if the userPokeDTO is not valid,
     * or with status 500 (Internal Server Error) if the userPokeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-pokes")
    @Timed
    public ResponseEntity<UserPokeDTO> updateUserPoke(@Valid @RequestBody UserPokeDTO userPokeDTO) throws URISyntaxException {
        log.debug("REST request to update UserPoke : {}", userPokeDTO);
        if (userPokeDTO.getId() == null) {
            return createUserPoke(userPokeDTO);
        }
        UserPokeDTO result = userPokeService.save(userPokeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userPokeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-pokes : get all the userPokes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userPokes in body
     */
    @GetMapping("/user-pokes")
    @Timed
    public ResponseEntity<List<UserPokeDTO>> getAllUserPokes(Pageable pageable) {
        log.debug("REST request to get a page of UserPokes");
        Page<UserPokeDTO> page = userPokeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-pokes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /user-pokes/:id : get the "id" userPoke.
     *
     * @param id the id of the userPokeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userPokeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-pokes/{id}")
    @Timed
    public ResponseEntity<UserPokeDTO> getUserPoke(@PathVariable Long id) {
        log.debug("REST request to get UserPoke : {}", id);
        UserPokeDTO userPokeDTO = userPokeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userPokeDTO));
    }

    /**
     * DELETE  /user-pokes/:id : delete the "id" userPoke.
     *
     * @param id the id of the userPokeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-pokes/{id}")
    @Timed
    public ResponseEntity<Void> deleteUserPoke(@PathVariable Long id) {
        log.debug("REST request to delete UserPoke : {}", id);
        userPokeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
