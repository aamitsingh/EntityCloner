package com.workspan.entity.cloner.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.workspan.entity.cloner.exception.EntityFormatException;
import com.workspan.entity.cloner.service.Serializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SerializerImpl implements Serializer {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public String serialize(Object obj) throws EntityFormatException {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new EntityFormatException("Error while serialization", e);
        }
    }
}
