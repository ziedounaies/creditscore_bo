package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.Agencies;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Agencies entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgenciesRepository extends JpaRepository<Agencies, Long>, JpaSpecificationExecutor<Agencies> {}
