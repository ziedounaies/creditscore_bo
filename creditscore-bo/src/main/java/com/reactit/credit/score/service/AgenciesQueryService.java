package com.reactit.credit.score.service;

import com.reactit.credit.score.domain.*; // for static metamodels
import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.repository.AgenciesRepository;
import com.reactit.credit.score.service.criteria.AgenciesCriteria;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.mapper.AgenciesMapper;
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
 * Service for executing complex queries for {@link Agencies} entities in the database.
 * The main input is a {@link AgenciesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AgenciesDTO} or a {@link Page} of {@link AgenciesDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AgenciesQueryService extends QueryService<Agencies> {

    private final Logger log = LoggerFactory.getLogger(AgenciesQueryService.class);

    private final AgenciesRepository agenciesRepository;

    private final AgenciesMapper agenciesMapper;

    public AgenciesQueryService(AgenciesRepository agenciesRepository, AgenciesMapper agenciesMapper) {
        this.agenciesRepository = agenciesRepository;
        this.agenciesMapper = agenciesMapper;
    }

    /**
     * Return a {@link List} of {@link AgenciesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AgenciesDTO> findByCriteria(AgenciesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Agencies> specification = createSpecification(criteria);
        return agenciesMapper.toDto(agenciesRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AgenciesDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AgenciesDTO> findByCriteria(AgenciesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Agencies> specification = createSpecification(criteria);
        return agenciesRepository.findAll(specification, page).map(agenciesMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AgenciesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Agencies> specification = createSpecification(criteria);
        return agenciesRepository.count(specification);
    }

    /**
     * Function to convert {@link AgenciesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Agencies> createSpecification(AgenciesCriteria criteria) {
        Specification<Agencies> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Agencies_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Agencies_.name));
            }
            if (criteria.getDatefounded() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDatefounded(), Agencies_.datefounded));
            }
            if (criteria.getEnabled() != null) {
                specification = specification.and(buildSpecification(criteria.getEnabled(), Agencies_.enabled));
            }
            if (criteria.getCreatedAt() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedAt(), Agencies_.createdAt));
            }
            if (criteria.getBanksId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getBanksId(), root -> root.join(Agencies_.banks, JoinType.LEFT).get(Banks_.id))
                    );
            }
            if (criteria.getContactId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getContactId(), root -> root.join(Agencies_.contacts, JoinType.LEFT).get(Contact_.id))
                    );
            }
            if (criteria.getAddressId() != null) {
                specification =
                    specification.and(
                        buildSpecification(criteria.getAddressId(), root -> root.join(Agencies_.addresses, JoinType.LEFT).get(Address_.id))
                    );
            }
        }
        return specification;
    }
}
