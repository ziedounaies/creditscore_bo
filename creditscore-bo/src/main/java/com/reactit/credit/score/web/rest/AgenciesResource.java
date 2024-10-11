package com.reactit.credit.score.web.rest;

import com.reactit.credit.score.repository.AgenciesRepository;
import com.reactit.credit.score.service.AgenciesQueryService;
import com.reactit.credit.score.service.AgenciesService;
import com.reactit.credit.score.service.criteria.AgenciesCriteria;
import com.reactit.credit.score.service.dto.AgenciesDTO;
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
 * REST controller for managing {@link com.reactit.credit.score.domain.Agencies}.
 */
@RestController
@RequestMapping("/api/agencies")
public class AgenciesResource {

    private final Logger log = LoggerFactory.getLogger(AgenciesResource.class);

    private static final String ENTITY_NAME = "agencies";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenciesService agenciesService;

    private final AgenciesRepository agenciesRepository;

    private final AgenciesQueryService agenciesQueryService;

    public AgenciesResource(
        AgenciesService agenciesService,
        AgenciesRepository agenciesRepository,
        AgenciesQueryService agenciesQueryService
    ) {
        this.agenciesService = agenciesService;
        this.agenciesRepository = agenciesRepository;
        this.agenciesQueryService = agenciesQueryService;
    }

    /**
     * {@code POST  /agencies} : Create a new agencies.
     *
     * @param agenciesDTO the agenciesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenciesDTO, or with status {@code 400 (Bad Request)} if the agencies has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgenciesDTO> createAgencies(@RequestBody AgenciesDTO agenciesDTO) throws URISyntaxException {
        log.debug("REST request to save Agencies : {}", agenciesDTO);
        if (agenciesDTO.getId() != null) {
            throw new BadRequestAlertException("A new agencies cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AgenciesDTO result = agenciesService.save(agenciesDTO);
        return ResponseEntity
            .created(new URI("/api/agencies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agencies/:id} : Updates an existing agencies.
     *
     * @param id the id of the agenciesDTO to save.
     * @param agenciesDTO the agenciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenciesDTO,
     * or with status {@code 400 (Bad Request)} if the agenciesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AgenciesDTO> updateAgencies(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AgenciesDTO agenciesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Agencies : {}, {}", id, agenciesDTO);
        if (agenciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenciesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AgenciesDTO result = agenciesService.update(agenciesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agenciesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /agencies/:id} : Partial updates given fields of an existing agencies, field will ignore if it is null
     *
     * @param id the id of the agenciesDTO to save.
     * @param agenciesDTO the agenciesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenciesDTO,
     * or with status {@code 400 (Bad Request)} if the agenciesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the agenciesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the agenciesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgenciesDTO> partialUpdateAgencies(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AgenciesDTO agenciesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Agencies partially : {}, {}", id, agenciesDTO);
        if (agenciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, agenciesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenciesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgenciesDTO> result = agenciesService.partialUpdate(agenciesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, agenciesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /agencies} : get all the agencies.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agencies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AgenciesDTO>> getAllAgencies(
        AgenciesCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Agencies by criteria: {}", criteria);

        Page<AgenciesDTO> page = agenciesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agencies/count} : count all the agencies.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countAgencies(AgenciesCriteria criteria) {
        log.debug("REST request to count Agencies by criteria: {}", criteria);
        return ResponseEntity.ok().body(agenciesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /agencies/:id} : get the "id" agencies.
     *
     * @param id the id of the agenciesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenciesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgenciesDTO> getAgencies(@PathVariable("id") Long id) {
        log.debug("REST request to get Agencies : {}", id);
        Optional<AgenciesDTO> agenciesDTO = agenciesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenciesDTO);
    }

    /**
     * {@code DELETE  /agencies/:id} : delete the "id" agencies.
     *
     * @param id the id of the agenciesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgencies(@PathVariable("id") Long id) {
        log.debug("REST request to delete Agencies : {}", id);
        agenciesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
