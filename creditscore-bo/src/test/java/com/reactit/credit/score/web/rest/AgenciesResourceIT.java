package com.reactit.credit.score.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.credit.score.IntegrationTest;
import com.reactit.credit.score.domain.Address;
import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.domain.Contact;
import com.reactit.credit.score.repository.AgenciesRepository;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.mapper.AgenciesMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AgenciesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AgenciesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DATEFOUNDED = "AAAAAAAAAA";
    private static final String UPDATED_DATEFOUNDED = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/agencies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AgenciesRepository agenciesRepository;

    @Autowired
    private AgenciesMapper agenciesMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAgenciesMockMvc;

    private Agencies agencies;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agencies createEntity(EntityManager em) {
        Agencies agencies = new Agencies()
            .name(DEFAULT_NAME)
            .datefounded(DEFAULT_DATEFOUNDED)
            .enabled(DEFAULT_ENABLED)
            .createdAt(DEFAULT_CREATED_AT);
        return agencies;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agencies createUpdatedEntity(EntityManager em) {
        Agencies agencies = new Agencies()
            .name(UPDATED_NAME)
            .datefounded(UPDATED_DATEFOUNDED)
            .enabled(UPDATED_ENABLED)
            .createdAt(UPDATED_CREATED_AT);
        return agencies;
    }

    @BeforeEach
    public void initTest() {
        agencies = createEntity(em);
    }

    @Test
    @Transactional
    void createAgencies() throws Exception {
        int databaseSizeBeforeCreate = agenciesRepository.findAll().size();
        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);
        restAgenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeCreate + 1);
        Agencies testAgencies = agenciesList.get(agenciesList.size() - 1);
        assertThat(testAgencies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgencies.getDatefounded()).isEqualTo(DEFAULT_DATEFOUNDED);
        assertThat(testAgencies.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testAgencies.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createAgenciesWithExistingId() throws Exception {
        // Create the Agencies with an existing ID
        agencies.setId(1L);
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        int databaseSizeBeforeCreate = agenciesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenciesMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAgencies() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].datefounded").value(hasItem(DEFAULT_DATEFOUNDED)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getAgencies() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get the agencies
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL_ID, agencies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(agencies.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.datefounded").value(DEFAULT_DATEFOUNDED))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getAgenciesByIdFiltering() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        Long id = agencies.getId();

        defaultAgenciesShouldBeFound("id.equals=" + id);
        defaultAgenciesShouldNotBeFound("id.notEquals=" + id);

        defaultAgenciesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAgenciesShouldNotBeFound("id.greaterThan=" + id);

        defaultAgenciesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAgenciesShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where name equals to DEFAULT_NAME
        defaultAgenciesShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the agenciesList where name equals to UPDATED_NAME
        defaultAgenciesShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where name in DEFAULT_NAME or UPDATED_NAME
        defaultAgenciesShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the agenciesList where name equals to UPDATED_NAME
        defaultAgenciesShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where name is not null
        defaultAgenciesShouldBeFound("name.specified=true");

        // Get all the agenciesList where name is null
        defaultAgenciesShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByNameContainsSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where name contains DEFAULT_NAME
        defaultAgenciesShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the agenciesList where name contains UPDATED_NAME
        defaultAgenciesShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where name does not contain DEFAULT_NAME
        defaultAgenciesShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the agenciesList where name does not contain UPDATED_NAME
        defaultAgenciesShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllAgenciesByDatefoundedIsEqualToSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where datefounded equals to DEFAULT_DATEFOUNDED
        defaultAgenciesShouldBeFound("datefounded.equals=" + DEFAULT_DATEFOUNDED);

        // Get all the agenciesList where datefounded equals to UPDATED_DATEFOUNDED
        defaultAgenciesShouldNotBeFound("datefounded.equals=" + UPDATED_DATEFOUNDED);
    }

    @Test
    @Transactional
    void getAllAgenciesByDatefoundedIsInShouldWork() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where datefounded in DEFAULT_DATEFOUNDED or UPDATED_DATEFOUNDED
        defaultAgenciesShouldBeFound("datefounded.in=" + DEFAULT_DATEFOUNDED + "," + UPDATED_DATEFOUNDED);

        // Get all the agenciesList where datefounded equals to UPDATED_DATEFOUNDED
        defaultAgenciesShouldNotBeFound("datefounded.in=" + UPDATED_DATEFOUNDED);
    }

    @Test
    @Transactional
    void getAllAgenciesByDatefoundedIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where datefounded is not null
        defaultAgenciesShouldBeFound("datefounded.specified=true");

        // Get all the agenciesList where datefounded is null
        defaultAgenciesShouldNotBeFound("datefounded.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByDatefoundedContainsSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where datefounded contains DEFAULT_DATEFOUNDED
        defaultAgenciesShouldBeFound("datefounded.contains=" + DEFAULT_DATEFOUNDED);

        // Get all the agenciesList where datefounded contains UPDATED_DATEFOUNDED
        defaultAgenciesShouldNotBeFound("datefounded.contains=" + UPDATED_DATEFOUNDED);
    }

    @Test
    @Transactional
    void getAllAgenciesByDatefoundedNotContainsSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where datefounded does not contain DEFAULT_DATEFOUNDED
        defaultAgenciesShouldNotBeFound("datefounded.doesNotContain=" + DEFAULT_DATEFOUNDED);

        // Get all the agenciesList where datefounded does not contain UPDATED_DATEFOUNDED
        defaultAgenciesShouldBeFound("datefounded.doesNotContain=" + UPDATED_DATEFOUNDED);
    }

    @Test
    @Transactional
    void getAllAgenciesByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where enabled equals to DEFAULT_ENABLED
        defaultAgenciesShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the agenciesList where enabled equals to UPDATED_ENABLED
        defaultAgenciesShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllAgenciesByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultAgenciesShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the agenciesList where enabled equals to UPDATED_ENABLED
        defaultAgenciesShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllAgenciesByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where enabled is not null
        defaultAgenciesShouldBeFound("enabled.specified=true");

        // Get all the agenciesList where enabled is null
        defaultAgenciesShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where createdAt equals to DEFAULT_CREATED_AT
        defaultAgenciesShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the agenciesList where createdAt equals to UPDATED_CREATED_AT
        defaultAgenciesShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAgenciesByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultAgenciesShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the agenciesList where createdAt equals to UPDATED_CREATED_AT
        defaultAgenciesShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllAgenciesByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        // Get all the agenciesList where createdAt is not null
        defaultAgenciesShouldBeFound("createdAt.specified=true");

        // Get all the agenciesList where createdAt is null
        defaultAgenciesShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllAgenciesByBanksIsEqualToSomething() throws Exception {
        Banks banks;
        if (TestUtil.findAll(em, Banks.class).isEmpty()) {
            agenciesRepository.saveAndFlush(agencies);
            banks = BanksResourceIT.createEntity(em);
        } else {
            banks = TestUtil.findAll(em, Banks.class).get(0);
        }
        em.persist(banks);
        em.flush();
        agencies.setBanks(banks);
        agenciesRepository.saveAndFlush(agencies);
        Long banksId = banks.getId();
        // Get all the agenciesList where banks equals to banksId
        defaultAgenciesShouldBeFound("banksId.equals=" + banksId);

        // Get all the agenciesList where banks equals to (banksId + 1)
        defaultAgenciesShouldNotBeFound("banksId.equals=" + (banksId + 1));
    }

    @Test
    @Transactional
    void getAllAgenciesByContactIsEqualToSomething() throws Exception {
        Contact contact;
        if (TestUtil.findAll(em, Contact.class).isEmpty()) {
            agenciesRepository.saveAndFlush(agencies);
            contact = ContactResourceIT.createEntity(em);
        } else {
            contact = TestUtil.findAll(em, Contact.class).get(0);
        }
        em.persist(contact);
        em.flush();
        agencies.addContact(contact);
        agenciesRepository.saveAndFlush(agencies);
        Long contactId = contact.getId();
        // Get all the agenciesList where contact equals to contactId
        defaultAgenciesShouldBeFound("contactId.equals=" + contactId);

        // Get all the agenciesList where contact equals to (contactId + 1)
        defaultAgenciesShouldNotBeFound("contactId.equals=" + (contactId + 1));
    }

    @Test
    @Transactional
    void getAllAgenciesByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            agenciesRepository.saveAndFlush(agencies);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        agencies.addAddress(address);
        agenciesRepository.saveAndFlush(agencies);
        Long addressId = address.getId();
        // Get all the agenciesList where address equals to addressId
        defaultAgenciesShouldBeFound("addressId.equals=" + addressId);

        // Get all the agenciesList where address equals to (addressId + 1)
        defaultAgenciesShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgenciesShouldBeFound(String filter) throws Exception {
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agencies.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].datefounded").value(hasItem(DEFAULT_DATEFOUNDED)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgenciesShouldNotBeFound(String filter) throws Exception {
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgenciesMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAgencies() throws Exception {
        // Get the agencies
        restAgenciesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAgencies() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();

        // Update the agencies
        Agencies updatedAgencies = agenciesRepository.findById(agencies.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAgencies are not directly saved in db
        em.detach(updatedAgencies);
        updatedAgencies.name(UPDATED_NAME).datefounded(UPDATED_DATEFOUNDED).enabled(UPDATED_ENABLED).createdAt(UPDATED_CREATED_AT);
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(updatedAgencies);

        restAgenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenciesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isOk());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
        Agencies testAgencies = agenciesList.get(agenciesList.size() - 1);
        assertThat(testAgencies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgencies.getDatefounded()).isEqualTo(UPDATED_DATEFOUNDED);
        assertThat(testAgencies.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testAgencies.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, agenciesDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAgenciesWithPatch() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();

        // Update the agencies using partial update
        Agencies partialUpdatedAgencies = new Agencies();
        partialUpdatedAgencies.setId(agencies.getId());

        partialUpdatedAgencies.datefounded(UPDATED_DATEFOUNDED).createdAt(UPDATED_CREATED_AT);

        restAgenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgencies.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgencies))
            )
            .andExpect(status().isOk());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
        Agencies testAgencies = agenciesList.get(agenciesList.size() - 1);
        assertThat(testAgencies.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAgencies.getDatefounded()).isEqualTo(UPDATED_DATEFOUNDED);
        assertThat(testAgencies.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testAgencies.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateAgenciesWithPatch() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();

        // Update the agencies using partial update
        Agencies partialUpdatedAgencies = new Agencies();
        partialUpdatedAgencies.setId(agencies.getId());

        partialUpdatedAgencies.name(UPDATED_NAME).datefounded(UPDATED_DATEFOUNDED).enabled(UPDATED_ENABLED).createdAt(UPDATED_CREATED_AT);

        restAgenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAgencies.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAgencies))
            )
            .andExpect(status().isOk());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
        Agencies testAgencies = agenciesList.get(agenciesList.size() - 1);
        assertThat(testAgencies.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAgencies.getDatefounded()).isEqualTo(UPDATED_DATEFOUNDED);
        assertThat(testAgencies.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testAgencies.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, agenciesDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAgencies() throws Exception {
        int databaseSizeBeforeUpdate = agenciesRepository.findAll().size();
        agencies.setId(longCount.incrementAndGet());

        // Create the Agencies
        AgenciesDTO agenciesDTO = agenciesMapper.toDto(agencies);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAgenciesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(agenciesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Agencies in the database
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAgencies() throws Exception {
        // Initialize the database
        agenciesRepository.saveAndFlush(agencies);

        int databaseSizeBeforeDelete = agenciesRepository.findAll().size();

        // Delete the agencies
        restAgenciesMockMvc
            .perform(delete(ENTITY_API_URL_ID, agencies.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agencies> agenciesList = agenciesRepository.findAll();
        assertThat(agenciesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
