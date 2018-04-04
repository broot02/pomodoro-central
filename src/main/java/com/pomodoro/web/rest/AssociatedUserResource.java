package com.pomodoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pomodoro.service.AssociatedUserService;
import com.pomodoro.web.rest.errors.BadRequestAlertException;
import com.pomodoro.web.rest.util.HeaderUtil;
import com.pomodoro.web.rest.util.PaginationUtil;
import com.pomodoro.service.dto.AssociatedUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing AssociatedUser.
 */
@RestController
@RequestMapping("/api")
public class AssociatedUserResource {

    private final Logger log = LoggerFactory.getLogger(AssociatedUserResource.class);

    private static final String ENTITY_NAME = "associatedUser";

    private final AssociatedUserService associatedUserService;

    public AssociatedUserResource(AssociatedUserService associatedUserService) {
        this.associatedUserService = associatedUserService;
    }

    /**
     * POST  /associated-users : Create a new associatedUser.
     *
     * @param associatedUserDTO the associatedUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new associatedUserDTO, or with status 400 (Bad Request) if the associatedUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/associated-users")
    @Timed
    public ResponseEntity<AssociatedUserDTO> createAssociatedUser(@RequestBody AssociatedUserDTO associatedUserDTO) throws URISyntaxException {
        log.debug("REST request to save AssociatedUser : {}", associatedUserDTO);
        if (associatedUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new associatedUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssociatedUserDTO result = associatedUserService.save(associatedUserDTO);
        return ResponseEntity.created(new URI("/api/associated-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /associated-users : Updates an existing associatedUser.
     *
     * @param associatedUserDTO the associatedUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated associatedUserDTO,
     * or with status 400 (Bad Request) if the associatedUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the associatedUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/associated-users")
    @Timed
    public ResponseEntity<AssociatedUserDTO> updateAssociatedUser(@RequestBody AssociatedUserDTO associatedUserDTO) throws URISyntaxException {
        log.debug("REST request to update AssociatedUser : {}", associatedUserDTO);
        if (associatedUserDTO.getId() == null) {
            return createAssociatedUser(associatedUserDTO);
        }
        AssociatedUserDTO result = associatedUserService.save(associatedUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, associatedUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /associated-users : get all the associatedUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of associatedUsers in body
     */
    @GetMapping("/associated-users")
    @Timed
    public ResponseEntity<List<AssociatedUserDTO>> getAllAssociatedUsers(Pageable pageable) {
        log.debug("REST request to get a page of AssociatedUsers");
        Page<AssociatedUserDTO> page = associatedUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/associated-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /associated-users/:id : get the "id" associatedUser.
     *
     * @param id the id of the associatedUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the associatedUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/associated-users/{id}")
    @Timed
    public ResponseEntity<AssociatedUserDTO> getAssociatedUser(@PathVariable Long id) {
        log.debug("REST request to get AssociatedUser : {}", id);
        AssociatedUserDTO associatedUserDTO = associatedUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(associatedUserDTO));
    }

    /**
     * DELETE  /associated-users/:id : delete the "id" associatedUser.
     *
     * @param id the id of the associatedUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/associated-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteAssociatedUser(@PathVariable Long id) {
        log.debug("REST request to delete AssociatedUser : {}", id);
        associatedUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
