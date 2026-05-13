package com.example.protaskinxxw.service;

import com.example.protaskinxxw.service.dto.SprintDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.example.protaskinxxw.domain.Sprint}.
 */
public interface SprintService {
    /**
     * Save a sprint.
     *
     * @param sprintDTO the entity to save.
     * @return the persisted entity.
     */
    SprintDTO save(SprintDTO sprintDTO);

    /**
     * Updates a sprint.
     *
     * @param sprintDTO the entity to update.
     * @return the persisted entity.
     */
    SprintDTO update(SprintDTO sprintDTO);

    /**
     * Partially updates a sprint.
     *
     * @param sprintDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SprintDTO> partialUpdate(SprintDTO sprintDTO);

    /**
     * Get all the sprints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SprintDTO> findAll(Pageable pageable);

    /**
     * Get the "id" sprint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SprintDTO> findOne(Long id);

    /**
     * Delete the "id" sprint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
