package com.reactit.credit.score.web.rest;

import com.reactit.credit.score.repository.ClaimRepository;
import com.reactit.credit.score.service.ClaimQueryService;
import com.reactit.credit.score.service.ClaimService;
import com.reactit.credit.score.service.criteria.ClaimCriteria;
import com.reactit.credit.score.service.dto.ClaimDTO;
import com.reactit.credit.score.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reactit.credit.score.domain.Claim}.
 */
@RestController
@RequestMapping("/api/claims")
public class ClaimResource {

    private final Logger log = LoggerFactory.getLogger(ClaimResource.class);

    private static final String ENTITY_NAME = "claim";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClaimService claimService;

    private final ClaimRepository claimRepository;

    private final ClaimQueryService claimQueryService;

    public ClaimResource(ClaimService claimService, ClaimRepository claimRepository, ClaimQueryService claimQueryService) {
        this.claimService = claimService;
        this.claimRepository = claimRepository;
        this.claimQueryService = claimQueryService;
    }

    /**
     * {@code POST  /claims} : Create a new claim.
     *
     * @param claimDTO the claimDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new claimDTO, or with status {@code 400 (Bad Request)} if the claim has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ClaimDTO> createClaim(@RequestBody ClaimDTO claimDTO) throws URISyntaxException {
        log.debug("REST request to save Claim : {}", claimDTO);
        if (claimDTO.getId() != null) {
            throw new BadRequestAlertException("A new claim cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClaimDTO result = claimService.save(claimDTO);
        return ResponseEntity
            .created(new URI("/api/claims/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /claims/:id} : Updates an existing claim.
     *
     * @param id the id of the claimDTO to save.
     * @param claimDTO the claimDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimDTO,
     * or with status {@code 400 (Bad Request)} if the claimDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the claimDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClaimDTO> updateClaim(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimDTO claimDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Claim : {}, {}", id, claimDTO);
        if (claimDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ClaimDTO result = claimService.update(claimDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /claims/:id} : Partial updates given fields of an existing claim, field will ignore if it is null
     *
     * @param id the id of the claimDTO to save.
     * @param claimDTO the claimDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated claimDTO,
     * or with status {@code 400 (Bad Request)} if the claimDTO is not valid,
     * or with status {@code 404 (Not Found)} if the claimDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the claimDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ClaimDTO> partialUpdateClaim(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ClaimDTO claimDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Claim partially : {}, {}", id, claimDTO);
        if (claimDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, claimDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!claimRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ClaimDTO> result = claimService.partialUpdate(claimDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, claimDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /claims} : get all the claims.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of claims in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ClaimDTO>> getAllClaims(
        ClaimCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Claims by criteria: {}", criteria);

        Page<ClaimDTO> page = claimQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /claims/count} : count all the claims.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countClaims(ClaimCriteria criteria) {
        log.debug("REST request to count Claims by criteria: {}", criteria);
        return ResponseEntity.ok().body(claimQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /claims/:id} : get the "id" claim.
     *
     * @param id the id of the claimDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the claimDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClaimDTO> getClaim(@PathVariable("id") Long id) {
        log.debug("REST request to get Claim : {}", id);
        Optional<ClaimDTO> claimDTO = claimService.findOne(id);
        return ResponseUtil.wrapOrNotFound(claimDTO);
    }

    /**
     * {@code DELETE  /claims/:id} : delete the "id" claim.
     *
     * @param id the id of the claimDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClaim(@PathVariable("id") Long id) {
        log.debug("REST request to delete Claim : {}", id);
        claimService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
