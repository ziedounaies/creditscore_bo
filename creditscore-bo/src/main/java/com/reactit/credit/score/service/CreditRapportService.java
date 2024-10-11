package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.CreditRapportDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.CreditRapport}.
 */
public interface CreditRapportService {
    /**
     * Save a creditRapport.
     *
     * @param creditRapportDTO the entity to save.
     * @return the persisted entity.
     */
    CreditRapportDTO save(CreditRapportDTO creditRapportDTO);

    /**
     * Updates a creditRapport.
     *
     * @param creditRapportDTO the entity to update.
     * @return the persisted entity.
     */
    CreditRapportDTO update(CreditRapportDTO creditRapportDTO);

    /**
     * Partially updates a creditRapport.
     *
     * @param creditRapportDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreditRapportDTO> partialUpdate(CreditRapportDTO creditRapportDTO);

    /**
     * Get all the creditRapports.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CreditRapportDTO> findAll(Pageable pageable);

    /**
     * Get the "id" creditRapport.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreditRapportDTO> findOne(Long id);

    /**
     * Delete the "id" creditRapport.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
