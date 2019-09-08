package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.exception.EntityFormatException;
import com.workspan.entity.cloner.model.EntityStream;
import mockit.Deencapsulation;
import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class DeserializerImplTest {
    private DeserializerImpl deserializer;

    @Injectable
    private ObjectMapper objectMapper;

    @Injectable
    private IOException ioException;

    @Mocked
    private File file;

    @Before
    public void setUp() {
        deserializer = new DeserializerImpl();
        Deencapsulation.setField(deserializer, objectMapper);
    }

    @Test
    public void testWhenTheFormatOfFileIsCorrect() throws IOException, EntityFormatException {
        String location = "/tmp/file.json";
        EntityStream expectedResult = new EntityStream();
        new StrictExpectations() {
            {
                new File(location);
                result = file;

                objectMapper.readValue(file, EntityStream.class);
                result = expectedResult;
            }
        };
        EntityStream entityStream = deserializer.deserialize(location, EntityStream.class);
        Assert.assertEquals(expectedResult, entityStream);
    }

    @Test(expected = EntityFormatException.class)
    public void testWhenTheFileIsNotPresentOrFormatIsIncorrect() throws IOException, EntityFormatException {
        String location = "/tmp/file.json";
        new StrictExpectations() {
            {
                new File(location);
                result = file;

                objectMapper.readValue(file, EntityStream.class);
                result = ioException;
            }
        };
        deserializer.deserialize(location, EntityStream.class);
    }
}
