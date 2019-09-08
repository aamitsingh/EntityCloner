package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.exception.EntityFormatException;
import mockit.Deencapsulation;
import mockit.Injectable;
import mockit.StrictExpectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SerializerImplTest {
    private SerializerImpl serializer;

    @Injectable
    private ObjectMapper objectMapper;

    @Injectable
    private JsonProcessingException jsonProcessingException;

    @Before
    public void setUp() {
        serializer = new SerializerImpl();
        Deencapsulation.setField(serializer, objectMapper);
    }

    @Test
    public void testWhenTheFormatOfObjectIsCorrect() throws JsonProcessingException, EntityFormatException {
        Object object = new Object();
        String expectedResult = "{\"id\"=3}";
        new StrictExpectations() {
            {
                objectMapper.writeValueAsString(object);
                result = expectedResult;
            }
        };
        String output = serializer.serialize(object);
        Assert.assertEquals(expectedResult, output);
    }

    @Test(expected = EntityFormatException.class)
    public void testWhenTheFormatOfObjectIsNotValid() throws JsonProcessingException, EntityFormatException {
        Object object = new Object();
        new StrictExpectations() {
            {
                objectMapper.writeValueAsString(object);
                result = jsonProcessingException;
            }
        };
        serializer.serialize(object);
    }
}
