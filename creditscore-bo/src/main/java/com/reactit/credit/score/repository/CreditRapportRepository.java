package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.CreditRapport;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreditRapport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreditRapportRepository extends JpaRepository<CreditRapport, Long>, JpaSpecificationExecutor<CreditRapport> {}
