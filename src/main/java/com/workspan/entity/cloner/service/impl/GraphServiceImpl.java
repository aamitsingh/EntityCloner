package com.workspan.entity.cloner.service.impl;

import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.EntityGraph;
import com.workspan.entity.cloner.model.EntityStream;
import com.workspan.entity.cloner.model.GraphResult;
import com.workspan.entity.cloner.service.GraphService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the {@link GraphService}
 */
@Service
public class GraphServiceImpl implements GraphService {
    private static final String ENTITY_NOT_FOUND = "Entity %s not found in links";

    @Override
    public GraphResult create(Integer entityId, EntityStream entityStream) {
        Map<Integer, EntityGraph> entityGraphMap = new HashMap<>();
        Set<Integer> parents = new HashSet<>();
        entityStream.getLinks().stream().forEach(link -> {
            if (!entityGraphMap.containsKey(link.getFrom())) {
                entityGraphMap.put(link.getFrom(), new EntityGraph(link.getFrom()));
            }

            if (!entityGraphMap.containsKey(link.getTo())) {
                entityGraphMap.put(link.getTo(), new EntityGraph(link.getTo()));
            }
            EntityGraph graph = entityGraphMap.get(link.getFrom());
            EntityGraph edge = entityGraphMap.get(link.getTo());
            if (entityId == link.getTo()) {
                parents.add(link.getFrom());
            }
            graph.getEdges().add(edge);
        });
        return new GraphResult(entityGraphMap, parents);
    }

    public Set<Integer> getRelatedEntities(Map<Integer, EntityGraph> entityGraphMap, Integer entityId) throws EntityNotFoundException {
        if (!entityGraphMap.containsKey(entityId)) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUND, entityId));
        }
        Set<Integer> entities = new HashSet<>();
        EntityGraph entityGraph = entityGraphMap.get(entityId);
        findEntities(entityGraph, entities);
        return entities;
    }

    private void findEntities(EntityGraph entityGraph, Set<Integer> entities) {
        for (EntityGraph entityEdge : entityGraph.getEdges()) {
            if (!entities.contains(entityEdge.getId())) {
                entities.add(entityEdge.getId());
                findEntities(entityEdge, entities);
            }

        }
    }
}
