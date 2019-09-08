package com.workspan.entity.cloner.service;

import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.EntityGraph;
import com.workspan.entity.cloner.model.EntityStream;
import com.workspan.entity.cloner.model.GraphResult;

import java.util.Map;
import java.util.Set;

/**
 * This Interface provides methods to create entity graph and its relations.
 *
 */
public interface GraphService {
    /**
     * This method creates the graph of entities and its edges for the given {entityStream}
     * @param entityId entity identifier
     * @param entityStream entity stream
     * @return Graph containing entity and its links
     */
    GraphResult create(Integer entityId, EntityStream entityStream);

    /**
     * This method provides all the entities linked to given {entityId}
     *
     * @param entityGraphMap entity graph map
     * @param entityId entity identifier
     * @return all child entities for the give {entityId}
     * @throws EntityNotFoundException
     *  when the entity is not present for the given {entityId}
     */
    Set<Integer> getRelatedEntities(Map<Integer, EntityGraph> entityGraphMap, Integer entityId) throws EntityNotFoundException;
}
