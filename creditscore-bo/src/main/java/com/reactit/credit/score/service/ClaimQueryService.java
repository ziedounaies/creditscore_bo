package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.Claim;
import com.reactit.credit.score.repository.ClaimRepository;
import com.reactit.credit.score.service.criteria.ClaimCriteria;
import com.reactit.credit.score.service.dto.ClaimDTO;
import com.reactit.credit.score.service.mapper.ClaimMapper;
import jakarta.persistence.criteria.JoinType;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Claim} entities in the database.
 * The main input is a {@link ClaimCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ClaimDTO} or a {@link Page} of {@link ClaimDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClaimQueryService extends QueryService<Claim> {

    private final Logger log = LoggerFactory.getLogger(ClaimQueryService.class);

    private final ClaimRepository claimRepository;

    private final ClaimMapper claimMapper;

    public ClaimQueryService(ClaimRepository claimRepository, ClaimMapper claimMapper) {
        this.claimRepository = claimRepository;
        this.claimMapper = claimMapper;
    }

    /**
     * Return a {@link List} of {@link ClaimDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ClaimDTO> findByCriteria(ClaimCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Claim> specification = createSpecification(criteria);
        return claimMapper.toDto(claimRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ClaimDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ClaimDTO> findByCriteria(ClaimCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Claim> specification = createSpecification(criteria);
        return claimRepository.findAll(specification, page).map(claimMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ClaimCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Claim> specification = createSpecification(criteria);
        return claimRepository.count(specification);
    }

    /**
     * Function to convert {@link ClaimCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Claim> createSpecification(ClaimCriteria criteria) {
        Specification<Claim> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Claim_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Claim_.status));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTitle(), Claim_.title));
            }
            if (criteria.getMessage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessage(), Claim_.message));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Claim_.createdAt));
            }
            if (criteria.getMemberUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberUserId(),
                            root -> root.join(Claim_.memberUser, JoinType.LEFT).get(MemberUser_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
