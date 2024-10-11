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
import com.reactit.credit.score.repository.BanksRepository;
import com.reactit.credit.score.service.dto.BanksDTO;
import com.reactit.credit.score.service.mapper.BanksMapper;
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
 * Integration tests for the {@link BanksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BanksResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_FOUNDED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FOUNDED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BRANCHES = "AAAAAAAAAA";
    private static final String UPDATED_BRANCHES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/banks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BanksRepository banksRepository;

    @Autowired
    private BanksMapper banksMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBanksMockMvc;

    private Banks banks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createEntity(EntityManager em) {
        Banks banks = new Banks()
            .name(DEFAULT_NAME)
            .foundedDate(DEFAULT_FOUNDED_DATE)
            .branches(DEFAULT_BRANCHES)
            .enabled(DEFAULT_ENABLED)
            .createdAt(DEFAULT_CREATED_AT);
        return banks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Banks createUpdatedEntity(EntityManager em) {
        Banks banks = new Banks()
            .name(UPDATED_NAME)
            .foundedDate(UPDATED_FOUNDED_DATE)
            .branches(UPDATED_BRANCHES)
            .enabled(UPDATED_ENABLED)
            .createdAt(UPDATED_CREATED_AT);
        return banks;
    }

    @BeforeEach
    public void initTest() {
        banks = createEntity(em);
    }

    @Test
    @Transactional
    void createBanks() throws Exception {
        int databaseSizeBeforeCreate = banksRepository.findAll().size();
        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);
        restBanksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate + 1);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBanks.getFoundedDate()).isEqualTo(DEFAULT_FOUNDED_DATE);
        assertThat(testBanks.getBranches()).isEqualTo(DEFAULT_BRANCHES);
        assertThat(testBanks.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testBanks.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createBanksWithExistingId() throws Exception {
        // Create the Banks with an existing ID
        banks.setId(1L);
        BanksDTO banksDTO = banksMapper.toDto(banks);

        int databaseSizeBeforeCreate = banksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBanksMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].foundedDate").value(hasItem(DEFAULT_FOUNDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].branches").value(hasItem(DEFAULT_BRANCHES)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get the banks
        restBanksMockMvc
            .perform(get(ENTITY_API_URL_ID, banks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(banks.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.foundedDate").value(DEFAULT_FOUNDED_DATE.toString()))
            .andExpect(jsonPath("$.branches").value(DEFAULT_BRANCHES))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getBanksByIdFiltering() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        Long id = banks.getId();

        defaultBanksShouldBeFound("id.equals=" + id);
        defaultBanksShouldNotBeFound("id.notEquals=" + id);

        defaultBanksShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBanksShouldNotBeFound("id.greaterThan=" + id);

        defaultBanksShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBanksShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where name equals to DEFAULT_NAME
        defaultBanksShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the banksList where name equals to UPDATED_NAME
        defaultBanksShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where name in DEFAULT_NAME or UPDATED_NAME
        defaultBanksShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the banksList where name equals to UPDATED_NAME
        defaultBanksShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where name is not null
        defaultBanksShouldBeFound("name.specified=true");

        // Get all the banksList where name is null
        defaultBanksShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByNameContainsSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where name contains DEFAULT_NAME
        defaultBanksShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the banksList where name contains UPDATED_NAME
        defaultBanksShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByNameNotContainsSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where name does not contain DEFAULT_NAME
        defaultBanksShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the banksList where name does not contain UPDATED_NAME
        defaultBanksShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllBanksByFoundedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where foundedDate equals to DEFAULT_FOUNDED_DATE
        defaultBanksShouldBeFound("foundedDate.equals=" + DEFAULT_FOUNDED_DATE);

        // Get all the banksList where foundedDate equals to UPDATED_FOUNDED_DATE
        defaultBanksShouldNotBeFound("foundedDate.equals=" + UPDATED_FOUNDED_DATE);
    }

    @Test
    @Transactional
    void getAllBanksByFoundedDateIsInShouldWork() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where foundedDate in DEFAULT_FOUNDED_DATE or UPDATED_FOUNDED_DATE
        defaultBanksShouldBeFound("foundedDate.in=" + DEFAULT_FOUNDED_DATE + "," + UPDATED_FOUNDED_DATE);

        // Get all the banksList where foundedDate equals to UPDATED_FOUNDED_DATE
        defaultBanksShouldNotBeFound("foundedDate.in=" + UPDATED_FOUNDED_DATE);
    }

    @Test
    @Transactional
    void getAllBanksByFoundedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where foundedDate is not null
        defaultBanksShouldBeFound("foundedDate.specified=true");

        // Get all the banksList where foundedDate is null
        defaultBanksShouldNotBeFound("foundedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByBranchesIsEqualToSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where branches equals to DEFAULT_BRANCHES
        defaultBanksShouldBeFound("branches.equals=" + DEFAULT_BRANCHES);

        // Get all the banksList where branches equals to UPDATED_BRANCHES
        defaultBanksShouldNotBeFound("branches.equals=" + UPDATED_BRANCHES);
    }

    @Test
    @Transactional
    void getAllBanksByBranchesIsInShouldWork() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where branches in DEFAULT_BRANCHES or UPDATED_BRANCHES
        defaultBanksShouldBeFound("branches.in=" + DEFAULT_BRANCHES + "," + UPDATED_BRANCHES);

        // Get all the banksList where branches equals to UPDATED_BRANCHES
        defaultBanksShouldNotBeFound("branches.in=" + UPDATED_BRANCHES);
    }

    @Test
    @Transactional
    void getAllBanksByBranchesIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where branches is not null
        defaultBanksShouldBeFound("branches.specified=true");

        // Get all the banksList where branches is null
        defaultBanksShouldNotBeFound("branches.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByBranchesContainsSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where branches contains DEFAULT_BRANCHES
        defaultBanksShouldBeFound("branches.contains=" + DEFAULT_BRANCHES);

        // Get all the banksList where branches contains UPDATED_BRANCHES
        defaultBanksShouldNotBeFound("branches.contains=" + UPDATED_BRANCHES);
    }

    @Test
    @Transactional
    void getAllBanksByBranchesNotContainsSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where branches does not contain DEFAULT_BRANCHES
        defaultBanksShouldNotBeFound("branches.doesNotContain=" + DEFAULT_BRANCHES);

        // Get all the banksList where branches does not contain UPDATED_BRANCHES
        defaultBanksShouldBeFound("branches.doesNotContain=" + UPDATED_BRANCHES);
    }

    @Test
    @Transactional
    void getAllBanksByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where enabled equals to DEFAULT_ENABLED
        defaultBanksShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the banksList where enabled equals to UPDATED_ENABLED
        defaultBanksShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllBanksByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultBanksShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the banksList where enabled equals to UPDATED_ENABLED
        defaultBanksShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllBanksByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where enabled is not null
        defaultBanksShouldBeFound("enabled.specified=true");

        // Get all the banksList where enabled is null
        defaultBanksShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where createdAt equals to DEFAULT_CREATED_AT
        defaultBanksShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the banksList where createdAt equals to UPDATED_CREATED_AT
        defaultBanksShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultBanksShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the banksList where createdAt equals to UPDATED_CREATED_AT
        defaultBanksShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllBanksByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        // Get all the banksList where createdAt is not null
        defaultBanksShouldBeFound("createdAt.specified=true");

        // Get all the banksList where createdAt is null
        defaultBanksShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllBanksByContactIsEqualToSomething() throws Exception {
        Contact contact;
        if (TestUtil.findAll(em, Contact.class).isEmpty()) {
            banksRepository.saveAndFlush(banks);
            contact = ContactResourceIT.createEntity(em);
        } else {
            contact = TestUtil.findAll(em, Contact.class).get(0);
        }
        em.persist(contact);
        em.flush();
        banks.addContact(contact);
        banksRepository.saveAndFlush(banks);
        Long contactId = contact.getId();
        // Get all the banksList where contact equals to contactId
        defaultBanksShouldBeFound("contactId.equals=" + contactId);

        // Get all the banksList where contact equals to (contactId + 1)
        defaultBanksShouldNotBeFound("contactId.equals=" + (contactId + 1));
    }

    @Test
    @Transactional
    void getAllBanksByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            banksRepository.saveAndFlush(banks);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        banks.addAddress(address);
        banksRepository.saveAndFlush(banks);
        Long addressId = address.getId();
        // Get all the banksList where address equals to addressId
        defaultBanksShouldBeFound("addressId.equals=" + addressId);

        // Get all the banksList where address equals to (addressId + 1)
        defaultBanksShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllBanksByAgenciesIsEqualToSomething() throws Exception {
        Agencies agencies;
        if (TestUtil.findAll(em, Agencies.class).isEmpty()) {
            banksRepository.saveAndFlush(banks);
            agencies = AgenciesResourceIT.createEntity(em);
        } else {
            agencies = TestUtil.findAll(em, Agencies.class).get(0);
        }
        em.persist(agencies);
        em.flush();
        banks.addAgencies(agencies);
        banksRepository.saveAndFlush(banks);
        Long agenciesId = agencies.getId();
        // Get all the banksList where agencies equals to agenciesId
        defaultBanksShouldBeFound("agenciesId.equals=" + agenciesId);

        // Get all the banksList where agencies equals to (agenciesId + 1)
        defaultBanksShouldNotBeFound("agenciesId.equals=" + (agenciesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBanksShouldBeFound(String filter) throws Exception {
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(banks.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].foundedDate").value(hasItem(DEFAULT_FOUNDED_DATE.toString())))
            .andExpect(jsonPath("$.[*].branches").value(hasItem(DEFAULT_BRANCHES)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBanksShouldNotBeFound(String filter) throws Exception {
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBanksMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBanks() throws Exception {
        // Get the banks
        restBanksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks
        Banks updatedBanks = banksRepository.findById(banks.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedBanks are not directly saved in db
        em.detach(updatedBanks);
        updatedBanks
            .name(UPDATED_NAME)
            .foundedDate(UPDATED_FOUNDED_DATE)
            .branches(UPDATED_BRANCHES)
            .enabled(UPDATED_ENABLED)
            .createdAt(UPDATED_CREATED_AT);
        BanksDTO banksDTO = banksMapper.toDto(updatedBanks);

        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanks.getFoundedDate()).isEqualTo(UPDATED_FOUNDED_DATE);
        assertThat(testBanks.getBranches()).isEqualTo(UPDATED_BRANCHES);
        assertThat(testBanks.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testBanks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, banksDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks.branches(UPDATED_BRANCHES);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBanks.getFoundedDate()).isEqualTo(DEFAULT_FOUNDED_DATE);
        assertThat(testBanks.getBranches()).isEqualTo(UPDATED_BRANCHES);
        assertThat(testBanks.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testBanks.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateBanksWithPatch() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeUpdate = banksRepository.findAll().size();

        // Update the banks using partial update
        Banks partialUpdatedBanks = new Banks();
        partialUpdatedBanks.setId(banks.getId());

        partialUpdatedBanks
            .name(UPDATED_NAME)
            .foundedDate(UPDATED_FOUNDED_DATE)
            .branches(UPDATED_BRANCHES)
            .enabled(UPDATED_ENABLED)
            .createdAt(UPDATED_CREATED_AT);

        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBanks.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBanks))
            )
            .andExpect(status().isOk());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
        Banks testBanks = banksList.get(banksList.size() - 1);
        assertThat(testBanks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBanks.getFoundedDate()).isEqualTo(UPDATED_FOUNDED_DATE);
        assertThat(testBanks.getBranches()).isEqualTo(UPDATED_BRANCHES);
        assertThat(testBanks.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testBanks.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, banksDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBanks() throws Exception {
        int databaseSizeBeforeUpdate = banksRepository.findAll().size();
        banks.setId(longCount.incrementAndGet());

        // Create the Banks
        BanksDTO banksDTO = banksMapper.toDto(banks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBanksMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(banksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Banks in the database
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBanks() throws Exception {
        // Initialize the database
        banksRepository.saveAndFlush(banks);

        int databaseSizeBeforeDelete = banksRepository.findAll().size();

        // Delete the banks
        restBanksMockMvc
            .perform(delete(ENTITY_API_URL_ID, banks.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Banks> banksList = banksRepository.findAll();
        assertThat(banksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
