package com.reactit.credit.score.service;

import com.reactit.credit.score.service.dto.ContactDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.reactit.credit.score.domain.Contact}.
 */
public interface ContactService {
    /**
     * Save a contact.
     *
     * @param contactDTO the entity to save.
     * @return the persisted entity.
     */
    ContactDTO save(ContactDTO contactDTO);

    /**
     * Updates a contact.
     *
     * @param contactDTO the entity to update.
     * @return the persisted entity.
     */
    ContactDTO update(ContactDTO contactDTO);

    /**
     * Partially updates a contact.
     *
     * @param contactDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContactDTO> partialUpdate(ContactDTO contactDTO);

    /**
     * Get all the contacts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContactDTO> findAll(Pageable pageable);

    /**
     * Get the "id" contact.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContactDTO> findOne(Long id);

    /**
     * Delete the "id" contact.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
