package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.exception.EntityNotFoundException;
import com.workspan.entity.cloner.model.EntityGraph;
import com.workspan.entity.cloner.model.EntityStream;
import com.workspan.entity.cloner.model.GraphResult;
import com.workspan.entity.cloner.service.GraphService;
import mockit.Deencapsulation;
import mockit.Injectable;
import mockit.StrictExpectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CloneServiceImplTest {
    private CloneServiceImpl cloneService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Injectable
    private GraphService graphService;

    @Before
    public void setUp() {
        cloneService = new CloneServiceImpl();
        Deencapsulation.setField(cloneService, graphService);
    }

    @Test
    public void testCloneWhenTheInputIsValid() throws IOException, EntityNotFoundException {
        Integer entityId = 5;
        EntityStream entityStream = objectMapper.readValue(new File("src/test/resources/input.json"), EntityStream.class);
        EntityStream expectedOutputStream = objectMapper.readValue(new File("src/test/resources/output.json"), EntityStream.class);

        Set<Integer> parents = new HashSet<>();
        parents.add(3);
        EntityGraph entityGraph3 = new EntityGraph(3);
        EntityGraph entityGraph5 = new EntityGraph(5);
        EntityGraph entityGraph7 = new EntityGraph(7);
        EntityGraph entityGraph11 = new EntityGraph(11);

        entityGraph3.getEdges().add(entityGraph5);
        entityGraph3.getEdges().add(entityGraph7);
        entityGraph5.getEdges().add(entityGraph7);
        entityGraph7.getEdges().add(entityGraph11);
        Map<Integer, EntityGraph> entityGraphMap = new HashMap<>();
        entityGraphMap.put(3, entityGraph3);
        entityGraphMap.put(5, entityGraph5);
        entityGraphMap.put(7, entityGraph7);
        entityGraphMap.put(11, entityGraph11);
        GraphResult graphResult = new GraphResult(entityGraphMap, parents);

        Set<Integer> relatedEntities = new HashSet<>();
        relatedEntities.add(7);
        relatedEntities.add(11);
        new StrictExpectations() {
            {
                graphService.create(entityId, entityStream);
                result = graphResult;

                graphService.getRelatedEntities(entityGraphMap, entityId);
                result = relatedEntities;
            }
        };
        EntityStream output = cloneService.clone(entityStream, entityId);
        Assert.assertEquals(expectedOutputStream, output);
    }

    @Test(expected = EntityNotFoundException.class)
    public void testCloneWhenTheEntityIsNotPresentInInput() throws IOException, EntityNotFoundException {
        Integer entityId = 14;
        EntityStream entityStream = objectMapper.readValue(new File("src/test/resources/input.json"), EntityStream.class);

        new StrictExpectations() {
            {
            }
        };
        cloneService.clone(entityStream, entityId);
    }
}
