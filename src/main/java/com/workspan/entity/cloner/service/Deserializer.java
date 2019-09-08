package com.workspan.entity.cloner.service;

import com.workspan.entity.cloner.exception.EntityFormatException;

/**
 * This interface provides deserialization service
 */
public interface Deserializer {
    /**
     * This method deserialize the object from file to the Entity {entityClass}
     * @param location location of the string representation of object
     * @param entityClass object class
     * @param <T> Type of Object
     * @return Deserialized object
     * @throws EntityFormatException
     *  when the format of the object in file is not correct or the file itself is not present
     */
    <T> T deserialize(String location, Class<T> entityClass) throws EntityFormatException;
}
