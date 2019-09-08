package com.workspan.entity.cloner.service;

import com.workspan.entity.cloner.exception.EntityFormatException;

/**
 * This method provides the serialization for given object.
 */
public interface Serializer {
    /**
     * This method serialize the object to the string
     *
     * @param obj object to be serialized
     * @return serialized object
     * @throws EntityFormatException
     *  when The object format is not valid
     * @see com.workspan.entity.cloner.service.impl.SerializerImpl
     */
    String serialize(Object obj) throws EntityFormatException;
}
