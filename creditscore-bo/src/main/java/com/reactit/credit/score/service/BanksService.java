package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.BanksDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.Banks}.
 */
public interface BanksService {
    /**
     * Save a banks.
     *
     * @param banksDTO the entity to save.
     * @return the persisted entity.
     */
    BanksDTO save(BanksDTO banksDTO);

    /**
     * Updates a banks.
     *
     * @param banksDTO the entity to update.
     * @return the persisted entity.
     */
    BanksDTO update(BanksDTO banksDTO);

    /**
     * Partially updates a banks.
     *
     * @param banksDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BanksDTO> partialUpdate(BanksDTO banksDTO);

    /**
     * Get all the banks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BanksDTO> findAll(Pageable pageable);

    /**
     * Get the "id" banks.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BanksDTO> findOne(Long id);

    /**
     * Delete the "id" banks.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
