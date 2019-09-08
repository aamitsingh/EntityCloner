package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.model.EntityGraph;
import com.workspan.entity.cloner.model.EntityStream;
import com.workspan.entity.cloner.model.GraphResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GraphServiceImplTest {
    private GraphServiceImpl graphService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp() {
        graphService = new GraphServiceImpl();
    }

    @Test
    public void testCreateWhenThereIsNoLoop() throws IOException {
        Integer entityId = 5;
        EntityStream entityStream = objectMapper.readValue(new File("src/test/resources/input.json"), EntityStream.class);

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

        GraphResult output = graphService.create(entityId, entityStream);
        Assert.assertEquals(graphResult, output);
    }

    @Test
    public void testCreateWhenThereIsALoop() throws IOException {
        Integer entityId = 5;
        EntityStream entityStream = objectMapper.readValue(new File("src/test/resources/input-with-loop.json"), EntityStream.class);

        Set<Integer> parents = new HashSet<>();
        parents.add(3);
        EntityGraph entityGraph3 = new EntityGraph(3);
        EntityGraph entityGraph5 = new EntityGraph(5);
        EntityGraph entityGraph7 = new EntityGraph(7);

        entityGraph3.getEdges().add(entityGraph5);
        entityGraph5.getEdges().add(entityGraph3);
        entityGraph5.getEdges().add(entityGraph7);
        Map<Integer, EntityGraph> entityGraphMap = new HashMap<>();
        entityGraphMap.put(3, entityGraph3);
        entityGraphMap.put(5, entityGraph5);
        entityGraphMap.put(7, entityGraph7);
        GraphResult graphResult = new GraphResult(entityGraphMap, parents);

        GraphResult output = graphService.create(entityId, entityStream);
        Assert.assertEquals(graphResult, output);
    }
}
