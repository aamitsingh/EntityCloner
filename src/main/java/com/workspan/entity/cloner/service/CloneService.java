package com.workspan.entity.cloner.service;

import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.EntityStream;

/**
 * This service provides the cloning of EntityStream.
 */
public interface CloneService {
    /**
     * This method clones the entity stream for the {entityId} and its children
     *
     * @param entityStream Entity Stream
     * @param entityId entity Id to be cloned
     * @return Entity Stream updated with the clonned entities
     * @throws EntityNotFoundException
     *  when the entity given {entityId} not found in the {entityStream}
     */
    EntityStream clone(EntityStream entityStream, final Integer entityId) throws EntityNotFoundException;
}
