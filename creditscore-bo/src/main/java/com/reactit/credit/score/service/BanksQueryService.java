package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.repository.BanksRepository;
import com.reactit.credit.score.service.criteria.BanksCriteria;
import com.reactit.credit.score.service.dto.BanksDTO;
import com.reactit.credit.score.service.mapper.BanksMapper;
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
 * Service for executing complex queries for {@link Banks} entities in the database.
 * The main input is a {@link BanksCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BanksDTO} or a {@link Page} of {@link BanksDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BanksQueryService extends QueryService<Banks> {

    private final Logger log = LoggerFactory.getLogger(BanksQueryService.class);

    private final BanksRepository banksRepository;

    private final BanksMapper banksMapper;

    public BanksQueryService(BanksRepository banksRepository, BanksMapper banksMapper) {
        this.banksRepository = banksRepository;
        this.banksMapper = banksMapper;
    }

    /**
     * Return a {@link List} of {@link BanksDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BanksDTO> findByCriteria(BanksCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Banks> specification = createSpecification(criteria);
        return banksMapper.toDto(banksRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link BanksDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BanksDTO> findByCriteria(BanksCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Banks> specification = createSpecification(criteria);
        return banksRepository.findAll(specification, page).map(banksMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BanksCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Banks> specification = createSpecification(criteria);
        return banksRepository.count(specification);
    }

    /**
     * Function to convert {@link BanksCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Banks> createSpecification(BanksCriteria criteria) {
        Specification<Banks> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Banks_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Banks_.name));
            }
            if (criteria.getFoundedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFoundedDate(), Banks_.foundedDate));
            }
            if (criteria.getBranches() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBranches(), Banks_.branches));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Banks_.enabled));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Banks_.createdAt));
            }
            if (criteria.getContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContactId(), root -> root.join(Banks_.contacts, JoinType.LEFT).get(Contact_.id))
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAddressId(), root -> root.join(Banks_.addresses, JoinType.LEFT).get(Address_.id))
                    );
            }
            if (criteria.getAgenciesId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAgenciesId(), root -> root.join(Banks_.agencies, JoinType.LEFT).get(Agencies_.id))
                    );
            }
        }
        return specification;
    }
}
