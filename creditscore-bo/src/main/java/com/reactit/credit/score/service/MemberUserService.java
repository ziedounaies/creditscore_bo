package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.MemberUserDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.MemberUser}.
 */
public interface MemberUserService {
    /**
     * Save a memberUser.
     *
     * @param memberUserDTO the entity to save.
     * @return the persisted entity.
     */
    MemberUserDTO save(MemberUserDTO memberUserDTO);

    /**
     * Updates a memberUser.
     *
     * @param memberUserDTO the entity to update.
     * @return the persisted entity.
     */
    MemberUserDTO update(MemberUserDTO memberUserDTO);

    /**
     * Partially updates a memberUser.
     *
     * @param memberUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MemberUserDTO> partialUpdate(MemberUserDTO memberUserDTO);

    /**
     * Get all the memberUsers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MemberUserDTO> findAll(Pageable pageable);

    /**
     * Get all the MemberUserDTO where CreditRapport is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MemberUserDTO> findAllWhereCreditRapportIsNull();
    /**
     * Get all the MemberUserDTO where Invoice is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<MemberUserDTO> findAllWhereInvoiceIsNull();

    /**
     * Get the "id" memberUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MemberUserDTO> findOne(Long id);

    /**
     * Delete the "id" memberUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
