package com.reactit.credit.score.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reactit.credit.score.IntegrationTest;
import com.reactit.credit.score.domain.Claim;
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.domain.enumeration.StatusType;
import com.reactit.credit.score.repository.ClaimRepository;
import com.reactit.credit.score.service.dto.ClaimDTO;
import com.reactit.credit.score.service.mapper.ClaimMapper;
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
 * Integration tests for the {@link ClaimResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ClaimResourceIT {

    private static final StatusType DEFAULT_STATUS = StatusType.PENDING;
    private static final StatusType UPDATED_STATUS = StatusType.IN_PROGRESS;

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/claims";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private ClaimMapper claimMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClaimMockMvc;

    private Claim claim;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createEntity(EntityManager em) {
        Claim claim = new Claim().status(DEFAULT_STATUS).title(DEFAULT_TITLE).message(DEFAULT_MESSAGE).createdAt(DEFAULT_CREATED_AT);
        return claim;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Claim createUpdatedEntity(EntityManager em) {
        Claim claim = new Claim().status(UPDATED_STATUS).title(UPDATED_TITLE).message(UPDATED_MESSAGE).createdAt(UPDATED_CREATED_AT);
        return claim;
    }

    @BeforeEach
    public void initTest() {
        claim = createEntity(em);
    }

    @Test
    @Transactional
    void createClaim() throws Exception {
        int databaseSizeBeforeCreate = claimRepository.findAll().size();
        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);
        restClaimMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate + 1);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClaim.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClaim.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testClaim.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void createClaimWithExistingId() throws Exception {
        // Create the Claim with an existing ID
        claim.setId(1L);
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        int databaseSizeBeforeCreate = claimRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClaimMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClaims() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));
    }

    @Test
    @Transactional
    void getClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get the claim
        restClaimMockMvc
            .perform(get(ENTITY_API_URL_ID, claim.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(claim.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()));
    }

    @Test
    @Transactional
    void getClaimsByIdFiltering() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        Long id = claim.getId();

        defaultClaimShouldBeFound("id.equals=" + id);
        defaultClaimShouldNotBeFound("id.notEquals=" + id);

        defaultClaimShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClaimShouldNotBeFound("id.greaterThan=" + id);

        defaultClaimShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClaimShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClaimsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where status equals to DEFAULT_STATUS
        defaultClaimShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the claimList where status equals to UPDATED_STATUS
        defaultClaimShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultClaimShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the claimList where status equals to UPDATED_STATUS
        defaultClaimShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllClaimsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where status is not null
        defaultClaimShouldBeFound("status.specified=true");

        // Get all the claimList where status is null
        defaultClaimShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimsByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where title equals to DEFAULT_TITLE
        defaultClaimShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the claimList where title equals to UPDATED_TITLE
        defaultClaimShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllClaimsByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultClaimShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the claimList where title equals to UPDATED_TITLE
        defaultClaimShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllClaimsByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where title is not null
        defaultClaimShouldBeFound("title.specified=true");

        // Get all the claimList where title is null
        defaultClaimShouldNotBeFound("title.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimsByTitleContainsSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where title contains DEFAULT_TITLE
        defaultClaimShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the claimList where title contains UPDATED_TITLE
        defaultClaimShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllClaimsByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where title does not contain DEFAULT_TITLE
        defaultClaimShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the claimList where title does not contain UPDATED_TITLE
        defaultClaimShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    void getAllClaimsByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where message equals to DEFAULT_MESSAGE
        defaultClaimShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the claimList where message equals to UPDATED_MESSAGE
        defaultClaimShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllClaimsByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultClaimShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the claimList where message equals to UPDATED_MESSAGE
        defaultClaimShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllClaimsByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where message is not null
        defaultClaimShouldBeFound("message.specified=true");

        // Get all the claimList where message is null
        defaultClaimShouldNotBeFound("message.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimsByMessageContainsSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where message contains DEFAULT_MESSAGE
        defaultClaimShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the claimList where message contains UPDATED_MESSAGE
        defaultClaimShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllClaimsByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where message does not contain DEFAULT_MESSAGE
        defaultClaimShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the claimList where message does not contain UPDATED_MESSAGE
        defaultClaimShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    void getAllClaimsByCreatedAtIsEqualToSomething() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where createdAt equals to DEFAULT_CREATED_AT
        defaultClaimShouldBeFound("createdAt.equals=" + DEFAULT_CREATED_AT);

        // Get all the claimList where createdAt equals to UPDATED_CREATED_AT
        defaultClaimShouldNotBeFound("createdAt.equals=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllClaimsByCreatedAtIsInShouldWork() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where createdAt in DEFAULT_CREATED_AT or UPDATED_CREATED_AT
        defaultClaimShouldBeFound("createdAt.in=" + DEFAULT_CREATED_AT + "," + UPDATED_CREATED_AT);

        // Get all the claimList where createdAt equals to UPDATED_CREATED_AT
        defaultClaimShouldNotBeFound("createdAt.in=" + UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void getAllClaimsByCreatedAtIsNullOrNotNull() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        // Get all the claimList where createdAt is not null
        defaultClaimShouldBeFound("createdAt.specified=true");

        // Get all the claimList where createdAt is null
        defaultClaimShouldNotBeFound("createdAt.specified=false");
    }

    @Test
    @Transactional
    void getAllClaimsByMemberUserIsEqualToSomething() throws Exception {
        MemberUser memberUser;
        if (TestUtil.findAll(em, MemberUser.class).isEmpty()) {
            claimRepository.saveAndFlush(claim);
            memberUser = MemberUserResourceIT.createEntity(em);
        } else {
            memberUser = TestUtil.findAll(em, MemberUser.class).get(0);
        }
        em.persist(memberUser);
        em.flush();
        claim.setMemberUser(memberUser);
        claimRepository.saveAndFlush(claim);
        Long memberUserId = memberUser.getId();
        // Get all the claimList where memberUser equals to memberUserId
        defaultClaimShouldBeFound("memberUserId.equals=" + memberUserId);

        // Get all the claimList where memberUser equals to (memberUserId + 1)
        defaultClaimShouldNotBeFound("memberUserId.equals=" + (memberUserId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClaimShouldBeFound(String filter) throws Exception {
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(claim.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())));

        // Check, that the count call also returns 1
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClaimShouldNotBeFound(String filter) throws Exception {
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClaimMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClaim() throws Exception {
        // Get the claim
        restClaimMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim
        Claim updatedClaim = claimRepository.findById(claim.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedClaim are not directly saved in db
        em.detach(updatedClaim);
        updatedClaim.status(UPDATED_STATUS).title(UPDATED_TITLE).message(UPDATED_MESSAGE).createdAt(UPDATED_CREATED_AT);
        ClaimDTO claimDTO = claimMapper.toDto(updatedClaim);

        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClaim.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClaim.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testClaim.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void putNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, claimDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClaim.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testClaim.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testClaim.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
    }

    @Test
    @Transactional
    void fullUpdateClaimWithPatch() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeUpdate = claimRepository.findAll().size();

        // Update the claim using partial update
        Claim partialUpdatedClaim = new Claim();
        partialUpdatedClaim.setId(claim.getId());

        partialUpdatedClaim.status(UPDATED_STATUS).title(UPDATED_TITLE).message(UPDATED_MESSAGE).createdAt(UPDATED_CREATED_AT);

        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClaim.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClaim))
            )
            .andExpect(status().isOk());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
        Claim testClaim = claimList.get(claimList.size() - 1);
        assertThat(testClaim.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClaim.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testClaim.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testClaim.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
    }

    @Test
    @Transactional
    void patchNonExistingClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, claimDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClaim() throws Exception {
        int databaseSizeBeforeUpdate = claimRepository.findAll().size();
        claim.setId(longCount.incrementAndGet());

        // Create the Claim
        ClaimDTO claimDTO = claimMapper.toDto(claim);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClaimMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(claimDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Claim in the database
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClaim() throws Exception {
        // Initialize the database
        claimRepository.saveAndFlush(claim);

        int databaseSizeBeforeDelete = claimRepository.findAll().size();

        // Delete the claim
        restClaimMockMvc
            .perform(delete(ENTITY_API_URL_ID, claim.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Claim> claimList = claimRepository.findAll();
        assertThat(claimList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
