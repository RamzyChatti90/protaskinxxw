package com.example.protaskinxxw.web.rest;

import static com.example.protaskinxxw.domain.SprintAsserts.*;
import static com.example.protaskinxxw.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.protaskinxxw.IntegrationTest;
import com.example.protaskinxxw.domain.Sprint;
import com.example.protaskinxxw.repository.SprintRepository;
import com.example.protaskinxxw.service.dto.SprintDTO;
import com.example.protaskinxxw.service.mapper.SprintMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SprintResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SprintResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/sprints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SprintRepository sprintRepository;

    @Autowired
    private SprintMapper sprintMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSprintMockMvc;

    private Sprint sprint;

    private Sprint insertedSprint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sprint createEntity() {
        return new Sprint().name(DEFAULT_NAME).description(DEFAULT_DESCRIPTION).startDate(DEFAULT_START_DATE).createdAt(DEFAULT_CREATED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sprint createUpdatedEntity() {
        return new Sprint().name(UPDATED_NAME).description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE).createdAt(UPDATED_CREATED_AT);
    }

    @BeforeEach
    void initTest() {
        sprint = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSprint != null) {
            sprintRepository.delete(insertedSprint);
            insertedSprint = null;
        }
    }

    @Test
    @Transactional
    void createSprint() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);
        var returnedSprintDTO = om.readValue(
            restSprintMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SprintDTO.class
        );

        // Validate the Sprint in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSprint = sprintMapper.toEntity(returnedSprintDTO);
        assertSprintUpdatableFieldsEquals(returnedSprint, getPersistedSprint(returnedSprint));

        insertedSprint = returnedSprint;
    }

    @Test
    @Transactional
    void createSprintWithExistingId() throws Exception {
        // Create the Sprint with an existing ID
        sprint.setId(1L);
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        sprint.setName(null);

        // Create the Sprint, which fails.
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        restSprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSprints() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        // Get all the sprintList
        restSprintMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sprint.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getSprint() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        // Get the sprint
        restSprintMockMvc
            .perform(get(ENTITY_API_URL_ID, sprint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sprint.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSprint() throws Exception {
        // Get the sprint
        restSprintMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSprint() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sprint
        Sprint updatedSprint = sprintRepository.findById(sprint.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSprint are not directly saved in db
        em.detach(updatedSprint);
        updatedSprint.name(UPDATED_NAME).description(UPDATED_DESCRIPTION).startDate(UPDATED_START_DATE).createdAt(UPDATED_CREATED_AT);
        SprintDTO sprintDTO = sprintMapper.toDto(updatedSprint);

        restSprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sprintDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO))
            )
            .andExpect(status().isOk());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSprintToMatchAllProperties(updatedSprint);
    }

    @Test
    @Transactional
    void putNonExistingSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sprintDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sprintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sprintDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSprintWithPatch() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sprint using partial update
        Sprint partialUpdatedSprint = new Sprint();
        partialUpdatedSprint.setId(sprint.getId());

        partialUpdatedSprint.startDate(UPDATED_START_DATE);

        restSprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSprint.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSprint))
            )
            .andExpect(status().isOk());

        // Validate the Sprint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSprintUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSprint, sprint), getPersistedSprint(sprint));
    }

    @Test
    @Transactional
    void fullUpdateSprintWithPatch() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the sprint using partial update
        Sprint partialUpdatedSprint = new Sprint();
        partialUpdatedSprint.setId(sprint.getId());

        partialUpdatedSprint
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .startDate(UPDATED_START_DATE)
            .createdAt(UPDATED_CREATED_AT);

        restSprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSprint.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSprint))
            )
            .andExpect(status().isOk());

        // Validate the Sprint in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSprintUpdatableFieldsEquals(partialUpdatedSprint, getPersistedSprint(partialUpdatedSprint));
    }

    @Test
    @Transactional
    void patchNonExistingSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sprintDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sprintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sprintDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSprint() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        sprint.setId(longCount.incrementAndGet());

        // Create the Sprint
        SprintDTO sprintDTO = sprintMapper.toDto(sprint);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSprintMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sprintDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Sprint in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSprint() throws Exception {
        // Initialize the database
        insertedSprint = sprintRepository.saveAndFlush(sprint);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the sprint
        restSprintMockMvc
            .perform(delete(ENTITY_API_URL_ID, sprint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sprintRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Sprint getPersistedSprint(Sprint sprint) {
        return sprintRepository.findById(sprint.getId()).orElseThrow();
    }

    protected void assertPersistedSprintToMatchAllProperties(Sprint expectedSprint) {
        assertSprintAllPropertiesEquals(expectedSprint, getPersistedSprint(expectedSprint));
    }

    protected void assertPersistedSprintToMatchUpdatableProperties(Sprint expectedSprint) {
        assertSprintAllUpdatablePropertiesEquals(expectedSprint, getPersistedSprint(expectedSprint));
    }
}
