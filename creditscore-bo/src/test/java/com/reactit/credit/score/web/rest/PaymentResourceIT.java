package com.reactit.credit.score.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.credit.score.IntegrationTest;
import com.reactit.credit.score.domain.Invoice;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.domain.Payment;
import com.reactit.credit.score.domain.Product;
import com.reactit.credit.score.domain.enumeration.PaymentType;
import com.reactit.credit.score.domain.enumeration.StatusType;
import com.reactit.credit.score.repository.PaymentRepository;
import com.reactit.credit.score.service.PaymentService;
import com.reactit.credit.score.service.dto.PaymentDTO;
import com.reactit.credit.score.service.mapper.PaymentMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PaymentResourceIT {

    private static final String DEFAULT_CHECK_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_ISSUER = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_ISSUER = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NUMBER = "BBBBBBBBBB";

    private static final Instant DEFAULT_CHECK_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CHECK_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RECIPIENT = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_OF_SIGNATURE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_SIGNATURE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final PaymentType DEFAULT_PAYMENT_METHOD = PaymentType.CHECK;
    private static final PaymentType UPDATED_PAYMENT_METHOD = PaymentType.CASH;

    private static final String DEFAULT_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_AMOUNT = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPECTED_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EXPECTED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_PAYMENT_MADE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_PAYMENT_MADE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final StatusType DEFAULT_STATUS = StatusType.PENDING;
    private static final StatusType UPDATED_STATUS = StatusType.IN_PROGRESS;

    private static final String DEFAULT_CURRENCY = "AAAAAAAAAA";
    private static final String UPDATED_CURRENCY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/payments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentRepository paymentRepositoryMock;

    @Autowired
    private PaymentMapper paymentMapper;

    @Mock
    private PaymentService paymentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .checkNumber(DEFAULT_CHECK_NUMBER)
            .checkIssuer(DEFAULT_CHECK_ISSUER)
            .accountNumber(DEFAULT_ACCOUNT_NUMBER)
            .checkDate(DEFAULT_CHECK_DATE)
            .recipient(DEFAULT_RECIPIENT)
            .dateOfSignature(DEFAULT_DATE_OF_SIGNATURE)
            .paymentMethod(DEFAULT_PAYMENT_METHOD)
            .amount(DEFAULT_AMOUNT)
            .expectedPaymentDate(DEFAULT_EXPECTED_PAYMENT_DATE)
            .datePaymentMade(DEFAULT_DATE_PAYMENT_MADE)
            .status(DEFAULT_STATUS)
            .currency(DEFAULT_CURRENCY)
            .createdAt(DEFAULT_CREATED_AT);
        return payment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkIssuer(UPDATED_CHECK_ISSUER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .checkDate(UPDATED_CHECK_DATE)
            .recipient(UPDATED_RECIPIENT)
            .dateOfSignature(UPDATED_DATE_OF_SIGNATURE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .amount(UPDATED_AMOUNT)
            .expectedPaymentDate(UPDATED_EXPECTED_PAYMENT_DATE)
            .datePaymentMade(UPDATED_DATE_PAYMENT_MADE)
            .status(UPDATED_STATUS)
            .currency(UPDATED_CURRENCY)
            .createdAt(UPDATED_CREATED_AT);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);
        restPaymentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getCheckNumber()).isEqualTo(DEFAULT_CHECK_NUMBER);
        assertThat(testPayment.getCheckIssuer()).isEqualTo(DEFAULT_CHECK_ISSUER);
        assertThat(testPayment.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPayment.getCheckDate()).isEqualTo(DEFAULT_CHECK_DATE);
        assertThat(testPayment.getRecipient()).isEqualTo(DEFAULT_RECIPIENT);
        assertThat(testPayment.getDateOfSignature()).isEqualTo(DEFAULT_DATE_OF_SIGNATURE);
        assertThat(testPayment.getPaymentMethod()).isEqualTo(DEFAULT_PAYMENT_METHOD);
        assertThat(testPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayment.getExpectedPaymentDate()).isEqualTo(DEFAULT_EXPECTED_PAYMENT_DATE);
        assertThat(testPayment.getDatePaymentMade()).isEqualTo(DEFAULT_DATE_PAYMENT_MADE);
        assertThat(testPayment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPayment.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testPayment.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createPaymentWithExistingId() throws Exception {
        // Create the Payment with an existing ID
        payment.setId(1L);
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].checkIssuer").value(hasItem(DEFAULT_CHECK_ISSUER)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].checkDate").value(hasItem(DEFAULT_CHECK_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipient").value(hasItem(DEFAULT_RECIPIENT)))
            .andExpect(jsonPath("$.[*].dateOfSignature").value(hasItem(DEFAULT_DATE_OF_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].expectedPaymentDate").value(hasItem(DEFAULT_EXPECTED_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePaymentMade").value(hasItem(DEFAULT_DATE_PAYMENT_MADE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(paymentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(paymentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPaymentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(paymentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPaymentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(paymentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL_ID, payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.checkNumber").value(DEFAULT_CHECK_NUMBER))
            .andExpect(jsonPath("$.checkIssuer").value(DEFAULT_CHECK_ISSUER))
            .andExpect(jsonPath("$.accountNumber").value(DEFAULT_ACCOUNT_NUMBER))
            .andExpect(jsonPath("$.checkDate").value(DEFAULT_CHECK_DATE.toString()))
            .andExpect(jsonPath("$.recipient").value(DEFAULT_RECIPIENT))
            .andExpect(jsonPath("$.dateOfSignature").value(DEFAULT_DATE_OF_SIGNATURE.toString()))
            .andExpect(jsonPath("$.paymentMethod").value(DEFAULT_PAYMENT_METHOD.toString()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT))
            .andExpect(jsonPath("$.expectedPaymentDate").value(DEFAULT_EXPECTED_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.datePaymentMade").value(DEFAULT_DATE_PAYMENT_MADE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        Long id = payment.getId();

        defaultPaymentShouldBeFound("id.equals=" + id);
        defaultPaymentShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkNumber equals to DEFAULT_CHECK_NUMBER
        defaultPaymentShouldBeFound("checkNumber.equals=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultPaymentShouldNotBeFound("checkNumber.equals=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkNumber in DEFAULT_CHECK_NUMBER or UPDATED_CHECK_NUMBER
        defaultPaymentShouldBeFound("checkNumber.in=" + DEFAULT_CHECK_NUMBER + "," + UPDATED_CHECK_NUMBER);

        // Get all the paymentList where checkNumber equals to UPDATED_CHECK_NUMBER
        defaultPaymentShouldNotBeFound("checkNumber.in=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkNumber is not null
        defaultPaymentShouldBeFound("checkNumber.specified=true");

        // Get all the paymentList where checkNumber is null
        defaultPaymentShouldNotBeFound("checkNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkNumber contains DEFAULT_CHECK_NUMBER
        defaultPaymentShouldBeFound("checkNumber.contains=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentList where checkNumber contains UPDATED_CHECK_NUMBER
        defaultPaymentShouldNotBeFound("checkNumber.contains=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkNumber does not contain DEFAULT_CHECK_NUMBER
        defaultPaymentShouldNotBeFound("checkNumber.doesNotContain=" + DEFAULT_CHECK_NUMBER);

        // Get all the paymentList where checkNumber does not contain UPDATED_CHECK_NUMBER
        defaultPaymentShouldBeFound("checkNumber.doesNotContain=" + UPDATED_CHECK_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckIssuerIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkIssuer equals to DEFAULT_CHECK_ISSUER
        defaultPaymentShouldBeFound("checkIssuer.equals=" + DEFAULT_CHECK_ISSUER);

        // Get all the paymentList where checkIssuer equals to UPDATED_CHECK_ISSUER
        defaultPaymentShouldNotBeFound("checkIssuer.equals=" + UPDATED_CHECK_ISSUER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckIssuerIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkIssuer in DEFAULT_CHECK_ISSUER or UPDATED_CHECK_ISSUER
        defaultPaymentShouldBeFound("checkIssuer.in=" + DEFAULT_CHECK_ISSUER + "," + UPDATED_CHECK_ISSUER);

        // Get all the paymentList where checkIssuer equals to UPDATED_CHECK_ISSUER
        defaultPaymentShouldNotBeFound("checkIssuer.in=" + UPDATED_CHECK_ISSUER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckIssuerIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkIssuer is not null
        defaultPaymentShouldBeFound("checkIssuer.specified=true");

        // Get all the paymentList where checkIssuer is null
        defaultPaymentShouldNotBeFound("checkIssuer.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckIssuerContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkIssuer contains DEFAULT_CHECK_ISSUER
        defaultPaymentShouldBeFound("checkIssuer.contains=" + DEFAULT_CHECK_ISSUER);

        // Get all the paymentList where checkIssuer contains UPDATED_CHECK_ISSUER
        defaultPaymentShouldNotBeFound("checkIssuer.contains=" + UPDATED_CHECK_ISSUER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckIssuerNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkIssuer does not contain DEFAULT_CHECK_ISSUER
        defaultPaymentShouldNotBeFound("checkIssuer.doesNotContain=" + DEFAULT_CHECK_ISSUER);

        // Get all the paymentList where checkIssuer does not contain UPDATED_CHECK_ISSUER
        defaultPaymentShouldBeFound("checkIssuer.doesNotContain=" + UPDATED_CHECK_ISSUER);
    }

    @Test
    @Transactional
    void getAllPaymentsByAccountNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where accountNumber equals to DEFAULT_ACCOUNT_NUMBER
        defaultPaymentShouldBeFound("accountNumber.equals=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentShouldNotBeFound("accountNumber.equals=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByAccountNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where accountNumber in DEFAULT_ACCOUNT_NUMBER or UPDATED_ACCOUNT_NUMBER
        defaultPaymentShouldBeFound("accountNumber.in=" + DEFAULT_ACCOUNT_NUMBER + "," + UPDATED_ACCOUNT_NUMBER);

        // Get all the paymentList where accountNumber equals to UPDATED_ACCOUNT_NUMBER
        defaultPaymentShouldNotBeFound("accountNumber.in=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByAccountNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where accountNumber is not null
        defaultPaymentShouldBeFound("accountNumber.specified=true");

        // Get all the paymentList where accountNumber is null
        defaultPaymentShouldNotBeFound("accountNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByAccountNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where accountNumber contains DEFAULT_ACCOUNT_NUMBER
        defaultPaymentShouldBeFound("accountNumber.contains=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentList where accountNumber contains UPDATED_ACCOUNT_NUMBER
        defaultPaymentShouldNotBeFound("accountNumber.contains=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByAccountNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where accountNumber does not contain DEFAULT_ACCOUNT_NUMBER
        defaultPaymentShouldNotBeFound("accountNumber.doesNotContain=" + DEFAULT_ACCOUNT_NUMBER);

        // Get all the paymentList where accountNumber does not contain UPDATED_ACCOUNT_NUMBER
        defaultPaymentShouldBeFound("accountNumber.doesNotContain=" + UPDATED_ACCOUNT_NUMBER);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkDate equals to DEFAULT_CHECK_DATE
        defaultPaymentShouldBeFound("checkDate.equals=" + DEFAULT_CHECK_DATE);

        // Get all the paymentList where checkDate equals to UPDATED_CHECK_DATE
        defaultPaymentShouldNotBeFound("checkDate.equals=" + UPDATED_CHECK_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkDate in DEFAULT_CHECK_DATE or UPDATED_CHECK_DATE
        defaultPaymentShouldBeFound("checkDate.in=" + DEFAULT_CHECK_DATE + "," + UPDATED_CHECK_DATE);

        // Get all the paymentList where checkDate equals to UPDATED_CHECK_DATE
        defaultPaymentShouldNotBeFound("checkDate.in=" + UPDATED_CHECK_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByCheckDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where checkDate is not null
        defaultPaymentShouldBeFound("checkDate.specified=true");

        // Get all the paymentList where checkDate is null
        defaultPaymentShouldNotBeFound("checkDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByRecipientIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where recipient equals to DEFAULT_RECIPIENT
        defaultPaymentShouldBeFound("recipient.equals=" + DEFAULT_RECIPIENT);

        // Get all the paymentList where recipient equals to UPDATED_RECIPIENT
        defaultPaymentShouldNotBeFound("recipient.equals=" + UPDATED_RECIPIENT);
    }

    @Test
    @Transactional
    void getAllPaymentsByRecipientIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where recipient in DEFAULT_RECIPIENT or UPDATED_RECIPIENT
        defaultPaymentShouldBeFound("recipient.in=" + DEFAULT_RECIPIENT + "," + UPDATED_RECIPIENT);

        // Get all the paymentList where recipient equals to UPDATED_RECIPIENT
        defaultPaymentShouldNotBeFound("recipient.in=" + UPDATED_RECIPIENT);
    }

    @Test
    @Transactional
    void getAllPaymentsByRecipientIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where recipient is not null
        defaultPaymentShouldBeFound("recipient.specified=true");

        // Get all the paymentList where recipient is null
        defaultPaymentShouldNotBeFound("recipient.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByRecipientContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where recipient contains DEFAULT_RECIPIENT
        defaultPaymentShouldBeFound("recipient.contains=" + DEFAULT_RECIPIENT);

        // Get all the paymentList where recipient contains UPDATED_RECIPIENT
        defaultPaymentShouldNotBeFound("recipient.contains=" + UPDATED_RECIPIENT);
    }

    @Test
    @Transactional
    void getAllPaymentsByRecipientNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where recipient does not contain DEFAULT_RECIPIENT
        defaultPaymentShouldNotBeFound("recipient.doesNotContain=" + DEFAULT_RECIPIENT);

        // Get all the paymentList where recipient does not contain UPDATED_RECIPIENT
        defaultPaymentShouldBeFound("recipient.doesNotContain=" + UPDATED_RECIPIENT);
    }

    @Test
    @Transactional
    void getAllPaymentsByDateOfSignatureIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where dateOfSignature equals to DEFAULT_DATE_OF_SIGNATURE
        defaultPaymentShouldBeFound("dateOfSignature.equals=" + DEFAULT_DATE_OF_SIGNATURE);

        // Get all the paymentList where dateOfSignature equals to UPDATED_DATE_OF_SIGNATURE
        defaultPaymentShouldNotBeFound("dateOfSignature.equals=" + UPDATED_DATE_OF_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllPaymentsByDateOfSignatureIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where dateOfSignature in DEFAULT_DATE_OF_SIGNATURE or UPDATED_DATE_OF_SIGNATURE
        defaultPaymentShouldBeFound("dateOfSignature.in=" + DEFAULT_DATE_OF_SIGNATURE + "," + UPDATED_DATE_OF_SIGNATURE);

        // Get all the paymentList where dateOfSignature equals to UPDATED_DATE_OF_SIGNATURE
        defaultPaymentShouldNotBeFound("dateOfSignature.in=" + UPDATED_DATE_OF_SIGNATURE);
    }

    @Test
    @Transactional
    void getAllPaymentsByDateOfSignatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where dateOfSignature is not null
        defaultPaymentShouldBeFound("dateOfSignature.specified=true");

        // Get all the paymentList where dateOfSignature is null
        defaultPaymentShouldNotBeFound("dateOfSignature.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentMethod equals to DEFAULT_PAYMENT_METHOD
        defaultPaymentShouldBeFound("paymentMethod.equals=" + DEFAULT_PAYMENT_METHOD);

        // Get all the paymentList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultPaymentShouldNotBeFound("paymentMethod.equals=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentMethodIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentMethod in DEFAULT_PAYMENT_METHOD or UPDATED_PAYMENT_METHOD
        defaultPaymentShouldBeFound("paymentMethod.in=" + DEFAULT_PAYMENT_METHOD + "," + UPDATED_PAYMENT_METHOD);

        // Get all the paymentList where paymentMethod equals to UPDATED_PAYMENT_METHOD
        defaultPaymentShouldNotBeFound("paymentMethod.in=" + UPDATED_PAYMENT_METHOD);
    }

    @Test
    @Transactional
    void getAllPaymentsByPaymentMethodIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentMethod is not null
        defaultPaymentShouldBeFound("paymentMethod.specified=true");

        // Get all the paymentList where paymentMethod is null
        defaultPaymentShouldNotBeFound("paymentMethod.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount equals to DEFAULT_AMOUNT
        defaultPaymentShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount equals to UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPaymentShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the paymentList where amount equals to UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is not null
        defaultPaymentShouldBeFound("amount.specified=true");

        // Get all the paymentList where amount is null
        defaultPaymentShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount contains DEFAULT_AMOUNT
        defaultPaymentShouldBeFound("amount.contains=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount contains UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.contains=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByAmountNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount does not contain DEFAULT_AMOUNT
        defaultPaymentShouldNotBeFound("amount.doesNotContain=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount does not contain UPDATED_AMOUNT
        defaultPaymentShouldBeFound("amount.doesNotContain=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    void getAllPaymentsByExpectedPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expectedPaymentDate equals to DEFAULT_EXPECTED_PAYMENT_DATE
        defaultPaymentShouldBeFound("expectedPaymentDate.equals=" + DEFAULT_EXPECTED_PAYMENT_DATE);

        // Get all the paymentList where expectedPaymentDate equals to UPDATED_EXPECTED_PAYMENT_DATE
        defaultPaymentShouldNotBeFound("expectedPaymentDate.equals=" + UPDATED_EXPECTED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByExpectedPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expectedPaymentDate in DEFAULT_EXPECTED_PAYMENT_DATE or UPDATED_EXPECTED_PAYMENT_DATE
        defaultPaymentShouldBeFound("expectedPaymentDate.in=" + DEFAULT_EXPECTED_PAYMENT_DATE + "," + UPDATED_EXPECTED_PAYMENT_DATE);

        // Get all the paymentList where expectedPaymentDate equals to UPDATED_EXPECTED_PAYMENT_DATE
        defaultPaymentShouldNotBeFound("expectedPaymentDate.in=" + UPDATED_EXPECTED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    void getAllPaymentsByExpectedPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where expectedPaymentDate is not null
        defaultPaymentShouldBeFound("expectedPaymentDate.specified=true");

        // Get all the paymentList where expectedPaymentDate is null
        defaultPaymentShouldNotBeFound("expectedPaymentDate.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByDatePaymentMadeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where datePaymentMade equals to DEFAULT_DATE_PAYMENT_MADE
        defaultPaymentShouldBeFound("datePaymentMade.equals=" + DEFAULT_DATE_PAYMENT_MADE);

        // Get all the paymentList where datePaymentMade equals to UPDATED_DATE_PAYMENT_MADE
        defaultPaymentShouldNotBeFound("datePaymentMade.equals=" + UPDATED_DATE_PAYMENT_MADE);
    }

    @Test
    @Transactional
    void getAllPaymentsByDatePaymentMadeIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where datePaymentMade in DEFAULT_DATE_PAYMENT_MADE or UPDATED_DATE_PAYMENT_MADE
        defaultPaymentShouldBeFound("datePaymentMade.in=" + DEFAULT_DATE_PAYMENT_MADE + "," + UPDATED_DATE_PAYMENT_MADE);

        // Get all the paymentList where datePaymentMade equals to UPDATED_DATE_PAYMENT_MADE
        defaultPaymentShouldNotBeFound("datePaymentMade.in=" + UPDATED_DATE_PAYMENT_MADE);
    }

    @Test
    @Transactional
    void getAllPaymentsByDatePaymentMadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where datePaymentMade is not null
        defaultPaymentShouldBeFound("datePaymentMade.specified=true");

        // Get all the paymentList where datePaymentMade is null
        defaultPaymentShouldNotBeFound("datePaymentMade.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where status equals to DEFAULT_STATUS
        defaultPaymentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the paymentList where status equals to UPDATED_STATUS
        defaultPaymentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPaymentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the paymentList where status equals to UPDATED_STATUS
        defaultPaymentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllPaymentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where status is not null
        defaultPaymentShouldBeFound("status.specified=true");

        // Get all the paymentList where status is null
        defaultPaymentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCurrencyIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where currency equals to DEFAULT_CURRENCY
        defaultPaymentShouldBeFound("currency.equals=" + DEFAULT_CURRENCY);

        // Get all the paymentList where currency equals to UPDATED_CURRENCY
        defaultPaymentShouldNotBeFound("currency.equals=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void getAllPaymentsByCurrencyIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where currency in DEFAULT_CURRENCY or UPDATED_CURRENCY
        defaultPaymentShouldBeFound("currency.in=" + DEFAULT_CURRENCY + "," + UPDATED_CURRENCY);

        // Get all the paymentList where currency equals to UPDATED_CURRENCY
        defaultPaymentShouldNotBeFound("currency.in=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void getAllPaymentsByCurrencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where currency is not null
        defaultPaymentShouldBeFound("currency.specified=true");

        // Get all the paymentList where currency is null
        defaultPaymentShouldNotBeFound("currency.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByCurrencyContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where currency contains DEFAULT_CURRENCY
        defaultPaymentShouldBeFound("currency.contains=" + DEFAULT_CURRENCY);

        // Get all the paymentList where currency contains UPDATED_CURRENCY
        defaultPaymentShouldNotBeFound("currency.contains=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void getAllPaymentsByCurrencyNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where currency does not contain DEFAULT_CURRENCY
        defaultPaymentShouldNotBeFound("currency.doesNotContain=" + DEFAULT_CURRENCY);

        // Get all the paymentList where currency does not contain UPDATED_CURRENCY
        defaultPaymentShouldBeFound("currency.doesNotContain=" + UPDATED_CURRENCY);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdAt equals to DEFAULT_CREATED_AT
        defaultPaymentShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the paymentList where createdAt equals to UPDATED_CREATED_AT
        defaultPaymentShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultPaymentShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the paymentList where createdAt equals to UPDATED_CREATED_AT
        defaultPaymentShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllPaymentsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where createdAt is not null
        defaultPaymentShouldBeFound("createdAt.specified=true");

        // Get all the paymentList where createdAt is null
        defaultPaymentShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllPaymentsByMemberUserIsEqualToSomething() throws Exception {
        MemberUser memberUser;
        if (TestUtil.findAll(em, MemberUser.class).isEmpty()) {
            paymentRepository.saveAndFlush(payment);
            memberUser = MemberUserResourceIT.createEntity(em);
        } else {
            memberUser = TestUtil.findAll(em, MemberUser.class).get(0);
        }
        em.persist(memberUser);
        em.flush();
        payment.setMemberUser(memberUser);
        paymentRepository.saveAndFlush(payment);
        Long memberUserId = memberUser.getId();
        // Get all the paymentList where memberUser equals to memberUserId
        defaultPaymentShouldBeFound("memberUserId.equals=" + memberUserId);

        // Get all the paymentList where memberUser equals to (memberUserId + 1)
        defaultPaymentShouldNotBeFound("memberUserId.equals=" + (memberUserId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentsByProductIsEqualToSomething() throws Exception {
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            paymentRepository.saveAndFlush(payment);
            product = ProductResourceIT.createEntity(em);
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        em.persist(product);
        em.flush();
        payment.addProduct(product);
        paymentRepository.saveAndFlush(payment);
        Long productId = product.getId();
        // Get all the paymentList where product equals to productId
        defaultPaymentShouldBeFound("productId.equals=" + productId);

        // Get all the paymentList where product equals to (productId + 1)
        defaultPaymentShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllPaymentsByInvoiceIsEqualToSomething() throws Exception {
        Invoice invoice;
        if (TestUtil.findAll(em, Invoice.class).isEmpty()) {
            paymentRepository.saveAndFlush(payment);
            invoice = InvoiceResourceIT.createEntity(em);
        } else {
            invoice = TestUtil.findAll(em, Invoice.class).get(0);
        }
        em.persist(invoice);
        em.flush();
        payment.setInvoice(invoice);
        paymentRepository.saveAndFlush(payment);
        Long invoiceId = invoice.getId();
        // Get all the paymentList where invoice equals to invoiceId
        defaultPaymentShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the paymentList where invoice equals to (invoiceId + 1)
        defaultPaymentShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].checkNumber").value(hasItem(DEFAULT_CHECK_NUMBER)))
            .andExpect(jsonPath("$.[*].checkIssuer").value(hasItem(DEFAULT_CHECK_ISSUER)))
            .andExpect(jsonPath("$.[*].accountNumber").value(hasItem(DEFAULT_ACCOUNT_NUMBER)))
            .andExpect(jsonPath("$.[*].checkDate").value(hasItem(DEFAULT_CHECK_DATE.toString())))
            .andExpect(jsonPath("$.[*].recipient").value(hasItem(DEFAULT_RECIPIENT)))
            .andExpect(jsonPath("$.[*].dateOfSignature").value(hasItem(DEFAULT_DATE_OF_SIGNATURE.toString())))
            .andExpect(jsonPath("$.[*].paymentMethod").value(hasItem(DEFAULT_PAYMENT_METHOD.toString())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT)))
            .andExpect(jsonPath("$.[*].expectedPaymentDate").value(hasItem(DEFAULT_EXPECTED_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].datePaymentMade").value(hasItem(DEFAULT_DATE_PAYMENT_MADE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkIssuer(UPDATED_CHECK_ISSUER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .checkDate(UPDATED_CHECK_DATE)
            .recipient(UPDATED_RECIPIENT)
            .dateOfSignature(UPDATED_DATE_OF_SIGNATURE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .amount(UPDATED_AMOUNT)
            .expectedPaymentDate(UPDATED_EXPECTED_PAYMENT_DATE)
            .datePaymentMade(UPDATED_DATE_PAYMENT_MADE)
            .status(UPDATED_STATUS)
            .currency(UPDATED_CURRENCY)
            .createdAt(UPDATED_CREATED_AT);
        PaymentDTO paymentDTO = paymentMapper.toDto(updatedPayment);

        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPayment.getCheckIssuer()).isEqualTo(UPDATED_CHECK_ISSUER);
        assertThat(testPayment.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPayment.getCheckDate()).isEqualTo(UPDATED_CHECK_DATE);
        assertThat(testPayment.getRecipient()).isEqualTo(UPDATED_RECIPIENT);
        assertThat(testPayment.getDateOfSignature()).isEqualTo(UPDATED_DATE_OF_SIGNATURE);
        assertThat(testPayment.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getExpectedPaymentDate()).isEqualTo(UPDATED_EXPECTED_PAYMENT_DATE);
        assertThat(testPayment.getDatePaymentMade()).isEqualTo(UPDATED_DATE_PAYMENT_MADE);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPayment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkIssuer(UPDATED_CHECK_ISSUER)
            .checkDate(UPDATED_CHECK_DATE)
            .dateOfSignature(UPDATED_DATE_OF_SIGNATURE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .amount(UPDATED_AMOUNT)
            .status(UPDATED_STATUS)
            .currency(UPDATED_CURRENCY)
            .createdAt(UPDATED_CREATED_AT);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPayment.getCheckIssuer()).isEqualTo(UPDATED_CHECK_ISSUER);
        assertThat(testPayment.getAccountNumber()).isEqualTo(DEFAULT_ACCOUNT_NUMBER);
        assertThat(testPayment.getCheckDate()).isEqualTo(UPDATED_CHECK_DATE);
        assertThat(testPayment.getRecipient()).isEqualTo(DEFAULT_RECIPIENT);
        assertThat(testPayment.getDateOfSignature()).isEqualTo(UPDATED_DATE_OF_SIGNATURE);
        assertThat(testPayment.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getExpectedPaymentDate()).isEqualTo(DEFAULT_EXPECTED_PAYMENT_DATE);
        assertThat(testPayment.getDatePaymentMade()).isEqualTo(DEFAULT_DATE_PAYMENT_MADE);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPayment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdatePaymentWithPatch() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment using partial update
        Payment partialUpdatedPayment = new Payment();
        partialUpdatedPayment.setId(payment.getId());

        partialUpdatedPayment
            .checkNumber(UPDATED_CHECK_NUMBER)
            .checkIssuer(UPDATED_CHECK_ISSUER)
            .accountNumber(UPDATED_ACCOUNT_NUMBER)
            .checkDate(UPDATED_CHECK_DATE)
            .recipient(UPDATED_RECIPIENT)
            .dateOfSignature(UPDATED_DATE_OF_SIGNATURE)
            .paymentMethod(UPDATED_PAYMENT_METHOD)
            .amount(UPDATED_AMOUNT)
            .expectedPaymentDate(UPDATED_EXPECTED_PAYMENT_DATE)
            .datePaymentMade(UPDATED_DATE_PAYMENT_MADE)
            .status(UPDATED_STATUS)
            .currency(UPDATED_CURRENCY)
            .createdAt(UPDATED_CREATED_AT);

        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPayment.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPayment))
            )
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getCheckNumber()).isEqualTo(UPDATED_CHECK_NUMBER);
        assertThat(testPayment.getCheckIssuer()).isEqualTo(UPDATED_CHECK_ISSUER);
        assertThat(testPayment.getAccountNumber()).isEqualTo(UPDATED_ACCOUNT_NUMBER);
        assertThat(testPayment.getCheckDate()).isEqualTo(UPDATED_CHECK_DATE);
        assertThat(testPayment.getRecipient()).isEqualTo(UPDATED_RECIPIENT);
        assertThat(testPayment.getDateOfSignature()).isEqualTo(UPDATED_DATE_OF_SIGNATURE);
        assertThat(testPayment.getPaymentMethod()).isEqualTo(UPDATED_PAYMENT_METHOD);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getExpectedPaymentDate()).isEqualTo(UPDATED_EXPECTED_PAYMENT_DATE);
        assertThat(testPayment.getDatePaymentMade()).isEqualTo(UPDATED_DATE_PAYMENT_MADE);
        assertThat(testPayment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPayment.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testPayment.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();
        payment.setId(longCount.incrementAndGet());

        // Create the Payment
        PaymentDTO paymentDTO = paymentMapper.toDto(payment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc
            .perform(delete(ENTITY_API_URL_ID, payment.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
