package com.pomodoro.web.rest;

import com.pomodoro.PomodoroApp;

import com.pomodoro.domain.UserPoke;
import com.pomodoro.repository.UserPokeRepository;
import com.pomodoro.service.UserPokeService;
import com.pomodoro.service.dto.UserPokeDTO;
import com.pomodoro.service.mapper.UserPokeMapper;
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
 * Test class for the UserPokeResource REST controller.
 *
 * @see UserPokeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PomodoroApp.class)
public class UserPokeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UserPokeRepository userPokeRepository;

    @Autowired
    private UserPokeMapper userPokeMapper;

    @Autowired
    private UserPokeService userPokeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserPokeMockMvc;

    private UserPoke userPoke;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserPokeResource userPokeResource = new UserPokeResource(userPokeService);
        this.restUserPokeMockMvc = MockMvcBuilders.standaloneSetup(userPokeResource)
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
    public static UserPoke createEntity(EntityManager em) {
        UserPoke userPoke = new UserPoke()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return userPoke;
    }

    @Before
    public void initTest() {
        userPoke = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserPoke() throws Exception {
        int databaseSizeBeforeCreate = userPokeRepository.findAll().size();

        // Create the UserPoke
        UserPokeDTO userPokeDTO = userPokeMapper.toDto(userPoke);
        restUserPokeMockMvc.perform(post("/api/user-pokes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPoke in the database
        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeCreate + 1);
        UserPoke testUserPoke = userPokeList.get(userPokeList.size() - 1);
        assertThat(testUserPoke.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testUserPoke.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createUserPokeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userPokeRepository.findAll().size();

        // Create the UserPoke with an existing ID
        userPoke.setId(1L);
        UserPokeDTO userPokeDTO = userPokeMapper.toDto(userPoke);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserPokeMockMvc.perform(post("/api/user-pokes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserPoke in the database
        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userPokeRepository.findAll().size();
        // set the field null
        userPoke.setTitle(null);

        // Create the UserPoke, which fails.
        UserPokeDTO userPokeDTO = userPokeMapper.toDto(userPoke);

        restUserPokeMockMvc.perform(post("/api/user-pokes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokeDTO)))
            .andExpect(status().isBadRequest());

        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserPokes() throws Exception {
        // Initialize the database
        userPokeRepository.saveAndFlush(userPoke);

        // Get all the userPokeList
        restUserPokeMockMvc.perform(get("/api/user-pokes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userPoke.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }

    @Test
    @Transactional
    public void getUserPoke() throws Exception {
        // Initialize the database
        userPokeRepository.saveAndFlush(userPoke);

        // Get the userPoke
        restUserPokeMockMvc.perform(get("/api/user-pokes/{id}", userPoke.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userPoke.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserPoke() throws Exception {
        // Get the userPoke
        restUserPokeMockMvc.perform(get("/api/user-pokes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserPoke() throws Exception {
        // Initialize the database
        userPokeRepository.saveAndFlush(userPoke);
        int databaseSizeBeforeUpdate = userPokeRepository.findAll().size();

        // Update the userPoke
        UserPoke updatedUserPoke = userPokeRepository.findOne(userPoke.getId());
        // Disconnect from session so that the updates on updatedUserPoke are not directly saved in db
        em.detach(updatedUserPoke);
        updatedUserPoke
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        UserPokeDTO userPokeDTO = userPokeMapper.toDto(updatedUserPoke);

        restUserPokeMockMvc.perform(put("/api/user-pokes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokeDTO)))
            .andExpect(status().isOk());

        // Validate the UserPoke in the database
        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeUpdate);
        UserPoke testUserPoke = userPokeList.get(userPokeList.size() - 1);
        assertThat(testUserPoke.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testUserPoke.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserPoke() throws Exception {
        int databaseSizeBeforeUpdate = userPokeRepository.findAll().size();

        // Create the UserPoke
        UserPokeDTO userPokeDTO = userPokeMapper.toDto(userPoke);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserPokeMockMvc.perform(put("/api/user-pokes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userPokeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserPoke in the database
        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserPoke() throws Exception {
        // Initialize the database
        userPokeRepository.saveAndFlush(userPoke);
        int databaseSizeBeforeDelete = userPokeRepository.findAll().size();

        // Get the userPoke
        restUserPokeMockMvc.perform(delete("/api/user-pokes/{id}", userPoke.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserPoke> userPokeList = userPokeRepository.findAll();
        assertThat(userPokeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPoke.class);
        UserPoke userPoke1 = new UserPoke();
        userPoke1.setId(1L);
        UserPoke userPoke2 = new UserPoke();
        userPoke2.setId(userPoke1.getId());
        assertThat(userPoke1).isEqualTo(userPoke2);
        userPoke2.setId(2L);
        assertThat(userPoke1).isNotEqualTo(userPoke2);
        userPoke1.setId(null);
        assertThat(userPoke1).isNotEqualTo(userPoke2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserPokeDTO.class);
        UserPokeDTO userPokeDTO1 = new UserPokeDTO();
        userPokeDTO1.setId(1L);
        UserPokeDTO userPokeDTO2 = new UserPokeDTO();
        assertThat(userPokeDTO1).isNotEqualTo(userPokeDTO2);
        userPokeDTO2.setId(userPokeDTO1.getId());
        assertThat(userPokeDTO1).isEqualTo(userPokeDTO2);
        userPokeDTO2.setId(2L);
        assertThat(userPokeDTO1).isNotEqualTo(userPokeDTO2);
        userPokeDTO1.setId(null);
        assertThat(userPokeDTO1).isNotEqualTo(userPokeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userPokeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userPokeMapper.fromId(null)).isNull();
    }
}
