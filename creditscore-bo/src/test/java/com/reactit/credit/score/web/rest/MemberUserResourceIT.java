package com.reactit.credit.score.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.credit.score.IntegrationTest;
import com.reactit.credit.score.domain.Address;
import com.reactit.credit.score.domain.Claim;
import com.reactit.credit.score.domain.Contact;
import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.domain.Notification;
import com.reactit.credit.score.domain.Payment;
import com.reactit.credit.score.domain.enumeration.AcountType;
import com.reactit.credit.score.domain.enumeration.IdentifierType;
import com.reactit.credit.score.domain.enumeration.Role;
import com.reactit.credit.score.repository.MemberUserRepository;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import com.reactit.credit.score.service.mapper.MemberUserMapper;
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
 * Integration tests for the {@link MemberUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MemberUserResourceIT {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BUSINESS_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BUSINESS_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTH_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTH_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final AcountType DEFAULT_ACOUNT_TYPE = AcountType.PHYSICAL_PERSON;
    private static final AcountType UPDATED_ACOUNT_TYPE = AcountType.CORPORATION;

    private static final IdentifierType DEFAULT_IDENTIFIER_TYPE = IdentifierType.CIN;
    private static final IdentifierType UPDATED_IDENTIFIER_TYPE = IdentifierType.PASSPORT;

    private static final String DEFAULT_IDENTIFIER_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYERS_REPORTED = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYERS_REPORTED = "BBBBBBBBBB";

    private static final String DEFAULT_INCOME = "AAAAAAAAAA";
    private static final String UPDATED_INCOME = "BBBBBBBBBB";

    private static final String DEFAULT_EXPENSES = "AAAAAAAAAA";
    private static final String UPDATED_EXPENSES = "BBBBBBBBBB";

    private static final String DEFAULT_GROSS_PROFIT = "AAAAAAAAAA";
    private static final String UPDATED_GROSS_PROFIT = "BBBBBBBBBB";

    private static final String DEFAULT_NET_PROFIT_MARGIN = "AAAAAAAAAA";
    private static final String UPDATED_NET_PROFIT_MARGIN = "BBBBBBBBBB";

    private static final String DEFAULT_DEBTS_OBLIGATIONS = "AAAAAAAAAA";
    private static final String UPDATED_DEBTS_OBLIGATIONS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Role DEFAULT_ROLE = Role.CUSTOMER;
    private static final Role UPDATED_ROLE = Role.COMMERCANT;

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/member-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MemberUserRepository memberUserRepository;

    @Autowired
    private MemberUserMapper memberUserMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMemberUserMockMvc;

    private MemberUser memberUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberUser createEntity(EntityManager em) {
        MemberUser memberUser = new MemberUser()
            .userName(DEFAULT_USER_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .businessName(DEFAULT_BUSINESS_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .acountType(DEFAULT_ACOUNT_TYPE)
            .identifierType(DEFAULT_IDENTIFIER_TYPE)
            .identifierValue(DEFAULT_IDENTIFIER_VALUE)
            .employersReported(DEFAULT_EMPLOYERS_REPORTED)
            .income(DEFAULT_INCOME)
            .expenses(DEFAULT_EXPENSES)
            .grossProfit(DEFAULT_GROSS_PROFIT)
            .netProfitMargin(DEFAULT_NET_PROFIT_MARGIN)
            .debtsObligations(DEFAULT_DEBTS_OBLIGATIONS)
            .enabled(DEFAULT_ENABLED)
            .role(DEFAULT_ROLE)
            .createdAt(DEFAULT_CREATED_AT);
        return memberUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MemberUser createUpdatedEntity(EntityManager em) {
        MemberUser memberUser = new MemberUser()
            .userName(UPDATED_USER_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .businessName(UPDATED_BUSINESS_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .acountType(UPDATED_ACOUNT_TYPE)
            .identifierType(UPDATED_IDENTIFIER_TYPE)
            .identifierValue(UPDATED_IDENTIFIER_VALUE)
            .employersReported(UPDATED_EMPLOYERS_REPORTED)
            .income(UPDATED_INCOME)
            .expenses(UPDATED_EXPENSES)
            .grossProfit(UPDATED_GROSS_PROFIT)
            .netProfitMargin(UPDATED_NET_PROFIT_MARGIN)
            .debtsObligations(UPDATED_DEBTS_OBLIGATIONS)
            .enabled(UPDATED_ENABLED)
            .role(UPDATED_ROLE)
            .createdAt(UPDATED_CREATED_AT);
        return memberUser;
    }

    @BeforeEach
    public void initTest() {
        memberUser = createEntity(em);
    }

    @Test
    @Transactional
    void createMemberUser() throws Exception {
        int databaseSizeBeforeCreate = memberUserRepository.findAll().size();
        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);
        restMemberUserMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeCreate + 1);
        MemberUser testMemberUser = memberUserList.get(memberUserList.size() - 1);
        assertThat(testMemberUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMemberUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMemberUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMemberUser.getBusinessName()).isEqualTo(DEFAULT_BUSINESS_NAME);
        assertThat(testMemberUser.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testMemberUser.getAcountType()).isEqualTo(DEFAULT_ACOUNT_TYPE);
        assertThat(testMemberUser.getIdentifierType()).isEqualTo(DEFAULT_IDENTIFIER_TYPE);
        assertThat(testMemberUser.getIdentifierValue()).isEqualTo(DEFAULT_IDENTIFIER_VALUE);
        assertThat(testMemberUser.getEmployersReported()).isEqualTo(DEFAULT_EMPLOYERS_REPORTED);
        assertThat(testMemberUser.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testMemberUser.getExpenses()).isEqualTo(DEFAULT_EXPENSES);
        assertThat(testMemberUser.getGrossProfit()).isEqualTo(DEFAULT_GROSS_PROFIT);
        assertThat(testMemberUser.getNetProfitMargin()).isEqualTo(DEFAULT_NET_PROFIT_MARGIN);
        assertThat(testMemberUser.getDebtsObligations()).isEqualTo(DEFAULT_DEBTS_OBLIGATIONS);
        assertThat(testMemberUser.getEnabled()).isEqualTo(DEFAULT_ENABLED);
        assertThat(testMemberUser.getRole()).isEqualTo(DEFAULT_ROLE);
        assertThat(testMemberUser.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createMemberUserWithExistingId() throws Exception {
        // Create the MemberUser with an existing ID
        memberUser.setId(1L);
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        int databaseSizeBeforeCreate = memberUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMemberUserMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllMemberUsers() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].acountType").value(hasItem(DEFAULT_ACOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identifierType").value(hasItem(DEFAULT_IDENTIFIER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identifierValue").value(hasItem(DEFAULT_IDENTIFIER_VALUE)))
            .andExpect(jsonPath("$.[*].employersReported").value(hasItem(DEFAULT_EMPLOYERS_REPORTED)))
            .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME)))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES)))
            .andExpect(jsonPath("$.[*].grossProfit").value(hasItem(DEFAULT_GROSS_PROFIT)))
            .andExpect(jsonPath("$.[*].netProfitMargin").value(hasItem(DEFAULT_NET_PROFIT_MARGIN)))
            .andExpect(jsonPath("$.[*].debtsObligations").value(hasItem(DEFAULT_DEBTS_OBLIGATIONS)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getMemberUser() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get the memberUser
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL_ID, memberUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(memberUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.businessName").value(DEFAULT_BUSINESS_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.acountType").value(DEFAULT_ACOUNT_TYPE.toString()))
            .andExpect(jsonPath("$.identifierType").value(DEFAULT_IDENTIFIER_TYPE.toString()))
            .andExpect(jsonPath("$.identifierValue").value(DEFAULT_IDENTIFIER_VALUE))
            .andExpect(jsonPath("$.employersReported").value(DEFAULT_EMPLOYERS_REPORTED))
            .andExpect(jsonPath("$.income").value(DEFAULT_INCOME))
            .andExpect(jsonPath("$.expenses").value(DEFAULT_EXPENSES))
            .andExpect(jsonPath("$.grossProfit").value(DEFAULT_GROSS_PROFIT))
            .andExpect(jsonPath("$.netProfitMargin").value(DEFAULT_NET_PROFIT_MARGIN))
            .andExpect(jsonPath("$.debtsObligations").value(DEFAULT_DEBTS_OBLIGATIONS))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getMemberUsersByIdFiltering() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        Long id = memberUser.getId();

        defaultMemberUserShouldBeFound("id.equals=" + id);
        defaultMemberUserShouldNotBeFound("id.notEquals=" + id);

        defaultMemberUserShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMemberUserShouldNotBeFound("id.greaterThan=" + id);

        defaultMemberUserShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMemberUserShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMemberUsersByUserNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where userName equals to DEFAULT_USER_NAME
        defaultMemberUserShouldBeFound("userName.equals=" + DEFAULT_USER_NAME);

        // Get all the memberUserList where userName equals to UPDATED_USER_NAME
        defaultMemberUserShouldNotBeFound("userName.equals=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByUserNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where userName in DEFAULT_USER_NAME or UPDATED_USER_NAME
        defaultMemberUserShouldBeFound("userName.in=" + DEFAULT_USER_NAME + "," + UPDATED_USER_NAME);

        // Get all the memberUserList where userName equals to UPDATED_USER_NAME
        defaultMemberUserShouldNotBeFound("userName.in=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByUserNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where userName is not null
        defaultMemberUserShouldBeFound("userName.specified=true");

        // Get all the memberUserList where userName is null
        defaultMemberUserShouldNotBeFound("userName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByUserNameContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where userName contains DEFAULT_USER_NAME
        defaultMemberUserShouldBeFound("userName.contains=" + DEFAULT_USER_NAME);

        // Get all the memberUserList where userName contains UPDATED_USER_NAME
        defaultMemberUserShouldNotBeFound("userName.contains=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByUserNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where userName does not contain DEFAULT_USER_NAME
        defaultMemberUserShouldNotBeFound("userName.doesNotContain=" + DEFAULT_USER_NAME);

        // Get all the memberUserList where userName does not contain UPDATED_USER_NAME
        defaultMemberUserShouldBeFound("userName.doesNotContain=" + UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where firstName equals to DEFAULT_FIRST_NAME
        defaultMemberUserShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the memberUserList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberUserShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultMemberUserShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the memberUserList where firstName equals to UPDATED_FIRST_NAME
        defaultMemberUserShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where firstName is not null
        defaultMemberUserShouldBeFound("firstName.specified=true");

        // Get all the memberUserList where firstName is null
        defaultMemberUserShouldNotBeFound("firstName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where firstName contains DEFAULT_FIRST_NAME
        defaultMemberUserShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the memberUserList where firstName contains UPDATED_FIRST_NAME
        defaultMemberUserShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where firstName does not contain DEFAULT_FIRST_NAME
        defaultMemberUserShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the memberUserList where firstName does not contain UPDATED_FIRST_NAME
        defaultMemberUserShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where lastName equals to DEFAULT_LAST_NAME
        defaultMemberUserShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the memberUserList where lastName equals to UPDATED_LAST_NAME
        defaultMemberUserShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultMemberUserShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the memberUserList where lastName equals to UPDATED_LAST_NAME
        defaultMemberUserShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where lastName is not null
        defaultMemberUserShouldBeFound("lastName.specified=true");

        // Get all the memberUserList where lastName is null
        defaultMemberUserShouldNotBeFound("lastName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByLastNameContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where lastName contains DEFAULT_LAST_NAME
        defaultMemberUserShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the memberUserList where lastName contains UPDATED_LAST_NAME
        defaultMemberUserShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where lastName does not contain DEFAULT_LAST_NAME
        defaultMemberUserShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the memberUserList where lastName does not contain UPDATED_LAST_NAME
        defaultMemberUserShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBusinessNameIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where businessName equals to DEFAULT_BUSINESS_NAME
        defaultMemberUserShouldBeFound("businessName.equals=" + DEFAULT_BUSINESS_NAME);

        // Get all the memberUserList where businessName equals to UPDATED_BUSINESS_NAME
        defaultMemberUserShouldNotBeFound("businessName.equals=" + UPDATED_BUSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBusinessNameIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where businessName in DEFAULT_BUSINESS_NAME or UPDATED_BUSINESS_NAME
        defaultMemberUserShouldBeFound("businessName.in=" + DEFAULT_BUSINESS_NAME + "," + UPDATED_BUSINESS_NAME);

        // Get all the memberUserList where businessName equals to UPDATED_BUSINESS_NAME
        defaultMemberUserShouldNotBeFound("businessName.in=" + UPDATED_BUSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBusinessNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where businessName is not null
        defaultMemberUserShouldBeFound("businessName.specified=true");

        // Get all the memberUserList where businessName is null
        defaultMemberUserShouldNotBeFound("businessName.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByBusinessNameContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where businessName contains DEFAULT_BUSINESS_NAME
        defaultMemberUserShouldBeFound("businessName.contains=" + DEFAULT_BUSINESS_NAME);

        // Get all the memberUserList where businessName contains UPDATED_BUSINESS_NAME
        defaultMemberUserShouldNotBeFound("businessName.contains=" + UPDATED_BUSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBusinessNameNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where businessName does not contain DEFAULT_BUSINESS_NAME
        defaultMemberUserShouldNotBeFound("businessName.doesNotContain=" + DEFAULT_BUSINESS_NAME);

        // Get all the memberUserList where businessName does not contain UPDATED_BUSINESS_NAME
        defaultMemberUserShouldBeFound("businessName.doesNotContain=" + UPDATED_BUSINESS_NAME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultMemberUserShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the memberUserList where birthDate equals to UPDATED_BIRTH_DATE
        defaultMemberUserShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultMemberUserShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the memberUserList where birthDate equals to UPDATED_BIRTH_DATE
        defaultMemberUserShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where birthDate is not null
        defaultMemberUserShouldBeFound("birthDate.specified=true");

        // Get all the memberUserList where birthDate is null
        defaultMemberUserShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByAcountTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where acountType equals to DEFAULT_ACOUNT_TYPE
        defaultMemberUserShouldBeFound("acountType.equals=" + DEFAULT_ACOUNT_TYPE);

        // Get all the memberUserList where acountType equals to UPDATED_ACOUNT_TYPE
        defaultMemberUserShouldNotBeFound("acountType.equals=" + UPDATED_ACOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByAcountTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where acountType in DEFAULT_ACOUNT_TYPE or UPDATED_ACOUNT_TYPE
        defaultMemberUserShouldBeFound("acountType.in=" + DEFAULT_ACOUNT_TYPE + "," + UPDATED_ACOUNT_TYPE);

        // Get all the memberUserList where acountType equals to UPDATED_ACOUNT_TYPE
        defaultMemberUserShouldNotBeFound("acountType.in=" + UPDATED_ACOUNT_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByAcountTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where acountType is not null
        defaultMemberUserShouldBeFound("acountType.specified=true");

        // Get all the memberUserList where acountType is null
        defaultMemberUserShouldNotBeFound("acountType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierType equals to DEFAULT_IDENTIFIER_TYPE
        defaultMemberUserShouldBeFound("identifierType.equals=" + DEFAULT_IDENTIFIER_TYPE);

        // Get all the memberUserList where identifierType equals to UPDATED_IDENTIFIER_TYPE
        defaultMemberUserShouldNotBeFound("identifierType.equals=" + UPDATED_IDENTIFIER_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierTypeIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierType in DEFAULT_IDENTIFIER_TYPE or UPDATED_IDENTIFIER_TYPE
        defaultMemberUserShouldBeFound("identifierType.in=" + DEFAULT_IDENTIFIER_TYPE + "," + UPDATED_IDENTIFIER_TYPE);

        // Get all the memberUserList where identifierType equals to UPDATED_IDENTIFIER_TYPE
        defaultMemberUserShouldNotBeFound("identifierType.in=" + UPDATED_IDENTIFIER_TYPE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierType is not null
        defaultMemberUserShouldBeFound("identifierType.specified=true");

        // Get all the memberUserList where identifierType is null
        defaultMemberUserShouldNotBeFound("identifierType.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierValueIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierValue equals to DEFAULT_IDENTIFIER_VALUE
        defaultMemberUserShouldBeFound("identifierValue.equals=" + DEFAULT_IDENTIFIER_VALUE);

        // Get all the memberUserList where identifierValue equals to UPDATED_IDENTIFIER_VALUE
        defaultMemberUserShouldNotBeFound("identifierValue.equals=" + UPDATED_IDENTIFIER_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierValueIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierValue in DEFAULT_IDENTIFIER_VALUE or UPDATED_IDENTIFIER_VALUE
        defaultMemberUserShouldBeFound("identifierValue.in=" + DEFAULT_IDENTIFIER_VALUE + "," + UPDATED_IDENTIFIER_VALUE);

        // Get all the memberUserList where identifierValue equals to UPDATED_IDENTIFIER_VALUE
        defaultMemberUserShouldNotBeFound("identifierValue.in=" + UPDATED_IDENTIFIER_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierValue is not null
        defaultMemberUserShouldBeFound("identifierValue.specified=true");

        // Get all the memberUserList where identifierValue is null
        defaultMemberUserShouldNotBeFound("identifierValue.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierValueContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierValue contains DEFAULT_IDENTIFIER_VALUE
        defaultMemberUserShouldBeFound("identifierValue.contains=" + DEFAULT_IDENTIFIER_VALUE);

        // Get all the memberUserList where identifierValue contains UPDATED_IDENTIFIER_VALUE
        defaultMemberUserShouldNotBeFound("identifierValue.contains=" + UPDATED_IDENTIFIER_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIdentifierValueNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where identifierValue does not contain DEFAULT_IDENTIFIER_VALUE
        defaultMemberUserShouldNotBeFound("identifierValue.doesNotContain=" + DEFAULT_IDENTIFIER_VALUE);

        // Get all the memberUserList where identifierValue does not contain UPDATED_IDENTIFIER_VALUE
        defaultMemberUserShouldBeFound("identifierValue.doesNotContain=" + UPDATED_IDENTIFIER_VALUE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEmployersReportedIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where employersReported equals to DEFAULT_EMPLOYERS_REPORTED
        defaultMemberUserShouldBeFound("employersReported.equals=" + DEFAULT_EMPLOYERS_REPORTED);

        // Get all the memberUserList where employersReported equals to UPDATED_EMPLOYERS_REPORTED
        defaultMemberUserShouldNotBeFound("employersReported.equals=" + UPDATED_EMPLOYERS_REPORTED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEmployersReportedIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where employersReported in DEFAULT_EMPLOYERS_REPORTED or UPDATED_EMPLOYERS_REPORTED
        defaultMemberUserShouldBeFound("employersReported.in=" + DEFAULT_EMPLOYERS_REPORTED + "," + UPDATED_EMPLOYERS_REPORTED);

        // Get all the memberUserList where employersReported equals to UPDATED_EMPLOYERS_REPORTED
        defaultMemberUserShouldNotBeFound("employersReported.in=" + UPDATED_EMPLOYERS_REPORTED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEmployersReportedIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where employersReported is not null
        defaultMemberUserShouldBeFound("employersReported.specified=true");

        // Get all the memberUserList where employersReported is null
        defaultMemberUserShouldNotBeFound("employersReported.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByEmployersReportedContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where employersReported contains DEFAULT_EMPLOYERS_REPORTED
        defaultMemberUserShouldBeFound("employersReported.contains=" + DEFAULT_EMPLOYERS_REPORTED);

        // Get all the memberUserList where employersReported contains UPDATED_EMPLOYERS_REPORTED
        defaultMemberUserShouldNotBeFound("employersReported.contains=" + UPDATED_EMPLOYERS_REPORTED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEmployersReportedNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where employersReported does not contain DEFAULT_EMPLOYERS_REPORTED
        defaultMemberUserShouldNotBeFound("employersReported.doesNotContain=" + DEFAULT_EMPLOYERS_REPORTED);

        // Get all the memberUserList where employersReported does not contain UPDATED_EMPLOYERS_REPORTED
        defaultMemberUserShouldBeFound("employersReported.doesNotContain=" + UPDATED_EMPLOYERS_REPORTED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIncomeIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where income equals to DEFAULT_INCOME
        defaultMemberUserShouldBeFound("income.equals=" + DEFAULT_INCOME);

        // Get all the memberUserList where income equals to UPDATED_INCOME
        defaultMemberUserShouldNotBeFound("income.equals=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIncomeIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where income in DEFAULT_INCOME or UPDATED_INCOME
        defaultMemberUserShouldBeFound("income.in=" + DEFAULT_INCOME + "," + UPDATED_INCOME);

        // Get all the memberUserList where income equals to UPDATED_INCOME
        defaultMemberUserShouldNotBeFound("income.in=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIncomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where income is not null
        defaultMemberUserShouldBeFound("income.specified=true");

        // Get all the memberUserList where income is null
        defaultMemberUserShouldNotBeFound("income.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByIncomeContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where income contains DEFAULT_INCOME
        defaultMemberUserShouldBeFound("income.contains=" + DEFAULT_INCOME);

        // Get all the memberUserList where income contains UPDATED_INCOME
        defaultMemberUserShouldNotBeFound("income.contains=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByIncomeNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where income does not contain DEFAULT_INCOME
        defaultMemberUserShouldNotBeFound("income.doesNotContain=" + DEFAULT_INCOME);

        // Get all the memberUserList where income does not contain UPDATED_INCOME
        defaultMemberUserShouldBeFound("income.doesNotContain=" + UPDATED_INCOME);
    }

    @Test
    @Transactional
    void getAllMemberUsersByExpensesIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where expenses equals to DEFAULT_EXPENSES
        defaultMemberUserShouldBeFound("expenses.equals=" + DEFAULT_EXPENSES);

        // Get all the memberUserList where expenses equals to UPDATED_EXPENSES
        defaultMemberUserShouldNotBeFound("expenses.equals=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllMemberUsersByExpensesIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where expenses in DEFAULT_EXPENSES or UPDATED_EXPENSES
        defaultMemberUserShouldBeFound("expenses.in=" + DEFAULT_EXPENSES + "," + UPDATED_EXPENSES);

        // Get all the memberUserList where expenses equals to UPDATED_EXPENSES
        defaultMemberUserShouldNotBeFound("expenses.in=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllMemberUsersByExpensesIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where expenses is not null
        defaultMemberUserShouldBeFound("expenses.specified=true");

        // Get all the memberUserList where expenses is null
        defaultMemberUserShouldNotBeFound("expenses.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByExpensesContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where expenses contains DEFAULT_EXPENSES
        defaultMemberUserShouldBeFound("expenses.contains=" + DEFAULT_EXPENSES);

        // Get all the memberUserList where expenses contains UPDATED_EXPENSES
        defaultMemberUserShouldNotBeFound("expenses.contains=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllMemberUsersByExpensesNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where expenses does not contain DEFAULT_EXPENSES
        defaultMemberUserShouldNotBeFound("expenses.doesNotContain=" + DEFAULT_EXPENSES);

        // Get all the memberUserList where expenses does not contain UPDATED_EXPENSES
        defaultMemberUserShouldBeFound("expenses.doesNotContain=" + UPDATED_EXPENSES);
    }

    @Test
    @Transactional
    void getAllMemberUsersByGrossProfitIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where grossProfit equals to DEFAULT_GROSS_PROFIT
        defaultMemberUserShouldBeFound("grossProfit.equals=" + DEFAULT_GROSS_PROFIT);

        // Get all the memberUserList where grossProfit equals to UPDATED_GROSS_PROFIT
        defaultMemberUserShouldNotBeFound("grossProfit.equals=" + UPDATED_GROSS_PROFIT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByGrossProfitIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where grossProfit in DEFAULT_GROSS_PROFIT or UPDATED_GROSS_PROFIT
        defaultMemberUserShouldBeFound("grossProfit.in=" + DEFAULT_GROSS_PROFIT + "," + UPDATED_GROSS_PROFIT);

        // Get all the memberUserList where grossProfit equals to UPDATED_GROSS_PROFIT
        defaultMemberUserShouldNotBeFound("grossProfit.in=" + UPDATED_GROSS_PROFIT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByGrossProfitIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where grossProfit is not null
        defaultMemberUserShouldBeFound("grossProfit.specified=true");

        // Get all the memberUserList where grossProfit is null
        defaultMemberUserShouldNotBeFound("grossProfit.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByGrossProfitContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where grossProfit contains DEFAULT_GROSS_PROFIT
        defaultMemberUserShouldBeFound("grossProfit.contains=" + DEFAULT_GROSS_PROFIT);

        // Get all the memberUserList where grossProfit contains UPDATED_GROSS_PROFIT
        defaultMemberUserShouldNotBeFound("grossProfit.contains=" + UPDATED_GROSS_PROFIT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByGrossProfitNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where grossProfit does not contain DEFAULT_GROSS_PROFIT
        defaultMemberUserShouldNotBeFound("grossProfit.doesNotContain=" + DEFAULT_GROSS_PROFIT);

        // Get all the memberUserList where grossProfit does not contain UPDATED_GROSS_PROFIT
        defaultMemberUserShouldBeFound("grossProfit.doesNotContain=" + UPDATED_GROSS_PROFIT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByNetProfitMarginIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where netProfitMargin equals to DEFAULT_NET_PROFIT_MARGIN
        defaultMemberUserShouldBeFound("netProfitMargin.equals=" + DEFAULT_NET_PROFIT_MARGIN);

        // Get all the memberUserList where netProfitMargin equals to UPDATED_NET_PROFIT_MARGIN
        defaultMemberUserShouldNotBeFound("netProfitMargin.equals=" + UPDATED_NET_PROFIT_MARGIN);
    }

    @Test
    @Transactional
    void getAllMemberUsersByNetProfitMarginIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where netProfitMargin in DEFAULT_NET_PROFIT_MARGIN or UPDATED_NET_PROFIT_MARGIN
        defaultMemberUserShouldBeFound("netProfitMargin.in=" + DEFAULT_NET_PROFIT_MARGIN + "," + UPDATED_NET_PROFIT_MARGIN);

        // Get all the memberUserList where netProfitMargin equals to UPDATED_NET_PROFIT_MARGIN
        defaultMemberUserShouldNotBeFound("netProfitMargin.in=" + UPDATED_NET_PROFIT_MARGIN);
    }

    @Test
    @Transactional
    void getAllMemberUsersByNetProfitMarginIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where netProfitMargin is not null
        defaultMemberUserShouldBeFound("netProfitMargin.specified=true");

        // Get all the memberUserList where netProfitMargin is null
        defaultMemberUserShouldNotBeFound("netProfitMargin.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByNetProfitMarginContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where netProfitMargin contains DEFAULT_NET_PROFIT_MARGIN
        defaultMemberUserShouldBeFound("netProfitMargin.contains=" + DEFAULT_NET_PROFIT_MARGIN);

        // Get all the memberUserList where netProfitMargin contains UPDATED_NET_PROFIT_MARGIN
        defaultMemberUserShouldNotBeFound("netProfitMargin.contains=" + UPDATED_NET_PROFIT_MARGIN);
    }

    @Test
    @Transactional
    void getAllMemberUsersByNetProfitMarginNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where netProfitMargin does not contain DEFAULT_NET_PROFIT_MARGIN
        defaultMemberUserShouldNotBeFound("netProfitMargin.doesNotContain=" + DEFAULT_NET_PROFIT_MARGIN);

        // Get all the memberUserList where netProfitMargin does not contain UPDATED_NET_PROFIT_MARGIN
        defaultMemberUserShouldBeFound("netProfitMargin.doesNotContain=" + UPDATED_NET_PROFIT_MARGIN);
    }

    @Test
    @Transactional
    void getAllMemberUsersByDebtsObligationsIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where debtsObligations equals to DEFAULT_DEBTS_OBLIGATIONS
        defaultMemberUserShouldBeFound("debtsObligations.equals=" + DEFAULT_DEBTS_OBLIGATIONS);

        // Get all the memberUserList where debtsObligations equals to UPDATED_DEBTS_OBLIGATIONS
        defaultMemberUserShouldNotBeFound("debtsObligations.equals=" + UPDATED_DEBTS_OBLIGATIONS);
    }

    @Test
    @Transactional
    void getAllMemberUsersByDebtsObligationsIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where debtsObligations in DEFAULT_DEBTS_OBLIGATIONS or UPDATED_DEBTS_OBLIGATIONS
        defaultMemberUserShouldBeFound("debtsObligations.in=" + DEFAULT_DEBTS_OBLIGATIONS + "," + UPDATED_DEBTS_OBLIGATIONS);

        // Get all the memberUserList where debtsObligations equals to UPDATED_DEBTS_OBLIGATIONS
        defaultMemberUserShouldNotBeFound("debtsObligations.in=" + UPDATED_DEBTS_OBLIGATIONS);
    }

    @Test
    @Transactional
    void getAllMemberUsersByDebtsObligationsIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where debtsObligations is not null
        defaultMemberUserShouldBeFound("debtsObligations.specified=true");

        // Get all the memberUserList where debtsObligations is null
        defaultMemberUserShouldNotBeFound("debtsObligations.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByDebtsObligationsContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where debtsObligations contains DEFAULT_DEBTS_OBLIGATIONS
        defaultMemberUserShouldBeFound("debtsObligations.contains=" + DEFAULT_DEBTS_OBLIGATIONS);

        // Get all the memberUserList where debtsObligations contains UPDATED_DEBTS_OBLIGATIONS
        defaultMemberUserShouldNotBeFound("debtsObligations.contains=" + UPDATED_DEBTS_OBLIGATIONS);
    }

    @Test
    @Transactional
    void getAllMemberUsersByDebtsObligationsNotContainsSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where debtsObligations does not contain DEFAULT_DEBTS_OBLIGATIONS
        defaultMemberUserShouldNotBeFound("debtsObligations.doesNotContain=" + DEFAULT_DEBTS_OBLIGATIONS);

        // Get all the memberUserList where debtsObligations does not contain UPDATED_DEBTS_OBLIGATIONS
        defaultMemberUserShouldBeFound("debtsObligations.doesNotContain=" + UPDATED_DEBTS_OBLIGATIONS);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEnabledIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where enabled equals to DEFAULT_ENABLED
        defaultMemberUserShouldBeFound("enabled.equals=" + DEFAULT_ENABLED);

        // Get all the memberUserList where enabled equals to UPDATED_ENABLED
        defaultMemberUserShouldNotBeFound("enabled.equals=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEnabledIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where enabled in DEFAULT_ENABLED or UPDATED_ENABLED
        defaultMemberUserShouldBeFound("enabled.in=" + DEFAULT_ENABLED + "," + UPDATED_ENABLED);

        // Get all the memberUserList where enabled equals to UPDATED_ENABLED
        defaultMemberUserShouldNotBeFound("enabled.in=" + UPDATED_ENABLED);
    }

    @Test
    @Transactional
    void getAllMemberUsersByEnabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where enabled is not null
        defaultMemberUserShouldBeFound("enabled.specified=true");

        // Get all the memberUserList where enabled is null
        defaultMemberUserShouldNotBeFound("enabled.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where role equals to DEFAULT_ROLE
        defaultMemberUserShouldBeFound("role.equals=" + DEFAULT_ROLE);

        // Get all the memberUserList where role equals to UPDATED_ROLE
        defaultMemberUserShouldNotBeFound("role.equals=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByRoleIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where role in DEFAULT_ROLE or UPDATED_ROLE
        defaultMemberUserShouldBeFound("role.in=" + DEFAULT_ROLE + "," + UPDATED_ROLE);

        // Get all the memberUserList where role equals to UPDATED_ROLE
        defaultMemberUserShouldNotBeFound("role.in=" + UPDATED_ROLE);
    }

    @Test
    @Transactional
    void getAllMemberUsersByRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where role is not null
        defaultMemberUserShouldBeFound("role.specified=true");

        // Get all the memberUserList where role is null
        defaultMemberUserShouldNotBeFound("role.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where createdAt equals to DEFAULT_CREATED_AT
        defaultMemberUserShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the memberUserList where createdAt equals to UPDATED_CREATED_AT
        defaultMemberUserShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultMemberUserShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the memberUserList where createdAt equals to UPDATED_CREATED_AT
        defaultMemberUserShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllMemberUsersByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        // Get all the memberUserList where createdAt is not null
        defaultMemberUserShouldBeFound("createdAt.specified=true");

        // Get all the memberUserList where createdAt is null
        defaultMemberUserShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllMemberUsersByCreditRapportIsEqualToSomething() throws Exception {
        CreditRapport creditRapport;
        if (TestUtil.findAll(em, CreditRapport.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            creditRapport = CreditRapportResourceIT.createEntity(em);
        } else {
            creditRapport = TestUtil.findAll(em, CreditRapport.class).get(0);
        }
        em.persist(creditRapport);
        em.flush();
        memberUser.setCreditRapport(creditRapport);
        creditRapport.setMemberUser(memberUser);
        memberUserRepository.saveAndFlush(memberUser);
        Long creditRapportId = creditRapport.getId();
        // Get all the memberUserList where creditRapport equals to creditRapportId
        defaultMemberUserShouldBeFound("creditRapportId.equals=" + creditRapportId);

        // Get all the memberUserList where creditRapport equals to (creditRapportId + 1)
        defaultMemberUserShouldNotBeFound("creditRapportId.equals=" + (creditRapportId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByInvoiceIsEqualToSomething() throws Exception {
        Invoice invoice;
        if (TestUtil.findAll(em, Invoice.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            invoice = InvoiceResourceIT.createEntity(em);
        } else {
            invoice = TestUtil.findAll(em, Invoice.class).get(0);
        }
        em.persist(invoice);
        em.flush();
        memberUser.setInvoice(invoice);
        invoice.setMemberUser(memberUser);
        memberUserRepository.saveAndFlush(memberUser);
        Long invoiceId = invoice.getId();
        // Get all the memberUserList where invoice equals to invoiceId
        defaultMemberUserShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the memberUserList where invoice equals to (invoiceId + 1)
        defaultMemberUserShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByAddressIsEqualToSomething() throws Exception {
        Address address;
        if (TestUtil.findAll(em, Address.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            address = AddressResourceIT.createEntity(em);
        } else {
            address = TestUtil.findAll(em, Address.class).get(0);
        }
        em.persist(address);
        em.flush();
        memberUser.addAddress(address);
        memberUserRepository.saveAndFlush(memberUser);
        Long addressId = address.getId();
        // Get all the memberUserList where address equals to addressId
        defaultMemberUserShouldBeFound("addressId.equals=" + addressId);

        // Get all the memberUserList where address equals to (addressId + 1)
        defaultMemberUserShouldNotBeFound("addressId.equals=" + (addressId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByPaymentIsEqualToSomething() throws Exception {
        Payment payment;
        if (TestUtil.findAll(em, Payment.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            payment = PaymentResourceIT.createEntity(em);
        } else {
            payment = TestUtil.findAll(em, Payment.class).get(0);
        }
        em.persist(payment);
        em.flush();
        memberUser.addPayment(payment);
        memberUserRepository.saveAndFlush(memberUser);
        Long paymentId = payment.getId();
        // Get all the memberUserList where payment equals to paymentId
        defaultMemberUserShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the memberUserList where payment equals to (paymentId + 1)
        defaultMemberUserShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByClaimIsEqualToSomething() throws Exception {
        Claim claim;
        if (TestUtil.findAll(em, Claim.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            claim = ClaimResourceIT.createEntity(em);
        } else {
            claim = TestUtil.findAll(em, Claim.class).get(0);
        }
        em.persist(claim);
        em.flush();
        memberUser.addClaim(claim);
        memberUserRepository.saveAndFlush(memberUser);
        Long claimId = claim.getId();
        // Get all the memberUserList where claim equals to claimId
        defaultMemberUserShouldBeFound("claimId.equals=" + claimId);

        // Get all the memberUserList where claim equals to (claimId + 1)
        defaultMemberUserShouldNotBeFound("claimId.equals=" + (claimId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByNotificationIsEqualToSomething() throws Exception {
        Notification notification;
        if (TestUtil.findAll(em, Notification.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            notification = NotificationResourceIT.createEntity(em);
        } else {
            notification = TestUtil.findAll(em, Notification.class).get(0);
        }
        em.persist(notification);
        em.flush();
        memberUser.addNotification(notification);
        memberUserRepository.saveAndFlush(memberUser);
        Long notificationId = notification.getId();
        // Get all the memberUserList where notification equals to notificationId
        defaultMemberUserShouldBeFound("notificationId.equals=" + notificationId);

        // Get all the memberUserList where notification equals to (notificationId + 1)
        defaultMemberUserShouldNotBeFound("notificationId.equals=" + (notificationId + 1));
    }

    @Test
    @Transactional
    void getAllMemberUsersByContactIsEqualToSomething() throws Exception {
        Contact contact;
        if (TestUtil.findAll(em, Contact.class).isEmpty()) {
            memberUserRepository.saveAndFlush(memberUser);
            contact = ContactResourceIT.createEntity(em);
        } else {
            contact = TestUtil.findAll(em, Contact.class).get(0);
        }
        em.persist(contact);
        em.flush();
        memberUser.addContact(contact);
        memberUserRepository.saveAndFlush(memberUser);
        Long contactId = contact.getId();
        // Get all the memberUserList where contact equals to contactId
        defaultMemberUserShouldBeFound("contactId.equals=" + contactId);

        // Get all the memberUserList where contact equals to (contactId + 1)
        defaultMemberUserShouldNotBeFound("contactId.equals=" + (contactId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMemberUserShouldBeFound(String filter) throws Exception {
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(memberUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].businessName").value(hasItem(DEFAULT_BUSINESS_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].acountType").value(hasItem(DEFAULT_ACOUNT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identifierType").value(hasItem(DEFAULT_IDENTIFIER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].identifierValue").value(hasItem(DEFAULT_IDENTIFIER_VALUE)))
            .andExpect(jsonPath("$.[*].employersReported").value(hasItem(DEFAULT_EMPLOYERS_REPORTED)))
            .andExpect(jsonPath("$.[*].income").value(hasItem(DEFAULT_INCOME)))
            .andExpect(jsonPath("$.[*].expenses").value(hasItem(DEFAULT_EXPENSES)))
            .andExpect(jsonPath("$.[*].grossProfit").value(hasItem(DEFAULT_GROSS_PROFIT)))
            .andExpect(jsonPath("$.[*].netProfitMargin").value(hasItem(DEFAULT_NET_PROFIT_MARGIN)))
            .andExpect(jsonPath("$.[*].debtsObligations").value(hasItem(DEFAULT_DEBTS_OBLIGATIONS)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMemberUserShouldNotBeFound(String filter) throws Exception {
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMemberUserMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMemberUser() throws Exception {
        // Get the memberUser
        restMemberUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMemberUser() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();

        // Update the memberUser
        MemberUser updatedMemberUser = memberUserRepository.findById(memberUser.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMemberUser are not directly saved in db
        em.detach(updatedMemberUser);
        updatedMemberUser
            .userName(UPDATED_USER_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .businessName(UPDATED_BUSINESS_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .acountType(UPDATED_ACOUNT_TYPE)
            .identifierType(UPDATED_IDENTIFIER_TYPE)
            .identifierValue(UPDATED_IDENTIFIER_VALUE)
            .employersReported(UPDATED_EMPLOYERS_REPORTED)
            .income(UPDATED_INCOME)
            .expenses(UPDATED_EXPENSES)
            .grossProfit(UPDATED_GROSS_PROFIT)
            .netProfitMargin(UPDATED_NET_PROFIT_MARGIN)
            .debtsObligations(UPDATED_DEBTS_OBLIGATIONS)
            .enabled(UPDATED_ENABLED)
            .role(UPDATED_ROLE)
            .createdAt(UPDATED_CREATED_AT);
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(updatedMemberUser);

        restMemberUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isOk());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
        MemberUser testMemberUser = memberUserList.get(memberUserList.size() - 1);
        assertThat(testMemberUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMemberUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMemberUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMemberUser.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testMemberUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testMemberUser.getAcountType()).isEqualTo(UPDATED_ACOUNT_TYPE);
        assertThat(testMemberUser.getIdentifierType()).isEqualTo(UPDATED_IDENTIFIER_TYPE);
        assertThat(testMemberUser.getIdentifierValue()).isEqualTo(UPDATED_IDENTIFIER_VALUE);
        assertThat(testMemberUser.getEmployersReported()).isEqualTo(UPDATED_EMPLOYERS_REPORTED);
        assertThat(testMemberUser.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testMemberUser.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testMemberUser.getGrossProfit()).isEqualTo(UPDATED_GROSS_PROFIT);
        assertThat(testMemberUser.getNetProfitMargin()).isEqualTo(UPDATED_NET_PROFIT_MARGIN);
        assertThat(testMemberUser.getDebtsObligations()).isEqualTo(UPDATED_DEBTS_OBLIGATIONS);
        assertThat(testMemberUser.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testMemberUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testMemberUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, memberUserDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMemberUserWithPatch() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();

        // Update the memberUser using partial update
        MemberUser partialUpdatedMemberUser = new MemberUser();
        partialUpdatedMemberUser.setId(memberUser.getId());

        partialUpdatedMemberUser
            .businessName(UPDATED_BUSINESS_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .acountType(UPDATED_ACOUNT_TYPE)
            .identifierValue(UPDATED_IDENTIFIER_VALUE)
            .debtsObligations(UPDATED_DEBTS_OBLIGATIONS)
            .enabled(UPDATED_ENABLED)
            .role(UPDATED_ROLE)
            .createdAt(UPDATED_CREATED_AT);

        restMemberUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberUser))
            )
            .andExpect(status().isOk());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
        MemberUser testMemberUser = memberUserList.get(memberUserList.size() - 1);
        assertThat(testMemberUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
        assertThat(testMemberUser.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testMemberUser.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testMemberUser.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testMemberUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testMemberUser.getAcountType()).isEqualTo(UPDATED_ACOUNT_TYPE);
        assertThat(testMemberUser.getIdentifierType()).isEqualTo(DEFAULT_IDENTIFIER_TYPE);
        assertThat(testMemberUser.getIdentifierValue()).isEqualTo(UPDATED_IDENTIFIER_VALUE);
        assertThat(testMemberUser.getEmployersReported()).isEqualTo(DEFAULT_EMPLOYERS_REPORTED);
        assertThat(testMemberUser.getIncome()).isEqualTo(DEFAULT_INCOME);
        assertThat(testMemberUser.getExpenses()).isEqualTo(DEFAULT_EXPENSES);
        assertThat(testMemberUser.getGrossProfit()).isEqualTo(DEFAULT_GROSS_PROFIT);
        assertThat(testMemberUser.getNetProfitMargin()).isEqualTo(DEFAULT_NET_PROFIT_MARGIN);
        assertThat(testMemberUser.getDebtsObligations()).isEqualTo(UPDATED_DEBTS_OBLIGATIONS);
        assertThat(testMemberUser.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testMemberUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testMemberUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateMemberUserWithPatch() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();

        // Update the memberUser using partial update
        MemberUser partialUpdatedMemberUser = new MemberUser();
        partialUpdatedMemberUser.setId(memberUser.getId());

        partialUpdatedMemberUser
            .userName(UPDATED_USER_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .businessName(UPDATED_BUSINESS_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .acountType(UPDATED_ACOUNT_TYPE)
            .identifierType(UPDATED_IDENTIFIER_TYPE)
            .identifierValue(UPDATED_IDENTIFIER_VALUE)
            .employersReported(UPDATED_EMPLOYERS_REPORTED)
            .income(UPDATED_INCOME)
            .expenses(UPDATED_EXPENSES)
            .grossProfit(UPDATED_GROSS_PROFIT)
            .netProfitMargin(UPDATED_NET_PROFIT_MARGIN)
            .debtsObligations(UPDATED_DEBTS_OBLIGATIONS)
            .enabled(UPDATED_ENABLED)
            .role(UPDATED_ROLE)
            .createdAt(UPDATED_CREATED_AT);

        restMemberUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMemberUser.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMemberUser))
            )
            .andExpect(status().isOk());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
        MemberUser testMemberUser = memberUserList.get(memberUserList.size() - 1);
        assertThat(testMemberUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
        assertThat(testMemberUser.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testMemberUser.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testMemberUser.getBusinessName()).isEqualTo(UPDATED_BUSINESS_NAME);
        assertThat(testMemberUser.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testMemberUser.getAcountType()).isEqualTo(UPDATED_ACOUNT_TYPE);
        assertThat(testMemberUser.getIdentifierType()).isEqualTo(UPDATED_IDENTIFIER_TYPE);
        assertThat(testMemberUser.getIdentifierValue()).isEqualTo(UPDATED_IDENTIFIER_VALUE);
        assertThat(testMemberUser.getEmployersReported()).isEqualTo(UPDATED_EMPLOYERS_REPORTED);
        assertThat(testMemberUser.getIncome()).isEqualTo(UPDATED_INCOME);
        assertThat(testMemberUser.getExpenses()).isEqualTo(UPDATED_EXPENSES);
        assertThat(testMemberUser.getGrossProfit()).isEqualTo(UPDATED_GROSS_PROFIT);
        assertThat(testMemberUser.getNetProfitMargin()).isEqualTo(UPDATED_NET_PROFIT_MARGIN);
        assertThat(testMemberUser.getDebtsObligations()).isEqualTo(UPDATED_DEBTS_OBLIGATIONS);
        assertThat(testMemberUser.getEnabled()).isEqualTo(UPDATED_ENABLED);
        assertThat(testMemberUser.getRole()).isEqualTo(UPDATED_ROLE);
        assertThat(testMemberUser.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, memberUserDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMemberUser() throws Exception {
        int databaseSizeBeforeUpdate = memberUserRepository.findAll().size();
        memberUser.setId(longCount.incrementAndGet());

        // Create the MemberUser
        MemberUserDTO memberUserDTO = memberUserMapper.toDto(memberUser);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMemberUserMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(memberUserDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MemberUser in the database
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMemberUser() throws Exception {
        // Initialize the database
        memberUserRepository.saveAndFlush(memberUser);

        int databaseSizeBeforeDelete = memberUserRepository.findAll().size();

        // Delete the memberUser
        restMemberUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, memberUser.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MemberUser> memberUserList = memberUserRepository.findAll();
        assertThat(memberUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
