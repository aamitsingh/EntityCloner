package com.workspan.entity.cloner.service.impl;

import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.*;
import com.workspan.entity.cloner.service.CloneService;
import com.workspan.entity.cloner.service.GraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class CloneServiceImpl implements CloneService {
    private static final String ENTITY_NOT_FOUNT_EXCEPTION = "Entity id %s not found";
    @Autowired
    private GraphService graphService;

    public EntityStream clone(EntityStream entityStream, final Integer entityId) throws EntityNotFoundException {
        final Map<Integer, Entity> entityMap = new HashMap<Integer, Entity>();
        int maxEntityId = 0;
        for (Entity entity : entityStream.getEntities()) {
            entityMap.put(entity.getEntityId(), entity);
            maxEntityId = Math.max(maxEntityId, entity.getEntityId());
        }

        if (!entityMap.containsKey(entityId)) {
            throw new EntityNotFoundException(String.format(ENTITY_NOT_FOUNT_EXCEPTION, entityId));
        }
        GraphResult graphResult = graphService.create(entityId, entityStream);

        Set<Integer> relatedEntities = graphService.getRelatedEntities(graphResult.getEntityGraphMap(), entityId);
        relatedEntities.add(entityId);
        Map<Integer, Integer> clonedEntities = copyEntities(maxEntityId, relatedEntities, entityMap, entityStream);
        for (Integer parent : graphResult.getParents()) {
            entityStream.getLinks().add(new Link(parent, clonedEntities.get(entityId)));
        }

        copyLinks(relatedEntities, entityStream, clonedEntities, graphResult.getEntityGraphMap());

        return entityStream;
    }

    private Map<Integer, Integer> copyEntities(Integer maxEntityId, Set<Integer> relatedEntities, final Map<Integer,
            Entity> entityMap, EntityStream entityStream) {
        int entityIdCounter = maxEntityId + 1;
        Map<Integer, Integer> clonedEntities = new HashMap<>();
        for (Integer relatedEntityId : relatedEntities) {
            Entity currentEntity = entityMap.get(relatedEntityId);
            Entity clonedEntity = new Entity(++entityIdCounter, currentEntity.getName(), currentEntity.getDescription());
            entityStream.getEntities().add(clonedEntity);
            clonedEntities.put(relatedEntityId, clonedEntity.getEntityId());
        }
        return clonedEntities;
    }

    private void copyLinks(Set<Integer> relatedEntities, EntityStream entityStream, Map<Integer, Integer> clonedEntities,
                           Map<Integer, EntityGraph> entityGraphMap) {
        for (Integer relatedEntityId : relatedEntities) {
            EntityGraph entityGraph = entityGraphMap.get(relatedEntityId);
            for (EntityGraph edge : entityGraph.getEdges()) {
                entityStream.getLinks().add(new Link(clonedEntities.get(entityGraph.getId()),
                        clonedEntities.get(edge.getId())));
            }
        }
    }
}
