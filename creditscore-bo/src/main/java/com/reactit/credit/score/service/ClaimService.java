package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.ClaimDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.Claim}.
 */
public interface ClaimService {
    /**
     * Save a claim.
     *
     * @param claimDTO the entity to save.
     * @return the persisted entity.
     */
    ClaimDTO save(ClaimDTO claimDTO);

    /**
     * Updates a claim.
     *
     * @param claimDTO the entity to update.
     * @return the persisted entity.
     */
    ClaimDTO update(ClaimDTO claimDTO);

    /**
     * Partially updates a claim.
     *
     * @param claimDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ClaimDTO> partialUpdate(ClaimDTO claimDTO);

    /**
     * Get all the claims.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ClaimDTO> findAll(Pageable pageable);

    /**
     * Get the "id" claim.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClaimDTO> findOne(Long id);

    /**
     * Delete the "id" claim.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
