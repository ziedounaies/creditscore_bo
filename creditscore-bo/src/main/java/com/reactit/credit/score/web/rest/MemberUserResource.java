package com.reactit.credit.score.web.rest;

import com.reactit.credit.score.repository.MemberUserRepository;
import com.reactit.credit.score.service.MemberUserQueryService;
import com.reactit.credit.score.service.MemberUserService;
import com.reactit.credit.score.service.criteria.MemberUserCriteria;
import com.reactit.credit.score.service.dto.MemberUserDTO;
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
 * REST controller for managing {@link com.reactit.credit.score.domain.MemberUser}.
 */
@RestController
@RequestMapping("/api/member-users")
public class MemberUserResource {

    private final Logger log = LoggerFactory.getLogger(MemberUserResource.class);

    private static final String ENTITY_NAME = "memberUser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MemberUserService memberUserService;

    private final MemberUserRepository memberUserRepository;

    private final MemberUserQueryService memberUserQueryService;

    public MemberUserResource(
        MemberUserService memberUserService,
        MemberUserRepository memberUserRepository,
        MemberUserQueryService memberUserQueryService
    ) {
        this.memberUserService = memberUserService;
        this.memberUserRepository = memberUserRepository;
        this.memberUserQueryService = memberUserQueryService;
    }

    /**
     * {@code POST  /member-users} : Create a new memberUser.
     *
     * @param memberUserDTO the memberUserDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new memberUserDTO, or with status {@code 400 (Bad Request)} if the memberUser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MemberUserDTO> createMemberUser(@RequestBody MemberUserDTO memberUserDTO) throws URISyntaxException {
        log.debug("REST request to save MemberUser : {}", memberUserDTO);
        if (memberUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new memberUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MemberUserDTO result = memberUserService.save(memberUserDTO);
        return ResponseEntity
            .created(new URI("/api/member-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
/*
    @PostMapping("/chercher")
    public ResponseEntity<MemberUserDTO> chercherMemberUser(@RequestBody String userName) throws URISyntaxException {
        MemberUserDTO result = memberUserService.findByName(userName);

    }
*/
    /**
     * {@code PUT  /member-users/:id} : Updates an existing memberUser.
     *
     * @param id the id of the memberUserDTO to save.
     * @param memberUserDTO the memberUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberUserDTO,
     * or with status {@code 400 (Bad Request)} if the memberUserDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the memberUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MemberUserDTO> updateMemberUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberUserDTO memberUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MemberUser : {}, {}", id, memberUserDTO);
        if (memberUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MemberUserDTO result = memberUserService.update(memberUserDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, memberUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /member-users/:id} : Partial updates given fields of an existing memberUser, field will ignore if it is null
     *
     * @param id the id of the memberUserDTO to save.
     * @param memberUserDTO the memberUserDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated memberUserDTO,
     * or with status {@code 400 (Bad Request)} if the memberUserDTO is not valid,
     * or with status {@code 404 (Not Found)} if the memberUserDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the memberUserDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MemberUserDTO> partialUpdateMemberUser(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MemberUserDTO memberUserDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MemberUser partially : {}, {}", id, memberUserDTO);
        if (memberUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, memberUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!memberUserRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MemberUserDTO> result = memberUserService.partialUpdate(memberUserDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, memberUserDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /member-users} : get all the memberUsers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of memberUsers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MemberUserDTO>> getAllMemberUsers(
        MemberUserCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get MemberUsers by criteria: {}", criteria);

        Page<MemberUserDTO> page = memberUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /member-users/count} : count all the memberUsers.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countMemberUsers(MemberUserCriteria criteria) {
        log.debug("REST request to count MemberUsers by criteria: {}", criteria);
        return ResponseEntity.ok().body(memberUserQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /member-users/:id} : get the "id" memberUser.
     *
     * @param id the id of the memberUserDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the memberUserDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberUserDTO> getMemberUser(@PathVariable("id") Long id) {
        log.debug("REST request to get MemberUser : {}", id);
        Optional<MemberUserDTO> memberUserDTO = memberUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(memberUserDTO);
    }

    /**
     * {@code DELETE  /member-users/:id} : delete the "id" memberUser.
     *
     * @param id the id of the memberUserDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMemberUser(@PathVariable("id") Long id) {
        log.debug("REST request to delete MemberUser : {}", id);
        memberUserService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
