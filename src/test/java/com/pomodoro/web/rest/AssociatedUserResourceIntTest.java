package com.pomodoro.web.rest;

import com.pomodoro.PomodoroApp;

import com.pomodoro.domain.AssociatedUser;
import com.pomodoro.repository.AssociatedUserRepository;
import com.pomodoro.service.AssociatedUserService;
import com.pomodoro.service.dto.AssociatedUserDTO;
import com.pomodoro.service.mapper.AssociatedUserMapper;
import com.pomodoro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.pomodoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssociatedUserResource REST controller.
 *
 * @see AssociatedUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PomodoroApp.class)
public class AssociatedUserResourceIntTest {

    @Autowired
    private AssociatedUserRepository associatedUserRepository;

    @Autowired
    private AssociatedUserMapper associatedUserMapper;

    @Autowired
    private AssociatedUserService associatedUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAssociatedUserMockMvc;

    private AssociatedUser associatedUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssociatedUserResource associatedUserResource = new AssociatedUserResource(associatedUserService);
        this.restAssociatedUserMockMvc = MockMvcBuilders.standaloneSetup(associatedUserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssociatedUser createEntity(EntityManager em) {
        AssociatedUser associatedUser = new AssociatedUser();
        return associatedUser;
    }

    @Before
    public void initTest() {
        associatedUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssociatedUser() throws Exception {
        int databaseSizeBeforeCreate = associatedUserRepository.findAll().size();

        // Create the AssociatedUser
        AssociatedUserDTO associatedUserDTO = associatedUserMapper.toDto(associatedUser);
        restAssociatedUserMockMvc.perform(post("/api/associated-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AssociatedUser in the database
        List<AssociatedUser> associatedUserList = associatedUserRepository.findAll();
        assertThat(associatedUserList).hasSize(databaseSizeBeforeCreate + 1);
        AssociatedUser testAssociatedUser = associatedUserList.get(associatedUserList.size() - 1);
    }

    @Test
    @Transactional
    public void createAssociatedUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = associatedUserRepository.findAll().size();

        // Create the AssociatedUser with an existing ID
        associatedUser.setId(1L);
        AssociatedUserDTO associatedUserDTO = associatedUserMapper.toDto(associatedUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssociatedUserMockMvc.perform(post("/api/associated-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssociatedUser in the database
        List<AssociatedUser> associatedUserList = associatedUserRepository.findAll();
        assertThat(associatedUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssociatedUsers() throws Exception {
        // Initialize the database
        associatedUserRepository.saveAndFlush(associatedUser);

        // Get all the associatedUserList
        restAssociatedUserMockMvc.perform(get("/api/associated-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(associatedUser.getId().intValue())));
    }

    @Test
    @Transactional
    public void getAssociatedUser() throws Exception {
        // Initialize the database
        associatedUserRepository.saveAndFlush(associatedUser);

        // Get the associatedUser
        restAssociatedUserMockMvc.perform(get("/api/associated-users/{id}", associatedUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(associatedUser.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAssociatedUser() throws Exception {
        // Get the associatedUser
        restAssociatedUserMockMvc.perform(get("/api/associated-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssociatedUser() throws Exception {
        // Initialize the database
        associatedUserRepository.saveAndFlush(associatedUser);
        int databaseSizeBeforeUpdate = associatedUserRepository.findAll().size();

        // Update the associatedUser
        AssociatedUser updatedAssociatedUser = associatedUserRepository.findOne(associatedUser.getId());
        // Disconnect from session so that the updates on updatedAssociatedUser are not directly saved in db
        em.detach(updatedAssociatedUser);
        AssociatedUserDTO associatedUserDTO = associatedUserMapper.toDto(updatedAssociatedUser);

        restAssociatedUserMockMvc.perform(put("/api/associated-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedUserDTO)))
            .andExpect(status().isOk());

        // Validate the AssociatedUser in the database
        List<AssociatedUser> associatedUserList = associatedUserRepository.findAll();
        assertThat(associatedUserList).hasSize(databaseSizeBeforeUpdate);
        AssociatedUser testAssociatedUser = associatedUserList.get(associatedUserList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAssociatedUser() throws Exception {
        int databaseSizeBeforeUpdate = associatedUserRepository.findAll().size();

        // Create the AssociatedUser
        AssociatedUserDTO associatedUserDTO = associatedUserMapper.toDto(associatedUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAssociatedUserMockMvc.perform(put("/api/associated-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(associatedUserDTO)))
            .andExpect(status().isCreated());

        // Validate the AssociatedUser in the database
        List<AssociatedUser> associatedUserList = associatedUserRepository.findAll();
        assertThat(associatedUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAssociatedUser() throws Exception {
        // Initialize the database
        associatedUserRepository.saveAndFlush(associatedUser);
        int databaseSizeBeforeDelete = associatedUserRepository.findAll().size();

        // Get the associatedUser
        restAssociatedUserMockMvc.perform(delete("/api/associated-users/{id}", associatedUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AssociatedUser> associatedUserList = associatedUserRepository.findAll();
        assertThat(associatedUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedUser.class);
        AssociatedUser associatedUser1 = new AssociatedUser();
        associatedUser1.setId(1L);
        AssociatedUser associatedUser2 = new AssociatedUser();
        associatedUser2.setId(associatedUser1.getId());
        assertThat(associatedUser1).isEqualTo(associatedUser2);
        associatedUser2.setId(2L);
        assertThat(associatedUser1).isNotEqualTo(associatedUser2);
        associatedUser1.setId(null);
        assertThat(associatedUser1).isNotEqualTo(associatedUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssociatedUserDTO.class);
        AssociatedUserDTO associatedUserDTO1 = new AssociatedUserDTO();
        associatedUserDTO1.setId(1L);
        AssociatedUserDTO associatedUserDTO2 = new AssociatedUserDTO();
        assertThat(associatedUserDTO1).isNotEqualTo(associatedUserDTO2);
        associatedUserDTO2.setId(associatedUserDTO1.getId());
        assertThat(associatedUserDTO1).isEqualTo(associatedUserDTO2);
        associatedUserDTO2.setId(2L);
        assertThat(associatedUserDTO1).isNotEqualTo(associatedUserDTO2);
        associatedUserDTO1.setId(null);
        assertThat(associatedUserDTO1).isNotEqualTo(associatedUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(associatedUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(associatedUserMapper.fromId(null)).isNull();
    }
}
