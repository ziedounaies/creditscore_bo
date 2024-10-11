package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.AgenciesDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.Agencies}.
 */
public interface AgenciesService {
    /**
     * Save a agencies.
     *
     * @param agenciesDTO the entity to save.
     * @return the persisted entity.
     */
    AgenciesDTO save(AgenciesDTO agenciesDTO);

    /**
     * Updates a agencies.
     *
     * @param agenciesDTO the entity to update.
     * @return the persisted entity.
     */
    AgenciesDTO update(AgenciesDTO agenciesDTO);

    /**
     * Partially updates a agencies.
     *
     * @param agenciesDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AgenciesDTO> partialUpdate(AgenciesDTO agenciesDTO);

    /**
     * Get all the agencies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AgenciesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" agencies.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AgenciesDTO> findOne(Long id);

    /**
     * Delete the "id" agencies.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
