package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.exception.EntityFormatException;
import com.workspan.entity.cloner.service.Deserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class DeserializerImpl implements Deserializer {
    @Autowired
    private ObjectMapper objectMapper;

    public <T> T deserialize(String location, Class<T> entityClass) throws EntityFormatException {
        try {
            return objectMapper.readValue(new File(location), entityClass);
        } catch (IOException e) {
            throw new EntityFormatException("Error while parsing file " + location, e);
        }
    }
}
