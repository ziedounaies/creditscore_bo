package com.reactit.credit.score.repository;

import com.reactit.credit.score.domain.MemberUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the MemberUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MemberUserRepository extends JpaRepository<MemberUser, Long>, JpaSpecificationExecutor<MemberUser> {}
