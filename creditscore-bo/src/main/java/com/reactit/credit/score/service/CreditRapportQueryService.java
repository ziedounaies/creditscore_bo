package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.repository.CreditRapportRepository;
import com.reactit.credit.score.service.criteria.CreditRapportCriteria;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
import com.reactit.credit.score.service.mapper.CreditRapportMapper;
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
 * Service for executing complex queries for {@link CreditRapport} entities in the database.
 * The main input is a {@link CreditRapportCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CreditRapportDTO} or a {@link Page} of {@link CreditRapportDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CreditRapportQueryService extends QueryService<CreditRapport> {

    private final Logger log = LoggerFactory.getLogger(CreditRapportQueryService.class);

    private final CreditRapportRepository creditRapportRepository;

    private final CreditRapportMapper creditRapportMapper;

    public CreditRapportQueryService(CreditRapportRepository creditRapportRepository, CreditRapportMapper creditRapportMapper) {
        this.creditRapportRepository = creditRapportRepository;
        this.creditRapportMapper = creditRapportMapper;
    }

    /**
     * Return a {@link List} of {@link CreditRapportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CreditRapportDTO> findByCriteria(CreditRapportCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CreditRapport> specification = createSpecification(criteria);
        return creditRapportMapper.toDto(creditRapportRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CreditRapportDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CreditRapportDTO> findByCriteria(CreditRapportCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CreditRapport> specification = createSpecification(criteria);
        return creditRapportRepository.findAll(specification, page).map(creditRapportMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CreditRapportCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CreditRapport> specification = createSpecification(criteria);
        return creditRapportRepository.count(specification);
    }

    /**
     * Function to convert {@link CreditRapportCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<CreditRapport> createSpecification(CreditRapportCriteria criteria) {
        Specification<CreditRapport> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), CreditRapport_.id));
            }
            if (criteria.getCreditScore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditScore(), CreditRapport_.creditScore));
            }
            if (criteria.getAccountAge() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountAge(), CreditRapport_.accountAge));
            }
            if (criteria.getCreditLimit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreditLimit(), CreditRapport_.creditLimit));
            }
            if (criteria.getInquiriesAndRequests() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getInquiriesAndRequests(), CreditRapport_.inquiriesAndRequests));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), CreditRapport_.createdAt));
            }
            if (criteria.getMemberUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberUserId(),
                            root -> root.join(CreditRapport_.memberUser, JoinType.LEFT).get(MemberUser_.id)
                        )
                    );
            }
            if (criteria.getInvoiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getInvoiceId(),
                            root -> root.join(CreditRapport_.invoices, JoinType.LEFT).get(Invoice_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
