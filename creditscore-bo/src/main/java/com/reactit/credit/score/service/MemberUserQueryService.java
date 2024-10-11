package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.repository.MemberUserRepository;
import com.reactit.credit.score.service.criteria.MemberUserCriteria;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import com.reactit.credit.score.service.mapper.MemberUserMapper;
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
 * Service for executing complex queries for {@link MemberUser} entities in the database.
 * The main input is a {@link MemberUserCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MemberUserDTO} or a {@link Page} of {@link MemberUserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MemberUserQueryService extends QueryService<MemberUser> {

    private final Logger log = LoggerFactory.getLogger(MemberUserQueryService.class);

    private final MemberUserRepository memberUserRepository;

    private final MemberUserMapper memberUserMapper;

    public MemberUserQueryService(MemberUserRepository memberUserRepository, MemberUserMapper memberUserMapper) {
        this.memberUserRepository = memberUserRepository;
        this.memberUserMapper = memberUserMapper;
    }

    /**
     * Return a {@link List} of {@link MemberUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MemberUserDTO> findByCriteria(MemberUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MemberUser> specification = createSpecification(criteria);
        return memberUserMapper.toDto(memberUserRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link MemberUserDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MemberUserDTO> findByCriteria(MemberUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MemberUser> specification = createSpecification(criteria);
        return memberUserRepository.findAll(specification, page).map(memberUserMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MemberUserCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MemberUser> specification = createSpecification(criteria);
        return memberUserRepository.count(specification);
    }

    /**
     * Function to convert {@link MemberUserCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MemberUser> createSpecification(MemberUserCriteria criteria) {
        Specification<MemberUser> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MemberUser_.id));
            }
            if (criteria.getUserName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUserName(), MemberUser_.userName));
            }
            if (criteria.getFirstName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstName(), MemberUser_.firstName));
            }
            if (criteria.getLastName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastName(), MemberUser_.lastName));
            }
            if (criteria.getBusinessName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBusinessName(), MemberUser_.businessName));
            }
            if (criteria.getBirthDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthDate(), MemberUser_.birthDate));
            }
            if (criteria.getAcountType() != null) {
                specification = specification.and(buildSpecification(criteria.getAcountType(), MemberUser_.acountType));
            }
            if (criteria.getIdentifierType() != null) {
                specification = specification.and(buildSpecification(criteria.getIdentifierType(), MemberUser_.identifierType));
            }
            if (criteria.getIdentifierValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIdentifierValue(), MemberUser_.identifierValue));
            }
            if (criteria.getEmployersReported() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEmployersReported(), MemberUser_.employersReported));
            }
            if (criteria.getIncome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getIncome(), MemberUser_.income));
            }
            if (criteria.getExpenses() != null) {
                specification = specification.and(buildStringSpecification(criteria.getExpenses(), MemberUser_.expenses));
            }
            if (criteria.getGrossProfit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGrossProfit(), MemberUser_.grossProfit));
            }
            if (criteria.getNetProfitMargin() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNetProfitMargin(), MemberUser_.netProfitMargin));
            }
            if (criteria.getDebtsObligations() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDebtsObligations(), MemberUser_.debtsObligations));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), MemberUser_.enabled));
            }
            if (criteria.getRole() != null) {
                specification = specification.and(buildSpecification(criteria.getRole(), MemberUser_.role));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), MemberUser_.createdAt));
            }
            if (criteria.getCreditRapportId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getCreditRapportId(),
                            root -> root.join(MemberUser_.creditRapport, JoinType.LEFT).get(CreditRapport_.id)
                        )
                    );
            }
            if (criteria.getInvoiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getInvoiceId(), root -> root.join(MemberUser_.invoice, JoinType.LEFT).get(Invoice_.id))
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getAddressId(),
                            root -> root.join(MemberUser_.addresses, JoinType.LEFT).get(Address_.id)
                        )
                    );
            }
            if (criteria.getPaymentId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getPaymentId(), root -> root.join(MemberUser_.payments, JoinType.LEFT).get(Payment_.id))
                    );
            }
            if (criteria.getClaimId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getClaimId(), root -> root.join(MemberUser_.claims, JoinType.LEFT).get(Claim_.id))
                    );
            }
            if (criteria.getNotificationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getNotificationId(),
                            root -> root.join(MemberUser_.notifications, JoinType.LEFT).get(Notification_.id)
                        )
                    );
            }
            if (criteria.getContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContactId(), root -> root.join(MemberUser_.contacts, JoinType.LEFT).get(Contact_.id))
                    );
            }
        }
        return specification;
    }
}
