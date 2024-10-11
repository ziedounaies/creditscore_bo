package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.Payment;
import com.reactit.credit.score.repository.PaymentRepository;
import com.reactit.credit.score.service.criteria.PaymentCriteria;
import com.reactit.credit.score.service.dto.PaymentDTO;
import com.reactit.credit.score.service.mapper.PaymentMapper;
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
 * Service for executing complex queries for {@link Payment} entities in the database.
 * The main input is a {@link PaymentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PaymentDTO} or a {@link Page} of {@link PaymentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PaymentQueryService extends QueryService<Payment> {

    private final Logger log = LoggerFactory.getLogger(PaymentQueryService.class);

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    public PaymentQueryService(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    /**
     * Return a {@link List} of {@link PaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentDTO> findByCriteria(PaymentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentMapper.toDto(paymentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link PaymentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentDTO> findByCriteria(PaymentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.findAll(specification, page).map(paymentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PaymentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Payment> specification = createSpecification(criteria);
        return paymentRepository.count(specification);
    }

    /**
     * Function to convert {@link PaymentCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Payment> createSpecification(PaymentCriteria criteria) {
        Specification<Payment> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Payment_.id));
            }
            if (criteria.getCheckNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckNumber(), Payment_.checkNumber));
            }
            if (criteria.getCheckIssuer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCheckIssuer(), Payment_.checkIssuer));
            }
            if (criteria.getAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAccountNumber(), Payment_.accountNumber));
            }
            if (criteria.getCheckDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCheckDate(), Payment_.checkDate));
            }
            if (criteria.getRecipient() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecipient(), Payment_.recipient));
            }
            if (criteria.getDateOfSignature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateOfSignature(), Payment_.dateOfSignature));
            }
            if (criteria.getPaymentMethod() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentMethod(), Payment_.paymentMethod));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAmount(), Payment_.amount));
            }
            if (criteria.getExpectedPaymentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedPaymentDate(), Payment_.expectedPaymentDate));
            }
            if (criteria.getDatePaymentMade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDatePaymentMade(), Payment_.datePaymentMade));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Payment_.status));
            }
            if (criteria.getCurrency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrency(), Payment_.currency));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Payment_.createdAt));
            }
            if (criteria.getMemberUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMemberUserId(),
                            root -> root.join(Payment_.memberUser, JoinType.LEFT).get(MemberUser_.id)
                        )
                    );
            }
            if (criteria.getProductId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getProductId(), root -> root.join(Payment_.products, JoinType.LEFT).get(Product_.id))
                    );
            }
            if (criteria.getInvoiceId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getInvoiceId(), root -> root.join(Payment_.invoice, JoinType.LEFT).get(Invoice_.id))
                    );
            }
        }
        return specification;
    }
}
