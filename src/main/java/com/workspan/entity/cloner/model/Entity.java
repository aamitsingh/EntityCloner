package com.workspan.entity.cloner.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity {
    @JsonProperty("entity_id")
    private int entityId;

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;

    public Entity() {
    }

    public Entity(int entityId, String name, String description) {
        this.entityId = entityId;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getEntityId() {
        return entityId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return entityId == entity.entityId;
    }

    @Override
    public int hashCode() {
        return entityId;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entityId=" + entityId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
