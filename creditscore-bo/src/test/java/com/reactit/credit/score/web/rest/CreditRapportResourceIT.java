package com.reactit.credit.score.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.credit.score.IntegrationTest;
import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.repository.CreditRapportRepository;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
import com.reactit.credit.score.service.mapper.CreditRapportMapper;
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
 * Integration tests for the {@link CreditRapportResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreditRapportResourceIT {

    private static final String DEFAULT_CREDIT_SCORE = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_SCORE = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_AGE = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_AGE = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_LIMIT = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_LIMIT = "BBBBBBBBBB";

    private static final String DEFAULT_INQUIRIES_AND_REQUESTS = "AAAAAAAAAA";
    private static final String UPDATED_INQUIRIES_AND_REQUESTS = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/credit-rapports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreditRapportRepository creditRapportRepository;

    @Autowired
    private CreditRapportMapper creditRapportMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreditRapportMockMvc;

    private CreditRapport creditRapport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditRapport createEntity(EntityManager em) {
        CreditRapport creditRapport = new CreditRapport()
            .creditScore(DEFAULT_CREDIT_SCORE)
            .accountAge(DEFAULT_ACCOUNT_AGE)
            .creditLimit(DEFAULT_CREDIT_LIMIT)
            .inquiriesAndRequests(DEFAULT_INQUIRIES_AND_REQUESTS)
            .createdAt(DEFAULT_CREATED_AT);
        return creditRapport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreditRapport createUpdatedEntity(EntityManager em) {
        CreditRapport creditRapport = new CreditRapport()
            .creditScore(UPDATED_CREDIT_SCORE)
            .accountAge(UPDATED_ACCOUNT_AGE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .inquiriesAndRequests(UPDATED_INQUIRIES_AND_REQUESTS)
            .createdAt(UPDATED_CREATED_AT);
        return creditRapport;
    }

    @BeforeEach
    public void initTest() {
        creditRapport = createEntity(em);
    }

    @Test
    @Transactional
    void createCreditRapport() throws Exception {
        int databaseSizeBeforeCreate = creditRapportRepository.findAll().size();
        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);
        restCreditRapportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeCreate + 1);
        CreditRapport testCreditRapport = creditRapportList.get(creditRapportList.size() - 1);
        assertThat(testCreditRapport.getCreditScore()).isEqualTo(DEFAULT_CREDIT_SCORE);
        assertThat(testCreditRapport.getAccountAge()).isEqualTo(DEFAULT_ACCOUNT_AGE);
        assertThat(testCreditRapport.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testCreditRapport.getInquiriesAndRequests()).isEqualTo(DEFAULT_INQUIRIES_AND_REQUESTS);
        assertThat(testCreditRapport.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createCreditRapportWithExistingId() throws Exception {
        // Create the CreditRapport with an existing ID
        creditRapport.setId(1L);
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        int databaseSizeBeforeCreate = creditRapportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreditRapportMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCreditRapports() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditRapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE)))
            .andExpect(jsonPath("$.[*].accountAge").value(hasItem(DEFAULT_ACCOUNT_AGE)))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT)))
            .andExpect(jsonPath("$.[*].inquiriesAndRequests").value(hasItem(DEFAULT_INQUIRIES_AND_REQUESTS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getCreditRapport() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get the creditRapport
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL_ID, creditRapport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(creditRapport.getId().intValue()))
            .andExpect(jsonPath("$.creditScore").value(DEFAULT_CREDIT_SCORE))
            .andExpect(jsonPath("$.accountAge").value(DEFAULT_ACCOUNT_AGE))
            .andExpect(jsonPath("$.creditLimit").value(DEFAULT_CREDIT_LIMIT))
            .andExpect(jsonPath("$.inquiriesAndRequests").value(DEFAULT_INQUIRIES_AND_REQUESTS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getCreditRapportsByIdFiltering() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        Long id = creditRapport.getId();

        defaultCreditRapportShouldBeFound("id.equals=" + id);
        defaultCreditRapportShouldNotBeFound("id.notEquals=" + id);

        defaultCreditRapportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCreditRapportShouldNotBeFound("id.greaterThan=" + id);

        defaultCreditRapportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCreditRapportShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditScoreIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditScore equals to DEFAULT_CREDIT_SCORE
        defaultCreditRapportShouldBeFound("creditScore.equals=" + DEFAULT_CREDIT_SCORE);

        // Get all the creditRapportList where creditScore equals to UPDATED_CREDIT_SCORE
        defaultCreditRapportShouldNotBeFound("creditScore.equals=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditScoreIsInShouldWork() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditScore in DEFAULT_CREDIT_SCORE or UPDATED_CREDIT_SCORE
        defaultCreditRapportShouldBeFound("creditScore.in=" + DEFAULT_CREDIT_SCORE + "," + UPDATED_CREDIT_SCORE);

        // Get all the creditRapportList where creditScore equals to UPDATED_CREDIT_SCORE
        defaultCreditRapportShouldNotBeFound("creditScore.in=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditScoreIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditScore is not null
        defaultCreditRapportShouldBeFound("creditScore.specified=true");

        // Get all the creditRapportList where creditScore is null
        defaultCreditRapportShouldNotBeFound("creditScore.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditScoreContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditScore contains DEFAULT_CREDIT_SCORE
        defaultCreditRapportShouldBeFound("creditScore.contains=" + DEFAULT_CREDIT_SCORE);

        // Get all the creditRapportList where creditScore contains UPDATED_CREDIT_SCORE
        defaultCreditRapportShouldNotBeFound("creditScore.contains=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditScoreNotContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditScore does not contain DEFAULT_CREDIT_SCORE
        defaultCreditRapportShouldNotBeFound("creditScore.doesNotContain=" + DEFAULT_CREDIT_SCORE);

        // Get all the creditRapportList where creditScore does not contain UPDATED_CREDIT_SCORE
        defaultCreditRapportShouldBeFound("creditScore.doesNotContain=" + UPDATED_CREDIT_SCORE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByAccountAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where accountAge equals to DEFAULT_ACCOUNT_AGE
        defaultCreditRapportShouldBeFound("accountAge.equals=" + DEFAULT_ACCOUNT_AGE);

        // Get all the creditRapportList where accountAge equals to UPDATED_ACCOUNT_AGE
        defaultCreditRapportShouldNotBeFound("accountAge.equals=" + UPDATED_ACCOUNT_AGE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByAccountAgeIsInShouldWork() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where accountAge in DEFAULT_ACCOUNT_AGE or UPDATED_ACCOUNT_AGE
        defaultCreditRapportShouldBeFound("accountAge.in=" + DEFAULT_ACCOUNT_AGE + "," + UPDATED_ACCOUNT_AGE);

        // Get all the creditRapportList where accountAge equals to UPDATED_ACCOUNT_AGE
        defaultCreditRapportShouldNotBeFound("accountAge.in=" + UPDATED_ACCOUNT_AGE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByAccountAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where accountAge is not null
        defaultCreditRapportShouldBeFound("accountAge.specified=true");

        // Get all the creditRapportList where accountAge is null
        defaultCreditRapportShouldNotBeFound("accountAge.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditRapportsByAccountAgeContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where accountAge contains DEFAULT_ACCOUNT_AGE
        defaultCreditRapportShouldBeFound("accountAge.contains=" + DEFAULT_ACCOUNT_AGE);

        // Get all the creditRapportList where accountAge contains UPDATED_ACCOUNT_AGE
        defaultCreditRapportShouldNotBeFound("accountAge.contains=" + UPDATED_ACCOUNT_AGE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByAccountAgeNotContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where accountAge does not contain DEFAULT_ACCOUNT_AGE
        defaultCreditRapportShouldNotBeFound("accountAge.doesNotContain=" + DEFAULT_ACCOUNT_AGE);

        // Get all the creditRapportList where accountAge does not contain UPDATED_ACCOUNT_AGE
        defaultCreditRapportShouldBeFound("accountAge.doesNotContain=" + UPDATED_ACCOUNT_AGE);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditLimitIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditLimit equals to DEFAULT_CREDIT_LIMIT
        defaultCreditRapportShouldBeFound("creditLimit.equals=" + DEFAULT_CREDIT_LIMIT);

        // Get all the creditRapportList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultCreditRapportShouldNotBeFound("creditLimit.equals=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditLimitIsInShouldWork() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditLimit in DEFAULT_CREDIT_LIMIT or UPDATED_CREDIT_LIMIT
        defaultCreditRapportShouldBeFound("creditLimit.in=" + DEFAULT_CREDIT_LIMIT + "," + UPDATED_CREDIT_LIMIT);

        // Get all the creditRapportList where creditLimit equals to UPDATED_CREDIT_LIMIT
        defaultCreditRapportShouldNotBeFound("creditLimit.in=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditLimitIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditLimit is not null
        defaultCreditRapportShouldBeFound("creditLimit.specified=true");

        // Get all the creditRapportList where creditLimit is null
        defaultCreditRapportShouldNotBeFound("creditLimit.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditLimitContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditLimit contains DEFAULT_CREDIT_LIMIT
        defaultCreditRapportShouldBeFound("creditLimit.contains=" + DEFAULT_CREDIT_LIMIT);

        // Get all the creditRapportList where creditLimit contains UPDATED_CREDIT_LIMIT
        defaultCreditRapportShouldNotBeFound("creditLimit.contains=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreditLimitNotContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where creditLimit does not contain DEFAULT_CREDIT_LIMIT
        defaultCreditRapportShouldNotBeFound("creditLimit.doesNotContain=" + DEFAULT_CREDIT_LIMIT);

        // Get all the creditRapportList where creditLimit does not contain UPDATED_CREDIT_LIMIT
        defaultCreditRapportShouldBeFound("creditLimit.doesNotContain=" + UPDATED_CREDIT_LIMIT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInquiriesAndRequestsIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where inquiriesAndRequests equals to DEFAULT_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldBeFound("inquiriesAndRequests.equals=" + DEFAULT_INQUIRIES_AND_REQUESTS);

        // Get all the creditRapportList where inquiriesAndRequests equals to UPDATED_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldNotBeFound("inquiriesAndRequests.equals=" + UPDATED_INQUIRIES_AND_REQUESTS);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInquiriesAndRequestsIsInShouldWork() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where inquiriesAndRequests in DEFAULT_INQUIRIES_AND_REQUESTS or UPDATED_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldBeFound(
            "inquiriesAndRequests.in=" + DEFAULT_INQUIRIES_AND_REQUESTS + "," + UPDATED_INQUIRIES_AND_REQUESTS
        );

        // Get all the creditRapportList where inquiriesAndRequests equals to UPDATED_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldNotBeFound("inquiriesAndRequests.in=" + UPDATED_INQUIRIES_AND_REQUESTS);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInquiriesAndRequestsIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where inquiriesAndRequests is not null
        defaultCreditRapportShouldBeFound("inquiriesAndRequests.specified=true");

        // Get all the creditRapportList where inquiriesAndRequests is null
        defaultCreditRapportShouldNotBeFound("inquiriesAndRequests.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInquiriesAndRequestsContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where inquiriesAndRequests contains DEFAULT_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldBeFound("inquiriesAndRequests.contains=" + DEFAULT_INQUIRIES_AND_REQUESTS);

        // Get all the creditRapportList where inquiriesAndRequests contains UPDATED_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldNotBeFound("inquiriesAndRequests.contains=" + UPDATED_INQUIRIES_AND_REQUESTS);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInquiriesAndRequestsNotContainsSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where inquiriesAndRequests does not contain DEFAULT_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldNotBeFound("inquiriesAndRequests.doesNotContain=" + DEFAULT_INQUIRIES_AND_REQUESTS);

        // Get all the creditRapportList where inquiriesAndRequests does not contain UPDATED_INQUIRIES_AND_REQUESTS
        defaultCreditRapportShouldBeFound("inquiriesAndRequests.doesNotContain=" + UPDATED_INQUIRIES_AND_REQUESTS);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where createdAt equals to DEFAULT_CREATED_AT
        defaultCreditRapportShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the creditRapportList where createdAt equals to UPDATED_CREATED_AT
        defaultCreditRapportShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultCreditRapportShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the creditRapportList where createdAt equals to UPDATED_CREATED_AT
        defaultCreditRapportShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllCreditRapportsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        // Get all the creditRapportList where createdAt is not null
        defaultCreditRapportShouldBeFound("createdAt.specified=true");

        // Get all the creditRapportList where createdAt is null
        defaultCreditRapportShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllCreditRapportsByMemberUserIsEqualToSomething() throws Exception {
        MemberUser memberUser;
        if (TestUtil.findAll(em, MemberUser.class).isEmpty()) {
            creditRapportRepository.saveAndFlush(creditRapport);
            memberUser = MemberUserResourceIT.createEntity(em);
        } else {
            memberUser = TestUtil.findAll(em, MemberUser.class).get(0);
        }
        em.persist(memberUser);
        em.flush();
        creditRapport.setMemberUser(memberUser);
        creditRapportRepository.saveAndFlush(creditRapport);
        Long memberUserId = memberUser.getId();
        // Get all the creditRapportList where memberUser equals to memberUserId
        defaultCreditRapportShouldBeFound("memberUserId.equals=" + memberUserId);

        // Get all the creditRapportList where memberUser equals to (memberUserId + 1)
        defaultCreditRapportShouldNotBeFound("memberUserId.equals=" + (memberUserId + 1));
    }

    @Test
    @Transactional
    void getAllCreditRapportsByInvoiceIsEqualToSomething() throws Exception {
        Invoice invoice;
        if (TestUtil.findAll(em, Invoice.class).isEmpty()) {
            creditRapportRepository.saveAndFlush(creditRapport);
            invoice = InvoiceResourceIT.createEntity(em);
        } else {
            invoice = TestUtil.findAll(em, Invoice.class).get(0);
        }
        em.persist(invoice);
        em.flush();
        creditRapport.addInvoice(invoice);
        creditRapportRepository.saveAndFlush(creditRapport);
        Long invoiceId = invoice.getId();
        // Get all the creditRapportList where invoice equals to invoiceId
        defaultCreditRapportShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the creditRapportList where invoice equals to (invoiceId + 1)
        defaultCreditRapportShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCreditRapportShouldBeFound(String filter) throws Exception {
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(creditRapport.getId().intValue())))
            .andExpect(jsonPath("$.[*].creditScore").value(hasItem(DEFAULT_CREDIT_SCORE)))
            .andExpect(jsonPath("$.[*].accountAge").value(hasItem(DEFAULT_ACCOUNT_AGE)))
            .andExpect(jsonPath("$.[*].creditLimit").value(hasItem(DEFAULT_CREDIT_LIMIT)))
            .andExpect(jsonPath("$.[*].inquiriesAndRequests").value(hasItem(DEFAULT_INQUIRIES_AND_REQUESTS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCreditRapportShouldNotBeFound(String filter) throws Exception {
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCreditRapportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCreditRapport() throws Exception {
        // Get the creditRapport
        restCreditRapportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCreditRapport() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();

        // Update the creditRapport
        CreditRapport updatedCreditRapport = creditRapportRepository.findById(creditRapport.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCreditRapport are not directly saved in db
        em.detach(updatedCreditRapport);
        updatedCreditRapport
            .creditScore(UPDATED_CREDIT_SCORE)
            .accountAge(UPDATED_ACCOUNT_AGE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .inquiriesAndRequests(UPDATED_INQUIRIES_AND_REQUESTS)
            .createdAt(UPDATED_CREATED_AT);
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(updatedCreditRapport);

        restCreditRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditRapportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
        CreditRapport testCreditRapport = creditRapportList.get(creditRapportList.size() - 1);
        assertThat(testCreditRapport.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCreditRapport.getAccountAge()).isEqualTo(UPDATED_ACCOUNT_AGE);
        assertThat(testCreditRapport.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testCreditRapport.getInquiriesAndRequests()).isEqualTo(UPDATED_INQUIRIES_AND_REQUESTS);
        assertThat(testCreditRapport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, creditRapportDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreditRapportWithPatch() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();

        // Update the creditRapport using partial update
        CreditRapport partialUpdatedCreditRapport = new CreditRapport();
        partialUpdatedCreditRapport.setId(creditRapport.getId());

        partialUpdatedCreditRapport.creditScore(UPDATED_CREDIT_SCORE).createdAt(UPDATED_CREATED_AT);

        restCreditRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditRapport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditRapport))
            )
            .andExpect(status().isOk());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
        CreditRapport testCreditRapport = creditRapportList.get(creditRapportList.size() - 1);
        assertThat(testCreditRapport.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCreditRapport.getAccountAge()).isEqualTo(DEFAULT_ACCOUNT_AGE);
        assertThat(testCreditRapport.getCreditLimit()).isEqualTo(DEFAULT_CREDIT_LIMIT);
        assertThat(testCreditRapport.getInquiriesAndRequests()).isEqualTo(DEFAULT_INQUIRIES_AND_REQUESTS);
        assertThat(testCreditRapport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateCreditRapportWithPatch() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();

        // Update the creditRapport using partial update
        CreditRapport partialUpdatedCreditRapport = new CreditRapport();
        partialUpdatedCreditRapport.setId(creditRapport.getId());

        partialUpdatedCreditRapport
            .creditScore(UPDATED_CREDIT_SCORE)
            .accountAge(UPDATED_ACCOUNT_AGE)
            .creditLimit(UPDATED_CREDIT_LIMIT)
            .inquiriesAndRequests(UPDATED_INQUIRIES_AND_REQUESTS)
            .createdAt(UPDATED_CREATED_AT);

        restCreditRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreditRapport.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreditRapport))
            )
            .andExpect(status().isOk());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
        CreditRapport testCreditRapport = creditRapportList.get(creditRapportList.size() - 1);
        assertThat(testCreditRapport.getCreditScore()).isEqualTo(UPDATED_CREDIT_SCORE);
        assertThat(testCreditRapport.getAccountAge()).isEqualTo(UPDATED_ACCOUNT_AGE);
        assertThat(testCreditRapport.getCreditLimit()).isEqualTo(UPDATED_CREDIT_LIMIT);
        assertThat(testCreditRapport.getInquiriesAndRequests()).isEqualTo(UPDATED_INQUIRIES_AND_REQUESTS);
        assertThat(testCreditRapport.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, creditRapportDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreditRapport() throws Exception {
        int databaseSizeBeforeUpdate = creditRapportRepository.findAll().size();
        creditRapport.setId(longCount.incrementAndGet());

        // Create the CreditRapport
        CreditRapportDTO creditRapportDTO = creditRapportMapper.toDto(creditRapport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreditRapportMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(creditRapportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreditRapport in the database
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreditRapport() throws Exception {
        // Initialize the database
        creditRapportRepository.saveAndFlush(creditRapport);

        int databaseSizeBeforeDelete = creditRapportRepository.findAll().size();

        // Delete the creditRapport
        restCreditRapportMockMvc
            .perform(delete(ENTITY_API_URL_ID, creditRapport.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreditRapport> creditRapportList = creditRapportRepository.findAll();
        assertThat(creditRapportList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
