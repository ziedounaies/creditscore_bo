package com.reactit.credit.score.web.rest;

import com.reactit.credit.score.repository.CreditRapportRepository;
import com.reactit.credit.score.service.CreditRapportQueryService;
import com.reactit.credit.score.service.CreditRapportService;
import com.reactit.credit.score.service.criteria.CreditRapportCriteria;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
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
 * REST controller for managing {@link com.reactit.credit.score.domain.CreditRapport}.
 */
@RestController
@RequestMapping("/api/credit-rapports")
public class CreditRapportResource {

    private final Logger log = LoggerFactory.getLogger(CreditRapportResource.class);

    private static final String ENTITY_NAME = "creditRapport";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreditRapportService creditRapportService;

    private final CreditRapportRepository creditRapportRepository;

    private final CreditRapportQueryService creditRapportQueryService;

    public CreditRapportResource(
        CreditRapportService creditRapportService,
        CreditRapportRepository creditRapportRepository,
        CreditRapportQueryService creditRapportQueryService
    ) {
        this.creditRapportService = creditRapportService;
        this.creditRapportRepository = creditRapportRepository;
        this.creditRapportQueryService = creditRapportQueryService;
    }

    /**
     * {@code POST  /credit-rapports} : Create a new creditRapport.
     *
     * @param creditRapportDTO the creditRapportDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new creditRapportDTO, or with status {@code 400 (Bad Request)} if the creditRapport has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CreditRapportDTO> createCreditRapport(@RequestBody CreditRapportDTO creditRapportDTO) throws URISyntaxException {
        log.debug("REST request to save CreditRapport : {}", creditRapportDTO);
        if (creditRapportDTO.getId() != null) {
            throw new BadRequestAlertException("A new creditRapport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreditRapportDTO result = creditRapportService.save(creditRapportDTO);
        return ResponseEntity
            .created(new URI("/api/credit-rapports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /credit-rapports/:id} : Updates an existing creditRapport.
     *
     * @param id the id of the creditRapportDTO to save.
     * @param creditRapportDTO the creditRapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditRapportDTO,
     * or with status {@code 400 (Bad Request)} if the creditRapportDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the creditRapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CreditRapportDTO> updateCreditRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditRapportDTO creditRapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreditRapport : {}, {}", id, creditRapportDTO);
        if (creditRapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditRapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditRapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreditRapportDTO result = creditRapportService.update(creditRapportDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, creditRapportDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /credit-rapports/:id} : Partial updates given fields of an existing creditRapport, field will ignore if it is null
     *
     * @param id the id of the creditRapportDTO to save.
     * @param creditRapportDTO the creditRapportDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated creditRapportDTO,
     * or with status {@code 400 (Bad Request)} if the creditRapportDTO is not valid,
     * or with status {@code 404 (Not Found)} if the creditRapportDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the creditRapportDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreditRapportDTO> partialUpdateCreditRapport(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CreditRapportDTO creditRapportDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreditRapport partially : {}, {}", id, creditRapportDTO);
        if (creditRapportDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, creditRapportDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!creditRapportRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreditRapportDTO> result = creditRapportService.partialUpdate(creditRapportDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, creditRapportDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /credit-rapports} : get all the creditRapports.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of creditRapports in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CreditRapportDTO>> getAllCreditRapports(
        CreditRapportCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get CreditRapports by criteria: {}", criteria);

        Page<CreditRapportDTO> page = creditRapportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /credit-rapports/count} : count all the creditRapports.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCreditRapports(CreditRapportCriteria criteria) {
        log.debug("REST request to count CreditRapports by criteria: {}", criteria);
        return ResponseEntity.ok().body(creditRapportQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /credit-rapports/:id} : get the "id" creditRapport.
     *
     * @param id the id of the creditRapportDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the creditRapportDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CreditRapportDTO> getCreditRapport(@PathVariable("id") Long id) {
        log.debug("REST request to get CreditRapport : {}", id);
        Optional<CreditRapportDTO> creditRapportDTO = creditRapportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(creditRapportDTO);
    }

    /**
     * {@code DELETE  /credit-rapports/:id} : delete the "id" creditRapport.
     *
     * @param id the id of the creditRapportDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCreditRapport(@PathVariable("id") Long id) {
        log.debug("REST request to delete CreditRapport : {}", id);
        creditRapportService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
