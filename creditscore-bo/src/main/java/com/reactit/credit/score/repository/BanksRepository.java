package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.Banks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Banks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanksRepository extends JpaRepository<Banks, Long>, JpaSpecificationExecutor<Banks> {}
