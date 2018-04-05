package com.pomodoro.web.rest;

import com.pomodoro.PomodoroApp;

import com.pomodoro.domain.DailyTaskList;
import com.pomodoro.repository.DailyTaskListRepository;
import com.pomodoro.service.DailyTaskListService;
import com.pomodoro.service.dto.DailyTaskListDTO;
import com.pomodoro.service.mapper.DailyTaskListMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.pomodoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DailyTaskListResource REST controller.
 *
 * @see DailyTaskListResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PomodoroApp.class)
public class DailyTaskListResourceIntTest {

    private static final LocalDate DEFAULT_TASK_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TASK_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private DailyTaskListRepository dailyTaskListRepository;

    @Autowired
    private DailyTaskListMapper dailyTaskListMapper;

    @Autowired
    private DailyTaskListService dailyTaskListService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDailyTaskListMockMvc;

    private DailyTaskList dailyTaskList;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DailyTaskListResource dailyTaskListResource = new DailyTaskListResource(dailyTaskListService);
        this.restDailyTaskListMockMvc = MockMvcBuilders.standaloneSetup(dailyTaskListResource)
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
    public static DailyTaskList createEntity(EntityManager em) {
        DailyTaskList dailyTaskList = new DailyTaskList()
            .taskDate(DEFAULT_TASK_DATE);
        return dailyTaskList;
    }

    @Before
    public void initTest() {
        dailyTaskList = createEntity(em);
    }

    @Test
    @Transactional
    public void createDailyTaskList() throws Exception {
        int databaseSizeBeforeCreate = dailyTaskListRepository.findAll().size();

        // Create the DailyTaskList
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListMapper.toDto(dailyTaskList);
        restDailyTaskListMockMvc.perform(post("/api/daily-task-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyTaskListDTO)))
            .andExpect(status().isCreated());

        // Validate the DailyTaskList in the database
        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeCreate + 1);
        DailyTaskList testDailyTaskList = dailyTaskListList.get(dailyTaskListList.size() - 1);
        assertThat(testDailyTaskList.getTaskDate()).isEqualTo(DEFAULT_TASK_DATE);
    }

    @Test
    @Transactional
    public void createDailyTaskListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dailyTaskListRepository.findAll().size();

        // Create the DailyTaskList with an existing ID
        dailyTaskList.setId(1L);
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListMapper.toDto(dailyTaskList);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDailyTaskListMockMvc.perform(post("/api/daily-task-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyTaskListDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DailyTaskList in the database
        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTaskDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dailyTaskListRepository.findAll().size();
        // set the field null
        dailyTaskList.setTaskDate(null);

        // Create the DailyTaskList, which fails.
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListMapper.toDto(dailyTaskList);

        restDailyTaskListMockMvc.perform(post("/api/daily-task-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyTaskListDTO)))
            .andExpect(status().isBadRequest());

        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDailyTaskLists() throws Exception {
        // Initialize the database
        dailyTaskListRepository.saveAndFlush(dailyTaskList);

        // Get all the dailyTaskListList
        restDailyTaskListMockMvc.perform(get("/api/daily-task-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dailyTaskList.getId().intValue())))
            .andExpect(jsonPath("$.[*].taskDate").value(hasItem(DEFAULT_TASK_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDailyTaskList() throws Exception {
        // Initialize the database
        dailyTaskListRepository.saveAndFlush(dailyTaskList);

        // Get the dailyTaskList
        restDailyTaskListMockMvc.perform(get("/api/daily-task-lists/{id}", dailyTaskList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dailyTaskList.getId().intValue()))
            .andExpect(jsonPath("$.taskDate").value(DEFAULT_TASK_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDailyTaskList() throws Exception {
        // Get the dailyTaskList
        restDailyTaskListMockMvc.perform(get("/api/daily-task-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDailyTaskList() throws Exception {
        // Initialize the database
        dailyTaskListRepository.saveAndFlush(dailyTaskList);
        int databaseSizeBeforeUpdate = dailyTaskListRepository.findAll().size();

        // Update the dailyTaskList
        DailyTaskList updatedDailyTaskList = dailyTaskListRepository.findOne(dailyTaskList.getId());
        // Disconnect from session so that the updates on updatedDailyTaskList are not directly saved in db
        em.detach(updatedDailyTaskList);
        updatedDailyTaskList
            .taskDate(UPDATED_TASK_DATE);
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListMapper.toDto(updatedDailyTaskList);

        restDailyTaskListMockMvc.perform(put("/api/daily-task-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyTaskListDTO)))
            .andExpect(status().isOk());

        // Validate the DailyTaskList in the database
        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeUpdate);
        DailyTaskList testDailyTaskList = dailyTaskListList.get(dailyTaskListList.size() - 1);
        assertThat(testDailyTaskList.getTaskDate()).isEqualTo(UPDATED_TASK_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDailyTaskList() throws Exception {
        int databaseSizeBeforeUpdate = dailyTaskListRepository.findAll().size();

        // Create the DailyTaskList
        DailyTaskListDTO dailyTaskListDTO = dailyTaskListMapper.toDto(dailyTaskList);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDailyTaskListMockMvc.perform(put("/api/daily-task-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dailyTaskListDTO)))
            .andExpect(status().isCreated());

        // Validate the DailyTaskList in the database
        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDailyTaskList() throws Exception {
        // Initialize the database
        dailyTaskListRepository.saveAndFlush(dailyTaskList);
        int databaseSizeBeforeDelete = dailyTaskListRepository.findAll().size();

        // Get the dailyTaskList
        restDailyTaskListMockMvc.perform(delete("/api/daily-task-lists/{id}", dailyTaskList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DailyTaskList> dailyTaskListList = dailyTaskListRepository.findAll();
        assertThat(dailyTaskListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyTaskList.class);
        DailyTaskList dailyTaskList1 = new DailyTaskList();
        dailyTaskList1.setId(1L);
        DailyTaskList dailyTaskList2 = new DailyTaskList();
        dailyTaskList2.setId(dailyTaskList1.getId());
        assertThat(dailyTaskList1).isEqualTo(dailyTaskList2);
        dailyTaskList2.setId(2L);
        assertThat(dailyTaskList1).isNotEqualTo(dailyTaskList2);
        dailyTaskList1.setId(null);
        assertThat(dailyTaskList1).isNotEqualTo(dailyTaskList2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DailyTaskListDTO.class);
        DailyTaskListDTO dailyTaskListDTO1 = new DailyTaskListDTO();
        dailyTaskListDTO1.setId(1L);
        DailyTaskListDTO dailyTaskListDTO2 = new DailyTaskListDTO();
        assertThat(dailyTaskListDTO1).isNotEqualTo(dailyTaskListDTO2);
        dailyTaskListDTO2.setId(dailyTaskListDTO1.getId());
        assertThat(dailyTaskListDTO1).isEqualTo(dailyTaskListDTO2);
        dailyTaskListDTO2.setId(2L);
        assertThat(dailyTaskListDTO1).isNotEqualTo(dailyTaskListDTO2);
        dailyTaskListDTO1.setId(null);
        assertThat(dailyTaskListDTO1).isNotEqualTo(dailyTaskListDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dailyTaskListMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dailyTaskListMapper.fromId(null)).isNull();
    }
}
